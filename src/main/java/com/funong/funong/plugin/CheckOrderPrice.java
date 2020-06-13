package com.funong.funong.plugin;

import com.funong.funong.mapper.GoodDao;
import com.funong.funong.mapper.GoodorderDao;
import com.funong.funong.mapper.OrderDao;
import com.funong.funong.pojo.Good;
import com.funong.funong.pojo.Goodorder;
import com.funong.funong.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/5/26 11:49
 */
@Component
public class CheckOrderPrice {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodorderDao goodorderDao;
    @Autowired
    private GoodDao goodDao;

    public void checkOrderPrice (int orderId){
        Order order = orderDao.selectByPrimaryKey(orderId);
        List<Goodorder> goodorders = goodorderDao.getAllShit(orderId);
        BigDecimal totalPrice=new BigDecimal("0");
        for (Goodorder goodorder:goodorders){
            Good good = goodDao.selectByPrimaryKey(goodorder.getGoodid());
            int goodNum = goodorder.getGoodnum();
            BigDecimal goodPrice = good.getGoodprice();
            totalPrice = totalPrice.add(goodPrice.multiply(BigDecimal.valueOf(goodNum)));
        }
        order.setTotalprice(totalPrice);
        orderDao.updateByPrimaryKeySelective(order);
    }
}
