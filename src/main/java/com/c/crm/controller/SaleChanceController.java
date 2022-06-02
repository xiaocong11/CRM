package com.c.crm.controller;

import com.c.crm.base.BaseController;
import com.c.crm.base.ResultInfo;
import com.c.crm.query.SaleChanceQuery;
import com.c.crm.service.SaleChanceService;
import com.c.crm.utils.CookieUtil;
import com.c.crm.utils.LoginUserUtil;
import com.c.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryByParams(SaleChanceQuery saleChanceQuery,
                                             Integer flag, HttpServletRequest req) {
        if (null != flag && flag == 1) {
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
            saleChanceQuery.setAssignMan(userId);
        }
        return saleChanceService.queryByParams(saleChanceQuery);
    }

    @RequestMapping("index")
    public String index() {

        return "saleChance/sale_chance";
    }

    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest req) {
        String userName = CookieUtil.getCookieValue(req, "userName");
        saleChance.setCreateMan(userName);
        saleChanceService.addSaleChance(saleChance);
        return success();
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance) {
        saleChanceService.updateSaleChance(saleChance);
        return success();
    }

    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(HttpServletRequest req, Integer id) {
        if (id != null) {
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            req.setAttribute("saleChance", saleChance);
        }

        return "saleChance/add_update";
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids) {
        saleChanceService.deleteSaleChance(ids);
        return success();
    }
}
