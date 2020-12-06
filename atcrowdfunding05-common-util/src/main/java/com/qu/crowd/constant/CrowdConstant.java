package com.qu.crowd.constant;

/**
 * @author 瞿琮
 * @create 2020-06-30 16:52
 *
 * 这个类是一个常量类，通过静态常量调用需要的信息
 * 这样可以防止重复使用一些固定信息时输入错误
 */
public class CrowdConstant {
    /**
     * 静态常量书写必须全大写
     * ATTR:属性
     * NAME：名称
     * EXCEPTION:异常
     */
    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_ADMIN = "admin";
    public static final String ATTR_NAME_MEMBER = "memberLogin";
    public static final String ATTR_NAME_TEMPLE_PROJECT = "tempProject";
    public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX";
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final String ATTR_NAME_PORTAL_DATA = "portal_data";
    public static final String ATTR_NAME_DETAIL_PROJECT_DATA = "detailProjectVO";

    public static final String MESSAGE_LOGIN_FAILED = "登录失败，请检查账号密码是否正确！";
    public static final String MESSAGE_INVALID_STRING = "输入字符串无效，请重新输入！";
    public static final String MESSAGE_ACCESS_FORBIDDEN= "访问资源受限，请先登录！";
    public static final String MESSAGE_LOGINACCT_REPETITON= "账户名已存在，请重新输入！";
    public static final String MESSAGE_LOGINACCT_NOT_EXISTS= "账户名密码不正确，请重新输入！";
    public static final String MESSAGE_LOGINACCT_REPETITON_EDIT= "修改的账户名已存在，请重新输入！";
    public static final String MESSAGE_SELECT_FAILED= "查询数据失败，亲联系管理员！";
    public static final String MESSAGE_INSERT_FAILED= "新增数据数据失败，请联系管理员！";
    public static final String MESSAGE_UPDATE_FAILED= "修改数据数据失败，请联系管理员！";
    public static final String MESSAGE_DELETE_FAILED= "删除数据数据失败，请联系管理员！";
    public static final String MESSAGE_CODE_NOT_EXISTS= "验证码失效，请检查手机号重新发送！";
    public static final String MESSAGE_CODE_INVALID= "验证码输入不正确，请重新输入！";
    public static final String MESSAGE_HEADER_PIC_EMPTY= "头图片为空，请重新上传！";
    public static final String MESSAGE_DETAIL_PIC_EMPTY= "详情图片为空，请重新上传！";
    public static final String MESSAGE_RETURN_PIC_EMPTY= "回报图片为空，请重新上传！";
    public static final String MESSAGE_HEADER_PIC_UPLOAD_FAILED= "头图片上传失败，请重新上传！";
    public static final String MESSAGE_DETAIL_PIC_UPLOAD_FAILED= "详情图片上传失败，请重新上传！";
    public static final String MESSAGE_TEMPLE_PROJECT_MISSING= "session中临时projectVO对象失效！";


}
