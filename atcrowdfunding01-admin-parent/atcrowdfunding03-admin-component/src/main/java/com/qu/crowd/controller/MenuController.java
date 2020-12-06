package com.qu.crowd.controller;

import com.qu.crowd.constant.CrowdConstant;
import com.qu.crowd.entity.Menu;
import com.qu.crowd.services.api.MenuService;
import com.qu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.plugin.dom.core.CoreConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 瞿琮
 * @create 2020-07-06 11:25
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/getAll.json")
    @ResponseBody
    public ResultEntity getAllMenu(){

       //获取所有的数据，并且找出父节点，将各自的子节点加进去
        try {
            List<Menu> menuList = menuService.getAll();
            //将每个menu的id作为key值存放在HashMap中
            Map<Integer,Menu> menuMap = new HashMap<>();
            for (Menu menu: menuList) {
                menuMap.put(menu.getId(),menu);
            }
            //找到根节点，存放子节点
            Menu root = null;
            for (Menu menu:menuList) {
                if (menu.getpId() == null){
                    //说明该数据是根节点
                    root = menu;
                    continue;
                }
                //通过menu的pid取出它的父节点
                Menu parentMenu = menuMap.get(menu.getpId());
                //将子节点加入到属性中
                parentMenu.getChildren().add(menu);
            }
            //根节点中存放了所有的节点
            return ResultEntity.successWithData(root);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_SELECT_FAILED);
        }
    }


    @RequestMapping("/do/add.json")
    @ResponseBody
    public ResultEntity addMenu(Menu menu){
        try {
            menuService.saveMenu(menu);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_INSERT_FAILED);
        }
    }


    @RequestMapping("/do/delete.json")
    @ResponseBody
    public ResultEntity deleteMenu(Integer id){
        try {
            menuService.removeMenu(id);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_DELETE_FAILED);
        }
    }

    @RequestMapping("/do/edit.json")
    @ResponseBody
    public ResultEntity editMenu(Menu menu){
        try {
            menuService.updateMenu(menu);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_UPDATE_FAILED);
        }
    }
}
