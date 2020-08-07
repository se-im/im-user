package com.mr.common;

public class Const {

    public enum UserStatus{
        NONDELETED(Byte.valueOf("0")),
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
    public enum ROLE{
        ROLE_CUSTOMER(0),
        ROLE_ADMIN(1),
        ROLE_SUPRER_ADMIN(2),
        ;

        ROLE(Integer code)
        {
            this.code = code;
        }

        private Integer code;

        public Integer getCode()
        {
            return this.code;
        }
    }

    public enum VISIBILITY{
        ALLOW(Byte.valueOf("0")),
        NON_ALLOW(Byte.valueOf("1"));


        VISIBILITY(Byte code)
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
