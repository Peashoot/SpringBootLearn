package com.peashoot.mybatis.mybatistest.control;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import com.peashoot.mybatis.mybatistest.entity.CookieMap;
import com.peashoot.mybatis.mybatistest.entity.EditUser;
import com.peashoot.mybatis.mybatistest.entity.RegisterUser;
import com.peashoot.mybatis.mybatistest.entity.User;
import com.peashoot.mybatis.mybatistest.service.IUserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class UserViewController {
    @Resource
    private IUserService userService;
    @Resource
    private CookieMap cookieMap;

    /***
     * 列出所有的对象
     * 
     * @param model   HTML的传入对象集合
     * @param request
     * @return
     */
    @GetMapping("list")
    public String listAll(Model model, HttpServletRequest request) {
        List<User> lst = userService.get();
        model.addAttribute("users", lst);
        return "pages/list";
    }

    @GetMapping("login")
    public String login() {
        return "pages/login";
    }

    /***
     * 用户登录处理
     * 
     * @param model    HTML的传入对象集合
     * @param response 储存cookie
     * @param account  用户账号
     * @param password 用户密码
     * @return
     */
    @PostMapping("login")
    public String login(Model model, HttpServletResponse response, String account, String password) {
        if (!userService.login(account, password)) {
            model.addAttribute("errMsg", "login failure");
            return "pages/login";
        }
        Cookie cookie = cookieMap.createCookie("login_session_id", "login_account_" + account);
        response.addCookie(cookie);
        return "redirect:list";
    }

    /***
     * 注销用户
     * 
     * @param response 装相应信息的类
     * @return
     */
    @RequestMapping(value = "logout", method = { RequestMethod.GET, RequestMethod.POST })
    public String logout(HttpServletResponse response) {
        Cookie cookie = cookieMap.createCookie("login_session_id", null, 0);
        response.addCookie(cookie);
        return "redirect:list";
    }

    /***
     * 获取注册页面
     * 
     * @return
     */
    @GetMapping("register")
    public String register() {
        return "pages/register";
    }

    /***
     * 处理用户注册提交的表单
     * 
     * @param model  html的ui集合
     * @param user   提交的信息
     * @param result 绑定信息的错误提示
     * @return
     */
    @PostMapping("register")
    public String register(Model model, @Valid RegisterUser user, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("errMsg", result.getFieldError().getDefaultMessage());
            model.addAttribute("user", user);
            return "pages/register";
        }
        User insertUser = new User();
        insertUser.setAccount(user.getAccount());
        insertUser.setUsername(user.getUsername());
        insertUser.setPassword(user.getPassword());
        insertUser.setEmailaddress(user.getEmailaddress());
        if (null == user || insertUser == null) {
            model.addAttribute("errMsg", "model can't be null");
            model.addAttribute("user", user);
            return "pages/register";
        }
        if (userService.insert(insertUser)) {
            return "redirect:list";
        } else {
            model.addAttribute("errMsg", "register failure");
            model.addAttribute("user", user);
            return "pages/register";
        }
    }

    /***
     * 打开用户编辑页面
     * 
     * @param model HTML的传入对象集合
     * @param id    用户id
     * @return
     */
    @GetMapping("edit")
    public String edit(Model model, @PathParam("id") int id) {
        User user = userService.get(id);
        if (user == null) {
            return "redirect:error";
        }
        model.addAttribute("user", user);
        return "pages/edit";
    }

    /***
     * 用户提交编辑表单后的处理
     * 
     * @param model  HTML的传入对象集合
     * @param user   传入的用户信息
     * @param result 绑定属性时的自动校验结果
     * @return
     */
    @PostMapping(value = "edit")
    public String edit(Model model, @ModelAttribute("user") @Valid EditUser user, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("errMsg", result.getFieldError().getDefaultMessage());
            model.addAttribute("user", user);
            return "pages/edit";
        }
        User updateUser = userService.get(user.getId());
        if (user == null || updateUser == null) {
            model.addAttribute("errMsg", "Model can't be null");
            model.addAttribute("user", user);
            return "pages/edit";
        }
        updateUser.setAccount(user.getAccount());
        updateUser.setUsername(user.getUsername());
        updateUser.setEmailaddress(user.getEmailaddress());
        if (userService.update(updateUser)) {
            return "redirect:list";
        } else {
            model.addAttribute("errMsg", "update failure");
            model.addAttribute("user", user);
            return "pages/edit";
        }
    }

    /***
     * 删除用户信息
     * 
     * @param model HTML的传入对象集合
     * @param id    要删除的用户Id
     * @return
     */
    @GetMapping("delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        User user = userService.get(id);
        if (user == null) {
            return "redirect:error";
        }
        model.addAttribute("user", user);
        return "pages/delete";
    }

    /***
     * 确定删除用户的处理
     * 
     * @param id 用户id
     * @return
     */
    @PostMapping("delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:list";
    }
}