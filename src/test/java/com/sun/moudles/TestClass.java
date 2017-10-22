package com.sun.moudles;

import com.sun.moudles.bean.dao.IVideoDAO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by sunguiyong on 2017/10/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestClass {

    @Resource
    private IVideoDAO videoDAO;

    private final String BASE_URL = "http://v.qq.com/x/list/movie?area=-1&sort=19&offset=";

    @Test
    public void sqlTest(){

        int count = 0;
        for(int i = 1; i < 166; i++){
            count += 30;
        }
        System.out.println(BASE_URL+count);

        if(videoDAO.getAllVideo() == null){
            System.out.println("null");
        }else {
            System.out.println(videoDAO.getAllVideo().size());
        }
    }

    @Test
    public void aa() {
        String html = "<html><head><title>First parse</title></head>"
                + "<body><iframe><html><body>Parsed HTML into a doc.</body></html></iframe></body></html>";

        Document doc = Jsoup.parse(html);
        String body = doc.select("iframe").first().text();// 得到ifrmae下的html字符串
        Document ifmDoc = Jsoup.parseBodyFragment(body); // 将html字符串转成Document对象
// System.out.println(ifmDoc);
        String ibody = ifmDoc.select("body").text();
        System.out.println(ibody);
    }
}
