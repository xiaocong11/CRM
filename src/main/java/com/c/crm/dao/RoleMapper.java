package com.c.crm.dao;

import com.c.crm.base.BaseMapper;
import com.c.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    //查询所有角色
    public List<Map<String,Object>> queryAllRoles();
}