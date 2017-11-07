package com.sun.moudles.analysis;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.moudles.constants.StopWords;
import com.sun.moudles.util.FileUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * Created by SunGuiyong on 2017/10/10.
 */
@Component
public class WordSegmentation {
    /**
     * smartcn 分词
     *
     * @param handleText
     * @throws IOException
     */
    public String smartcnPartWords(String handleText) throws IOException {
        StringBuilder sbContent = new StringBuilder();
        // 自定义停用词
        //String[] self_stop_words = {"的", "在", "了", "呢", "，", "0", "：", ",", "是"};
        CharArraySet cas = new CharArraySet(Version.LUCENE_48, 0, true);
        for (int i = 0; i < StopWords.STOP_WORD.size(); i++) {
            cas.add(StopWords.STOP_WORD.get(i));
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
            sbContent.append(ch.toString() + "|");
            System.out.print(ch.toString() + "|");
        }
        System.out.println();
        ts.end();
        ts.close();
        return sbContent.toString();
    }

    /**
     * IK分词
     *
     * @param text
     * @throws Exception
     */
    public String ikAnalyzer(String text) throws Exception {
        if(StringUtils.isEmpty(text)){
            return "";
        }
        StringBuilder sbContent = new StringBuilder();
        StringReader re = new StringReader(text);
        IKSegmenter ik = new IKSegmenter(re, true);
        Lexeme lex = null;
        while ((lex = ik.next()) != null) {
            if (StopWords.STOP_WORD.contains(lex.getLexemeText())) {
                continue;
            }
            sbContent.append(lex.getLexemeText() + "|");
        }
        return sbContent.toString();
    }

    /**
     * 把分词结果存到文件中
     *
     * @param filePath    未分词文件路径
     * @param newFileName 分词后，存储路径
     */
    private void writeClassWords(String filePath, String newFileName) throws Exception {
        File file = new File(filePath);
        File[] files = file.listFiles();

        WordSegmentation wordSegmentation = new WordSegmentation();
        String fileText;
        String segResult;
        int numbers = 0;
        List<String> wordList = new ArrayList<String>();
        for (File item : files) {
/*            if (numbers == 26) {
                break;
            }*/
            fileText = FileUtil.readFileAllContents(item.getPath());
            segResult = wordSegmentation.ikAnalyzer(fileText);

            //每一个文本存成一个向量
            wordList.add(segResult.trim());
            numbers++;
        }
        FileUtil.writeFileInBatch(wordList, newFileName);
    }


    public static void main(String[] args) throws Exception {
        WordSegmentation wordSegmentation = new WordSegmentation();
        wordSegmentation.writeClassWords("./data/positive", "./data/positiveResult.txt");
        wordSegmentation.writeClassWords("./data/negative", "./data/negativeResult.txt");
        wordSegmentation.writeClassWords("./data/neuter", "./data/neuterResult.txt");
    }
}
