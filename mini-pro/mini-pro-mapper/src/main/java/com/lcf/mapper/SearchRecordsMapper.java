package com.lcf.mapper;

import com.lcf.pojo.SearchRecords;
import com.lcf.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
     List<String> getHotwords();
}