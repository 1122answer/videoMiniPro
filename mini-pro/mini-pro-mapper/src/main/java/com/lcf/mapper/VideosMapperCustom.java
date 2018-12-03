package com.lcf.mapper;

import com.lcf.pojo.Videos;
import com.lcf.pojo.vo.VideosVo;
import com.lcf.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface VideosMapperCustom extends MyMapper<Videos> {
    public List<VideosVo> queryAllVideos(@Param("videoDesc") String videoDesc);
}