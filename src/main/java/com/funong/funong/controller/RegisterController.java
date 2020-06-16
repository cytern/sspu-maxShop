package com.funong.funong.controller;

import com.funong.funong.plugin.ChangeDate;
import com.funong.funong.plugin.MailTool;
import com.funong.funong.pojo.Customer;
import com.funong.funong.pojo.User;
import com.funong.funong.service.CustomerService;
import com.funong.funong.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author: cytern
 * @Date: 2020/6/15 13:19
 */
@Api(value = "注册分发器")
@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    ChangeDate changeDate = new ChangeDate();
    @Autowired
    private MailTool mailTool;
    @ApiOperation(value = "注册用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName",value = "用户名"),@ApiImplicitParam(name = "password",value = "密码"),@ApiImplicitParam(name = "email",value = "邮箱")})
    @RequestMapping("everyOne/addCustomersss")
    public String register(
            @RequestParam String userName,
            @RequestParam String password,
            @RequestParam String email,
            Model model
    ){
        System.out.println("sdaqdassa");
        User user1 =new User();
        if (userName ==null && password == null && email== null){
            model.addAttribute("msg","缺少必要属性");
            System.out.println(model.getAttribute("msg"));
            System.out.println("1231321312");
            return "redirect:/page/account_login";
        }
        User user2 = userService.getUserByName(userName);
        try {
            if (user2.getUserName() != null){
                model.addAttribute("msg","用户名重复");
                System.out.println(model.getAttribute("msg"));
                return "redirect:/page/account_login";
            }
        } catch (Exception e) {

        }
        user1.setUserName(userName);
        user1.setUserPassword(password);
        user1.setUserStatus("wait");
        user1.setUserType("customer");
        user1.setCreateTime(changeDate.getTime());
        user1.setUpdateTime(changeDate.getTime());
        int a=   userService.addUser(user1);
        if (a != 1 ){
            model.addAttribute("msg","添加失败");
            System.out.println(model.getAttribute("msg"));
            return "redirect:/page/account_login";
        }
        Customer customer =new Customer();
        customer.setUserId(user1.getUserId());
        customer.setCreateTime(changeDate.getTime());
        customer.setUpdateTime(changeDate.getTime());
        customer.setCustomerGrade("0");
        customer.setCustomerName(user1.getUserName());
        customer.setCustomerType("shopper");
        int b = customerService.addCustomer(customer);
        if (b != 1 ){
            model.addAttribute("msg","添加失败");
            System.out.println(model.getAttribute("msg"));
            return "redirect:/page/account_login";
        }
        mailTool.sendSureEmail(email,user1.getUserId(),user1.getUserName());
        model.addAttribute("msg","请完成邮箱验证");
        System.out.println(model.getAttribute("msg"));
        return "redirect:/page/account_login";
    }
    @ApiOperation(value = "邮件验证部分")
    @RequestMapping("everyOne/checkIn/{userId}/{time}")
    public String canUse(
            @PathVariable int userId,
            @PathVariable String time,
            Model model,
            HttpSession httpSession
    ){
        System.out.println("开始完成");
        User user = userService.getUserById(userId);
        if (time.equals(user.getUserName())){
            user.setUserStatus("live");
            userService.upDateUser(user);
            Date date = new Date();
            String acc = date + String.valueOf(date.hashCode());
            Customer customer = customerService.getCustomerByUserId(userId);
            httpSession.setAttribute("customer",customer);
            httpSession.setAttribute("User-Token",acc);
            httpSession.setAttribute("user",user);
            model.addAttribute("msg","登录成功");
            model.addAttribute("user1",user);
            return "redirect:/";
        }else {
            model.addAttribute("msg","验证失败");
            return "redirect:/page/account_login";
        }

    }
}
