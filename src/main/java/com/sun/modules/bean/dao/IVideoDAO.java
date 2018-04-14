package com.sun.modules.bean.dao;

import com.sun.modules.bean.po.VideoPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by SunGuiyong on 2017/10/9.
 */
@Repository(value = "videoDAO")
public interface IVideoDAO {

    @Select("select count(*) from video")
    int getTotalRecord();

    @Select("select * from video")
    List<VideoPO> getAllVideo();

    @Select("select * from video order by good_percent desc,time desc limit #{pageNum},#{pageSize}")
    List<VideoPO> getOrderByGoodPercent(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    @Select("select * from video order by man_good_percent desc,time desc limit #{pageNum}, #{pageSize}")
    List<VideoPO> getOrderByManGood(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    @Select("select * from video order by woman_good_percent desc,time desc limit #{pageNum}, #{pageSize}")
    List<VideoPO> getOrderByWomanGood(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    @Select("select cid from video")
    List<String> getAllCid();

    @Select("select * from video where cid=#{cid}")
    VideoPO getByCid(@Param("cid") String cid);

    void insertVideoInfo(List<VideoPO> list);
}
