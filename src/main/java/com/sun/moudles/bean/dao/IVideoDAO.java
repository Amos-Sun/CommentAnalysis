package com.sun.moudles.bean.dao;

import com.sun.moudles.bean.domain.VideoDO;
import com.sun.moudles.bean.po.VideoPO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by SunGuiyong on 2017/10/9.
 */
@Repository(value = "videoDAO")
public interface IVideoDAO {

    @Select("select * from video")
    List<VideoPO> getAllVideo();

    void insertVideoInfo(List<VideoPO> list);
}
