package com.sun.modules;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.modules.bean.json.VideoCommentId;
import com.sun.modules.util.JsonUtil;
import com.sun.modules.util.StrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sunguiyong on 2017/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class StrTest {

    private String str = "<html>\n" +
            " <head></head>\n" +
            " <body>\n" +
            "  QZOutputJson={&quot;comment_id&quot;:&quot;1551278768&quot;,&quot;result&quot;:{&quot;code&quot;:0,&quot;msg&quot;:&quot;Success!&quot;,&quot;ret&quot;:0},&quot;srcid&quot;:&quot;bt0g0evoxcqxz8d&quot;,&quot;srcid_type&quot;:1003};\n" +
            " </body>\n" +
            "</html>";


    @Test
    public void strTest() {
        String getStr = StrUtil.getAimedString(str, "QZOutputJson=", "</body>");
        System.out.println(StrUtil.hasSpecifyString(getStr, "&quot;"));
        String res = StrUtil.replaceString(getStr, "&quot;", "\"");
        System.out.println(res);
        VideoCommentId videoCommentId = JsonUtil.fromJson(res, new TypeReference<VideoCommentId>() {
        });
        System.out.println(videoCommentId.getComment_id());
    }
}
