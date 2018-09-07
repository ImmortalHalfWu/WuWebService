package com.wu.immortal.half.sql.bean.enums;

/**
 * 闪电下单 or 严格下单
 */
public enum DOCUMENTARY_TYPE {

    DOCUMENTARY_TYPE_FAST(1),
    DOCUMENTARY_TYPE_STRICT(0);

    DOCUMENTARY_TYPE(int code) {
        this.code = code;
    }

    private final int code;

    public int getCode() {
        return code;
    }

    public static DOCUMENTARY_TYPE valueOf(Integer code) {
        if (code == null) {
            return null;
        }
        switch (code) {
            case 1:
                return DOCUMENTARY_TYPE_FAST;
                default:
                    return DOCUMENTARY_TYPE_STRICT;
        }
    }
}
