package com.tencent.wxcloudrun.utils;

import com.alibaba.excel.util.CollectionUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class CopyListUtils {
    /**
     * @param input 输入集合
     * @param clzz  输出集合类型
     * @param <E>   输入集合类型
     * @param <T>   输出集合类型
     * @return 返回集合
     */
    public static <E, T> List<T> convertList2List(List<E> input, Class<T> clzz) {
        List<T> output = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(input)) {
            for (E source : input) {
                T target = BeanUtils.instantiate(clzz);
                BeanUtils.copyProperties(source, target);
                output.add(target);
            }
        }
        return output;
    }

}
