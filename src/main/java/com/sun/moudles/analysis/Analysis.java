package com.sun.moudles.analysis;

import java.io.*;
import java.util.Iterator;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.apache.lucene.analysis.Analyzer;
import net.paoding.analysis.analyzer.PaodingAnalyzer;

/**
 * Created by SunGuiyong on 2017/10/10.
 */
@Component
public class Analysis {

    public void smartcnPartWords(String handleText) throws IOException {
        // 自定义停用词
        String[] self_stop_words = {"的", "在", "了", "呢", "，", "0", "：", ",", "是"};
        CharArraySet cas = new CharArraySet(Version.LUCENE_48, 0, true);
        for (int i = 0; i < self_stop_words.length; i++) {
            cas.add(self_stop_words[i]);
        }

        // 加入系统默认停用词
        Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
        while (itor.hasNext()) {
            cas.add(itor.next());
        }

        // 中英文混合分词器(其他几个分词器对中文的分析都不行)
        SmartChineseAnalyzer sca = new SmartChineseAnalyzer(Version.LUCENE_48, cas);

        TokenStream ts = sca.tokenStream("field", handleText);
        CharTermAttribute ch = ts.addAttribute(CharTermAttribute.class);

        ts.reset();
        while (ts.incrementToken()) {
            System.out.print(ch.toString() + "\\");
        }
        ts.end();
        ts.close();
    }

    /*public static void main(String[] args) throws IOException {
        Analyzer analyzer = new PaodingAnalyzer();
        String indexStr = "我的QQ号码是58472399";
        StringReader reader = new StringReader(indexStr);
        TokenStream token = analyzer.tokenStream(indexStr, reader);
        token.reset();
        CharTermAttribute ta = token.addAttribute(CharTermAttribute.class);

        while (token.incrementToken()) {

            System.out.print(ta.toString());
        }
        System.out.println("t");
    }*/

/*    public static void parse(Analyzer analyzer, String text) throws IOException {
        TokenStream ts = analyzer.tokenStream("text", new StringReader(text));
        ts.reset();
        // 添加工具类 注意：以下这些与之前lucene2.x版本不同的地方
        CharTermAttribute offAtt = ts.addAttribute(CharTermAttribute.class);
        // 循环打印出分词的结果，及分词出现的位置
        while (ts.incrementToken()) {
            System.out.print(offAtt.toString() + "\t");
        }
        ts.close();
    }


    public static void main(String[] args) throws IOException {
        Analyzer paodingAnalyzer = new PaodingAnalyzer();

        String text = "你吃饭了吗";
        parse(paodingAnalyzer, text);
        parse(paodingAnalyzer, text);
        parse(paodingAnalyzer, text);
        parse(paodingAnalyzer, text);
        parse(paodingAnalyzer, text);
    }*/


    public static void main(String[] args) throws Exception {
        String dataDir="data";
        String indexDir="luceneindex";

        File[] files=new File(dataDir).listFiles();
        System.out.println(files.length);

        Analyzer analyzer=new PaodingAnalyzer();
        Directory dir= FSDirectory.open(new File(indexDir));
        IndexWriter writer=new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_48,analyzer));

        for(int i=0;i<files.length;i++){
            StringBuffer strBuffer=new StringBuffer();
            String line="";
            FileInputStream is=new FileInputStream(files[i].getCanonicalPath());
            BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf8"));
            line=reader.readLine();
            while(line != null){
                strBuffer.append(line);
                strBuffer.append("\n");
                line=reader.readLine();
            }

            Document doc=new Document();
            doc.add(new Field("fileName", files[i].getName(), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("contents", strBuffer.toString(), Field.Store.YES, Field.Index.ANALYZED));
            writer.addDocument(doc);
            reader.close();
            is.close();
        }

        writer.commit();
        writer.close();
        dir.close();
        System.out.println("ok");
    }
}
