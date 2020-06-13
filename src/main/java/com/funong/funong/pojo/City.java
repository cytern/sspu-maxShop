package com.funong.funong.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * city
 * @author 
 */
@Data
public class City implements Serializable {
    private Integer cityId;

    private String cityName;

    private Integer provinceId;

    private Integer civiId;

    private static final long serialVersionUID = 1L;
}