package com.funong.funong.controller;

import com.funong.funong.backPojo.BackPageIndex;
import com.funong.funong.backPojo.BackTypeIndex;
import com.funong.funong.mapper.*;
import com.funong.funong.plugin.*;
import com.funong.funong.pojo.*;
import com.funong.funong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
public class PageController {
    @Autowired
    private GoodDao goodDao;
    @Autowired
    private UserService userService;
    @Autowired
    private RepresentDao representDao;
    @Autowired
    private FarmerDao farmerDao;
    @Autowired
    private TypeDao typeDao;
    @Autowired
    private AdviceDao adviceDao;
    @Autowired
    private ImgDao imgDao;
    @Autowired
    GetType getType =new GetType();
    @Autowired
    GetMyOrder getMyOrder = new GetMyOrder();
    JundgeCan jundgeCan = new JundgeCan();
    ChangeDate changeDate = new ChangeDate();
    @Autowired
    GetBannerData getBannerData =new GetBannerData();

    GetImgUrl getImgUrl = new GetImgUrl();
    private HashMap<Object, Object> hashMap;

    @RequestMapping({"/","/page/index"})
    public String index(Model model, HttpSession session) {
        BackPageIndex backPageIndex =new BackPageIndex();
       backPageIndex.setStart(0);
       backPageIndex.setEnd(8);
      List<Good> goodList = goodDao.getAllGood(backPageIndex);
        getImgUrl.getBaseUrl();
        for (Good good:goodList){
            String url = getImgUrl.getExport() + good.getGoodurl();
            good.setGoodurl(url);
        }
        model.addAttribute("goodList",goodList);
        List<Advice> advices = adviceDao.getAllAdvice();
     User user = (User) session.getAttribute("user");
     Customer customer = (Customer) session.getAttribute("customer");
        HashMap<Object,Object> hashMap = getMyOrder.getMyOrders(user);
        session.setAttribute("cart",hashMap.get("order"));
        model.addAttribute("maxCount",hashMap.get("pageMax"));
        model.addAttribute("cart",hashMap.get("order"));
        model.addAttribute("customer",customer);
        List<Good> adviceGood = new ArrayList<>();
        for (Advice advice:advices){
            Good good = goodDao.selectByPrimaryKey(advice.getGoodid());
            String url = getImgUrl.getExport() + good.getGoodurl();
            good.setGoodurl(url);
            adviceGood.add(good);
        }
        model = getType.getType(model);
        model.addAttribute("adviceList",adviceGood);
        return "index";
    }

    @RequestMapping("/page/category/{type}")
    public String shopBy(Model model, HttpSession session, @PathVariable int type)
    {

        model = getBannerData.getBannerData(model,session);
        Type type1 = typeDao.selectByPrimaryKey(type);
        List<Good> goods = goodDao.getGoodByTypePage(type1.getTypeName());
        getImgUrl.getBaseUrl();
        for (Good good:goods){
            String url = getImgUrl.getExport() + good.getGoodurl();
            good.setGoodurl(url);
        }
        model.addAttribute("good",goods);
        model.addAttribute("msg1","添加成功");
        model = getType.getType(model);
        return "category";
    }
    @RequestMapping("/page/categoryNone")
    public String shopBy123(Model model, HttpSession session)
    {
        model = getBannerData.getBannerData(model,session);
        List<Good> goods = goodDao.getAllGoods();
        getImgUrl.getBaseUrl();
        for (Good good:goods){
            String url = getImgUrl.getExport() + good.getGoodurl();
            good.setGoodurl(url);
        }
        model.addAttribute("good",goods);
        model.addAttribute("msg1","添加成功");
        model = getType.getType(model);
        return "category";
    }


    @RequestMapping("/page/blog")
    public String blog(Model model,HttpSession session)
    {
        model = getBannerData.getBannerData(model,session);
        model.addAttribute("msg1","添加成功");
        model = getType.getType(model);
        return "blog";
    }
    @RequestMapping("/page/faq")
    public String faq(Model model,HttpSession session) {
        model = getBannerData.getBannerData(model,session);
        model.addAttribute("msg1","添加成功");
        model = getType.getType(model);
        return "faq";
    }
    @RequestMapping("/page/about_us")
    public String aboutUs(Model model,HttpSession session){
        model = getBannerData.getBannerData(model,session);
        model.addAttribute("msg1","添加成功");
        model = getType.getType(model);
        return "about_us";
    }
    @RequestMapping("/page/contact_us")
    public String contactUs(Model model,HttpSession session){
        model = getBannerData.getBannerData(model,session);
        model.addAttribute("msg1","添加成功");
        model = getType.getType(model);
        return "contact_us";
    }
    @RequestMapping("/page/account_login")
    public String login(Model model,HttpSession session){
        model = getType.getType(model);
        return "account_login";
    }
    @RequestMapping("/page/account_create")
    public String creatUser(Model model,HttpSession session) {
        model = getType.getType(model);
        return "account_create";
    }
    @RequestMapping("/page/404_error")
    public String error(Model model,HttpSession session){
        model = getBannerData.getBannerData(model,session);
        model.addAttribute("msg1","添加成功");
        model = getType.getType(model);
        return "404_error";
    }
    @RequestMapping("/page/cart")
    public String cart(Model model,HttpSession session){
        model = getBannerData.getBannerData(model,session);
        model.addAttribute("msg1","添加成功");
        model = getType.getType(model);
        return "cart";
    }
    @RequestMapping("/logout")
    public String logout(Model model,HttpSession session){
        session.invalidate();
        model = getType.getType(model);
        return "account_login";
    }
}
