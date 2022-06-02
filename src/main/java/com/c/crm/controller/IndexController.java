package com.c.crm.controller;

import com.c.crm.base.BaseController;
import com.c.crm.service.UserService;
import com.c.crm.utils.LoginUserUtil;
import com.c.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }


    // 系统界面欢迎页
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 后端管理主页面
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest req){
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(req);
        User user=userService.selectByPrimaryKey(userId);
        req.setAttribute("user",user);
        return "main";
    }
}
