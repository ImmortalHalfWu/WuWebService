package com.wu.immortal.half.sql.bean;


import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;
import com.wu.immortal.half.utils.DataUtil;

public class UserVipInfoBean extends BaseBean{

    private String startTime;
    private String endTime;
    private VIP_TYPE vipTypeEnum;
    private Integer vipType;
    private String startTimeFormat;
    private String endTimeFormat;

    public UserVipInfoBean(Integer id, Integer userId, String startTime, String endTime, Integer vipType) {
        this(
                id,
                userId,
                startTime,
                endTime,
                vipType,
                startTime != null ? DataUtil.timeFormatYMDToString(Long.valueOf(startTime)) : null,
                endTime != null ? DataUtil.timeFormatYMDToString(Long.valueOf(endTime)) : null
        );
    }

    public UserVipInfoBean(Integer id, Integer userId, String startTime, String endTime, Integer vipType, String startTimeFormat, String endTimeFormat) {
        super(id, userId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.startTimeFormat = startTimeFormat;
        this.endTimeFormat = endTimeFormat;
        if (vipType != null) {
            this.vipTypeEnum = VIP_TYPE.valueOf(vipType);
            this.vipType = vipTypeEnum.getCode();
        }
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public VIP_TYPE getVipTypeEnum() {
        if (vipTypeEnum == null && vipType != null) {
            vipTypeEnum = VIP_TYPE.valueOf(vipType);
        }
        return vipTypeEnum;
    }

    public Integer getVipType() {
        return vipType;
    }

    public String getStartTimeFormat() {
        return startTimeFormat;
    }

    public String getEndTimeFormat() {
        return endTimeFormat;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public void setStartTimeFormat(String startTimeFormat) {
        this.startTimeFormat = startTimeFormat;
    }

    public void setEndTimeFormat(String endTimeFormat) {
        this.endTimeFormat = endTimeFormat;
    }

    @Override
    public String toString() {
        return "UserVipInfoBean{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", vipTypeEnum=" + vipTypeEnum +
                ", vipType=" + vipType +
                '}';
    }

    public static UserVipInfoBean newInstanceByVipType(Integer id, Integer userId, String startTime, String endTime, VIP_TYPE vipType) {
        return new UserVipInfoBean(id, userId, startTime, endTime, vipType.getCode());
    }
    public static UserVipInfoBean newInstanceByUserId(Integer userId) {
        return new UserVipInfoBean(null, userId, null, null, null);
    }

    private static UserVipInfoBean NULL_INSTANCE;
    public static UserVipInfoBean newInstance() {
        if (NULL_INSTANCE == null) {
            synchronized (UserVipInfoBean.class) {
                NULL_INSTANCE = new UserVipInfoBean(null, null, null, null, null);
            }
        }
        return NULL_INSTANCE;
    }

}
