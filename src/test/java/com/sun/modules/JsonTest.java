package com.sun.modules;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.modules.bean.json.DataDetail;
import com.sun.modules.util.JsonUtil;
import com.sun.modules.util.StrUtil;
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
    private String jsonData_1 = "{\"errCode\":0,\"data\":{\"targetid\":1632164440,\"display\":1,\"total\":402,\"reqnum\":2,\"retnum\":2,\"maxid\":\"6329274444473334001\",\"first\":\"6329269555730851483\",\"last\":\"6329266287978176482\",\"hasnext\":true,\"commentid\":[{\"id\":\"6329269555730851483\",\"rootid\":\"0\",\"targetid\":1632164440,\"parent\":\"0\",\"timeDifference\":\"\\u4eca\\u5929 18:57:34\",\"time\":1509015454,\"content\":\"\\u597d\\u7684\",\"title\":\"\",\"up\":\"1\",\"rep\":\"0\",\"type\":\"1\",\"hotscale\":\"0\",\"checktype\":\"2\",\"checkstatus\":\"1\",\"isdeleted\":\"0\",\"tagself\":\"\",\"taghost\":\"\",\"source\":\"9\",\"location\":\"\",\"address\":\"\",\"rank\":\"-1\",\"custom\":\"usertype=0\",\"extend\":{\"at\":0,\"ut\":0,\"ct\":\"\",\"wt\":0},\"orireplynum\":\"0\",\"richtype\":0,\"userid\":\"203740381\",\"poke\":0,\"abstract\":\"\",\"thirdid\":\"pubsource=mobileupdate&msgid=2458074799320&userid=138698747&cfrom=0&scene=1&datakey=cid%3D4zjqix14x3nyntt%26vid%3Dv0024rsxygk%26type%3D1&seq=d88cb617-e869-446e-9cd5-c20e4c06ec6c&ctrid=0\",\"ispick\":0,\"ishide\":0,\"isauthor\":0,\"replyuser\":\"\",\"replyuserid\":0,\"replyhwvip\":0,\"replyhwlevel\":0,\"replyhwannual\":0,\"userinfo\":{\"userid\":\"203740381\",\"uidex\":\"ecc3ac5fdad55a29bc7d94bd42d81c57d0\",\"nick\":\"\\u5f7c\\u5cb8\\u82b1\\u261b\\u9001\\u846c\",\"head\":\"http:\\/\\/q2.qlogo.cn\\/g?b=qq&k=AbFib4eFAgN0EUxWAXsqFsw&s=40&t=1508947200\",\"gender\":1,\"viptype\":\"0\",\"mediaid\":0,\"region\":\"\\u65e5\\u672c::\\u9e7f\\u513f\\u5c9b\",\"thirdlogin\":0,\"hwvip\":1,\"hwlevel\":3,\"hwannual\":1,\"identity\":\"\",\"wbuserinfo\":[],\"certinfo\":\"\",\"remark\":\"\",\"fnd\":0}},{\"id\":\"6329266287978176482\",\"rootid\":\"0\",\"targetid\":1632164440,\"parent\":\"0\",\"timeDifference\":\"\\u4eca\\u5929 18:44:35\",\"time\":1509014675,\"content\":\"\\u9ec4\\u78ca\\u5f20\\u827a\\u5174\",\"title\":\"\",\"up\":\"0\",\"rep\":\"0\",\"type\":\"1\",\"hotscale\":\"0\",\"checktype\":\"2\",\"checkstatus\":\"1\",\"isdeleted\":\"0\",\"tagself\":\"\",\"taghost\":\"\",\"source\":\"9\",\"location\":\"\",\"address\":\"\",\"rank\":\"-1\",\"custom\":\"usertype=0\",\"extend\":{\"at\":0,\"ut\":0,\"ct\":\"\",\"wt\":0},\"orireplynum\":\"0\",\"richtype\":0,\"userid\":\"199080332\",\"poke\":0,\"abstract\":\"\",\"thirdid\":\"pubsource=mobileupdate&msgid=458069984572&userid=547497840&cfrom=0&scene=1&datakey=cid%3D4zjqix14x3nyntt%26vid%3Dw0024unilx1%26type%3D1&seq=8fbaed0d-edb6-4b54-9528-1d1c92722b11&ctrid=0\",\"ispick\":0,\"ishide\":0,\"isauthor\":0,\"replyuser\":\"\",\"replyuserid\":0,\"replyhwvip\":0,\"replyhwlevel\":0,\"replyhwannual\":0,\"userinfo\":{\"userid\":\"199080332\",\"uidex\":\"ecff375dad4a1e2af1902ebd99b9d93763\",\"nick\":\"\\u51b7\\u9762\\u7b11\\u5320.\",\"head\":\"http:\\/\\/q2.qlogo.cn\\/g?b=qq&k=ghwqRM07GFm9eY7al1x5Yg&s=40&t=1508947200\",\"gender\":1,\"viptype\":\"0\",\"mediaid\":0,\"region\":\"\\u4e2d\\u56fd::\",\"thirdlogin\":0,\"hwvip\":1,\"hwlevel\":2,\"hwannual\":0,\"identity\":\"\",\"wbuserinfo\":[],\"certinfo\":\"\",\"remark\":\"\",\"fnd\":0}}],\"targetinfo\":{\"orgcommentnum\":\"1503\",\"commentnum\":\"402\",\"checkstatus\":\"0\",\"checktype\":\"1\",\"city\":\"\",\"voteid\":\"11037723\",\"topicids\":\"\",\"commup\":\"1835\"}},\"info\":{\"time\":1509024617}}";
    private String jsonData = "{\"errCode\":0,\"data\":{\"targetid\":2044303146,\"display\":1,\"total\":16905,\"reqnum\":50,\"retnum\":50,\"maxid\":\"6356396883871226859\",\"first\":\"6353641738410292858\",\"last\":\"6353577090327687889\",\"hasnext\":true,\"commentid\":[{\"id\":\"6353623521578985190\",\"rootid\":\"0\",\"targetid\":2044303146,\"parent\":\"0\",\"timeDifference\":\"01\\u670801\\u65e5 23:51:32\",\"time\":1514821892,\"content\":\"\\u6211\\u80fd\\u8bf4\\u5267\\u60c5\\u70c2\\u4e48\\uff1f\\u5b8c\\u5168\\u9760\\u6f14\\u5458\\u652f\\u6491\\u3002\",\"title\":\"\",\"up\":\"0\",\"rep\":\"0\",\"type\":\"1\",\"hotscale\":\"0\",\"checktype\":\"1\",\"checkstatus\":\"0\",\"isdeleted\":\"0\",\"tagself\":\"\",\"taghost\":\"\",\"source\":\"9\",\"location\":\"\",\"address\":\"\",\"rank\":\"-1\",\"custom\":\"usertype=0\",\"extend\":{\"at\":0,\"ut\":0,\"ct\":\"10003\",\"wt\":0},\"orireplynum\":\"0\",\"richtype\":0,\"userid\":\"30383162\",\"poke\":0,\"abstract\":\"\",\"thirdid\":\"pubsource=mobileupdate&amp;msgid=6511027155787&amp;userid=125362817&amp;cfrom=0&amp;scene=1&amp;datakey=cid%3Dxyaud3hk6mmi6ss%26vid%3Di0025s06fdc%26type%3D1&amp;seq=6b5d8e2d-1fc0-494f-b105-1dc501810714&amp;ctrid=0\",\"ispick\":0,\"ishide\":0,\"isauthor\":0,\"replyuser\":\"\",\"replyuserid\":0,\"replyhwvip\":0,\"replyhwlevel\":0,\"replyhwannual\":0,\"userinfo\":{\"userid\":\"30383162\",\"uidex\":\"ecf3e1f6b08781f85d\",\"nick\":\"\\u6b22\\u4e50\\u7684\\u6d77\\u76d7\\u8700\\u9ecd\",\"head\":\"http:\\/\\/q1.qlogo.cn\\/g?b=qq&amp;k=SaH7QeIcttCKynvcqQeslw&amp;s=40&amp;t=1515427200\",\"gender\":1,\"viptype\":\"0\",\"mediaid\":0,\"region\":\"\\u4e2d\\u56fd:\\u56db\\u5ddd:\\u6210\\u90fd\",\"thirdlogin\":0,\"hwvip\":1,\"hwlevel\":3,\"hwannual\":1,\"identity\":\"\",\"wbuserinfo\":[],\"certinfo\":{\"certnick\":\"\\u6b22\\u4e50\\u7684\\u5927\\u809a\\u5b50\\u6d77\\u76d7\\u8700\\u9ecd\",\"certhead\":\"\",\"certinfo\":\"\",\"certidentityid\":\"10003\",\"certappids\":\"10002\"},\"remark\":\"\",\"fnd\":0}}],\"targetinfo\":{\"orgcommentnum\":\"22205\",\"commentnum\":\"16905\",\"checkstatus\":\"0\",\"checktype\":\"1\",\"city\":\"\",\"voteid\":\"12428066\",\"topicids\":\"\",\"commup\":\"69336\"}},\"info\":{\"time\":1515485069}}";

    @Test
    public void jsonTest() {
        System.out.println(jsonData.indexOf("\"certinfo\":"));
        System.out.println(jsonData.indexOf(",\"remark\":"));

        boolean flag = StrUtil.hasSpecifyString(jsonData, "abstract");
        System.out.println(flag);
        if (flag) {
            StrUtil.replaceString(jsonData, "abstract", "abstract__");
        }
        DataDetail dataDetail = JsonUtil.fromJson(jsonData, DataDetail.class);
        System.out.println(dataDetail.getErrCode());
        System.out.println(dataDetail.getData().getCommentid().get(0).getContent());
        /*JSONObject jObject= JSONObject.parseObject(jsonData_1);*/
    }

    private String userJson_error = "{\"errCode\":0,\"data\":{\"targetid\":1992888193,\"display\":1,\"total\":4684,\"reqnum\":50,\"retnum\":50,\"maxid\":\"6360035677753059067\",\"first\":\"6359763281666122200\",\"last\":\"6359741223381138239\",\"hasnext\":true,\"commentid\":[{\"id\":\"6359751032243403864\",\"rootid\":\"0\",\"targetid\":1992888193,\"parent\":\"0\",\"timeDifference\":\"\\u6628\\u5929 21:40:04\",\"time\":1516282804,\"content\":\"\\u9898\\u6750\\u4e0d\\u9519\\uff0c\\u4f46\\u5367\\u5e95\\u7684\\u65f6\\u5019\\u592a\\u5f15\\u4eba\\u77a9\\u76ee\\u4e0d\\u592a\\u597d\\u5427\\uff0c\\u7ed9\\u4eba\\u52a0\\u6df1\\u4e86\\u5370\\u8c61\\u8fd9\\u4e0d\\u662f\\u627e\\u6b7b\\u5417\\uff1f[\\u7591\\u95ee]\",\"title\":\"\",\"up\":\"0\",\"rep\":\"0\",\"type\":\"1\",\"hotscale\":\"0\",\"checktype\":\"1\",\"checkstatus\":\"0\",\"isdeleted\":\"0\",\"tagself\":\"\",\"taghost\":\"\",\"source\":\"9\",\"location\":\"\",\"address\":\"\",\"rank\":\"-1\",\"custom\":\"usertype=0\",\"extend\":{\"at\":0,\"ut\":0,\"ct\":\"\",\"wt\":0},\"orireplynum\":\"0\",\"richtype\":0,\"userid\":\"242310549\",\"poke\":0,\"abstract\":\"\",\"thirdid\":\"pubsource=mobileupdate&msgid=8024027199214&userid=383767736&cfrom=0&scene=0&datakey=cid%3Djhpwng9yy0qms36%26vid%3Dq002570yi7r%26type%3D1%26vtitle%3D%E5%B7%A8%E9%A2%9D%E6%9D%A5%E7%94%B5+%E6%A1%82%E7%BA%B6%E9%95%815%E5%88%86%E9%92%9F%E8%AF%88%E9%AA%974%E4%B8%87%E5%9D%97&seq=8f1370ea-5d5f-4b23-8795-3adbae7a50be&ctrid=0\",\"ispick\":0,\"ishide\":0,\"isauthor\":0,\"replyuser\":\"\",\"replyuserid\":0,\"replyhwvip\":0,\"replyhwlevel\":0,\"replyhwannual\":0,\"userinfo\":{\"userid\":\"242310549\",\"uidex\":\"ec79916facd7ff64e45e0f6b73ef527467\",\"nick\":\"WE\"\\u4e0d\\u6563\",\"head\":\"http:\\/\\/q4.qlogo.cn\\/g?b=qq&k=7VSRKXvyV5yicGGWtdUkc8w&s=40&t=1516291200\",\"gender\":1,\"viptype\":\"0\",\"mediaid\":0,\"region\":\"\\u4e2d\\u56fd:\\u5c71\\u4e1c:\\u9752\\u5c9b\",\"thirdlogin\":0,\"hwvip\":1,\"hwlevel\":4,\"hwannual\":0,\"identity\":\"\",\"wbuserinfo\":[],\"certinfo\":\"\",\"remark\":\"\",\"fnd\":0}}],\"targetinfo\":{\"orgcommentnum\":\"6524\",\"commentnum\":\"4684\",\"checkstatus\":\"0\",\"checktype\":\"1\",\"city\":\"\",\"voteid\":\"12053610\",\"topicids\":\"\",\"commup\":\"12816\"}},\"info\":{\"time\":1516350898}}";
    private String userJson_right = "{\"errCode\":0,\"data\":{\"targetid\":1992888193,\"display\":1,\"total\":4684,\"reqnum\":50,\"retnum\":50,\"maxid\":\"6360035677753059067\",\"first\":\"6359763281666122200\",\"last\":\"6359741223381138239\",\"hasnext\":true,\"commentid\":[{\"id\":\"6359751032243403864\",\"rootid\":\"0\",\"targetid\":1992888193,\"parent\":\"0\",\"timeDifference\":\"\\u6628\\u5929 21:40:04\",\"time\":1516282804,\"content\":\"\\u9898\\u6750\\u4e0d\\u9519\\uff0c\\u4f46\\u5367\\u5e95\\u7684\\u65f6\\u5019\\u592a\\u5f15\\u4eba\\u77a9\\u76ee\\u4e0d\\u592a\\u597d\\u5427\\uff0c\\u7ed9\\u4eba\\u52a0\\u6df1\\u4e86\\u5370\\u8c61\\u8fd9\\u4e0d\\u662f\\u627e\\u6b7b\\u5417\\uff1f[\\u7591\\u95ee]\",\"title\":\"\",\"up\":\"0\",\"rep\":\"0\",\"type\":\"1\",\"hotscale\":\"0\",\"checktype\":\"1\",\"checkstatus\":\"0\",\"isdeleted\":\"0\",\"tagself\":\"\",\"taghost\":\"\",\"source\":\"9\",\"location\":\"\",\"address\":\"\",\"rank\":\"-1\",\"custom\":\"usertype=0\",\"extend\":{\"at\":0,\"ut\":0,\"ct\":\"\",\"wt\":0},\"orireplynum\":\"0\",\"richtype\":0,\"userid\":\"242310549\",\"poke\":0,\"abstract\":\"\",\"thirdid\":\"pubsource=mobileupdate&msgid=8024027199214&userid=383767736&cfrom=0&scene=0&datakey=cid%3Djhpwng9yy0qms36%26vid%3Dq002570yi7r%26type%3D1%26vtitle%3D%E5%B7%A8%E9%A2%9D%E6%9D%A5%E7%94%B5+%E6%A1%82%E7%BA%B6%E9%95%815%E5%88%86%E9%92%9F%E8%AF%88%E9%AA%974%E4%B8%87%E5%9D%97&seq=8f1370ea-5d5f-4b23-8795-3adbae7a50be&ctrid=0\",\"ispick\":0,\"ishide\":0,\"isauthor\":0,\"replyuser\":\"\",\"replyuserid\":0,\"replyhwvip\":0,\"replyhwlevel\":0,\"replyhwannual\":0,\"userinfo\":{\"userid\":\"242310549\",\"uidex\":\"ec79916facd7ff64e45e0f6b73ef527467\",\"nick\":\"WE\'\\u4e0d\\u6563\",\"head\":\"http:\\/\\/q4.qlogo.cn\\/g?b=qq&k=7VSRKXvyV5yicGGWtdUkc8w&s=40&t=1516291200\",\"gender\":1,\"viptype\":\"0\",\"mediaid\":0,\"region\":\"\\u4e2d\\u56fd:\\u5c71\\u4e1c:\\u9752\\u5c9b\",\"thirdlogin\":0,\"hwvip\":1,\"hwlevel\":4,\"hwannual\":0,\"identity\":\"\",\"wbuserinfo\":[],\"certinfo\":\"\",\"remark\":\"\",\"fnd\":0}}],\"targetinfo\":{\"orgcommentnum\":\"6524\",\"commentnum\":\"4684\",\"checkstatus\":\"0\",\"checktype\":\"1\",\"city\":\"\",\"voteid\":\"12053610\",\"topicids\":\"\",\"commup\":\"12816\"}},\"info\":{\"time\":1516350898}}";

    @Test
    public void userJsonTest() {
        boolean flag;
        try {
            flag = StrUtil.hasSpecifyString(userJson_error, "abstract");
            System.out.println(flag);
            if (flag) {
                StrUtil.replaceString(userJson_error, "abstract", "abstract__");
            }
            DataDetail dataDetail = JsonUtil.fromJson(userJson_error, DataDetail.class);
            System.out.println(dataDetail.getErrCode());
            System.out.println(dataDetail.getData().getCommentid().get(0).getContent());
        } catch (Exception e) {
            System.out.println("json_error");
        }

        try {
            flag = StrUtil.hasSpecifyString(userJson_right, "abstract");
            System.out.println(flag);
            if (flag) {
                StrUtil.replaceString(userJson_right, "abstract", "abstract__");
            }
            DataDetail dataDetail = JsonUtil.fromJson(userJson_right, DataDetail.class);
            System.out.println(dataDetail);
        } catch (Exception e) {
            System.out.println("json right");
        }
    }

    @Test
    public void handleStr() {
        String str = StrUtil.handleMarks(userJson_error);

        boolean flag;

        flag = StrUtil.hasSpecifyString(str, "abstract");
        System.out.println(flag);
        if (flag) {
            StrUtil.replaceString(str, "abstract", "abstract__");
        }
        DataDetail dataDetail = JsonUtil.fromJson(str, DataDetail.class);
        System.out.println(dataDetail.getErrCode());
        System.out.println(dataDetail.getData().getCommentid().get(0).getContent());

        System.out.println(str);
    }
}
