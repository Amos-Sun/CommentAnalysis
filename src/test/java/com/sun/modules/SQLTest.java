package com.sun.modules;

import com.sun.modules.bean.dao.IRelationDAO;
import com.sun.modules.bean.dao.IUserDAO;
import com.sun.modules.bean.po.RelationPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SunGuiyong
 * on 2018/1/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class SQLTest {

    @Resource
    private IUserDAO userDAO;
    @Resource
    private IRelationDAO relationDAO;

    @Test
    public void getUserTest() {
        List<String> list = userDAO.getAllName();

        for (String item : list) {
            System.out.println(URLDecoder.decode(item));
        }
    }

    @Test
    public void insertRelation() {
        List<RelationPO> list = new ArrayList<>();
        RelationPO relationPO = new RelationPO();
        relationPO.setCid("12345");
        relationPO.setUserName("456");
        relationPO.setAddTime(new Date());
        list.add(relationPO);
        relationDAO.insertRecord(list);
    }
}
