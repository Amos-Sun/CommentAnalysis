package com.sun.modules.show.service;

import com.sun.modules.bean.vo.VideoVO;

import java.util.List;

public interface IVideoService {

    /**
     * 获取所有电影
     *
     * @return
     */
    List<VideoVO> getAllVideos();

    /**
     * 根据电影的好评百分比返回数据
     *
     * @param pageNum  当前是第几页
     * @param pageSize 一页有多少数据
     * @return
     */
    List<VideoVO> getVideosByGoodPercent(Integer pageNum, Integer pageSize);

    /**
     * 根据电影的男生好评百分比返回数据
     *
     * @param pageNum  当前是第几页
     * @param pageSize 一页有多少数据
     * @return
     */
    List<VideoVO> getVideosBySexGood(String sex, Integer pageNum, Integer pageSize);

    /**
     * 获取所有电影的数量
     *
     * @return
     */
    int getTotalNum();
}
