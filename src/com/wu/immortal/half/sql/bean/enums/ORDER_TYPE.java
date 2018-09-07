package com.wu.immortal.half.sql.bean.enums;

/**
 * 指定交易类型
 */
public enum ORDER_TYPE {

    ORDER_TYPE_BUY(0),
    ORDER_TYPE_SELL(1),
    ORDER_TYPE_ALL(2);

    ORDER_TYPE(int code) {
        this.code = code;
    }

    private final int code;

    public int getCode() {
        return code;
    }

    public static ORDER_TYPE valueOf(Integer code) {
        if (code == null) {
            return null;
        }
        switch (code) {
            case 2:
                return ORDER_TYPE_ALL;
            case 1:
                return ORDER_TYPE_SELL;
            default:
                return ORDER_TYPE_BUY;
        }
    }

}
