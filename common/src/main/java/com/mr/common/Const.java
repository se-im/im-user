package com.mr.common;

public class Const {

    public enum UserStatus{
        NORMAL(Byte.valueOf("0")),
        DELETED(Byte.valueOf("1"));


        UserStatus(Byte code)
        {
            this.code = code;
        }

        private Byte code;

        public Byte getCode()
        {
            return this.code;
        }
    }


}
