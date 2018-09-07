package com.wu.immortal.half.sql.bean;


import com.wu.immortal.half.sql.bean.enums.ORDER_TYPE;
import com.wu.immortal.half.sql.bean.enums.PLATFORM_TYPE;
import com.wu.immortal.half.sql.bean.enums.SCAN_OR_ORDER_TYPE;

public class HistoricalRecordsBean extends BaseBean {

    private transient PLATFORM_TYPE platformType;
    private Integer platform;
    private String tagName;
    private transient ORDER_TYPE orderTypeEnum;
    private Integer orderType;
    private String orderTime;
    private String stockNum;
    private String stockMoney;
    private String postitionRatio;
    private Boolean success;
    private String erroMsg;
    private transient SCAN_OR_ORDER_TYPE scanOrOrderTypeEnum;
    private Integer scanOrOrder;


    public HistoricalRecordsBean(
            Integer id,
            Integer userId,
            Integer platformType,
            String tagName,
            Integer orderType,
            String orderTime,
            String stockNum,
            String stockMoney,
            String postitionRatio,
            Boolean isSuc,
            String erroMsg,
            Integer scanOrOrderType) {
        super(id, userId);
        this.platform = platformType;
        this.platformType = PLATFORM_TYPE.valueOf(platform);
        this.tagName = tagName;
        this.orderType = orderType;
        orderTypeEnum = ORDER_TYPE.valueOf(orderType);
        this.orderTime = orderTime;
        this.stockNum = stockNum;
        this.stockMoney = stockMoney;
        this.postitionRatio = postitionRatio;
        this.success = isSuc;
        this.erroMsg = erroMsg;
        this.scanOrOrder = scanOrOrderType;
        scanOrOrderTypeEnum = SCAN_OR_ORDER_TYPE.valueOf(scanOrOrderType);
    }

    public PLATFORM_TYPE getPlatformType() {
        return platformType;
    }

    public Integer getPlatform() {
        return platform;
    }

    public String getTagName() {
        return tagName;
    }

    public ORDER_TYPE getOrderTypeEnum() {
        return orderTypeEnum;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getStockNum() {
        return stockNum;
    }

    public String getStockMoney() {
        return stockMoney;
    }

    public String getPostitionRatio() {
        return postitionRatio;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getErroMsg() {
        return erroMsg;
    }

    public SCAN_OR_ORDER_TYPE getScanOrOrderTypeEnum() {
        return scanOrOrderTypeEnum;
    }

    public Integer getScanOrOrder() {
        return scanOrOrder;
    }


    private static HistoricalRecordsBean NULL_INSTANCE;

    public static HistoricalRecordsBean newInstance() {
        if (NULL_INSTANCE == null) {
            NULL_INSTANCE = new HistoricalRecordsBean(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                    );
        }
        return NULL_INSTANCE;
    }


    public static HistoricalRecordsBean newInstance(
            PLATFORM_TYPE platformType,
            String tagName,
            ORDER_TYPE orderType,
            String orderTime,
            String stockNum,
            String stockMoney,
            String postitionRatio,
            Boolean success,
            String erroMsg,
            SCAN_OR_ORDER_TYPE scanOrOrderType) {
        return new HistoricalRecordsBean(
                null, 0,
                platformType.getCode(),  tagName,
                orderType.getCode(), orderTime,
                stockNum, stockMoney,
                postitionRatio,
                success, erroMsg,
                scanOrOrderType.getCode()
        );
    }


    @Override
    public String toString() {
        return "HistoricalRecordsBean{" +
                "platformType=" + platformType +
                ", platform=" + platform +
                ", tagName='" + tagName + '\'' +
                ", orderTypeEnum=" + orderTypeEnum +
                ", orderType=" + orderType +
                ", orderTime='" + orderTime + '\'' +
                ", stockNum='" + stockNum + '\'' +
                ", stockMoney='" + stockMoney + '\'' +
                ", postitionRatio='" + postitionRatio + '\'' +
                ", success=" + success +
                ", erroMsg='" + erroMsg + '\'' +
                ", scanOrOrderTypeEnum=" + scanOrOrderTypeEnum +
                ", scanOrOrder=" + scanOrOrder +
                '}';
    }
}
