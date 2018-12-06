package com.lcf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcf.mapper.*;
import com.lcf.pojo.*;
import com.lcf.pojo.vo.CommentsVO;
import com.lcf.pojo.vo.VideosVo;
import com.lcf.service.BgmService;
import com.lcf.service.VideoService;
import com.lcf.utils.PagedResult;
import com.lcf.utils.TimeAgoUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
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
    private CommentsMapper commentsMapper;
    @Autowired
    private CommentsMapperCustom commentMapperCustom;
    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;
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
        if(isSaveRecord!=null && isSaveRecord == 1){
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
    public List<String> getHotwords() {
        return searchRecordsMapper.getHotwords();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {
        String id = sid.nextShort();
        comment.setId(id);
        comment.setCreateTime(new Date());
        commentsMapper.insert(comment);

    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        List<CommentsVO> list = commentMapperCustom.queryComments(videoId);

        for (CommentsVO c : list) {
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgoStr(timeAgo);
        }

        PageInfo<CommentsVO> pageList = new PageInfo<>(list);
        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(list);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }

    @Override
    public void saveLike(String userId, String videoId) {
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        String id = sid.nextShort();
        usersLikeVideos.setId(id);
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);
        usersLikeVideosMapper.insert(usersLikeVideos);
        videosMapperCustom.addVideoLikeCount(videoId);
    }

    @Override
    public void deleteLike(String userId, String videoId) {
        Example likeExample = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = likeExample.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        usersLikeVideosMapper.deleteByExample(likeExample);
        videosMapperCustom.reduceVideoLikeCount(videoId);
    }

}
