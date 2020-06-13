package com.funong.funong.controller;

import com.funong.funong.mapper.FarmerDao;
import com.funong.funong.mapper.ManagerDao;
import com.funong.funong.mapper.RepresentDao;
import com.funong.funong.mapper.RootDao;
import com.funong.funong.plugin.ChangeDate;
import com.funong.funong.pojo.*;
import com.funong.funong.service.CustomerService;
import com.funong.funong.service.UserService;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListTransducedAccessorImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RootDao rootDao;
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private FarmerDao farmerDao;
    @Autowired
    private RepresentDao representDao;
    ChangeDate changeDate =new ChangeDate();
    @PostMapping("everyOne/addUser")
    public HashMap<Object,Object> addUser(User user){
        HashMap<Object,Object> hashMap =new HashMap<>();
         User user1 =new User();
         if (user.getUserName() ==null && user.getUserPassword() == null){
             hashMap.put("error","缺少用户名或密码");
             return hashMap;
         }
         User user2 = userService.getUserByName(user.getUserName());
         if (user2.getUserName() != null){
             hashMap.put("error","已经存在的用户名");
             return hashMap;
         }
         user1.setUserName(user.getUserName());
         user1.setUserPassword(user.getUserPassword());
         user1.setUserStatus("live");
         user1.setUserType("customer");
         user1.setCreateTime(changeDate.getTime());
         user1.setUpdateTime(changeDate.getTime());
          int a=   userService.addUser(user1);
         if (a != 1 ){
             hashMap.put("error","添加失败");
             return hashMap;
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
            hashMap.put("error","添加失败");
            return hashMap;
        }
        hashMap.put("user",user1);
        hashMap.put("customer",customer);
        hashMap.put("success","添加成功");
        return hashMap;
    }
    @GetMapping("everyOne/hasSameName/{userName}")
    public boolean hasSameName(@PathVariable String userName){
        User user = userService.getUserByName(userName);
        if (user.getUserName() != null){
            return true;
        }
        return false;
    }

    @GetMapping("manager/deleteUser/{userId}")
    public HashMap<Object,Object> deleteUser(@PathVariable Integer userId){
        HashMap<Object,Object> hashMap =new HashMap<>();
        User user = userService.getUserById(userId);
        if (user.getUserName() == null){
            hashMap.put("error","无效的userid");
            return hashMap;
        }
        user.setUserStatus("dead");
        userService.upDateUser(user);
        hashMap.put("user",user);
        return hashMap;
    }
      @PostMapping("root/addUser")
    public HashMap<Object,Object> adminAddUser(@RequestBody User user){
          HashMap<Object,Object> hashMap =new HashMap<>();
        User user1 = new User();
        if (user.getUserName() == null && user.getUserPassword() == null){
            hashMap.put("error","缺少用户名或密码");
            return hashMap;
        }
        User user2 = userService.getUserByName(user.getUserName());
        if (user2.getUserPassword() !=null){
            hashMap.put("error","已存在的用户名");
            return hashMap;
        }
        user1.setUserPassword(user.getUserPassword());
        user1.setUserName(user.getUserName());
        user1.setCreateTime(changeDate.getTime());
        user1.setUpdateTime(changeDate.getTime());
        if (user.getUserType() != null){
            user1.setUserType(user.getUserType());
        }else {
            user1.setUserType("customer");
        }
          if (user.getUserStatus() != null){
              user1.setUserStatus(user.getUserStatus());
          }else {
              user1.setUserStatus("live");
          }
          userService.addUser(user1);
          if (user1.getUserId() != 0){
              switch (user1.getUserType()){
                  case "customer":{
                      Customer customer = new Customer();
                      customer.setUserId(user1.getUserId());
                      customer.setCustomerName(user1.getUserName());
                      customer.setCustomerType("shopper");
                      customer.setCreateTime(changeDate.getTime());
                      customer.setUpdateTime(changeDate.getTime());
                      customerService.addCustomer(customer);
                      hashMap.put("customer",customer);
                      break;
                  }
                  case "farmer":{
                      Farmer farmer = new Farmer();
                      farmer.setUserid(user1.getUserId());
                      farmer.setCreatetime(changeDate.getDate());
                      farmer.setUpdatetime(changeDate.getDate());
                      farmerDao.insertSelective(farmer);
                      hashMap.put("farmer",farmer);
                      break;
                  }
                  case "represent":{
                      Represent represent = new Represent();
                      represent.setCreatetime(changeDate.getDate());
                      represent.setUpdatetime(changeDate.getDate());
                      represent.setUserid(user1.getUserId());
                      representDao.insertSelective(represent);
                      hashMap.put("represent",represent);
                      break;
                  }
                  case "root":{
                      Root root = new Root();
                      root.setUserid(user1.getUserId());
                      root.setCreatetime(changeDate.getDate());
                      root.setUpdatetime(changeDate.getDate());
                      root.setRootname(user1.getUserName());
                      rootDao.insertSelective(root);
                      hashMap.put("root",root);
                      break;
                  }
                  case "manager":{
                      Manager manager = new Manager();
                      manager.setUserid(user1.getUserId());
                      manager.setCreatetime(changeDate.getDate());
                      manager.setUpdatetime(changeDate.getDate());
                      managerDao.insertSelective(manager);
                      hashMap.put("manager",manager);
                      break;
                  }
              }
          }else {
              hashMap.put("error","添加失败");
              return hashMap;
          }
          hashMap.put("success","成功添加");
          return hashMap;
    }

    @PostMapping("manager/addUser")
    public HashMap<Object,Object> adminAddUser1(@RequestBody User user){
        HashMap<Object,Object> hashMap =new HashMap<>();
        User user1 = new User();
        if (user.getUserName() == null && user.getUserPassword() == null){
            hashMap.put("error","缺少用户名或密码");
            return hashMap;
        }
        User user2 = userService.getUserByName(user.getUserName());
        if (user2.getUserPassword() !=null){
            hashMap.put("error","已存在的用户名");
            return hashMap;
        }
        user1.setUserPassword(user.getUserPassword());
        user1.setUserName(user.getUserName());
        user1.setCreateTime(changeDate.getTime());
        user1.setUpdateTime(changeDate.getTime());
        if (user.getUserType() != null){
            user1.setUserType(user.getUserType());
        }else {
            user1.setUserType("customer");
        }
        if (user.getUserStatus() != null){
            user1.setUserStatus(user.getUserStatus());
        }else {
            user1.setUserStatus("live");
        }
        userService.addUser(user1);
        if (user1.getUserId() != 0){
            switch (user1.getUserType()){
                case "customer":{
                    Customer customer = new Customer();
                    customer.setUserId(user1.getUserId());
                    customer.setCustomerName(user1.getUserName());
                    customer.setCustomerType("shopper");
                    customer.setCreateTime(changeDate.getTime());
                    customer.setUpdateTime(changeDate.getTime());
                    customerService.addCustomer(customer);
                    hashMap.put("customer",customer);
                    break;
                }
                case "farmer":{
                    Farmer farmer = new Farmer();
                    farmer.setUserid(user1.getUserId());
                    farmer.setCreatetime(changeDate.getDate());
                    farmer.setUpdatetime(changeDate.getDate());
                    farmerDao.insertSelective(farmer);
                    hashMap.put("farmer",farmer);
                    break;
                }
                case "represent":{
                    Represent represent = new Represent();
                    represent.setCreatetime(changeDate.getDate());
                    represent.setUpdatetime(changeDate.getDate());
                    represent.setUserid(user1.getUserId());
                    representDao.insertSelective(represent);
                    hashMap.put("represent",represent);
                    break;
                }
                case "root":{
//                    Root root = new Root();
//                    root.setUserid(user1.getUserId());
//                    root.setCreatetime(changeDate.getDate());
//                    root.setUpdatetime(changeDate.getDate());
//                    root.setRootname(user1.getUserName());
//                    rootDao.insertSelective(root);
                    hashMap.put("error","无权添加");
                    break;
                }
                case "manager":{
                    Manager manager = new Manager();
                    manager.setUserid(user1.getUserId());
                    manager.setCreatetime(changeDate.getDate());
                    manager.setUpdatetime(changeDate.getDate());
                    managerDao.insertSelective(manager);
                    hashMap.put("error","无权添加");
                    break;
                }
            }
        }else {
            hashMap.put("error","添加失败");
            return hashMap;
        }
        hashMap.put("success","成功添加");
        return hashMap;
    }
    @PostMapping("customer/updateCustomer")
    public HashMap<Object,Object> updateCustomer(@RequestBody Customer customer){
        HashMap<Object,Object> hashMap = new HashMap<>();
         Customer customer1 =customerService.getCustomerByCustomerId(customer.getCustomerId());
         if (customer1.getCustomerName() == null){
             hashMap.put("error","错误,无效的customerid");
         }
         if (customer.getCustomerConf() != null){
             customer1.setCustomerConf(customer.getCustomerConf());
         }
         if (customer.getCustomerUrl() != null){
             customer1.setCustomerUrl(customer.getCustomerUrl());
         }
         if (customer.getCustomerName() != null){
             customer1.setCustomerName(customer.getCustomerName());
         }
         if (customer.getCustomerPhone() !=null){
             customer1.setCustomerPhone(customer.getCustomerPhone());
         }
         customer1.setUpdateTime(changeDate.getTime());
         customerService.updateCustomer(customer1);
         hashMap.put("customer",customer1);
         hashMap.put("success","成功修改");
         return hashMap;
    }
//修改farmer
    @PostMapping("represent/updateFarmer")
    public HashMap<Object,Object> updateFarmer(@RequestBody Farmer farmer){
        HashMap<Object,Object> hashMap = new HashMap<>();
        Farmer farmer1 =farmerDao.selectByPrimaryKey(farmer.getFarmerid());
        if (farmer1.getFarmername() == null){
            hashMap.put("error","错误，无效的farmerid");
            return hashMap;
        }
       farmerDao.updateByPrimaryKeySelective(farmer);
        hashMap.put("farmer",farmer);
        hashMap.put("success","success");
        return hashMap;
    }

    @GetMapping("represent/getFarmer/{representId}")
     public HashMap<Object,Object> getFarmer(@PathVariable Integer representId ){
      HashMap<Object,Object> hashMap = new HashMap<>();
        if (representId == 0){
            List<Farmer> farmers = farmerDao.getAllFarmer();
            hashMap.put("farmers",farmers);
            return hashMap;
        }
        List<Farmer> farmers = farmerDao.getAllByRepresentId(representId);
        hashMap.put("farmers",farmers);
        return hashMap;
    }

    @GetMapping("manager/getRepresent")
    public HashMap<Object,Object> getRepresent(){
        HashMap<Object,Object> hashMap = new HashMap<>();
        List<Represent> represents = representDao.getAllRepresent();
        hashMap.put("represent",represents);
        return hashMap;
    }
    @PostMapping("represent/updateRepresent")
    public HashMap<Object,Object> updateRepresent(@RequestBody Represent represent){
        HashMap<Object,Object> hashMap = new HashMap<>();
        representDao.updateByPrimaryKeySelective(represent);
        hashMap.put("represent",represent);
        return hashMap;
    }
    @PostMapping("represent/addFarmer")
    public HashMap<Object,Object> addFarmer(@RequestBody Farmer farmer){
        HashMap<Object,Object> hashMap = new HashMap<>();
        if (farmer.getRepresentid() == null ){
            hashMap.put("error","添加失败,缺少representid");
            return hashMap;
        }
        User user = new User();
        user.setUserName(farmer.hashCode()+changeDate.getTime());
        user.setUserPassword(farmer.hashCode()+changeDate.getTime());
        user.setUserStatus("live");
        user.setUserType("farmer");
        user.setUpdateTime(changeDate.getTime());
        user.setCreateTime(changeDate.getTime());
        userService.addUser(user);
        if (user.getUserId() == 0){
            hashMap.put("error","添加失败");
            return hashMap;
        }
        farmer.setUserid(user.getUserId());
        farmer.setFarmergrade(0);
        farmer.setUpdatetime(changeDate.getDate());
        farmer.setCreatetime(changeDate.getDate());
        farmerDao.insertSelective(farmer);
        hashMap.put("farmer",farmer);
        hashMap.put("success","成功添加");
        return hashMap;
    }

    @GetMapping("represent/getFarmerDetail/{farmerId}")
    public HashMap<Object,Object> getFarmerDetail(@PathVariable int farmerId){
        HashMap<Object,Object> hashMap = new HashMap<>();
        Farmer farmer = farmerDao.selectByPrimaryKey(farmerId);
        if (farmer.getFarmername() == null || farmer.getUserid() ==null){
            return null;
        }
        User user = userService.getUserById(farmer.getUserid());
        Represent represent = representDao.selectByPrimaryKey(farmer.getRepresentid());
        hashMap.put("user",user);
        hashMap.put("farmer",farmer);
        hashMap.put("represent",represent);
        hashMap.put("success","success");
        return hashMap;

    }

    @GetMapping("manager/getAllManager")
    public HashMap<Object,Object> getAllManager(){
        HashMap<Object,Object> hashMap = new HashMap<>();
        List<Manager> managers = managerDao.getAllManager();
        hashMap.put("manager",managers);
        return hashMap;
    }

    @GetMapping("root/getAllRoot")
    public HashMap<Object,Object> getAllRoot(){
        HashMap<Object,Object> hashMap = new HashMap<>();
        List<Root> roots = rootDao.getAllRoot();
        hashMap.put("root",roots);
        return hashMap;
    }
}
