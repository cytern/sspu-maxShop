package com.funong.funong.controller;

import com.funong.funong.backPojo.BackGoodOrder;
import com.funong.funong.backPojo.BackOrderOne;
import com.funong.funong.mapper.GoodorderDao;
import com.funong.funong.mapper.OrderDao;
import com.funong.funong.plugin.*;
import com.funong.funong.pojo.Customer;
import com.funong.funong.pojo.Goodorder;
import com.funong.funong.pojo.Order;
import com.funong.funong.pojo.User;
import com.funong.funong.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/5/26 10:56
 */
@Controller
public class CartController {
    @Autowired
    CheckOrderPrice checkOrderPrice = new CheckOrderPrice();
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GoodorderDao goodorderDao;
    @Autowired
    GetMyOrder getMyOrder = new GetMyOrder();
    GetPageIndex getPageIndex = new GetPageIndex();
    ChangeDate changeDate = new ChangeDate();
    @Autowired
    GetBannerData getBannerData = new GetBannerData();
    @RequestMapping("addCart/{goodid}")
    public String addCart(Model model, HttpSession session, @PathVariable int goodid){
        Customer customer = (Customer)session.getAttribute("customer");
        User user = (User)session.getAttribute("user");
        BackOrderOne backOrderOne = (BackOrderOne) session.getAttribute("cart");
        Goodorder goodorder = new Goodorder();
        goodorder.setOrderid(backOrderOne.getOrderid());
        goodorder.setUpdatetime(changeDate.getDate());
        goodorder.setUpdatetime(changeDate.getDate());
        List<BackGoodOrder> goodorders = backOrderOne.getGoodorders();
        goodorder.setGoodnum(1);
        goodorder.setGoodid(goodid);
        boolean addOrPlus = true;
        int goodOrderId = 0;
        for (BackGoodOrder backGoodOrder:goodorders){
            int goodId = backGoodOrder.getGoodid();
            if (goodId == goodid){
                addOrPlus = false;
                goodOrderId = backGoodOrder.getGoodorderid();
            }
        }
        if (addOrPlus){
            goodorderDao.insertSelective(goodorder);
            checkOrderPrice.checkOrderPrice(backOrderOne.getOrderid());
        }else {
                Goodorder goodorder1 = goodorderDao.selectByPrimaryKey(goodOrderId);
                goodorder1.setGoodnum(goodorder1.getGoodnum() + 1);
                goodorder1.setUpdatetime(changeDate.getDate());
                goodorderDao.updateByPrimaryKeySelective(goodorder1);
                checkOrderPrice.checkOrderPrice(backOrderOne.getOrderid());
        }
        return "redirect:/page/index";
    }

    @RequestMapping("removeCart/{goodOrderid}")
    public String removeCart(Model model,HttpSession session,@PathVariable int goodOrderid){
        Goodorder goodorder = goodorderDao.selectByPrimaryKey(goodOrderid);
        Order order  = orderDao.selectByPrimaryKey(goodorder.getOrderid());
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer.getCustomerId() == order.getCustomerid()){
            goodorderDao.deleteByPrimaryKey(goodOrderid);
            checkOrderPrice.checkOrderPrice(order.getOrderid());
        }
        return "redirect:/page/index";
    }

}
