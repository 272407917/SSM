package com.qu.crowd.services.impl;

import com.qu.crowd.entity.Menu;
import com.qu.crowd.mapper.MenuMapper;
import com.qu.crowd.services.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-06 11:24
 */
@Service
public class MenuServiceImp implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        List<Menu> menuList = menuMapper.selectByExample(null);
        return menuList;
    }

    @Override
    public void saveMenu(Menu menu) {
        //insertSelective只会给有值的属性赋值
        menuMapper.insertSelective(menu);
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }
}
