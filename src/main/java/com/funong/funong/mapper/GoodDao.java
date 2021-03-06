package com.funong.funong.mapper;

import com.funong.funong.backPojo.BackPageIndex;
import com.funong.funong.backPojo.BackTypeIndex;
import com.funong.funong.pojo.Good;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@Cacheable(value = "good", keyGenerator = "keyGenerator")
public interface GoodDao {
    int deleteByPrimaryKey(Integer goodid);

    int insert(Good record);

    int insertSelective(Good record);

    Good selectByPrimaryKey(Integer goodid);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);
    List<Good> getAllGood(BackPageIndex backPageIndex);

    int getNum();

    List<Good> getGood(BackTypeIndex backTypeIndex);

    Good getGoodByGoodName(String goodname);

    List<Good> getAllGoods();

    List<Good> getGoodByTypePage(String type);

}