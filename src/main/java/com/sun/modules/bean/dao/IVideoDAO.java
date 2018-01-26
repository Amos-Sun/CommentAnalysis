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

    @Select("select * from video")
    List<VideoPO> getAllVideo();

    @Select("select * from video where id>=#{pageNum}  limit #{pageSize}")
    List<VideoPO> getBySize(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    @Select("select cid from video")
    List<String> getAllCid();

    void insertVideoInfo(List<VideoPO> list);
}
