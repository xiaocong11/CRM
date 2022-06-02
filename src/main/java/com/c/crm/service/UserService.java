package com.c.crm.service;

import com.c.crm.Model.UserModel;
import com.c.crm.base.BaseService;
import com.c.crm.dao.UserMapper;
import com.c.crm.utils.AssertUtil;
import com.c.crm.utils.Md5Util;
import com.c.crm.utils.PhoneUtil;
import com.c.crm.utils.UserIDBase64;
import com.c.crm.vo.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User, Integer> {

    @Resource
    private UserMapper userMapper;

    public UserModel userLogin(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName) || StringUtils.isBlank(userPwd), "用户或账号不能为空");
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(user == null, "该用户不存在！");
        //将前台传递的密码，通过MD5算法加密
        String pwd = Md5Util.encode(userPwd);
        //将加密后的密码与数据库中查询用户对象中的密码作比较
        AssertUtil.isTrue(!pwd.equals(user.getUserPwd()), "用户密码错误");

        return buildUserModel(user);
    }

    private UserModel buildUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPwd(Integer userId, String oldPwd, String newPwd, String repeatPwd) {
        AssertUtil.isTrue(userId == null, "待更新用户不存在");
        User user = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(user == null, "待更新用户不存在");
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd), "原始密码不能为空");
        AssertUtil.isTrue(!Md5Util.encode(oldPwd).equals(user.getUserPwd()), "原始密码不正确！");
        AssertUtil.isTrue(StringUtils.isBlank(newPwd), "新密码不能为空！");
        AssertUtil.isTrue(newPwd.equals(oldPwd), "新密码与原始密码一致！");
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd), "重复密码不能为空！");
        AssertUtil.isTrue(!newPwd.equals(repeatPwd), "重复密码与新密码不一致！");
        AssertUtil.isTrue(userMapper.updateUserPwd(userId, Md5Util.encode(newPwd)) < 1, "修改密码失败！");
    }

    public List<Map<String, Object>> queryAllSales() {
        return userMapper.queryAllSales();
    }

    /**
     * 添加用户
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        checkUserParams(user);

        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1, "用户添加失败！");
    }

    /**
     * 修改用户
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        AssertUtil.isTrue(null == user.getId(), "待更新记录不存在！");

        checkUserParams(user);

        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "用户数据更新失败！");
    }

    private void checkUserParams(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空！");
        User temp = userMapper.queryUserByName(user.getUserName());
        if (user.getId() == null) {
            AssertUtil.isTrue(null != temp, "用户名已存在，请重新输入！");
        } else {
            AssertUtil.isTrue(null != temp && !temp.getId().equals(user.getId()), "用户名已存在，请重新输入！");
        }
        AssertUtil.isTrue(null != temp, "用户名已存在，请重新输入！");
        AssertUtil.isTrue(StringUtils.isBlank(user.getTrueName()), "真实姓名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()), "邮箱不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()), "手机号不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()), "手机格式不正确！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(Integer[] ids) {
        AssertUtil.isTrue(null == ids || ids.length < 1, "待删除记录不存在！");
        AssertUtil.isTrue(userMapper.deleteBatch(ids) != ids.length, "用户删除失败！");

    }
}
