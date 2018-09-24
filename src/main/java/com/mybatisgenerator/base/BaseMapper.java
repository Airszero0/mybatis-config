package com.mybatisgenerator.base;

import java.util.List;

public interface BaseMapper<T extends BaseModel,K extends String> {

    List<T> selectByStringCondition(String condition);

    List<T> selectByCondition(T record);

    int deleteByGuid(K guid);

    int insert(T record);

    int insertSelective(T record);

    T selectByGuid(K guid);

    int updateByGuidSelective(T record);

    int updateByGuid(T record);
}
