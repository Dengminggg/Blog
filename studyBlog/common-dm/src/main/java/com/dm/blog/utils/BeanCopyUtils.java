package com.dm.blog.utils;

import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Bean工具拷贝类
 */

@NoArgsConstructor
public class BeanCopyUtils {

    /**
     * 单个Bean拷贝
     *
     * @param source 源对象
     * @param clazz  目标对象类
     * @param <T>    目标对象
     * @return 目标对象
     */
    public static <T> T copyBean(Object source, Class<T> clazz) {
        //创建目标对象
        T copy = null;

        //实现属性copy
        try {
            copy = clazz.newInstance();
            BeanUtils.copyProperties(source, copy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return copy;
    }

    /**
     * Bean集合拷贝
     *
     * @param list  源集合
     * @param clazz 目标对象类
     * @param <T>   目标对象
     * @param <O>   源集合对象
     * @return 目标对象集合
     */
    public static <T, O> List<T> copyBeanList(List<O> list, Class<T> clazz) {
        //使用stream流map映射处理
        return list.stream().map(O -> copyBean(O, clazz)).collect(Collectors.toList());
    }

}
