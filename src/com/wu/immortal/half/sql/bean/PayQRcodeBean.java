package com.wu.immortal.half.sql.bean;

import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;

public class PayQRcodeBean extends BaseBean {

    private String qrId;
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
    private String qrUrl;       // 支付二维码对应的url


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
        PayQRcodeBean payQRcodeBean = new PayQRcodeBean(null, userId);
        payQRcodeBean.setAllMoney(vipTypeInfo.getAllMoney());
        payQRcodeBean.setMonthMoney(vipTypeInfo.getMonthMoney());
        payQRcodeBean.setSaveMoney(vipTypeInfo.getSaveMoney());
        payQRcodeBean.setTimeLong(vipTypeInfo.getTimeLong());
        payQRcodeBean.setTimeNum(vipTypeInfo.getTimeNum());
        payQRcodeBean.setTimeUnit(vipTypeInfo.getTimeUnit());
        payQRcodeBean.setVipType(vipTypeInfo.getVipType());
        payQRcodeBean.setQrName(vipTypeInfo.getQrName());
        return payQRcodeBean;
    }

    public PayQRcodeBean(Integer id, Integer userId) {
        super(id, userId);
    }

    public PayQRcodeBean(Integer id, Integer userId, String qrId, String qrName, Integer vipType, Integer timeNum, String timeUnit, String timeLong, Integer allMoney, Integer monthMoney, String createTime, Integer saveMoney, String qrUrl) {
        super(id, userId);
        this.qrId = qrId;
        this.qrName = qrName;
        this.vipType = vipType;
        this.timeNum = timeNum;
        this.timeUnit = timeUnit;
        this.timeLong = timeLong;
        this.allMoney = allMoney;
        this.monthMoney = monthMoney;
        this.createTime = createTime;
        this.saveMoney = saveMoney;
        this.qrUrl = qrUrl;
    }

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
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
        if(vipType == null) {
            return null;
        }
        if (vipType / 10 == 1) {
            return VIP_TYPE.VIP_TYPE_SUPER;
        } else {
            return VIP_TYPE.VIP_TYPE_SENIOR;
        }
    }

    public Integer getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(Integer saveMoney) {
        this.saveMoney = saveMoney;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    @Override
    public String toString() {
        return "PayQRcodeBean{" +
                "qrId=" + qrId +
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
