package com.wu.immortal.half.sql.bean.enums;

/**
 * 对应平台JD 雪球
 */
public enum PLATFORM_TYPE {

    PLATFORM_TYPE_JD(1),
    PLATFORM_TYPE_XQ(0);

    PLATFORM_TYPE(int code) {
        this.code = code;
    }

    private final int code;

    public int getCode() {
        return code;
    }

    public static PLATFORM_TYPE valueOf(Integer code) {
        if (code == null) {
            return null;
        }
        switch (code) {
            case 0:
                return PLATFORM_TYPE_XQ;
            default:
                return PLATFORM_TYPE_JD;
        }

    }

}
