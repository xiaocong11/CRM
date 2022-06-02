package com.c.crm.service;

import com.c.crm.base.BaseService;
import com.c.crm.dao.CusDevPlanMapper;
import com.c.crm.dao.SaleChanceMapper;
import com.c.crm.utils.AssertUtil;
import com.c.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan, Integer> {

    @Resource
    private CusDevPlanMapper cusDevPlanMapper;

    @Resource
    private SaleChanceMapper saleChanceMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void addCusDevPlan(CusDevPlan cusDevPlan) {
        checkCusDevPlanParams(cusDevPlan);

        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        cusDevPlan.setIsValid(1);
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan) < 1, "添加失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        AssertUtil.isTrue(null == cusDevPlan.getId(), "id不能为空");
        checkCusDevPlanParams(cusDevPlan);
        cusDevPlan.setIsValid(1);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan) < 1, "更新失败");
    }

    private void checkCusDevPlanParams(CusDevPlan cusDevPlan) {
        AssertUtil.isTrue(null == cusDevPlan.getSaleChanceId(), "营销数据不存在");
        AssertUtil.isTrue(null == saleChanceMapper.selectByPrimaryKey(cusDevPlan.getSaleChanceId()), "营销数据不存在");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()), "计划内容不能为空");
        AssertUtil.isTrue(null == cusDevPlan.getPlanDate(), "计划时间不能为空");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCusDevPlan(Integer cusDevPlanId) {
        AssertUtil.isTrue(null==cusDevPlanId,"待删除记录不存在！");
        CusDevPlan cusDevPlan=cusDevPlanMapper.selectByPrimaryKey(cusDevPlanId);
        AssertUtil.isTrue(null==cusDevPlan,"待删除记录不存在");
        cusDevPlan.setIsValid(0);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)<1,"计划删除失败！");
    }


}
