package com.c.crm.dao;

import com.c.crm.base.BaseMapper;
import com.c.crm.query.SaleChanceQuery;
import com.c.crm.vo.SaleChance;

import java.util.List;

public interface SaleChanceMapper extends BaseMapper<SaleChance , Integer> {
    List<SaleChance> querySaleChacneByParams(SaleChanceQuery saleChanceQuery);
}