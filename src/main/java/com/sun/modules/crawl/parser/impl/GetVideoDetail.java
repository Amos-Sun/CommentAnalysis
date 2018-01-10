package com.sun.modules.crawl.parser.impl;

import com.google.common.base.Joiner;
import com.sun.modules.bean.dao.IVideoDAO;
import com.sun.modules.bean.po.VideoPO;
import com.sun.modules.crawl.parser.IGetVideoDetail;
import com.sun.modules.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SunGuiyong on 2017/9/26.
 */
public class GetVideoDetail implements IGetVideoDetail {

//        全部
//        最新上架
    /**
     * actors  type  detail
     * get from video url
     */

    /*@Resource
    private IVideoDAO videoDAO;*/
    /**
     * 这个界面中可以得到url name picture
     */
    private final String url = "http://v.qq.com/x/list/movie?area=-1&sort=19&offset=0";

    private String BASE_URL = "http://v.qq.com/x/list/movie?area=-1&sort=19&offset=";

    private List<String> willCrwalUrl = new ArrayList<String>();

    public List<VideoPO> getVideoInfo() throws IOException {
        AbstractApplicationContext ctx
                = new ClassPathXmlApplicationContext(new String[]{"spring-mybatis.xml"});
        IVideoDAO videoDAO = (IVideoDAO) ctx.getBean("videoDAO");
        initCrawlList();

        //存放整理好的video数据
        List<VideoPO> videoPOList = new ArrayList<>();
        List<VideoPO> poList = new ArrayList<>();
        List<String> existsCidList = new ArrayList<>();
        existsCidList = videoDAO.getAllCid();
        for (int i = 0; i < willCrwalUrl.size(); i++) {
            Document doc = Jsoup.connect(willCrwalUrl.get(i)).timeout(5000).get();

            //figures_list
            Document ulDoc = getChildDocument(doc, "figures_list");
            Elements liContainer = ulDoc.select(".list_item");

            VideoPO videoPO = new VideoPO();
            for (Element liItem : liContainer) {
                Document liDoc = Jsoup.parse(liItem.toString());
                Elements title = liDoc.select("strong a");

                videoPO.setUrl(title.attr("href"));
                getCid(videoPO);
                videoPO.setName(title.text());

                title = liDoc.select("a img");
                //图片地址 r-lazyload
                videoPO.setPicturePath(title.attr("r-lazyload"));

                Document detailDoc = Jsoup.connect(videoPO.getUrl()).timeout(5000).get();

                getTypeAndTime(detailDoc, videoPO);
                getActors(detailDoc, videoPO);
                getDetail(detailDoc, videoPO);
            }
            if (!existsCidList.contains(videoPO.getCid())) {
                videoPO.setAddTime(new Date());
                videoPOList.add(videoPO);
            }
            poList.add(videoPO);
            System.out.println(videoPOList.size());

            if (i == 3) {
                break;
            }
        }
        if (!CollectionUtils.isEmpty(videoPOList)) {
            videoDAO.insertVideoInfo(videoPOList);
        }
        return poList;
    }

    /**
     * 获取cid
     *
     * @param videoPO
     */
    private void getCid(VideoPO videoPO) {
        //https://v.qq.com/x/cover/bt0g0evoxcqxz8d.html
        String[] info = videoPO.getUrl().split("/");
        String[] cid = info[info.length - 1].split("\\.");//正则的时候注意 \\
        if (0 == cid.length) {
            throw new Error("没有cid");
        }
        videoPO.setCid(cid[0]);
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
     * @param videoPO
     * @param doc     connect utl get document
     */
    private void getTypeAndTime(Document doc, VideoPO videoPO) throws IOException {

        //video_tags _video_tags
        Document infoDoc = getChildDocument(doc, "video_info");

        //video_tags
        Document tagsDoc = getChildDocument(infoDoc, "video_tags");

        Elements tagElements = tagsDoc.getElementsByTag("a");
        List<String> tagList = new ArrayList<String>();
        String str = null;
        for (Element item : tagElements) {
            str = item.text().trim();
            if (StrUtil.judgeIsNum(str)) {
                videoPO.setTime(str);
            } else {
                tagList.add(item.text().trim());
            }
        }
        videoPO.setType(Joiner.on(",").skipNulls().join(tagList));
    }

    /**
     * 获取演员列表
     *
     * @param doc
     * @param videoPO
     */
    private void getActors(Document doc, VideoPO videoPO) {

        //mod_bd
        Document infoDoc = getChildDocument(doc, "mod_bd");

        //director
        Document tagsDoc = getChildDocument(infoDoc, "director");

        Elements actors = tagsDoc.getElementsByTag("a");
        List<String> actorsList = new ArrayList<String>();
        String actor = "";
        for (Element item : actors) {
            actor = item.text().trim();
            actorsList.add(actor);
        }
        videoPO.setActors(Joiner.on(",").skipNulls().join(actorsList));
    }

    /**
     * 获取演员列表
     *
     * @param doc
     * @param videoPO
     */
    private void getDetail(Document doc, VideoPO videoPO) {

        //video_summary open
        Document infoDoc = getChildDocument(doc, "video_summary");

        Elements detail = infoDoc.select("p");
        videoPO.setDetail(detail.text().trim());
    }

    /**
     * 根据标签名获取html中的内容
     *
     * @param doc 较大div的document
     * @param tag 大div的子集class 或者 tags
     * @return 根据className 或者 tagsName获取到的子集document
     */
    private Document getChildDocument(Document doc, String tag) {
        Elements info = doc.getElementsByClass(tag);
        return Jsoup.parse(info.toString());
    }
}
