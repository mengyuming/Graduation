package com.graduation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.bean.Index;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface IndexDao extends BaseMapper<Index>{



    public void addSquestion(@Param("index") Index index);

    public void addTquestion(@Param("index")Index index);

    public CopyOnWriteArrayList<Index> getSquestionMyIndex(String usernumber);

    public CopyOnWriteArrayList<Index> getTquestionMyIndex(String usernumber);

    public CopyOnWriteArrayList<Index> getSquestionOtherIndex(String usernumber);

    public CopyOnWriteArrayList<Index> getTquestionOtherIndex(String usernumber);

    public CopyOnWriteArrayList<Index> getAllSquestionIndex();

    public CopyOnWriteArrayList<Index> getAllTquestionIndex();
}
