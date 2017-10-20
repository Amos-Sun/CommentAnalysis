package com.sun.moudles.analysis;

import java.io.*;
import java.util.Iterator;

import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.springframework.stereotype.Component;

/**
 * Created by SunGuiyong on 2017/10/10.
 */
@Component
public class Analysis {

    /*public void smartcnPartWords(String handleText) throws IOException {
        // 自定义停用词
        String[] self_stop_words = {"的", "在", "了", "呢", "，", "0", "：", ",", "是"};
        CharArraySet cas = new CharArraySet(Version.LUCENE_46, 0, true);
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
    }*/

   /* public static void main(String[] args) {
// TODOAuto-generated method stub
        Analyzer analyzer=new PaodingAnalyzer();
        String docText=null;
        File file=new File("F:\\selfproject\\CommentAnalysis\\data\\test.txt");
        docText=readText(file);
        TokenStream tokenStream=analyzer.tokenStream(docText, new StringReader(docText));
        try{
            Token t;
            //System.out.println(docText);
            while((t=tokenStream.next())!=null){
                System.out.println(t);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static String readText(File file) {
        // TODOAuto-generated method stub
        String text=null;
        try{
            InputStreamReader read1=new InputStreamReader(new FileInputStream(file),"GBK");
            BufferedReader br1=new BufferedReader(read1);
            StringBuffer buff1=new StringBuffer();
            while((text=br1.readLine())!=null){
                buff1.append(text+"/r/n");
            }
            br1.close();
            text=buff1.toString();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
        return text;
    }*/

   public void paodingAnalysis(String indexStr)throws IOException{
       Analyzer analyzer = new PaodingAnalyzer();
       StringReader reader = new StringReader(indexStr);
       TokenStream ts = analyzer.tokenStream(indexStr, reader);
       Token t = ts.next();
       while (t != null) {
           System.out.print(t.termText()+"  ");
           t = ts.next();
       }
   }
}
