package com.funong.funong.plugin;


import java.lang.reflect.Field;

public class JundgeCan {
    public boolean canUse(Object object){
        if (null == object){
            return false;
        }else {
            try {
                for (Field f : object.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    if (f.get(object) == null) {
                        return false;
                    }

                }
                return true;
            }catch (Exception e){
             return false;
            }
        }
    }
}
