package com.sun.moudles;

import com.sun.moudles.crawl.dao.VideoDAO;
import com.sun.moudles.crawl.po.VideoPO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by sunguiyong on 2017/10/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestClass {

    @Resource
    private VideoDAO videoDAO;

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
}
