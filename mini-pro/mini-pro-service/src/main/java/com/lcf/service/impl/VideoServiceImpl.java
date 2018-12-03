package com.lcf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcf.mapper.BgmMapper;
import com.lcf.mapper.SearchRecordsMapper;
import com.lcf.mapper.VideosMapper;
import com.lcf.mapper.VideosMapperCustom;
import com.lcf.pojo.Bgm;
import com.lcf.pojo.SearchRecords;
import com.lcf.pojo.Videos;
import com.lcf.pojo.vo.VideosVo;
import com.lcf.service.BgmService;
import com.lcf.service.VideoService;
import com.lcf.utils.PagedResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideosMapper videosMapper;
    @Autowired
    private VideosMapperCustom videosMapperCustom;
    @Autowired
    private SearchRecordsMapper searchRecordsMapper;
    @Autowired
    private Sid sid;
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveVideo(Videos video) {
        String id=sid.nextShort();
        video.setId(id);
        videosMapper.insertSelective(video);

    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PagedResult getAllVideos(Videos video,Integer isSaveRecord,Integer page, Integer pageSize) {
        String desc = video.getVideoDesc();
        //保存热搜词
        if( isSaveRecord == 1){
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            searchRecordsMapper.insert(record);
        }
        PageHelper.startPage(page,pageSize);
        List<VideosVo> list = videosMapperCustom.queryAllVideos(desc);

        PageInfo<VideosVo> pageList=new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getHotWords() {

        return searchRecordsMapper.getHotWords();
    }

}
