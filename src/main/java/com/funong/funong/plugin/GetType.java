package com.funong.funong.plugin;

import com.funong.funong.mapper.TypeDao;
import com.funong.funong.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/6/2 10:47
 */
@Component
public class GetType {
    @Autowired
    private TypeDao typeDao;

    public Model getType(Model model){
        List<Type> types = typeDao.getAllType();
        model.addAttribute("type",types);
        return model;
    }
}
