package com.sun.modules.crawl.parser.impl;

import com.fasterxml.jackson.core.type.TypeReference;
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

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<UserPO> saveUserAndRelation(List<VideoPO> videoPOList) throws IOException {
        AbstractApplicationContext ctx
                = new ClassPathXmlApplicationContext(new String[]{"spring-mybatis.xml"});
        IUserDAO userDAO = (IUserDAO) ctx.getBean("userDAO");
        IRelationDAO relationDAO = (IRelationDAO) ctx.getBean("relationDAO");

        List<UserPO> userPOList;
        List<RelationPO> relationPOList;
        List<String> userNameList;

        Connection con = Jsoup.connect(COMMENT_ID_URL).timeout(5000);
        VideoCommentId videoCommentId;

        String baseUrl = "https://coral.qq.com/article/";
        for (VideoPO item : videoPOList) {
            userPOList = new ArrayList<>();
            relationPOList = new ArrayList<>();
            userNameList = userDAO.getAllName();

            System.out.println(item.getUrl());
            Document doc = con.data("cid", item.getCid())
                    .ignoreContentType(true).get();
            String pageStr = doc.toString();
            videoCommentId = getVideoCommentIdContent(pageStr);

            if (!"Success!".equals(videoCommentId.getResult().getMsg())) {
                System.out.println("请求video_comment_id时出错");
                continue;
            }
            String url = setCommentDetailUrl(baseUrl, videoCommentId.getComment_id());
            Connection getDateCon = Jsoup.connect(url).timeout(5000);
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
//            FileUtil.writeFileInBatch(comments, "./data/comments.txt");
            if (!CollectionUtils.isEmpty(userPOList)) {
                userDAO.insertUserInfo(userPOList);
            }
            if (!CollectionUtils.isEmpty(relationPOList)) {
                relationDAO.insertRecord(relationPOList);
            }
        }

        return null;
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
            getRelationInfo(item, relation, cid);
//            comments.add(item.getContent() + "\r\n=====================\r\n");
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
            if (user.size() < 10000) {
                user.add(userPO);
            }
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
        relationPO.setUserName(name);
        relationPO.setCid(cid);
        relationPO.setComment(content);
        relationPO.setAddTime(new Date());
        if (relation.size() < 10000) {
            relation.add(relationPO);
        }
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
}
