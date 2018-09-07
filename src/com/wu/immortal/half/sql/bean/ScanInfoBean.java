package com.wu.immortal.half.sql.bean;


import com.wu.immortal.half.sql.bean.enums.ORDER_TYPE;

public class ScanInfoBean extends BaseBean{

    private String scanUrl;
    private String tagName;
    private transient ORDER_TYPE orderTypeEnum;
    private Integer frequency;
    private Boolean canUser;
    private Integer orderType;

    public ScanInfoBean(Integer id, Integer userId, String scanUrl, String tagName, Integer orderType, Integer frequency, Boolean canUser) {
        super(id, userId);
        this.scanUrl = scanUrl;
        this.tagName = tagName;
        if (orderType != null) {
            this.orderTypeEnum = ORDER_TYPE.valueOf(orderType);
        }
        this.frequency = frequency;
        this.canUser = canUser;
        this.orderType = orderType;
    }

    public String getScanUrl() {
        return scanUrl;
    }

    public String getTagName() {
        return tagName;
    }

    public ORDER_TYPE getOrderTypeEnum() {
        return orderTypeEnum;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public Boolean isCanUser() {
        return canUser;
    }

    public Integer getOrderType() {
        return orderType;
    }

    private static ScanInfoBean NULL_INSTANCE;
    public static ScanInfoBean newInstance() {
        if (NULL_INSTANCE == null) {
            NULL_INSTANCE = new ScanInfoBean(null, null, null, null, null, null, null);
        }
        return NULL_INSTANCE;
    }

    public static ScanInfoBean newInstance(
            Integer userId,
            String scanUrl,
            String tagName,
            ORDER_TYPE orderType,
            Integer frequency,
            Boolean canUser) {
        return new ScanInfoBean(null, userId, scanUrl, tagName, orderType.getCode(), frequency, canUser);
    }
    public static ScanInfoBean newInstanceByUrl(
            String scanUrl) {
        return new ScanInfoBean(null, null, scanUrl, null, null, null, null);
    }
}
