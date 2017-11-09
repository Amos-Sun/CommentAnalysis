package com.sun.moudles.crawl.parser.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mysql.jdbc.StringUtils;
import com.sun.moudles.bean.domain.UserDO;
import com.sun.moudles.bean.domain.VideoDO;
import com.sun.moudles.bean.json.CommentDetail;
import com.sun.moudles.bean.json.DataDetail;
import com.sun.moudles.bean.json.VideoCommentId;
import com.sun.moudles.crawl.parser.IGetUserInfo;
import com.sun.moudles.util.FileUtil;
import com.sun.moudles.util.JsonUtil;
import com.sun.moudles.util.StrUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.soap.Detail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public class GetUserDetail implements IGetUserInfo {

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

    public List<UserDO> getUserInfo(List<VideoDO> videoDOList) throws IOException {

        Connection con = Jsoup.connect(COMMENT_ID_URL).timeout(5000);
        VideoCommentId videoCommentId;
        int i = 0;
        for (VideoDO item : videoDOList) {
            if (i != 2) {
                i++;
                continue;
            }
            System.out.println(item.getVideoUrl());
            getCid(item);
            Document doc = con.data("cid", item.getCid())
                    .ignoreContentType(true).get();
            String pageStr = doc.toString();
            videoCommentId = getVideoCommentIdContent(pageStr);

            if (!"Success!".equals(videoCommentId.getResult().getMsg())) {
                throw new Error("请求video_comment_id时出错");
            }
            setCommentDetailUrl(videoCommentId.getComment_id());
            //Connection detailCon = Jsoup.connect(COMMENT_DETAIL_URL).timeout(5000);
            String last = "0";
            int numbers = 0;
            List<String> comments = new ArrayList<String>();
            while (true) {
                Document detailDoc = Jsoup.connect(COMMENT_DETAIL_URL).timeout(5000)
                        .data("commentid", last)
                        .data("reqnum", "50").get();
                String detailStr = detailDoc.toString();
                DataDetail commentData = getCommentDetail(detailStr);

                DataDetail.Data data = commentData.getData();
                //获取评论
                getAllComments(data, comments);
                numbers += data.getRetnum();
                last = data.getLast();
                if (numbers >= data.getTotal()) {
                    break;
                }
            }
            FileUtil.writeFileInBatch(comments, "./data/comments.txt");
            break;
        }
        return null;
    }

    /**
     * 获取所有的评论
     *
     * @param data
     * @param comments
     */
    private void getAllComments(DataDetail.Data data, List<String> comments) {
        List<CommentDetail> commentDetail = data.getCommentid();
        for (CommentDetail item : commentDetail) {
            comments.add(item.getContent() + "\r\n=====================\r\n");
        }
    }

    /**
     * 获取cid
     *
     * @param videoDO
     */
    private void getCid(VideoDO videoDO) {
        //https://v.qq.com/x/cover/bt0g0evoxcqxz8d.html
        String[] info = videoDO.getVideoUrl().split("/");
        String[] cid = info[info.length - 1].split("\\.");//正则的时候注意 \\
        if (0 == cid.length) {
            throw new Error("没有cid");
        }
        videoDO.setCid(cid[0]);
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

    private void setCommentDetailUrl(String commentId) {
        COMMENT_DETAIL_URL = COMMENT_DETAIL_URL + commentId + "/comment?";
    }
}
