package com.funong.funong.backPojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: cytern
 * @Date: 2020/5/26 10:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackGoodOrder {
    private Integer goodorderid;

    private Integer goodid;

    private Integer goodnum;

    private Integer orderid;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatetime;

    private String goodname;

    private String goodstatus;

    private Integer farmerid;

    private BigDecimal goodprice;

    private String goodlocation;

    private Integer representid;

    private String goodsize;

    private String goodurl;

    private String goodtype;
}
