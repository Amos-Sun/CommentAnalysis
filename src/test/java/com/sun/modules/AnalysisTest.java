package com.sun.modules;

import com.sun.modules.analysis.AnalyzeSentenceAffection;
import com.sun.modules.analysis.WordSegmentation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class AnalysisTest {

    @Test
    public void analysisTest() {
        WordSegmentation wordSegmentation = new WordSegmentation();
        AnalyzeSentenceAffection analyzeSentenceAffection = new AnalyzeSentenceAffection();
        try {
            String sentence = wordSegmentation.ikAnalyzer("很好");
            String[] words = sentence.split("\\|");
            int prResult = analyzeSentenceAffection.getSentenceAffection(words);
            System.out.println(prResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
