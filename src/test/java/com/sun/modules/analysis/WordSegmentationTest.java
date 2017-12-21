package com.sun.modules.analysis;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WordSegmentationTest {

    //WordSegmentation segmentation = new WordSegmentation();
    @InjectMocks
    private WordSegmentation segmentation;

    //或者使用 @RunWith(MockitoJUnitRunner.class)  进行mock的注入
    @BeforeTest
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void smartcnPartWords() throws Exception {
        segmentation.smartcnPartWords("这是个测试");
    }

    @Test
    public void ikAnalyzer() throws Exception {
        segmentation.ikAnalyzer("");

        segmentation.ikAnalyzer(null);
        segmentation.ikAnalyzer("testng测试");
        segmentation.ikAnalyzer("testng测试，刘亦菲");
    }

}