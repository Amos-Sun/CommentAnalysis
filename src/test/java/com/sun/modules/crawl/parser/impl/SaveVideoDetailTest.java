package com.sun.modules.crawl.parser.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.AbstractApplicationContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SaveVideoDetailTest {

    @InjectMocks
    SaveVideoDetail saveVideoDetail;

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