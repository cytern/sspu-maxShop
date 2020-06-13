package com.funong.funong.plugin;

import com.funong.funong.backPojo.BackGoodOrder;
import com.funong.funong.backPojo.BackOrder;
import com.funong.funong.backPojo.BackOrderOne;
import com.funong.funong.backPojo.BackTypeIndex;
import com.funong.funong.mapper.*;
import com.funong.funong.pojo.*;
import com.funong.funong.service.CustomerService;
import com.funong.funong.service.TokenService;
import com.funong.funong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/5/26 9:51
 */
@Component
public class GetMyOrder {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private RepresentDao representDao;
    @Autowired
    private RootDao rootDao;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodorderDao goodorderDao;
    @Autowired
    private GoodDao goodDao;
    @Autowired
    private TypeDao typeDao;
    GetPageIndex getPageIndex = new GetPageIndex();
    GetImgUrl getImgUrl = new GetImgUrl();
    ChangeDate changeDate = new ChangeDate();
    public HashMap<Object,Object> getMyOrders(User user){
        int userId = user.getUserId();
        String type = user.getUserType();
        BackTypeIndex backTypeIndex = getPageIndex.getTypeIndex(1);
        HashMap<Object, Object> hashMap = new HashMap<>();
        switch (type) {
            case "customer": {
                Customer customer = customerService.getCustomerByUserId(userId);
                backTypeIndex.setType(String.valueOf(customer.getCustomerId()));
               Order order = orderDao.getOrderWhichCreate(customer.getCustomerId());
               BackOrderOne backOrder = getOneBackOrder(order);
                int pageMax = backOrder.getGoodorders().size();

                hashMap.put("order", backOrder);
                hashMap.put("pageMax", pageMax);
                hashMap.put("customer", customer);

                return hashMap;

            }
            case "manager": {
                Manager manager = managerDao.getManagerByUserId(userId);
                List<Order> orders = orderDao.getAllOrder(backTypeIndex);
                int pageMax = orderDao.getNumAll();
                hashMap.put("pageMax", pageMax);
                hashMap.put("manager", manager);
                List<BackOrder> backOrders = getBackOrder(orders);
                hashMap.put("order", backOrders);
                return hashMap;
            }
            case "represent": {
                Represent represent = representDao.getRepresentById(userId);
                backTypeIndex.setType(String.valueOf(represent.getRepresentid()));
                List<Order> orders = orderDao.getOrderByRepresentId(backTypeIndex);
                int pageMax = orderDao.getNumRepresent(represent.getRepresentid());
                hashMap.put("pageMax", pageMax);
                hashMap.put("represent", represent);
                List<BackOrder> backOrders = getBackOrder(orders);
                hashMap.put("order", backOrders);
                return hashMap;
            }
            case "root": {
                Root root = rootDao.getRootByUserId(userId);
                List<Order> orders = orderDao.getAllOrder(backTypeIndex);
                int pageMax = orderDao.getNumAll();
                hashMap.put("pageMax", pageMax);
                hashMap.put("root", root);
                List<BackOrder> backOrders = getBackOrder(orders);
                hashMap.put("order", backOrders);
                return hashMap;
            }
            default: {
                hashMap.put("error", "无效用户");
                return hashMap;
            }
        }

    }
    public List<BackOrder> getBackOrder(List<Order> orders){
        List<BackOrder> backOrders = new ArrayList<>();
        for (Order order:orders){
            BackOrder backOrder = new BackOrder();
            BackTypeIndex backTypeIndex10 = new BackTypeIndex();
            backTypeIndex10.setId(order.getOrderid());
            List<Goodorder> goodorders = goodorderDao.getByOrderId(backTypeIndex10);
            backOrder.setCreatetime(order.getCreatetime());
            backOrder.setCustomerid(order.getCustomerid());
            backOrder.setGoodorders(goodorders);
            backOrder.setOrderconf(order.getOrderconf());
            backOrder.setOrderid(order.getOrderid());
            backOrder.setOrderstatus(order.getOrderstatus());
            backOrder.setOrdertype(order.getOrdertype());
            backOrder.setRepresentid(order.getRepresentid());
            backOrder.setTotalprice(order.getTotalprice());
            backOrder.setUpdatetime(order.getUpdatetime());
            backOrders.add(backOrder);
        }
        return backOrders;
    }
    public BackOrderOne getOneBackOrder(Order order){
        BackOrderOne backOrder = new BackOrderOne();
        BackTypeIndex backTypeIndex10 = new BackTypeIndex();
        backTypeIndex10.setId(order.getOrderid());
        List<Goodorder> goodorders = goodorderDao.getByOrderId(backTypeIndex10);
        List<BackGoodOrder> backGoodOrders = new ArrayList<>();
        getImgUrl.getBaseUrl();
        for (Goodorder goodorder:goodorders){
            BackGoodOrder backGoodOrder = new BackGoodOrder();
            Good good = goodDao.selectByPrimaryKey(goodorder.getGoodid());
            backGoodOrder.setGoodid(good.getGoodid());
            backGoodOrder.setGoodname(good.getGoodname());
            backGoodOrder.setGoodlocation(good.getGoodlocation());
            backGoodOrder.setGoodnum(goodorder.getGoodnum());
            backGoodOrder.setGoodprice(good.getGoodprice());
            backGoodOrder.setGoodorderid(goodorder.getGoodorderid());
            backGoodOrder.setGoodurl(getImgUrl.getExport() + good.getGoodurl());
            backGoodOrders.add(backGoodOrder);
        }
        backOrder.setCreatetime(order.getCreatetime());
        backOrder.setCustomerid(order.getCustomerid());
        backOrder.setGoodorders(backGoodOrders);
        backOrder.setOrderconf(order.getOrderconf());
        backOrder.setOrderid(order.getOrderid());
        backOrder.setOrderstatus(order.getOrderstatus());
        backOrder.setOrdertype(order.getOrdertype());
        backOrder.setRepresentid(order.getRepresentid());
        backOrder.setTotalprice(order.getTotalprice());
        backOrder.setUpdatetime(order.getUpdatetime());
       return backOrder;
    }
}
