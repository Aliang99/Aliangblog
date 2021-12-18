package com.blog.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/4/8 0:52
 * @Description:
 */
public class MyBeanUtils {

    /**
     * 获取source中属性值为空的属性名数组
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source){

        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds){
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }
            return nullPropertyNames.toArray(new String [nullPropertyNames.size()]);

        }
}
