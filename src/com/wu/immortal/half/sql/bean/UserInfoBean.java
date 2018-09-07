package com.wu.immortal.half.sql.bean;

public class UserInfoBean extends BaseBean{

    private Integer vipId;
    private String phone;
    private String registTime;
    private Boolean isLogin;
    private String passWord;
    private String token;

    public UserInfoBean(Integer id, Integer userId, Integer vipId, String phone, String registTime, Boolean isLogin, String passWord, String token) {
        super(id, userId);
        this.vipId = vipId;
        this.phone = phone;
        this.registTime = registTime;
        this.isLogin = isLogin;
        this.passWord = passWord;
        this.token = token;
    }

    public Integer getVipId() {
        return vipId;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegistTime() {
        return registTime;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "vipId=" + vipId +
                ", phone='" + phone + '\'' +
                ", registTime='" + registTime + '\'' +
                ", isLogin=" + isLogin +
                ", passWord='" + passWord + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    private static UserInfoBean NULL_INSTANCE;
    public static UserInfoBean newInstance() {
        if (NULL_INSTANCE == null) {
            synchronized (UserInfoBean.class) {
                NULL_INSTANCE = new UserInfoBean(null, null, null, null, null, null, null, null);
            }
        }
        return NULL_INSTANCE;
    }

    public static UserInfoBean newInstanceById(int id) {
        return new UserInfoBean(id, null, null, null, null, null, null, null);
    }

    public static UserInfoBean newInstanceByVipId(int vipId) {
        return new UserInfoBean(null, null, vipId, null, null, null, null, null);
    }
}
