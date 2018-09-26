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
    private Integer saveMoney;   // 节省了多少钱，单位分


    public static PayQRcodeBean createSuper1YearBean(Integer userId) {
        return createPayQRcodeBean(userId,  PayCodeVipTypeInfoBean.VIP_TYPE_SUPER_1YEAR);
    }

    public static PayQRcodeBean createSuper3MonthBean(Integer userId) {
        return createPayQRcodeBean(userId,  PayCodeVipTypeInfoBean.VIP_TYPE_SUPER_3MONTH);
    }

    public static PayQRcodeBean createSuper1MonthBean(Integer userId) {
        return createPayQRcodeBean(userId,  PayCodeVipTypeInfoBean.VIP_TYPE_SUPER_1MONTH);
    }

    public static PayQRcodeBean createSenior1YearBean(Integer userId) {
        return createPayQRcodeBean(userId,  PayCodeVipTypeInfoBean.VIP_TYPE_SENIOR_1YEAR);
    }

    public static PayQRcodeBean createSenior3MonthBean(Integer userId) {
        return createPayQRcodeBean(userId,  PayCodeVipTypeInfoBean.VIP_TYPE_SENIOR_3MONTH);
    }

    public static PayQRcodeBean createSenior1MonthBean(Integer userId) {
        return createPayQRcodeBean(userId,  PayCodeVipTypeInfoBean.VIP_TYPE_SENIOR_1MONTH);
    }

    private static PayQRcodeBean createPayQRcodeBean(Integer userId, PayCodeVipTypeInfoBean.QRCodeVipTypeInfo vipTypeInfo) {
        // todo createTime 字段， 尽量靠近二维码创建时间
        PayQRcodeBean payQRcodeBean = new PayQRcodeBean(null, userId);
        payQRcodeBean.setAllMoney(vipTypeInfo.getAllMoney());
        payQRcodeBean.setMonthMoney(vipTypeInfo.getMonthMoney());
        payQRcodeBean.setSaveMoney(vipTypeInfo.getSaveMoney());
        payQRcodeBean.setTimeLong(vipTypeInfo.getTimeLong());
        payQRcodeBean.setTimeNum(vipTypeInfo.getTimeNum());
        payQRcodeBean.setTimeUnit(vipTypeInfo.getTimeUnit());
        payQRcodeBean.setVipType(vipTypeInfo.getVipType());
        return payQRcodeBean;
    }

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
        enumVipType = VIP_TYPE.valueOf(vipType / 10);
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
        // 超级会员年11，6月12,1月13，高级会员，年21, 6月22，1月23
        return enumVipType == null ? enumVipType = VIP_TYPE.valueOf(vipType / 10) : enumVipType;
    }

    public Integer getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(Integer saveMoney) {
        this.saveMoney = saveMoney;
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
                ", saveMoney='" + saveMoney + '\'' +
                '}';
    }
}
