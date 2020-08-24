package com.im.user.entity.enums;

public enum GenderEnum {
    MALE(Byte.valueOf("0")),
    FEMALE(Byte.valueOf("1"));

    GenderEnum(Byte code, String name)
    {
        this.code = code;
        this.name = name;
    }

    private Byte code;
    private String name;

    GenderEnum(Byte code) {
        this.code = code;
    }

    public Byte getCode()
    {
        return this.code;
    }
    public String getName(){
        return this.name;
    }
    public static GenderEnum codeOf(Byte code) {
        switch (code)
        {
            case 0: return MALE;
            case 1: return FEMALE;
            default: return null;
        }
    }
    public static GenderEnum nameOf(String name){
        if(name == null){
            return null;
        }
        if(name.equals("male")){
            return MALE;
        }else if(name.equals("female")){
            return FEMALE;
        }else{
            return null;
        }
    }

}
