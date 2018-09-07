package com.wu.immortal.half.sql.bean.enums;

/**
 * 是扫描还是跟单
 */
public enum SCAN_OR_ORDER_TYPE {

    SCAN_OR_ORDER_TYPE_SCAN(0),
    SCAN_OR_ORDER_TYPE_ORDER(1);

    SCAN_OR_ORDER_TYPE(int code) {
        this.code = code;
    }

    private final int code;

    public int getCode() {
        return code;
    }

    public static SCAN_OR_ORDER_TYPE valueOf(Integer code) {
        if (code == null) {
            return null;
        }
        switch (code) {
            case 0:
                return SCAN_OR_ORDER_TYPE_SCAN;
                default:
                    return SCAN_OR_ORDER_TYPE_ORDER;
        }
    }

}
