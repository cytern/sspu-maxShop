package com.funong.funong.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.funong.funong.backPojo.BackPageIndex;
import com.funong.funong.backPojo.BackTypeIndex;
import com.funong.funong.mapper.*;
import com.funong.funong.plugin.ChangeDate;
import com.funong.funong.plugin.GetPageIndex;
import com.funong.funong.plugin.JundgeCan;
import com.funong.funong.pojo.*;
import com.funong.funong.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Api(value = "商品处理")
@RestController
public class GoodController {
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
    JundgeCan jundgeCan = new JundgeCan();
    ChangeDate changeDate = new ChangeDate();
    @ApiOperation(value = "分页获取商品")
    @ApiImplicitParam(name = "pageIndex",value = "第几页")
    @GetMapping("everyOne/getGood/{pageIndex}")
    public List<Good> getAllGood(@PathVariable int pageIndex) {
        GetPageIndex getPageIndex = new GetPageIndex();
        BackPageIndex backPageIndex = getPageIndex.getPageIndex(pageIndex);
        List<Good> goods = goodDao.getAllGood(backPageIndex);
        return goods;
    }
    @ApiOperation(value = "分页分类获取商品")
    @ApiImplicitParam(name = "type",value = "分类")
    @GetMapping("everyOne/getTypeGood/{pageIndex}/{type}")
    public List<Good> getTypeGood(@PathVariable int pageIndex, @PathVariable String type) {
        GetPageIndex getPageIndex = new GetPageIndex();
        BackPageIndex backPageIndex = getPageIndex.getPageIndex(pageIndex);
        BackTypeIndex backTypeIndex = new BackTypeIndex();
        backTypeIndex.setStart(backPageIndex.getStart());
        backTypeIndex.setEnd(backPageIndex.getEnd());
        backTypeIndex.setType(type);
        List<Good> goods = goodDao.getGood(backTypeIndex);
        return goods;
    }
    @ApiOperation(value = "获取所有分类")
    @GetMapping("everyOne/getTypes")
    public List<Type> getAllType() {
        List<Type> types = typeDao.getAllType();
        return types;
    }
    @ApiOperation(value = "总共多少叶")
    @GetMapping("everyOne/getGoodPage")
    public int getPage() {
        return goodDao.getNum() / 10 + 1;
    }

    @GetMapping("everyOne/getGoodDetail/{goodId}")
    public HashMap<Object, Object> getGoodDetail(@PathVariable int goodId) {
        Good good = goodDao.selectByPrimaryKey(goodId);
        int farmerId = good.getFarmerid();
        Farmer farmer = farmerDao.selectByPrimaryKey(farmerId);
        int representId = good.getRepresentid();
        Represent represent = representDao.selectByPrimaryKey(representId);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("good", good);
        hashMap.put("farmer", farmer);
        hashMap.put("represent", represent);
        return hashMap;

    }

    @PostMapping("test")
    public void test() {
        Good good = new Good();
        good.setGoodname("kekekeke");
        good.setGoodurl("aaaaa");
        int a = goodDao.insert(good);
        int b = goodDao.insertSelective(good);
    }

    @GetMapping("everyOne/getAdviceGood/{type}")
    public List<Good> getAdviceGood(@PathVariable String type) {
        List<Advice> advice = adviceDao.getAllAdvice();
        List<Good> goods = new ArrayList<>();
        for (Advice advice1 : advice) {
            Good good = goodDao.selectByPrimaryKey(advice1.getGoodid());
            goods.add(good);
        }
        return goods;
    }
@ApiOperation(value = "获取推荐商品")
    @GetMapping("everyOne/getALLAdvice")
    private List<Advice> getAllAdvice() {
        return adviceDao.getAllAdvice();
    }

    @PostMapping("represent/addGood")
    private HashMap<Object, Object> addGood(@RequestBody Good good) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        Date createTime = changeDate.getDate();
        good.setCreatetime(createTime);
        good.setUpdatetime(createTime);
        good.setGoodid(1);
        String string = good.getGoodurl();
        if (string == null){
            hashMap.put("msg", "没有图片");
            return hashMap;
        }else {
            Img img = new Img();
            img.setImgUrl(good.getGoodurl());
            img.setImgStatus("good"+ ":" + good.getGoodname());
            imgDao.updateByPrimaryKeySelective(img);
        }
        if (good.getGoodstatus()== null){
            good.setGoodstatus("live");
        }
        if (jundgeCan.canUse(good)) {
            int a = goodDao.insertSelective(good);
            if (a != 0) {
                hashMap.put("good", good);
                hashMap.put("success", "添加成功");
                return hashMap;
            } else {
                hashMap.put("msg", "数据添加失败，检查是否合法！");
                return hashMap;
            }
        } else {
            hashMap.put("msg", "内容不完整");
            hashMap.put("error",good);
            return hashMap;
        }

    }

     @PostMapping("represent/updateGood")
    private HashMap<Object,Object> updateGood(@RequestBody Good good){
        HashMap<Object,Object> hashMap = new HashMap<>();
        good.setUpdatetime(changeDate.getDate());
         int a = goodDao.updateByPrimaryKeySelective(good);
         if (a != 0){
             Good good1 = goodDao.selectByPrimaryKey(good.getGoodid());
             hashMap.put("Good",good1);
             hashMap.put("success","添加成功");

             return  hashMap;

         }else {
             hashMap.put("msg","数据异常");
             hashMap.put("error",good);
             return hashMap;
         }
    }

    @GetMapping("represent/canUserGoodName/{goodName}")
    public boolean canUse(@PathVariable String goodName){
     Good good = goodDao.getGoodByGoodName(goodName);
     if (good.getGoodname().isEmpty() ){
         return false;
     }else {
         return true;
     }
    }

    @GetMapping("represent/deleteGood/{goodId}")
    public HashMap<Object,Object>  deleteGood(@PathVariable Integer goodId){
        HashMap<Object,Object> hashMap = new HashMap<>();
        Good good = goodDao.selectByPrimaryKey(goodId);
        if (good.getGoodname() == null){
            hashMap.put("error","无效的goodid");
            return hashMap;
        }
        good.setGoodstatus("dead");
        int a=  goodDao.updateByPrimaryKeySelective(good);
        if (a == 1){
            hashMap.put("success","删除成功");
            return hashMap;
        }
        hashMap.put("error","删除失败");
        return hashMap;
    }


}
