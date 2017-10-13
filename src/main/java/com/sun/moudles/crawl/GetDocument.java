package com.sun.moudles.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by sunguiyong on 2017/10/8.
 */
public class GetDocument {

//        全部 ~~~
//        最新上架
//        http://v.qq.com/x/list/movie?area=-1&offset=0&sort=19
    private final String url = "http://v.qq.com/x/list/movie?area=-1&offset=0&sort=19";

    public void getPageContent() {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements divContainer = doc.getElementsByClass("mod_figures_wrapper");//div content
            Document divDoc = Jsoup.parse(divContainer.toString());

            Elements ulContainer = doc.getElementsByClass("figures_list");
            Document ulDoc = Jsoup.parse(ulContainer.toString());

            Elements liContainer = ulDoc.select(".list_item");

            for (Element liItem : liContainer) {
                Document liDoc = Jsoup.parse(liItem.toString());
                Elements title = liDoc.select("strong a");
                System.out.println("名字：" + title.text());
                System.out.println("链接：" + title.attr("href"));
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
