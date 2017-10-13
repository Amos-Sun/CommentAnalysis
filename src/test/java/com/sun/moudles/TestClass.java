package com.sun.moudles;

import com.sun.moudles.crawl.dao.VideoDAO;
import com.sun.moudles.crawl.po.VideoPO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.util.List;

/**
 * Created by sunguiyong on 2017/10/13.
 */
public class TestClass {

    @Test
    public void sqlTest(){


        try {
            //1.  用配置文件构建SqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().
                    build(Resources.getResourceAsStream("mybatis-conf.xml"));

            //2.  利用SqlSessionFactory打开一个和数据库的SqlSession
            SqlSession session = sqlSessionFactory.openSession();

            //3. 利用这个SqlSession获取要使用的mapper接口
            VideoDAO videoDAO = session.getMapper(VideoDAO.class);

            //4. 使用mapper接口和数据库交互，运行mapper.xml文件中的SQL语句
            List<VideoPO> allAccountsList = videoDAO.getAllVideo();

            System.out.println(allAccountsList.size());
            //5. SqlSession提交SQL到数据库并关闭SqlSession
            session.commit();
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
