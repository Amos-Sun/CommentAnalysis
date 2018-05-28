package com.sun.modules.crawl;

import com.sun.modules.bean.po.VideoPO;
import com.sun.modules.crawl.parser.impl.SaveUserAndRelationDetail;
import com.sun.modules.crawl.parser.impl.SaveVideoDetail;

import java.io.*;
import java.util.List;

/**
 * Created by SunGuiyong on 2017/9/26.
 */
public class StartCrawl {

    public static void main(String[] args) {

        SaveVideoDetail getVideoDetail = new SaveVideoDetail();
        SaveUserAndRelationDetail getUserDetail = new SaveUserAndRelationDetail();
        List<VideoPO> videoPOList = getVideoDetail.saveVideoInfo();
        getUserDetail.saveUserAndRelation(videoPOList);

        System.out.println("爬虫结束");
        analysisMention();
        System.out.println("运行python脚本成功");
    }


    /**
     * 调用python进行情感分析
     *
     * @return
     */
    private static void analysisMention() {
        try {
//            D:\self\CommentAnalysis\src\main\java\com\sun\modules\analysis\analysis.py
//            analysis/analysis.py  ---新增一个module可以用相对路径
//            analysis.py
            Process pr = Runtime.getRuntime().exec("python D:\\self\\CommentAnalysis\\analysis\\analysis.py ");
//            Process pr = Runtime.getRuntime().exec("python D:\\self\\CommentAnalysis\\src\\main\\java\\com\\sun\\modules\\analysis\\analysis.py " + str);
            pr.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            LineNumberReader input = new LineNumberReader(in);
            line = input.readLine();
            System.out.println("result:" + line);
            in.close();
        } catch (Exception e) {
            System.out.println("error occur when use python " + e.getMessage());
        }
    }
}
