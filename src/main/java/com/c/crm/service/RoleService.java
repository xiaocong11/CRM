package com.c.crm.service;

import com.c.crm.base.BaseService;
import com.c.crm.dao.RoleMapper;
import com.c.crm.vo.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {
    @Resource
    private RoleMapper roleMapper;

    public List<Map<String,Object>> queryAllRoles(){
        return roleMapper.queryAllRoles();
    }
}
