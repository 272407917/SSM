package com.qu.crowd.services.api;

import com.github.pagehelper.PageInfo;
import com.qu.crowd.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-03 15:47
 */
public interface RoleService {
    PageInfo<Role> getRoleByKeyWord(Integer pageNum, Integer pageSize, String keyWord);

    void saveRole(Role role);

    void updateRole(Role role);

    void deleteRoleByIds(@Param("roleIds") List<Integer> roleIds);

    List<Role> getAssignedList(Integer adminId);

    List<Role> getunAssignedList(Integer adminId);

    void saveAssignRole(Integer adminId,Integer[] assignRoleId);
}
