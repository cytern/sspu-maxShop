package com.funong.funong.plugin;

import com.funong.funong.backPojo.BackPageIndex;
import com.funong.funong.mapper.*;
import com.funong.funong.pojo.Advice;
import com.funong.funong.pojo.Customer;
import com.funong.funong.pojo.Good;
import com.funong.funong.pojo.User;
import com.funong.funong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/5/26 12:21
 */
@Component
public class GetBannerData {
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
    GetMyOrder getMyOrder = new GetMyOrder();
    JundgeCan jundgeCan = new JundgeCan();
    ChangeDate changeDate = new ChangeDate();

    GetExcelUrl getExcelUrl = new GetExcelUrl();
    public Model getBannerData(Model model, HttpSession session) {
        getExcelUrl.getBaseUrl();
        BackPageIndex backPageIndex =new BackPageIndex();
        backPageIndex.setStart(0);
        backPageIndex.setEnd(8);
        List<Good> goodList = goodDao.getAllGood(backPageIndex);
        model.addAttribute("goodList",goodList);
        List<Advice> advices = adviceDao.getAllAdvice();
        User user = (User) session.getAttribute("user");
        Customer customer = (Customer) session.getAttribute("customer");
        HashMap<Object,Object> hashMap = getMyOrder.getMyOrders(user);
        String baseUrl = getExcelUrl.getHttpUrl();
        model.addAttribute("maxCount",hashMap.get("pageMax"));
        model.addAttribute("cart",hashMap.get("order"));
        model.addAttribute("customer",customer);
        model.addAttribute("baseUrl",baseUrl);
        List<Good> adviceGood = new ArrayList<>();
        for (Advice advice:advices){
            Good good = goodDao.selectByPrimaryKey(advice.getGoodid());
            adviceGood.add(good);
        }
        model.addAttribute("adviceList",adviceGood);
        return model;
    }
}
