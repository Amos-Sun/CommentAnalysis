package com.sun.moudles.crawl.parser;

import com.sun.moudles.bean.domain.VideoDO;

import java.io.IOException;
import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public interface IGetVideoDetail {

    List<VideoDO> getVideoInfo() throws IOException;
}
