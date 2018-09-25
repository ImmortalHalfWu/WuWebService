package com.wu.immortal.half.sql.bean;

import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;

public class PayQRcodeBean extends BaseBean {

    private Integer qrId;
    private String qrImg;
    private String qrName;
    private Integer vipType;    // 对应会员类型，超级会员年11，6月12,1月13，高级会员，年21, 6月22，1月23
    private VIP_TYPE enumVipType;   // 对应会员类型， 不参与数据库操作
    private Integer timeNum;    // 对应会员时长，配合timeUnit使用
    private String timeUnit;  // 对应会员时长单位，年 or 月
    private String timeLong;    // 对应会员时长ms
    private Integer allMoney;     // 此二维码支付金额，单位分
    private Integer monthMoney;    // 对应一个月多少钱，单位分
    private String createTime;  // 创建时间

    public PayQRcodeBean(Integer id, Integer userId) {
        super(id, userId);
    }

    public Integer getQrId() {
        return qrId;
    }

    public void setQrId(Integer qrId) {
        this.qrId = qrId;
    }

    public String getQrImg() {
        return qrImg;
    }

    public void setQrImg(String qrImg) {
        this.qrImg = qrImg;
    }

    public String getQrName() {
        return qrName;
    }

    public void setQrName(String qrName) {
        this.qrName = qrName;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        // 超级会员年11，6月12,1月13，高级会员，年21, 6月22，1月23
        enumVipType = vipType / 10 == 1 ? VIP_TYPE.VIP_TYPE_SUPER : VIP_TYPE.VIP_TYPE_SENIOR;
        this.vipType = vipType;
    }

    public Integer getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(Integer timeNum) {
        this.timeNum = timeNum;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public Integer getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(Integer allMoney) {
        this.allMoney = allMoney;
    }

    public Integer getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(Integer monthMoney) {
        this.monthMoney = monthMoney;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public VIP_TYPE getEnumVipType() {
        return enumVipType;
    }


    @Override
    public String toString() {
        return "PayQRcodeBean{" +
                "qrId=" + qrId +
                ", qrImg='" + qrImg + '\'' +
                ", qrName='" + qrName + '\'' +
                ", vipType=" + vipType +
                ", enumVipType=" + enumVipType +
                ", timeNum=" + timeNum +
                ", timeUnit='" + timeUnit + '\'' +
                ", timeLong='" + timeLong + '\'' +
                ", allMoney=" + allMoney +
                ", monthMoney=" + monthMoney +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
