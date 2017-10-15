package com.sun.moudles.crawl.dao;

import com.sun.moudles.crawl.po.VideoPO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by SunGuiyong on 2017/10/9.
 */
@Repository
public interface VideoDAO {

    @Select("select * from video")
    List<VideoPO> getAllVideo();
}
