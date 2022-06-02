package com.c.crm.controller;

import com.c.crm.base.BaseController;
import com.c.crm.base.ResultInfo;
import com.c.crm.query.CusDevPlanQuery;
import com.c.crm.service.CusDevPlanService;
import com.c.crm.service.SaleChanceService;
import com.c.crm.vo.CusDevPlan;
import com.c.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;
    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("index")
    public String index() {
        return "cusDevPlan/cus_dev_plan";
    }

    @RequestMapping("toCusDevPlanDataPage")
    public String toCusDevPlanDataPage(Integer sid, Model model) {
        //通过营销计划查对象
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(sid);
        model.addAttribute("saleChance", saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> selectByParams(CusDevPlanQuery cusDevPlanQuery) {
        return cusDevPlanService.queryByParamsForTable(cusDevPlanQuery);
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan) {
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return success();
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo updaetCusDevPlan(CusDevPlan cusDevPlan) {
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success();
    }

    @RequestMapping("addOrUpdateCusDevPlanPage")
    public String toCusDevPlanPage(Integer sid,Integer id, HttpServletRequest req) {
        req.setAttribute("sid",sid);
        if (id != null) {
            CusDevPlan cusDevPlan= cusDevPlanService.selectByPrimaryKey(id);
            req.setAttribute("cusDevPlan",cusDevPlan);
        }
        return "cusDevPlan/add_update";
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer cusDevPlanId){
        cusDevPlanService.deleteCusDevPlan(cusDevPlanId);
        return success();
    }

    @PostMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateDevResult(id,devResult);
        return success();
    }
}
