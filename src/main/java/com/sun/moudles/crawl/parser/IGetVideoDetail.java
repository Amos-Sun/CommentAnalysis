package com.sun.moudles.crawl.parser;

import com.sun.moudles.bean.domain.VideoDO;
import com.sun.moudles.bean.po.VideoPO;

import java.io.IOException;
import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
public interface IGetVideoDetail {

    List<VideoPO> getVideoInfo() throws IOException;
}
