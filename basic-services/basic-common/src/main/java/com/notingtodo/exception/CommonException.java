package com.notingtodo.exception;

/**
 * 公共异常
 * Created by qilin.liu on 2018/6/30.
 */
public class CommonException extends RuntimeException{ //继承运行时异常， 默认序列化编码为 1L

    //错误编码， 枚举类型
    private ErrorCode errorCode;

    //带参数构造函数
    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    //获得错误编码 枚举对象
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public int getCode() {
        return errorCode.getCode();
    }

    public String getMsg() {
        return errorCode.getMsg();
    }

}