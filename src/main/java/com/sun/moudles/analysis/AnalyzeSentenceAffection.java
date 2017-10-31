package com.sun.moudles.analysis;

import com.sun.moudles.util.FileUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
    private final double INIT_PR = 1 / 3.0;

    static{
        try {
            String key = FileUtil.readFileByLine("./data/positiveResult.txt");
            if(positiveWords.containsKey(key)){
                positiveWords.get(key);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static void initData(String filePath){

    }

    /**
     * 分析句子情感，sentence是分词之后的字符串
     *
     * @param sentence
     * @return
     */
    public int getSentenceAffection(String sentence) {

        return 0;
    }

    private double getPositiveProbability() {
        return 0;
    }
}
