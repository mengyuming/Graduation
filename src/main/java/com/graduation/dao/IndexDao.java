package com.graduation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.bean.Index;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndexDao extends BaseMapper<Index>{
    public void addAllIndex(Index indexsystem);

    public List<Index> getMyIndex(String usernumber);

    public List<Index> getOtherIndex(String usernumber);

    public List<Index> getAllIndex();
}
