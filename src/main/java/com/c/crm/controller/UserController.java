package com.c.crm.controller;

import com.c.crm.Model.UserModel;
import com.c.crm.base.BaseController;
import com.c.crm.base.ResultInfo;
import com.c.crm.query.UserQuery;
import com.c.crm.service.UserService;
import com.c.crm.utils.LoginUserUtil;
import com.c.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends BaseController {


    @Resource
    private UserService userService;

    /**
     * 用户登入
     *
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("user/login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd) {
        ResultInfo resultInfo = new ResultInfo();
        UserModel userModel = userService.userLogin(userName, userPwd);
        resultInfo.setCode(200);
        resultInfo.setResult(userModel);

        return resultInfo;
    }

    @PostMapping("user/updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request, String oldPwd,
                                         String newPwd, String repeatPwd) {
        ResultInfo resultInfo = new ResultInfo();
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updateUserPwd(userId, oldPwd, newPwd, repeatPwd);
        resultInfo.setCode(200);
        return resultInfo;
    }

    /**
     * 进入修改页面
     *
     * @return
     */
    @RequestMapping("user/toPasswordPage")
    public String toPasswordPage() {

        return "user/password";
    }

    @RequestMapping("/user/queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> queryAllSales() {
        return userService.queryAllSales();
    }

    @RequestMapping("/user/list")
    @ResponseBody
    public Map<String, Object> selectByParams(UserQuery userQuery) {
        return userService.queryByParamsForTable(userQuery);
    }

    @RequestMapping("user/index")
    public String index() {
        return "user/user";
    }

    @RequestMapping("user/add")
    @ResponseBody
    public ResultInfo addUser(User user) {
        userService.addUser(user);
        return success();
    }

    @RequestMapping("user/update")
    @ResponseBody
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        return success();
    }

    /**
     * 打开添加或修改页面
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("user/addOrUpdateUserPage")
    public String addOrUpdateUserPage(Integer id,HttpServletRequest req) {
        if (id != null) {
            User user=userService.selectByPrimaryKey(id);
            req.setAttribute("user",user);
        }
        return "user/add_update";
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @PostMapping("user/delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUser(ids);
        return success();
    }
}
