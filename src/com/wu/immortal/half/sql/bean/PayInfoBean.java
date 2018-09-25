package com.wu.immortal.half.sql.bean;

public class PayInfoBean extends BaseBean{

    private Integer qrId;
    private Integer money; // 支付的金额， 单位分
    private String timeFormat;  // 支付时间
    private String tomeToLong; // 支付时间转long
    private String allData;   // 有赞推送的所有数据

    public PayInfoBean(Integer id, Integer userId) {
        super(id, userId);
    }

    public Integer getQrId() {
        return qrId;
    }

    public void setQrId(Integer qrId) {
        this.qrId = qrId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getTomeToLong() {
        return tomeToLong;
    }

    public void setTomeToLong(String tomeToLong) {
        this.tomeToLong = tomeToLong;
    }

    public String getAllData() {
        return allData;
    }

    public void setAllData(String allData) {
        this.allData = allData;
    }

    @Override
    public String toString() {
        return "PayInfoBean{" +
                "qrId=" + qrId +
                ", money=" + money +
                ", timeFormat='" + timeFormat + '\'' +
                ", tomeToLong='" + tomeToLong + '\'' +
                ", allData='" + allData + '\'' +
                '}';
    }
}
