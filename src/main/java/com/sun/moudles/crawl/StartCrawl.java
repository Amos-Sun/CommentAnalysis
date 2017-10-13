package com.sun.moudles.crawl;

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
import java.util.Scanner;

/**
 * Created by SunGuiyong on 2017/9/26.
 */
public class StartCrawl {

    public static void downloadPagebyGetMethod() throws IOException {

        // 1、通过HttpGet获取到response对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        http://v.qq.com/x/list/movie?offset=0&area=-1
//        全部 ~~~
//        最新上架
//        http://v.qq.com/x/list/movie?area=-1&offset=0&sort=19
        HttpGet httpGet = new HttpGet("http://v.qq.com/x/list/movie?area=-1&offset=0");
        CloseableHttpResponse response = httpClient.execute(httpGet);

        InputStream is = null;
        Scanner sc = null;
        Writer os = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                // 2、获取response的entity。
                HttpEntity entity = response.getEntity();

                // 3、获取到InputStream对象，并对内容进行处理
                is = entity.getContent();
                sc = new Scanner(is);
                String filename = "2.txt";
                os = new PrintWriter(filename);
                while (sc.hasNext()) {
                    os.write(sc.nextLine());
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } finally {
                if (sc != null) {
                    sc.close();
                }
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (response != null) {
                    response.close();
                }
            }
        }

    }

    public static void main(String[] args) {
        /*try {
            downloadPagebyGetMethod();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        GetDocument getDoc = new GetDocument();
        getDoc.getPageContent();
    }
}
