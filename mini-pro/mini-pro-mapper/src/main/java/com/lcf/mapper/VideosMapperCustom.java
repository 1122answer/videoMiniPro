package com.lcf.mapper;

import com.lcf.pojo.Videos;
import com.lcf.pojo.vo.VideosVo;
import com.lcf.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface VideosMapperCustom extends MyMapper<Videos> {
    public List<VideosVo> queryAllVideos(@Param("videoDesc") String videoDesc);
    /**
     * @Description: 对视频喜欢的数量进行累加
     */
    public  void addVideoLikeCount(String videoId);

    /**
     * @Description: 对视频喜欢的数量进行累减
     */
    public  void reduceVideoLikeCount(String videoId);
}