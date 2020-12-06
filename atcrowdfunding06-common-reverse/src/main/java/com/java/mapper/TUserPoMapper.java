package com.java.mapper;

import com.java.po.TUserPo;
import com.java.po.TUserPoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUserPoMapper {
    int countByExample(TUserPoExample example);

    int deleteByExample(TUserPoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUserPo record);

    int insertSelective(TUserPo record);

    List<TUserPo> selectByExample(TUserPoExample example);

    TUserPo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUserPo record, @Param("example") TUserPoExample example);

    int updateByExample(@Param("record") TUserPo record, @Param("example") TUserPoExample example);

    int updateByPrimaryKeySelective(TUserPo record);

    int updateByPrimaryKey(TUserPo record);
}