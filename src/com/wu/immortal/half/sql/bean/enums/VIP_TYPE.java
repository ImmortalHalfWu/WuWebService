package com.wu.immortal.half.sql.bean.enums;

/**
 * 会员类型
 */
public enum VIP_TYPE {

    VIP_TYPE_ORDINARY(0),
    VIP_TYPE_SENIOR(1),
    VIP_TYPE_SUPER(2);

    VIP_TYPE(int code) {
        this.code = code;
    }

    private final int code;

    public int getCode() {
        return code;
    }

    public static VIP_TYPE valueOf(Integer code) {
        if (code == null) {
            return null;
        }
        switch (code) {
            case 2:
                return VIP_TYPE_SUPER;
            case 1:
                return VIP_TYPE_SENIOR;
            default:
                return VIP_TYPE_ORDINARY;
        }
    }

}
