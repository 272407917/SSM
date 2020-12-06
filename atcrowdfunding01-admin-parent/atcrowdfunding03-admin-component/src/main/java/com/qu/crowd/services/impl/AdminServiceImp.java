package com.qu.crowd.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qu.crowd.constant.CrowdConstant;
import com.qu.crowd.entity.Admin;
import com.qu.crowd.entity.AdminExample;
import com.qu.crowd.entity.Role;
import com.qu.crowd.exception.LoginAcctAlreadyExisted;
import com.qu.crowd.exception.LoginFailedException;
import com.qu.crowd.mapper.AdminMapper;
import com.qu.crowd.services.api.AdminService;
import com.qu.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 瞿琮
 * @create 2020-06-30 18:04
 */
@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired     //CrowdSecurityConfig配置类中的Bean带盐的加密
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin getAdminByLoginAcctAndPaswd(String loginAcct, String userPswd) {
        //1、查询数据库中同loginAcct的记录
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        //2、判断是否有相同记录
        if (admins==null||admins.size()<1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //3、有相同记录的话，就进行密码比较,这里不考虑相同账户名称
        Admin admin = admins.get(0);
        if (userPswd!=null&&""!=userPswd){
            //3.1、对传入的账户密码进行加密
            String md5Pswd = CrowdUtil.md5(userPswd);
            //3.2、与数据库中加密的密码进行比较,这里不使用md5Pswd.equals(admin.getUserPswd())
            //     是为了防止传入的userPswd是null造成空指针异常
            if (!Objects.equals(admin.getUserPswd(),md5Pswd)){
                throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
            }
        }
        return admin;
    }

    @Override
    public PageInfo<Admin> getAdminByKeyWord(Integer pageNum, Integer pageSize, String keyWord) {
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Admin> admins = adminMapper.selectByKeyWord(keyWord);
        //使用PageInfo存放信息,第二个参数5表示，页面底部导航条连续数的个数
        PageInfo<Admin> pageInfo = new PageInfo<>(admins,5);
        return pageInfo;
    }

    @Override
    public Integer removeAdminById(Integer id) {
        int delete = adminMapper.deleteByPrimaryKey(id);
        return delete;
    }

    @Override
    public Integer saveAdmin(Admin admin) {
        //1、判断新增的账户名是否已存在
        List<Admin> admins = validateLoginAcctIsAlready(admin);
        if (admins != null && admins.size()>0){
            throw new LoginAcctAlreadyExisted(CrowdConstant.MESSAGE_LOGINACCT_REPETITON);
        }

        //2、如果账户名不存在
        //2.1、初始密码为123456，因为数据库中约束字段不能为空
        String encode = passwordEncoder.encode("123456");
        //String md5Psw = CrowdUtil.md5("123456");
        admin.setUserPswd(encode);
        //2.2、写入录入时间，并格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String newDate = simpleDateFormat.format(new Date());
        admin.setCreateTime(newDate);
        int insert = adminMapper.insert(admin);
        return insert;
    }

    //抽取的判断用户名是否相同的方法
    private List<Admin> validateLoginAcctIsAlready(Admin admin) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(admin.getLoginAcct());
        return adminMapper.selectByExample(adminExample);
    }

    //修改操作
    @Override
    public Integer updateAdmin(Admin admin, String originalLoginAcct) {
        //1、判断，账户名是否被修改(去掉空格)
        if (!Objects.equals(admin.getLoginAcct().trim(),originalLoginAcct)){
            //1.2、如果账户名被修改，判断修改后的用户名是否重复
            List<Admin> adminList = validateLoginAcctIsAlready(admin);
            if (adminList != null && adminList.size()>0){
                throw new LoginAcctAlreadyExisted(CrowdConstant.MESSAGE_LOGINACCT_REPETITON_EDIT);
            }
        }
        int update = adminMapper.updateByPrimaryKey(admin);
        return update;
    }

    @Override
    public Admin getAdminByAdminId(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }


}
