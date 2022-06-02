package com.c.crm.service;

import com.c.crm.base.BaseService;
import com.c.crm.dao.SaleChanceMapper;
import com.c.crm.enums.DevResult;
import com.c.crm.enums.StateStatus;
import com.c.crm.query.SaleChanceQuery;
import com.c.crm.utils.AssertUtil;
import com.c.crm.utils.PhoneUtil;
import com.c.crm.vo.SaleChance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance, Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    public Map<String, Object> queryByParams(SaleChanceQuery saleChanceQuery) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        PageHelper.startPage(saleChanceQuery.getPage(), saleChanceQuery.getLimit());
        List<SaleChance> list = saleChanceMapper.querySaleChacneByParams(saleChanceQuery);
        PageInfo<SaleChance> pageInfo = new PageInfo<>(list);
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance) {
        checkSaleChanceParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        if (StringUtils.isBlank(saleChance.getAssignMan())) {
            saleChance.setAssignTime(null);
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        } else {
            saleChance.setAssignTime(new Date());
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }

        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());


        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance) < 1, "营销数据添加失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance) {
        AssertUtil.isTrue(null == saleChance.getId(), "待更新记录不存在！");
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");

        checkSaleChanceParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        saleChance.setUpdateDate(new Date());

        if (StringUtils.isBlank(temp.getAssignMan())) {
            if (!StringUtils.isBlank(saleChance.getAssignMan())) {
                saleChance.setAssignTime(new Date());
                saleChance.setState(StateStatus.STATED.getType());
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            }
        } else {
            if (StringUtils.isBlank(saleChance.getAssignMan())) {
                saleChance.setAssignTime(null);
                saleChance.setState(StateStatus.UNSTATE.getType());
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            } else {
                if (!temp.getAssignMan().equals(saleChance.getAssignMan())) {
                    saleChance.setAssignTime(new Date());
                } else {
                    saleChance.setAssignTime(temp.getAssignTime());
                }
            }
        }

        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) < 1, "营销数据修改失败");
    }

    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName), "客户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "联系人不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone), "联系号码不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone), "联系人格式不正确！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChance(Integer[] ids) {
        AssertUtil.isTrue(null == ids || ids.length < 1, "待删除记录不存在！");
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids) != ids.length, "删除失败！");

    }

    public void updateDevResult(Integer id, Integer devResult) {
        AssertUtil.isTrue(null == id, "待更新记录不存在");
        AssertUtil.isTrue(null == devResult, "系统异常，请重试");
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == saleChance, "待更新记录不存在");
        saleChance.setDevResult(devResult);
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) < 1, "更新状态失败");
    }
}
