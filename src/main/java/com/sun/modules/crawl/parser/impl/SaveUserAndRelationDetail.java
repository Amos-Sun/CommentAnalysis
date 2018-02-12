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
import com.sun.modules.util.JsonUtil;
import com.sun.modules.util.StrUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
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
    private String COMMENT_DETAIL_URL = "https://coral.qq.com/article/";

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
            VideoCommentId videoCommentId;

            String baseUrl = "https://coral.qq.com/article/";
            userNameList = userDAO.getAllName();
            for (int i = 0; i < videoPOList.size(); ) {
                latch = new CountDownLatch(10);
                userPOList = new ArrayList<>();
                relationPOList = new ArrayList<>();

                for (int j = 0; j < 10; j++) {
                    System.out.println("开始爬取第 " + (i + j) + " 个电影");
                    System.out.println(videoPOList.get(i + j).getUrl());
                    Document doc;
                    try {
                        doc = Jsoup.connect(COMMENT_ID_URL)
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                                .timeout(5000)
                                .data("cid", videoPOList.get(i + j).getCid())
                                .ignoreContentType(true).get();
                    } catch (Exception e) {
                        System.out.println("get cid error " + e.getMessage());
                        latch.countDown();
                        continue;
                    }
                    String pageStr = doc.toString();
                    videoCommentId = getVideoCommentIdContent(pageStr);

                    if (!"Success!".equals(videoCommentId.getResult().getMsg())) {
                        System.out.println("请求video_comment_id时出错");
                        latch.countDown();
                        continue;
                    }
                    String url = setCommentDetailUrl(baseUrl, videoCommentId.getComment_id());

                    exec.execute(new ThreadForGetUR(url, userPOList, relationPOList,
                            userNameList, videoPOList.get(i + j), relationDAO));
                }
                try {
                    latch.await();
                    Thread.sleep(2000);
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

    /**
     * 对数据库中的userName进行转码
     */
    private void userNameDecode() {
        for (int i = 0; i < userNameList.size(); i++) {
            userNameList.set(i, URLDecoder.decode(userNameList.get(i)));
        }
    }

    /**
     * 向user表中插入数据
     *
     * @param userDAO
     * @param userPOList
     */
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
                try {
                    userDAO.insertUserInfo(insertPO);
                } catch (Exception e) {
                    System.out.println("insert user error " + e.getMessage());
                } finally {
                    insertPO = new ArrayList<>();
                    num = 0;
                }
            }
        }
        if (num != 0) {
            try {
                userDAO.insertUserInfo(insertPO);
            } catch (Exception e) {
                System.out.println("insert user error " + e.getMessage());
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
                try {
                    relationDAO.insertRecord(insertPO);
                } catch (Exception e) {
                    System.out.println("insert relation error " + e.getMessage());
                } finally {
                    insertPO = new ArrayList<>();
                    num = 0;
                }
            }
        }
        if (num != 0) {
            try {
                relationDAO.insertRecord(insertPO);
            } catch (Exception e) {
                System.out.println("insert relation error " + e.getMessage());
            }
        }
    }

    public class ThreadForGetUR implements Runnable {

        private String url;
        private List<UserPO> userPOList;
        private List<RelationPO> relationPOList;
        private List<String> userNameList;
        private VideoPO item;
        private IRelationDAO relationDAO;

        ThreadForGetUR(String url, List<UserPO> userPOList, List<RelationPO> relationPOList,
                       List<String> userNameList, VideoPO videoPO, IRelationDAO relationDAO) {
            this.url = url;
            this.userNameList = userNameList;
            this.userPOList = userPOList;
            this.relationPOList = relationPOList;
            this.item = videoPO;
            this.relationDAO = relationDAO;
        }

        @Override
        public void run() {
            try {
                String last = "0";
                int numbers = 0;
                Connection getDataConn = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                        .timeout(5000);
                Connection tempConn = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                        .timeout(5000);
                while (true) {
                    getDataConn = tempConn;
                    //保持url的纯洁性
                    Document detailDoc = getDataConn.data("commentid", last)
                            .data("reqnum", "50").get();
                    String detailStr = detailDoc.toString();
                    DataDetail commentData = getCommentDetail(detailStr);
                    if (null == commentData) {
                        last = new BigInteger(last).add(new BigInteger(50 + "")).toString();
                        continue;
                    }

                    DataDetail.Data data = commentData.getData();
                    //获取评论
                    getUserAndRelation(data, relationDAO, userPOList, relationPOList, userNameList, item.getCid());
                    numbers += data.getRetnum();
                    last = data.getLast();
                    if ("false".equals(last)) {
                        break;
                    }
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
     * @param relationDAO
     * @param relation
     * @param user
     */
    private void getUserAndRelation(DataDetail.Data data, IRelationDAO relationDAO,
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
            getRelationInfo(relationDAO, item, relation, cid);
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
        if (nameList.contains(name)) {
            return;
        }
        userPO.setName(name);
        userPO.setAddTime(new Date());
        userPO.setSex(SexEnum.getByValue(item.getUserinfo().getGender()).getDesc());
        user.add(userPO);
        nameList.add(name);
    }

    /**
     * 获取评论信息
     *
     * @param item     每一条评论
     * @param relation relationPOList
     * @param cid      电影的cid
     */
    private void getRelationInfo(IRelationDAO relationDAO, CommentDetail item, List<RelationPO> relation, String cid) {

        String comment = relationDAO.getCommentByCidAndUserNaem(cid, item.getUserinfo().getNick());
        if (comment != null) {
            return;
        }
        RelationPO relationPO = new RelationPO();
        String name = item.getUserinfo().getNick();
        String content = item.getContent();
        relationPO.setUserName(name);
        relationPO.setCid(cid);
        relationPO.setComment(content);
        relationPO.setAddTime(new Date());
        relation.add(relationPO);
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
        String result = StrUtil.handleMarks(res);
        try {
//            FileUtil.writeFile(res, "json.txt");
//            FileUtil.writeFile(result, "json1.txt");
        } catch (Exception e) {
            System.out.println("write file error " + e.getMessage());
        }
        DataDetail dataDetail = JsonUtil.fromJson(result, new TypeReference<DataDetail>() {
        });
        return dataDetail;
    }

    private String setCommentDetailUrl(String baseUrl, String commentId) {
        return (baseUrl + commentId + "/comment?");
    }
}
