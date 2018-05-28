package com.sun.modules.crawl.parser.impl;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.*;
import com.sun.modules.bean.dao.IVideoDAO;
import com.sun.modules.bean.po.VideoPO;
import com.sun.modules.crawl.parser.ISaveVideoDetail;
import com.sun.modules.util.StrUtil;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by SunGuiyong on 2017/9/26.
 */
public class SaveVideoDetail implements ISaveVideoDetail {

    private String BASE_URL = "http://v.qq.com/x/list/movie?area=-1&sort=19&offset=";
    private ExecutorService exec = Executors.newFixedThreadPool(15);
    private ListeningExecutorService service = MoreExecutors.listeningDecorator(exec);

    private List<String> willCrwalUrl = new ArrayList<String>();

    public List<VideoPO> saveVideoInfo() {
        AbstractApplicationContext ctx
                = new ClassPathXmlApplicationContext(new String[]{"spring-mybatis.xml"});
        IVideoDAO videoDAO = (IVideoDAO) ctx.getBean("videoDAO");
        initCrawlList();

        List<String> existsCidList;
        List<VideoPO> poList = new ArrayList<>();
        poList = videoDAO.getAllVideo();
        existsCidList = videoDAO.getAllCid();

        for (int i = 0; i < willCrwalUrl.size(); i++) {
            try {
                List<VideoPO> videoPOList = new ArrayList<>();

                List<ListenableFuture<VideoPO>> futures = new ArrayList<>();
                Document doc = Jsoup.connect(willCrwalUrl.get(i)).timeout(5000).get();

                Document ulDoc = getChildDocument(doc, "figures_list");
                Elements liContainer = ulDoc.select(".list_item");

                for (Element liItem : liContainer) {
                    VideoPO videoPO = new VideoPO();
                    ListenableFuture<VideoPO> future = service.submit(new ThreadForGetVideo(liItem, videoPO));
                    Futures.addCallback(future, new FutureCallback<VideoPO>() {
                        @Override
                        public void onSuccess(VideoPO videoPO) {
                            System.out.println("video name " + videoPO.getName());
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                        }
                    });
                    futures.add(future);
                }
                try {
                    ListenableFuture<List<VideoPO>> resList = Futures.allAsList(futures);
                    poList = resList.get();
                    for (VideoPO item : poList) {
                        if (!existsCidList.contains(item.getCid())) {
                            existsCidList.add(item.getCid());
                            videoPOList.add(item);
                        }
                    }
                    if (!CollectionUtils.isEmpty(videoPOList)) {
                        videoDAO.insertVideoInfo(videoPOList);
                    }
                } catch (Exception e) {
                    System.out.println("listenableFuture get result error " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("connection error " + e.getMessage());
            }
        }
        exec.shutdown();
        service.shutdown();
        return poList;
    }

    public class ThreadForGetVideo implements Callable<VideoPO> {

        private Element liItem;
        private VideoPO videoPO;

        ThreadForGetVideo(Element liItem, VideoPO videoPO) {
            this.liItem = liItem;
            this.videoPO = videoPO;
        }

        @Override
        public VideoPO call() throws Exception {
            Document liDoc = Jsoup.parse(this.liItem.toString());
            Elements title = liDoc.select("strong a");

            this.videoPO.setUrl(title.attr("href"));
            getCid(this.videoPO);
            this.videoPO.setName(title.text());

            title = liDoc.select("a img");
            //图片地址 r-lazyload
            this.videoPO.setPicturePath(title.attr("r-lazyload"));

            Document detailDoc = Jsoup.connect(this.videoPO.getUrl()).timeout(5000).get();

            getTypeAndTime(detailDoc, this.videoPO);
            getActors(detailDoc, this.videoPO);
            getDetail(detailDoc, this.videoPO);
            return this.videoPO;
        }
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
