package com.qu.crowd.services.api;

import com.qu.crowd.entity.Admin;

/**
 * @author 瞿琮
 * @create 2020-06-28 22:42
 */

public interface TestService {
    Admin findAdmin(Integer id);

    Integer updateAdmin(Admin admin);
}
