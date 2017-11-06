package com.sun.moudles.crawl.parser.impl;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GetVideoDetailTest {

    @InjectMocks
    GetVideoDetail getVideoDetail;

    @Mock
    AbstractApplicationContext ctx;


    @BeforeTest
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetVideoInfo() throws Exception {
    }

}