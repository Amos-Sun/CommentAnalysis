package com.sun.modules.crawl;

import com.sun.modules.bean.po.UserPO;
import com.sun.modules.bean.po.VideoPO;
import com.sun.modules.crawl.parser.impl.SaveUserAndRelationDetail;
import com.sun.modules.crawl.parser.impl.SaveVideoDetail;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Scanner;

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
    }
}
