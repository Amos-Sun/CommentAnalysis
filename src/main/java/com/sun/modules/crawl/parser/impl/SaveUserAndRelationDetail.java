package com.sun.modules.crawl.parser.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mysql.jdbc.StringUtils;
import com.sun.modules.bean.dao.IRelationDAO;
import com.sun.modules.bean.dao.IUserDAO;
import com.sun.modules.bean.json.CommentDetail;
import com.sun.modules.bean.json.DataDetail;
import com.sun.modules.bean.json.VideoCommentId;
import com.sun.modules.bean.po.RelationPO;
import com.sun.modules.bean.po.UserPO;
import com.sun.modules.bean.po.VideoPO;
import com.sun.modules.constants.SexEnum;
import com.sun.modules.crawl.parser.ISaveUserAndRelationDetail;
import com.sun.modules.util.FileUtil;
import com.sun.modules.util.JsonUtil;
import com.sun.modules.util.StrUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public class SaveUserAndRelationDetail implements ISaveUserAndRelationDetail {

    /**
     * 对应https://ncgi.video.qq.com/fcgi-bin/video_comment_id?otype=json&op=3&cid=
     */
    private String COMMENT_ID_URL = "https://ncgi.video.qq.com/fcgi-bin/video_comment_id?otype=json&op=3";

    /**
     * 对应comment详细信息的url
     * https://coral.qq.com/article/1551278768/comment?commentid=6332862978740201115&reqnum=50
     * 1551278768/comment?commentid=6332862978740201115&reqnum=50
     */
    /*private String COMMENT_DETAIL_URL = "https://coral.qq.com/article/";*/

    private ExecutorService exec = Executors.newFixedThreadPool(15);
    private CountDownLatch latch;

    private volatile List<String> userNameList;

    public List<UserPO> saveUserAndRelation(List<VideoPO> videoPOList) {
        AbstractApplicationContext ctx
                = new ClassPathXmlApplicationContext(new String[]{"spring-mybatis.xml"});
        final IUserDAO userDAO = (IUserDAO) ctx.getBean("userDAO");
        final IRelationDAO relationDAO = (IRelationDAO) ctx.getBean("relationDAO");

        List<UserPO> userPOList;
        List<RelationPO> relationPOList;

        try {
            Connection con = Jsoup.connect(COMMENT_ID_URL).timeout(5000);
            VideoCommentId videoCommentId;

            String baseUrl = "https://coral.qq.com/article/";
            for (int i = 0; i < videoPOList.size(); ) {
                latch = new CountDownLatch(10);
                userPOList = new ArrayList<>();
                relationPOList = new ArrayList<>();
                userNameList = userDAO.getAllName();

                System.out.println(videoPOList.get(i).getUrl());
                Document doc = con.data("cid", videoPOList.get(i).getCid())
                        .ignoreContentType(true).get();
                String pageStr = doc.toString();
                videoCommentId = getVideoCommentIdContent(pageStr);

                if (!"Success!".equals(videoCommentId.getResult().getMsg())) {
                    System.out.println("请求video_comment_id时出错");
                    continue;
                }
                String url = setCommentDetailUrl(baseUrl, videoCommentId.getComment_id());

                for (int j = 0; j < 10; j++) {
                    exec.execute(new ThreadForGetUR(url, userPOList, relationPOList, userNameList, videoPOList.get(i + j)));
                }
                try {
                    latch.await();
                } catch (Exception e) {
                    System.out.println("latch.await error " + e.getMessage());
                }
                i = i + 10;
                if (!CollectionUtils.isEmpty(userPOList)) {
                    insertUser(userDAO, userPOList);
                }
                if (!CollectionUtils.isEmpty(relationPOList)) {
                    insertRelations(relationDAO, relationPOList);
                }
            }
        } catch (Exception e) {
            System.out.println("获取用户信息时出错 message={}" + e.getMessage());
        } finally {
            exec.shutdown();
        }
        return null;
    }

    private void insertUser(IUserDAO userDAO, List<UserPO> userPOList) {
        List<UserPO> insertPO = new ArrayList<>();
        int num = 0;
        for (UserPO item : userPOList) {
            if (null == item) {
                continue;
            }
            insertPO.add(item);
            num++;
            if (num == 1000) {
                userDAO.insertUserInfo(insertPO);
                insertPO = new ArrayList<>();
                num = 0;
            }
        }
    }

    private void insertRelations(IRelationDAO relationDAO, List<RelationPO> relationPOList) {
        List<RelationPO> insertPO = new ArrayList<>();
        int num = 0;
        for (RelationPO item : relationPOList) {
            if (null == item) {
                continue;
            }
            insertPO.add(item);
            num++;
            if (num == 1000) {
                relationDAO.insertRecord(insertPO);
                insertPO = new ArrayList<>();
                num = 0;
            }
        }
    }

    public class ThreadForGetUR implements Runnable {

        private String url;
        private List<UserPO> userPOList;
        private List<RelationPO> relationPOList;
        private List<String> userNameList;
        private VideoPO item;

        ThreadForGetUR(String url, List<UserPO> userPOList, List<RelationPO> relationPOList,
                       List<String> userNameList, VideoPO videoPO) {
            this.url = url;
            this.userNameList = userNameList;
            this.userPOList = userPOList;
            this.relationPOList = relationPOList;
            this.item = videoPO;
        }

        @Override
        public void run() {
            try {
                Connection getDateCon = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                        .timeout(5000);
                String last = "0";
                int numbers = 0;
                List<String> comments = new ArrayList<>();
                while (true) {
                    Document detailDoc = getDateCon.data("commentid", last)
                            .data("reqnum", "50").get();
                    String detailStr = detailDoc.toString();
                    DataDetail commentData = getCommentDetail(detailStr);
                    if (null == commentData) {
                        last = last + 50;
                        continue;
                    }

                    DataDetail.Data data = commentData.getData();
                    //获取评论
                    getUserAndRelation(data, comments, userPOList, relationPOList, userNameList, item.getCid());
                    numbers += data.getRetnum();
                    last = data.getLast();
                    if (numbers >= data.getTotal()) {
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("thread error when get user and relation " + e.getMessage());
            } finally {
                latch.countDown();
            }
        }
    }

    /**
     * 获取所有的评论
     *
     * @param data
     * @param comments
     * @param relation
     * @param user
     */
    private void getUserAndRelation(DataDetail.Data data, List<String> comments,
                                    List<UserPO> user, List<RelationPO> relation, List<String> nameList, String cid) {
        List<CommentDetail> commentDetail = data.getCommentid();
        for (CommentDetail item : commentDetail) {
            try {
                item.getUserinfo().setNick(URLEncoder.encode(item.getUserinfo().getNick(), "utf-8"));
                item.setContent(URLEncoder.encode(item.getContent(), "utf-8"));
            } catch (Exception e) {
                System.out.println("字符编码出错：" + e.getMessage());
                continue;
            }

            getUserInfo(item, user, nameList);
            if (StringUtils.isNullOrEmpty(cid)) {
                continue;
            }
            getRelationInfo(item, relation, cid);
        }
    }

    /**
     * 根据评论获取用户信息
     *
     * @param item     每一天评论
     * @param user     userPOLIst
     * @param nameList 已存的用户名字
     */
    private void getUserInfo(CommentDetail item, List<UserPO> user, List<String> nameList) {

        UserPO userPO = new UserPO();
        String name = item.getUserinfo().getNick();
        if (!nameList.contains(name)) {
            userPO.setName(name);
            userPO.setAddTime(new Date());
            userPO.setSex(SexEnum.getByValue(item.getUserinfo().getGender()).getDesc());
            user.add(userPO);
            nameList.add(name);
        }
    }


    /**
     * 获取评论信息
     *
     * @param item     每一条评论
     * @param relation relationPOList
     * @param cid      电影的cid
     */
    private void getRelationInfo(CommentDetail item, List<RelationPO> relation, String cid) {

        RelationPO relationPO = new RelationPO();
        String name = item.getUserinfo().getNick();
        String content = item.getContent();
        int mention = analysisMention(content);
        relationPO.setEvaluation(mention);
        relationPO.setUserName(name);
        relationPO.setCid(cid);
        relationPO.setComment(content);
        relationPO.setAddTime(new Date());
        relation.add(relationPO);
    }

    /**
     * 调用python进行情感分析
     *
     * @param str 要分析的句子
     * @return
     */
    private int analysisMention(String str) {
        try {
//            D:\self\CommentAnalysis\src\main\java\com\sun\modules\analysis\analysis.py
//            analysis/analysis.py  ---新增一个module可以用相对路径
//            analysis.py
            Process pr = Runtime.getRuntime().exec("python analysis/analysis.py " + str);
//            Process pr = Runtime.getRuntime().exec("python D:\\self\\CommentAnalysis\\src\\main\\java\\com\\sun\\modules\\analysis\\analysis.py " + str);
            pr.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            LineNumberReader input = new LineNumberReader(in);
            line = input.readLine();

            in.close();
            System.out.println("result " + line);
            return Integer.valueOf(line);
        } catch (Exception e) {
            System.out.println("error occur when use python " + e.getMessage());
        }
        return 0;
    }

    /**
     * 获取commentid 中targetid
     *
     * @param pageStr video_commentid 中的内容
     * @return
     */
    private VideoCommentId getVideoCommentIdContent(String pageStr) {
        String getStr = StrUtil.getAimedString(pageStr, "QZOutputJson=", "</body>");
        String res = StrUtil.replaceString(getStr, "&quot;", "\"");
        VideoCommentId videoCommentId = JsonUtil.fromJson(res, new TypeReference<VideoCommentId>() {
        });
        return videoCommentId;
    }

    /**
     * 获取评论的详细信息
     *
     * @param detail
     * @return
     */
    private DataDetail getCommentDetail(String detail) {
        String getStr = StrUtil.getAimedString(detail, "<body>", "</body>");
        String res = StrUtil.replaceString(getStr, "&quot;", "\"");
        DataDetail dataDetail = JsonUtil.fromJson(res, new TypeReference<DataDetail>() {
        });
        return dataDetail;
    }

    private String setCommentDetailUrl(String baseUrl, String commentId) {
        return (baseUrl + commentId + "/comment?");
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        File file = new File("./data/train/positive");
        File[] files = file.listFiles();
        for (File item : files) {
            String content = FileUtil.readFileAllContents(item.getPath());
            int mention = new SaveUserAndRelationDetail().analysisMention(URLDecoder.decode(content, "utf-8"));
            System.out.println(mention);
        }
        long end = System.currentTimeMillis();
        System.out.println("运行时间是： " + (start - end));
    }
}
