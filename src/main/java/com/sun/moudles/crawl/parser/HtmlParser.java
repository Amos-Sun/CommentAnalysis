package com.sun.moudles.crawl.parser;

import com.google.common.base.Joiner;
import com.sun.moudles.crawl.domain.VideoDO;
import com.sun.moudles.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SunGuiyong on 2017/9/26.
 */
public class HtmlParser {

//        全部
//        最新上架
    /**
     * actors  type  detail
     * get from video url
     */

    /**
     * 这个界面中可以得到url name picture
     */
    private final String url = "http://v.qq.com/x/list/movie?area=-1&sort=19&offset=0";

    private String BASE_URL = "http://v.qq.com/x/list/movie?area=-1&sort=19&offset=";
    //存放整理好的video数据
    private List<VideoDO> videoDoList = new ArrayList<VideoDO>();

    private List<String> willCrwalUrl = new ArrayList<String>();

    public void getPageContent() throws IOException {
        initCrawlList();
        for (int i = 0; i < willCrwalUrl.size(); i++) {
            Document doc = Jsoup.connect(willCrwalUrl.get(i)).get();

            //figures_list
            Document ulDoc = getChileDocument(doc, "figures_list");
            Elements liContainer = ulDoc.select(".list_item");

            VideoDO videoDO = new VideoDO();
            for (Element liItem : liContainer) {
                Document liDoc = Jsoup.parse(liItem.toString());
                Elements title = liDoc.select("strong a");

                videoDO.setVideoUrl(title.attr("href"));
                videoDO.setVideoName(title.text());

                title = liDoc.select("a img");
                //图片地址 r-lazyload
                videoDO.setVideoPicture(title.attr("r-lazyload"));

                Document detailDoc = Jsoup.connect(videoDO.getVideoUrl()).get();

                getTypeAndTime(detailDoc, videoDO);
                getActors(detailDoc, videoDO);
                getDetail(detailDoc, videoDO);

                videoDoList.add(videoDO);
            }
            System.out.println(videoDoList.size());
        }
    }

    /**
     * 初始化待爬取的电影页面
     * 因为腾讯视频的页面是有特点的，所以直接确定出来url
     */
    private void initCrawlList() {
        int count = 0;
        for (int i = 1; i < 166; i++) {
            willCrwalUrl.add(BASE_URL + count);
            count += 30;
        }
        willCrwalUrl.add(BASE_URL + count);
    }

    /**
     * 获取电影的详细信息
     * type and time
     *
     * @param videoDO
     * @param doc     connect utl get document
     */
    private void getTypeAndTime(Document doc, VideoDO videoDO) throws IOException {

        //video_tags _video_tags
        Document infoDoc = getChileDocument(doc, "video_info");

        //video_tags
        Document tagsDoc = getChileDocument(infoDoc, "video_tags");

        Elements tagElements = tagsDoc.getElementsByTag("a");
        List<String> tagList = new ArrayList<String>();
        String str = null;
        for (Element item : tagElements) {
            str = item.text().trim();
            if (StrUtil.judgeIsNum(str)) {
                videoDO.setVideoTime(str);
            } else {
                tagList.add(item.text().trim());
            }
        }
        videoDO.setVideoType(Joiner.on(",").skipNulls().join(tagList));
    }

    /**
     * 获取演员列表
     *
     * @param doc
     * @param videoDO
     */
    private void getActors(Document doc, VideoDO videoDO) {

        //mod_bd
        Document infoDoc = getChileDocument(doc, "mod_bd");

        //director
        Document tagsDoc = getChileDocument(infoDoc, "director");

        Elements actors = tagsDoc.getElementsByTag("a");
        List<String> actorsList = new ArrayList<String>();
        String actor = "";
        for (Element item : actors) {
            actor = item.text().trim();
            actorsList.add(actor);
        }
        videoDO.setVideoType(Joiner.on(",").skipNulls().join(actorsList));
    }

    /**
     * 获取演员列表
     *
     * @param doc
     * @param videoDO
     */
    private void getDetail(Document doc, VideoDO videoDO) {

        //video_summary open
        Document infoDoc = getChileDocument(doc, "video_summary");

        Elements detail = infoDoc.select("p");
        videoDO.setVideoDetail(detail.text().trim());
    }

    /**
     * 根据标签名获取html中的内容
     *
     * @param doc 较大div的document
     * @param tag 大div的子集class 或者 tags
     * @return 根据className 或者 tagsName获取到的子集document
     */
    private Document getChileDocument(Document doc, String tag) {
        Elements info = doc.getElementsByClass(tag);
        return Jsoup.parse(info.toString());
    }
}
