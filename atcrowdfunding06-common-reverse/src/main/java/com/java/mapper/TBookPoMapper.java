package com.java.mapper;

import com.java.po.TBookPo;
import com.java.po.TBookPoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBookPoMapper {
    int countByExample(TBookPoExample example);

    int deleteByExample(TBookPoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TBookPo record);

    int insertSelective(TBookPo record);

    List<TBookPo> selectByExample(TBookPoExample example);

    TBookPo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TBookPo record, @Param("example") TBookPoExample example);

    int updateByExample(@Param("record") TBookPo record, @Param("example") TBookPoExample example);

    int updateByPrimaryKeySelective(TBookPo record);

    int updateByPrimaryKey(TBookPo record);
}