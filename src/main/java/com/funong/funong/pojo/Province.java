package com.funong.funong.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * province
 * @author 
 */
@Data
public class Province implements Serializable {
    private Integer provinceId;

    private String provinceName;

    private Integer civiId;

    private static final long serialVersionUID = 1L;
}