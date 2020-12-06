package com.qu.crowd.mapper;

import com.qu.crowd.entity.Admin;
import com.qu.crowd.entity.AdminExample;
import com.qu.crowd.entity.Role;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface AdminMapper {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    //自定义查询，按keyWord进行模糊查询
    List<Admin> selectByKeyWord(@Param("keyWord") String keyWord);

}