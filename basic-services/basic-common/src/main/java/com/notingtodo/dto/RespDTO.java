package com.notingtodo.dto;

import java.io.Serializable;

/**
 * 反馈传输对象,并且该类是一个泛型类
 * Created by qilin.liu on 2018/6/30.
 */
public class RespDTO<T> implements Serializable{ //实现序列化,默认序列化编号为 1L

    public int code;
    public String error = "";
    public T data;


    /**
     * 静态方法 构造反馈对象的方法，外部传进来的数据放在反馈对象的 data 属性中
     * @param data
     * @return
     */
    public static RespDTO onSuc(Object data) {
        RespDTO resp = new RespDTO();
        resp.data = data;
        return resp;
    }

    /**
     * 重写 toString() 方法, 格式化展示反馈结果
     * @return
     */
    @Override
    public String toString() {
        return "RespDTO{" +
                "code=" + code +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}