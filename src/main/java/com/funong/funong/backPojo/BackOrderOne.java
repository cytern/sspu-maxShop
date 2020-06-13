package com.funong.funong.backPojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.funong.funong.pojo.Goodorder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/5/26 10:41
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackOrderOne {
    private Integer orderid;

    private BigDecimal totalprice;

    private Integer customerid;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatetime;

    private String orderstatus;

    private String orderconf;

    private String ordertype;

    private int representid;

    private List<BackGoodOrder> goodorders;

    private static final long serialVersionUID = 1L;
}
