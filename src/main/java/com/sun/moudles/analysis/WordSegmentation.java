package com.sun.moudles.analysis;

import java.io.*;
import java.util.*;

import com.sun.moudles.constants.StopWords;
import com.sun.moudles.util.FileUtil;
import com.sun.moudles.util.MathUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
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
        if (StringUtils.isEmpty(text)) {
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
        if (0 == files.length) {
            return;
        }

        WordSegmentation wordSegmentation = new WordSegmentation();
        String fileText;
        String segResult;
//        int numbers = 0;
        List<String> docList = new ArrayList<String>();
        for (File item : files) {
            fileText = FileUtil.readFileAllContents(item.getPath());
            segResult = wordSegmentation.ikAnalyzer(fileText);

            //每一个文本存成一个向量
            docList.add(segResult.trim());
        }
        //<词,频率> 二元组
        Map<String, Double> wordTfIdfMap = new HashMap<String, Double>(16);
        getWordTfIdf(docList, wordTfIdfMap);

        FileUtil.writeFileInBatch(wordTfIdfMap, newFileName);
    }

    /**
     * 获取每一个词的 tf-idf
     *
     * @param docList      文档数组
     * @param wordTfIdfMap 记录词信息的二元组
     */
    private void getWordTfIdf(List<String> docList, Map<String, Double> wordTfIdfMap) {

        int docSize = docList.size();
        for (String item : docList) {
            String[] words = item.split("\\|");
            int wordFreq = 0;
            int timesInDoc = 0;
            double tfIdfValue = 0;

            for (String word : words) {
                if ("".equals(word)) {
                    continue;
                }
                //计算词频
                wordFreq = getFrequency(words, word);

                //计算这个词在多少个文档中出现
                timesInDoc = getTimesInDoc(word, docList);

                //计算tf*idf
                tfIdfValue = calculateTfIdf(wordFreq * 1.0 / words.length, timesInDoc, docSize);

                setTfIdfMap(wordTfIdfMap, word, tfIdfValue);
            }
        }
    }

    /**
     * 计算词语在文档中的频率
     *
     * @param words 一个文档中所有的词
     * @param word  要计算频率从的词
     * @return
     */
    private int getFrequency(String[] words, String word) {
        int times = 0;
        if ("".equals(word)) {
            return 0;
        }
        //计算一个词的词频
        for (String w : words) {
            if (word.equals(w)) {
                // 除第一个词外，其他相同的词置为 ""
                if (times != 0) {
                    w = "";
                }
                times++;
            }
        }
        return times;
    }

    /**
     * 计算词在多少个文档中出现了
     *
     * @param word    要查找的词
     * @param docList 所有文档
     * @return 出现的次数
     */
    private int getTimesInDoc(String word, List<String> docList) {
        if ("".equals(word)) {
            return 0;
        }
        int times = 0;
        for (String item : docList) {
            if (item.contains(word)) {
                times++;
            }
        }
        return times;
    }

    /**
     * 计算tf*idf
     *
     * @param wordFreq   词频
     * @param timesInDoc 出现这个词的文档数目
     * @param docSize    一共有多少个文档
     * @return
     */
    private double calculateTfIdf(double wordFreq, int timesInDoc, int docSize) {

        double idf = MathUtil.log_x(docSize * 1.0 / timesInDoc, 10);
        return wordFreq * idf;
    }

    /**
     * 设置每一个词的map
     *
     * @param wordTfIdfmap
     * @param word
     * @param tfIdf
     */
    private void setTfIdfMap(Map<String, Double> wordTfIdfmap, String word, double tfIdf) {
        //不为空，说明这个词在train数据中已经存过一次
        //这一次存的时候，tf*idf取平均值
        if (null != wordTfIdfmap.get(word)) {
            Double value = wordTfIdfmap.get(word);
            value = (value + tfIdf) / 2.0;
            wordTfIdfmap.put(word, value);
        } else {
            wordTfIdfmap.put(word, tfIdf);
        }
    }

    /**
     * 这个main方法时吧分好词的train数据存起来
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        WordSegmentation wordSegmentation = new WordSegmentation();
        wordSegmentation.writeClassWords("./data/train/positive", "./data/positiveResult.txt");
        wordSegmentation.writeClassWords("./data/train/negative", "./data/negativeResult.txt");
        wordSegmentation.writeClassWords("./data/train/neuter", "./data/neuterResult.txt");
    }
}
