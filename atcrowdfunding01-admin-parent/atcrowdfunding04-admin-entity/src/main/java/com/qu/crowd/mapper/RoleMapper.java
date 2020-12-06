package com.qu.crowd.mapper;

import com.qu.crowd.entity.Role;
import com.qu.crowd.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByKeyWord(@Param("keyWord") String keyWord);

    void deleteRoleByIds(List<Integer> roleIds);

    List<Role> selectAssignedList(@Param("adminId") Integer adminId);

    List<Role> selectunAssignedList(@Param("adminId") Integer adminId);

    void deleteOldRoleByAdminId(@Param("adminId") Integer adminId);

    void saveNewAssignRole(@Param("adminId") Integer adminId, @Param("assignRoleIds") Integer[] assignRoleId);
}