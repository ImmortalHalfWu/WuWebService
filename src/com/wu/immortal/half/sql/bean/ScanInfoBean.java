package com.wu.immortal.half.sql.bean;


import com.wu.immortal.half.sql.bean.enums.ORDER_TYPE;
import com.wu.immortal.half.utils.FinalUtil;

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
        if (orderTypeEnum == null && orderType != null) {
            orderTypeEnum = ORDER_TYPE.valueOf(orderType);
        }
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


    public void setScanUrl(String scanUrl) {
        this.scanUrl = scanUrl;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setOrderTypeEnum(ORDER_TYPE orderTypeEnum) {
        this.orderTypeEnum = orderTypeEnum;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public void setCanUser(Boolean canUser) {
        this.canUser = canUser;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public static void setNullInstance(ScanInfoBean nullInstance) {
        NULL_INSTANCE = nullInstance;
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
            String scanUrl, Integer userId ) {
        return new ScanInfoBean(null, userId, scanUrl, null, null, null, null);
    }

    public static ScanInfoBean newInstanceById(
            Integer id ) {
        return new ScanInfoBean(id, null, null, null, null, null, null);
    }

    public static ScanInfoBean newInstanceByUserId(
            Integer userId ) {
        return new ScanInfoBean(null, userId, null, null, null, null, null);
    }


    public boolean checkNull() {
        return FinalUtil.checkNull(scanUrl)
                || FinalUtil.checkNull(tagName)
                || frequency == null
//                || canUser == null
                || orderType == null;
    }

    @Override
    public String toString() {
        return "ScanInfoBean{" +
                "scanUrl='" + scanUrl + '\'' +
                ", tagName='" + tagName + '\'' +
                ", orderTypeEnum=" + orderTypeEnum +
                ", frequency=" + frequency +
                ", canUser=" + canUser +
                ", orderType=" + orderType +
                '}';
    }
}
