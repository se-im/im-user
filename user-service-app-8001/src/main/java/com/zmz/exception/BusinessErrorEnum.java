package com.zmz.exception;


import com.zmz.response.error.CommonError;

public enum BusinessErrorEnum implements CommonError
{

    UNKNOWN_ERROR(5000, "未知错误"),

    PARAMETER_VALIDATION_ERROR(1000, "参数不合法"),

    PARAMETER_EMPTY_ERROR(1001, "参数不能为空"),

    USER_NOT_EXIST(1002,"用户不存在"),

    INVALID_PASSWORD(1003, "　密码不存在"),

    REGISTER_FAILED(1004, "注册失败")
    ;

    BusinessErrorEnum(int errorCode, String errorMessage)
    {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private int errorCode;
    private String errorMessage;

    @Override
    public int getErrorCode()
    {
        return errorCode;
    }

    @Override
    public String getErrorMessage()
    {
        return errorMessage;
    }

    @Override
    public CommonError setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
        return this;
    }
}
