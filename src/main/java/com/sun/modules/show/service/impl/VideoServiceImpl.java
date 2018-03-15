package com.sun.modules.show.service.impl;

import com.sun.modules.bean.dao.IVideoDAO;
import com.sun.modules.bean.vo.VideoVO;
import com.sun.modules.bean.po.VideoPO;
import com.sun.modules.show.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private IVideoDAO videoDAO;

    @Override
    public List<VideoVO> getAllVideos() {

        List<VideoPO> videoPOList = videoDAO.getAllVideo();
        if (CollectionUtils.isEmpty(videoPOList)) {
            return null;
        }
        return toVOList(videoPOList);
    }

    @Override
    public List<VideoVO> getVideosByGoodPercent(Integer pageNum, Integer pageSize) {
        int num = (pageNum - 1) * pageSize;
        List<VideoPO> poList = videoDAO.getOrderByGoodPercent(num, pageSize);
        return toVOList(poList);
    }

    @Override
    public List<VideoVO> getVideosByManGood(Integer pageNum, Integer pageSize) {
        int num = (pageNum - 1) * pageSize;
        List<VideoPO> poList = videoDAO.getOrderByManGood(num, pageSize);
        return toVOList(poList);
    }

    @Override
    public List<VideoVO> getVideosByWomanGood(Integer pageNum, Integer pageSize) {
        int num = (pageNum - 1) * pageSize;
        List<VideoPO> poList = videoDAO.getOrderByWomanGood(num, pageSize);
        return toVOList(poList);
    }

    @Override
    public int getTotalNum() {
        return videoDAO.getTotalRecord();
    }

    /**
     * 把PO转成VO
     *
     * @param poList po列表
     * @return 转换之后的list
     */
    private List<VideoVO> toVOList(List<VideoPO> poList) {
        List<VideoVO> list = new ArrayList<>();
        for (VideoPO item : poList) {
            VideoVO videoVO = new VideoVO(item);
            list.add(videoVO);
        }
        return list;
    }
}
