package com.funong.funong.mapper;

import com.funong.funong.pojo.Civi;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CiviDao {
    int insert(Civi record);

    int insertSelective(Civi record);
}