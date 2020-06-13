package com.funong.funong.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * area
 * @author 
 */
@Data
public class Area implements Serializable {
    private Integer areaId;

    private String areaName;

    private Integer cityId;

    private Integer provinceId;

    private Integer civiId;

    private static final long serialVersionUID = 1L;
}