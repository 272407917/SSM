package com.qu.crowd.services.api;

import com.qu.crowd.entity.Menu;

import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-06 11:24
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void removeMenu(Integer id);

    void updateMenu(Menu menu);
}
