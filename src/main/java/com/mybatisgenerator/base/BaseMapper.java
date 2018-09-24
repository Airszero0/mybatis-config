package com.mybatisgenerator.base;

import java.util.List;

public interface BaseMapper<T extends BaseModel> {

    List<T> selectAll();

    List<T> selectByCondiction(T record);

    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
