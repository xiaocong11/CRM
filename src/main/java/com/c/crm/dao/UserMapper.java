package com.c.crm.dao;

import com.c.crm.base.BaseMapper;
import com.c.crm.vo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User, Integer> {

    //通过用户名查询用户对象，返回用户对象
    User queryUserByName(String userName);

    int updateUserPwd(@Param("userId") Integer userId, @Param("userPwd") String newPwd);

    List<Map<String,Object>> queryAllSales();
}