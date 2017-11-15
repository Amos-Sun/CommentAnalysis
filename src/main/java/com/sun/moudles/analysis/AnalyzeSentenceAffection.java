package com.sun.moudles.analysis;

import com.sun.moudles.util.FileUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunguiyong
 * @date 2017-10.30
 */
@Component
public class AnalyzeSentenceAffection {

    /**
     * 记录已经分好类别的评论中的词语
     */
    private static Map<String, Double> positiveWords = new HashMap<String, Double>();
    private static Map<String, Double> negativeWords = new HashMap<String, Double>();
    private static Map<String, Double> neuterWords = new HashMap<String, Double>();

    /**
     * 设各个类别的初始概率都为1/3
     */
    private final double INIT_POSITIVE_PR = 141 / 310.0;
    private final double INIT_NEGATIVE_PR = 88 / 310.0;
    private final double INIT_NEUTER_PR = 81 / 310.0;

    /*static {
        setData("./data/positiveResult.txt", positiveWords);
        setData("./data/negativeResult.txt", negativeWords);
        setData("./data/neuterResult.txt", neuterWords);
    }*/

    /**
     * 初始化词概率map
     *
     * @param filePath 读取文件的路径
     * @param prMap    要初始化的map
     */
    private static void setData(String filePath, Map<String, Double> prMap) {
        try {
            String keys = FileUtil.readFileAllContents(filePath);
            String[] keyArray = keys.split("\r\n");
            for (String word : keyArray) {
                String[] wordValue = word.split(" ");
                if (0 == wordValue.length) {
                    continue;
                }
                prMap.put(wordValue[0], Double.parseDouble(wordValue[1]));
            }
            //setWordsProbability(prMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算词的概率
     *
     * @param prMap
     */
    private static void setWordsProbability(Map<String, Double> prMap) {
        double totalWords = prMap.size();
        for (String key : positiveWords.keySet()) {
            positiveWords.put(key, positiveWords.get(key) / totalWords);
        }
    }

    /**
     * 分析句子情感，sentence是分词之后的字符串
     * P(positive|words)
     * = p(words|positive)*p(positive)/p(words)
     * = p((word1|positive)*p(word2|positive)*p(word3|positive)*……*p(wordn|words))/p(words)
     * 计算之后是positive negative neuter几个概率进行比较，p(words)在几个概率中都是一样的，
     * 所有可以不进行计算p(words)
     *
     * @param words
     * @return 1->positive   0->neuter   -1->negative
     */
    public int getSentenceAffection(String[] words) {
        double positivePR = getProbability(words, positiveWords, INIT_POSITIVE_PR);
        double negativePR = getProbability(words, negativeWords, INIT_NEGATIVE_PR);
        double neuterPR = getProbability(words, neuterWords, INIT_NEUTER_PR);

        if (positivePR > negativePR && positivePR > neuterPR) {
            return 1;
        }
        if (negativePR > positivePR && negativePR > neuterPR) {
            return -1;
        }
        return 0;
    }

    /**
     * 计算这个句子是哪一个类别的概率
     *
     * @param words
     * @return
     */
    private double getProbability(String[] words, Map<String, Double> prMap, double initPR) {
        double sentenceRate = 1.0;
        for (String word : words) {
            if (prMap.containsKey(word)) {
                sentenceRate *= prMap.get(word);
            } else {
                sentenceRate = sentenceRate * initPR;
            }
        }
        return sentenceRate;
    }

    public static void main(String[] args) {
        setData("./data/neuterResult.txt", neuterWords);
        setData("./data/positiveResult.txt", positiveWords);
        setData("./data/negativeResult.txt", negativeWords);
        File file = new File("./data/train/negative");
        File[] files = file.listFiles();

        WordSegmentation wordSegmentation = new WordSegmentation();
        String content = "";
        String segmentResult = "";
        try {
            int sum = 0;
            int num = 0;
            for (File item : files) {
                num++;
                content = FileUtil.readFileAllContents(item.getPath());
                segmentResult = wordSegmentation.ikAnalyzer(content).trim();
                String[] words = segmentResult.split("\\|");
                int res = new AnalyzeSentenceAffection().getSentenceAffection(words);
                if (1 == res) {
                    sum++;
                }
            }
            System.out.println("文件总数：" + num);
            System.out.println("negative数目：" + sum);
            System.out.println("准确率：" + sum * 1.0 / num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
