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
        ROLE_CUSTOMER("普通用户",0),
        ROLE_ADMIN("管理员",1),
        ROLE_SUPRER_ADMIN("超级管理员",2),
        ;

        ROLE(String name, Integer code)
        {

            this.name = name;
            this.code = code;
        }

        private Integer code;
        private String name;

        public Integer getCode()
        {
            return this.code;
        }
        public String getName() {
            return this.name;
        }
        public static String getName(Integer code) {
            switch (code)
            {
                case 0: return "普通用户";
                case 1: return "管理员";
                case 2: return "超级管理员";
                default: return "";
            }
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
        public static boolean getBool(Byte code)
        {
            switch (code)
            {
                case 0: return true;
                case 1: return false;
                default: return false;
            }
        }
    }
    public enum NAMESPACE{
        MEETING_ROOM(0);


        NAMESPACE(Integer code)
        {
            this.code = code;
        }

        private Integer code;

        public Integer getCode()
        {
            return this.code;
        }
    }
}
