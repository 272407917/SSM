package com.qu.crowd.mapper;

import com.qu.crowd.entity.Auth;
import com.qu.crowd.entity.AuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Auth> selectAuthByRoleId(@Param("roleId") Integer roleId);

    void deleteOldAuthByRoleId(@Param("roleId") Integer roleId);

    void insertNewAuthByRoleId(@Param("roleId") Integer roleId,@Param("authIds") Integer[] authIds);

    List<Auth> selectByAdminId(@Param("adminId") Integer adminId);
}