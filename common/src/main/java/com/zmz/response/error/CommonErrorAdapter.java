package com.zmz.response.error;

/**
 * @author zhaomanzhou
 * @date 2020/3/18 12:00 上午
 */
public class CommonErrorAdapter implements CommonError {

    private int errCode;
    private String errMsg;

    public CommonErrorAdapter(int errCode, String msg)
    {
        this.errCode = errCode;
        this.errMsg = msg;
    }
    @Override
    public int getErrorCode() {
        return errCode;
    }

    @Override
    public String getErrorMessage() {
        return errMsg;
    }

    @Override
    public CommonError setErrorMessage(String errorMessage) {
        this.errMsg = errorMessage;
        return this;
    }
}
