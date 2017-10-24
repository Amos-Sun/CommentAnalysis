package com.sun.moudles.crawl.parser.impl;

import com.sun.moudles.bean.domain.UserDO;
import com.sun.moudles.bean.domain.VideoDO;
import com.sun.moudles.crawl.parser.IGetUserInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public class GetUserDetail implements IGetUserInfo {

    public List<UserDO> getUserInfo(List<VideoDO> videoDOList) throws IOException {

        String infoUrl = "";
        for (VideoDO item : videoDOList) {
            infoUrl = item.getVideoUrl();
            Document doc = Jsoup.connect(infoUrl).timeout(5000).get();
            getAllComments(doc);

            System.out.println("video detail url ：" + infoUrl);
            break;
        }
        return null;
    }

    private void getAllComments(Document doc) {
        Document leftContent = Jsoup.parse(doc.select("div#leftdown_content").toString());
        Elements elements = leftContent.select(".mod_row_box");

        for (Element item : elements) {
            if(item.text().contains("乐高幻影忍者大电影的影评")){
                System.out.println(item.toString());
                System.out.println(item.getElementById("iframe#commentIframe").toString());
            }

            if (0 != item.select("iframe").size()) {
                System.out.println("iframe");
                System.out.print(item.select("iframe").attr("src"));
            }
        }

       /* String iframeText = doc.select("iframe#commentIframe").first().text();
        Document ifmDoc = Jsoup.parseBodyFragment(iframeText); // 将html字符串转成Document对象
        String ibody = ifmDoc.select("body").text();
        System.out.println(ibody);
        //post-list np-comment-list
        System.out.println("comments list :");*/
    }
}
