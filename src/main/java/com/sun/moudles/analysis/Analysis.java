package com.sun.moudles.analysis;

import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;

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
}
