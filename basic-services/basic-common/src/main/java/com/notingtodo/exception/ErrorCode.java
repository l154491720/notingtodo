package com.notingtodo.exception;

/**
 * Created by qilin.liu on 2018/6/30.
 */
public enum  ErrorCode {

    OK(0, ""),
    FAIL(-1, "操作失败"),
    RPC_ERROR(-2,"远程调度失败"),
    USER_NOT_FOUND(1000,"用户不存在"),
    USER_PASSWORD_ERROR(1001,"密码错误"),
    GET_TOKEN_FAIL(1002,"获取token失败"),
    TOKEN_IS_NOT_MATCH_USER(1003,"请使用自己的token进行接口请求"),
    REQUEST_PARAMS_ERROR(40035,"请求参数不合法"),
    USER_ALREADY_EXIST(61453,"客户账号已存在")
    ;

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 通过错误编码来得到错误编码对象
     * @param code
     * @return
     */
    public static ErrorCode codeOf(int code){
        for (ErrorCode state : values()){
            if(state.getCode() == code){
                return state;
            }
        }
        return null;
    }
}