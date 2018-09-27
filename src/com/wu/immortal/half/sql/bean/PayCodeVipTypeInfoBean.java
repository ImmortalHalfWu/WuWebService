package com.wu.immortal.half.sql.bean;

import java.util.Objects;

/**
 * 标示支付二维码对应的会员信息
 */
public class PayCodeVipTypeInfoBean {


    /**
     * 超级会员一年
     */
    public static final QRCodeVipTypeInfo VIP_TYPE_SUPER_1YEAR = createVipTypeInfo(
            11,
            1,
            "年",
            "31536000000",
            28800,
            2400,
            7200,
            "尊敬的用户,您正在开通包年超级会员服务"
    );
    /**
     * 超级会员3个月
     */
    public static final QRCodeVipTypeInfo VIP_TYPE_SUPER_3MONTH = createVipTypeInfo(
            12,
            3,
            "月",
            "7568640000",
            8100,
            2700,
            900,
            "尊敬的用户,您正在开通3个月超级会员服务"
    );
    /**
     * 超级会员一个月
     */
    public static final QRCodeVipTypeInfo VIP_TYPE_SUPER_1MONTH = createVipTypeInfo(
            13,
            1,
            "月",
            "2678400000",
            3000,
            3000,
            0,
            "尊敬的用户,您正在开通1个月超级会员服务"
    );

    /**
     * 高级会员1年
     */
    public static final QRCodeVipTypeInfo VIP_TYPE_SENIOR_1YEAR = createVipTypeInfo(
            21,
            1,
            "年",
            "31536000000",
            14900,
            1240,
            3100,
            "尊敬的用户,您正在开通包年高级会员服务"
    );
    /**
     * 高级会员3个月
     */
    public static final QRCodeVipTypeInfo VIP_TYPE_SENIOR_3MONTH = createVipTypeInfo(
            22,
            3,
            "月",
            "7568640000",
            3900,
            1300,
            600,
            "尊敬的用户,您正在开通3个月高级会员服务"
    );
    /**
     * 高级会员1个月
     */
    public static final QRCodeVipTypeInfo VIP_TYPE_SENIOR_1MONTH = createVipTypeInfo(
            23,
            1,
            "月",
            "2678400000",
            1500,
            1500,
            0,
            "尊敬的用户,您正在开通1个月高级会员服务"
    );

    private static QRCodeVipTypeInfo createVipTypeInfo(int vipType, int timeNum, String timeUnit, String timeLong, int allMoney, int monthMoney, int saveMoney, String qrName) {
        return new QRCodeVipTypeInfo(vipType, timeNum, timeUnit, timeLong, allMoney, monthMoney, saveMoney, qrName);
    }

    public static class QRCodeVipTypeInfo {

        private final int vipType;
        private final int timeNum;
        private final String timeUnit;
        private final String timeLong;
        private final int allMoney;
        private final int monthMoney;
        private final int saveMoney;
        private final String qrName;

        QRCodeVipTypeInfo(int vipType, int timeNum, String timeUnit, String timeLong, int allMoney, int monthMoney, int saveMoney, String qrName) {
            this.vipType = vipType;
            this.timeNum = timeNum;
            this.timeUnit = timeUnit;
            this.timeLong = timeLong;
            this.allMoney = allMoney;
            this.monthMoney = monthMoney;
            this.saveMoney = saveMoney;
            this.qrName = qrName;
        }

        public int getVipType() {
            return vipType;
        }

        public int getTimeNum() {
            return timeNum;
        }

        public String getTimeUnit() {
            return timeUnit;
        }

        public String getTimeLong() {
            return timeLong;
        }

        public int getAllMoney() {
            return allMoney;
        }

        public int getMonthMoney() {
            return monthMoney;
        }

        public int getSaveMoney() {
            return saveMoney;
        }

        public String getQrName() {
            return qrName;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            QRCodeVipTypeInfo that = (QRCodeVipTypeInfo) object;
            return getVipType() == that.getVipType() &&
                    getTimeNum() == that.getTimeNum() &&
                    getAllMoney() == that.getAllMoney() &&
                    getMonthMoney() == that.getMonthMoney() &&
                    getSaveMoney() == that.getSaveMoney() &&
                    Objects.equals(getTimeUnit(), that.getTimeUnit()) &&
                    Objects.equals(getTimeLong(), that.getTimeLong());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getVipType(), getTimeNum(), getTimeUnit(), getTimeLong(), getAllMoney(), getMonthMoney(), getSaveMoney());
        }

        @Override
        public String toString() {
            return "QRCodeVipTypeInfo{" +
                    "vipType=" + vipType +
                    ", timeNum=" + timeNum +
                    ", timeUnit='" + timeUnit + '\'' +
                    ", timeLong='" + timeLong + '\'' +
                    ", allMoney=" + allMoney +
                    ", monthMoney=" + monthMoney +
                    ", saveMoney=" + saveMoney +
                    '}';
        }
    }
}
