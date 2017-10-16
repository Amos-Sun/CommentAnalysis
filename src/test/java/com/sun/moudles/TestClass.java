package com.sun.moudles;

import com.sun.moudles.crawl.dao.IVideoDAO;
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
    private IVideoDAO IVideoDAO;

    private final String BASE_URL = "http://v.qq.com/x/list/movie?area=-1&sort=19&offset=";

    @Test
    public void sqlTest(){

        int count = 0;
        for(int i = 1; i < 166; i++){
            count += 30;
        }
        System.out.println(BASE_URL+count);

        if(IVideoDAO.getAllVideo() == null){
            System.out.println("null");
        }else {
            System.out.println(IVideoDAO.getAllVideo().size());
        }
    }
}
