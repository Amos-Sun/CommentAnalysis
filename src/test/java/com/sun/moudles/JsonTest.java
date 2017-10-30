package com.sun.moudles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.moudles.bean.json.DataDetail;
import com.sun.moudles.util.JsonUtil;
import com.sun.moudles.util.StrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sunguiyong on 2017/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class JsonTest {
    private String jsonData = "{\"errCode\":0,\"data\":{\"targetid\":1632164440,\"display\":1,\"total\":402,\"reqnum\":2,\"retnum\":2,\"maxid\":\"6329274444473334001\",\"first\":\"6329269555730851483\",\"last\":\"6329266287978176482\",\"hasnext\":true,\"commentid\":[{\"id\":\"6329269555730851483\",\"rootid\":\"0\",\"targetid\":1632164440,\"parent\":\"0\",\"timeDifference\":\"\\u4eca\\u5929 18:57:34\",\"time\":1509015454,\"content\":\"\\u597d\\u7684\",\"title\":\"\",\"up\":\"1\",\"rep\":\"0\",\"type\":\"1\",\"hotscale\":\"0\",\"checktype\":\"2\",\"checkstatus\":\"1\",\"isdeleted\":\"0\",\"tagself\":\"\",\"taghost\":\"\",\"source\":\"9\",\"location\":\"\",\"address\":\"\",\"rank\":\"-1\",\"custom\":\"usertype=0\",\"extend\":{\"at\":0,\"ut\":0,\"ct\":\"\",\"wt\":0},\"orireplynum\":\"0\",\"richtype\":0,\"userid\":\"203740381\",\"poke\":0,\"abstract\":\"\",\"thirdid\":\"pubsource=mobileupdate&msgid=2458074799320&userid=138698747&cfrom=0&scene=1&datakey=cid%3D4zjqix14x3nyntt%26vid%3Dv0024rsxygk%26type%3D1&seq=d88cb617-e869-446e-9cd5-c20e4c06ec6c&ctrid=0\",\"ispick\":0,\"ishide\":0,\"isauthor\":0,\"replyuser\":\"\",\"replyuserid\":0,\"replyhwvip\":0,\"replyhwlevel\":0,\"replyhwannual\":0,\"userinfo\":{\"userid\":\"203740381\",\"uidex\":\"ecc3ac5fdad55a29bc7d94bd42d81c57d0\",\"nick\":\"\\u5f7c\\u5cb8\\u82b1\\u261b\\u9001\\u846c\",\"head\":\"http:\\/\\/q2.qlogo.cn\\/g?b=qq&k=AbFib4eFAgN0EUxWAXsqFsw&s=40&t=1508947200\",\"gender\":1,\"viptype\":\"0\",\"mediaid\":0,\"region\":\"\\u65e5\\u672c::\\u9e7f\\u513f\\u5c9b\",\"thirdlogin\":0,\"hwvip\":1,\"hwlevel\":3,\"hwannual\":1,\"identity\":\"\",\"wbuserinfo\":[],\"certinfo\":\"\",\"remark\":\"\",\"fnd\":0}},{\"id\":\"6329266287978176482\",\"rootid\":\"0\",\"targetid\":1632164440,\"parent\":\"0\",\"timeDifference\":\"\\u4eca\\u5929 18:44:35\",\"time\":1509014675,\"content\":\"\\u9ec4\\u78ca\\u5f20\\u827a\\u5174\",\"title\":\"\",\"up\":\"0\",\"rep\":\"0\",\"type\":\"1\",\"hotscale\":\"0\",\"checktype\":\"2\",\"checkstatus\":\"1\",\"isdeleted\":\"0\",\"tagself\":\"\",\"taghost\":\"\",\"source\":\"9\",\"location\":\"\",\"address\":\"\",\"rank\":\"-1\",\"custom\":\"usertype=0\",\"extend\":{\"at\":0,\"ut\":0,\"ct\":\"\",\"wt\":0},\"orireplynum\":\"0\",\"richtype\":0,\"userid\":\"199080332\",\"poke\":0,\"abstract\":\"\",\"thirdid\":\"pubsource=mobileupdate&msgid=458069984572&userid=547497840&cfrom=0&scene=1&datakey=cid%3D4zjqix14x3nyntt%26vid%3Dw0024unilx1%26type%3D1&seq=8fbaed0d-edb6-4b54-9528-1d1c92722b11&ctrid=0\",\"ispick\":0,\"ishide\":0,\"isauthor\":0,\"replyuser\":\"\",\"replyuserid\":0,\"replyhwvip\":0,\"replyhwlevel\":0,\"replyhwannual\":0,\"userinfo\":{\"userid\":\"199080332\",\"uidex\":\"ecff375dad4a1e2af1902ebd99b9d93763\",\"nick\":\"\\u51b7\\u9762\\u7b11\\u5320.\",\"head\":\"http:\\/\\/q2.qlogo.cn\\/g?b=qq&k=ghwqRM07GFm9eY7al1x5Yg&s=40&t=1508947200\",\"gender\":1,\"viptype\":\"0\",\"mediaid\":0,\"region\":\"\\u4e2d\\u56fd::\",\"thirdlogin\":0,\"hwvip\":1,\"hwlevel\":2,\"hwannual\":0,\"identity\":\"\",\"wbuserinfo\":[],\"certinfo\":\"\",\"remark\":\"\",\"fnd\":0}}],\"targetinfo\":{\"orgcommentnum\":\"1503\",\"commentnum\":\"402\",\"checkstatus\":\"0\",\"checktype\":\"1\",\"city\":\"\",\"voteid\":\"11037723\",\"topicids\":\"\",\"commup\":\"1835\"}},\"info\":{\"time\":1509024617}}";

    @Test
    public void jsonTest(){
        boolean flag = StrUtil.hasSpecifyString(jsonData, "abstract");
        System.out.println(flag);
        if(flag){
            StrUtil.replaceString(jsonData, "abstract", "abstract__");
        }

        DataDetail dataDetail = JsonUtil.fromJson(jsonData, DataDetail.class);
        System.out.println(dataDetail.getErrCode());
        System.out.println(dataDetail.getData().getCommentid().get(0).getContent());
    }
}
