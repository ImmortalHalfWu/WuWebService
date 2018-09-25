package com.wu.immortal.half.configs;

import com.google.gson.Gson;
import com.wu.immortal.half.sql.dao.utils.DaoUtil;
import com.wu.immortal.half.utils.LogUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ApplicationConfig {

    private static final String CONFIG_PROPERTIES_NAME = "applicationConfig.properties";
    private static final String CONFIG_PROPERTIES_PATH =
            Objects.requireNonNull(ApplicationConfig.class.getClassLoader().getResource(CONFIG_PROPERTIES_NAME)).getPath();

    private static ApplicationConfig applicationConfigBean;

    public static void init() throws IOException {
        LogUtil.i("加载配置文件：" + CONFIG_PROPERTIES_PATH);
        if (applicationConfigBean == null) {
            synchronized (ApplicationConfig.class) {
                loadConfigFile();
            }
        }

    }

    private static void loadConfigFile() throws IOException {
        String json = PropertieUtil.propertieToJson(CONFIG_PROPERTIES_PATH);
        LogUtil.i("加载配置文件成功：" + json);
        applicationConfigBean = new Gson().fromJson(json, ApplicationConfig.class);
        LogUtil.i("加载配置文件Bean：" + applicationConfigBean.toString());
    }

    public static ApplicationConfig instance() {
        return applicationConfigBean;
    }

    public synchronized void uploadToFile() {
        if (applicationConfigBean == null) {
            return;
        }
        LogUtil.i("更新配置文件：" + applicationConfigBean.toString());
        Map<String, Object> stringObjectMap = DaoUtil.object2Map(applicationConfigBean);
        PropertieUtil.mapToPropertie(stringObjectMap, CONFIG_PROPERTIES_PATH);
        LogUtil.i("配置文件更新成功，重新加载配置文件");
        try {
            loadConfigFile();
        } catch (IOException e) {
            LogUtil.e("加载配置文件失败!", e);
        }
    }

    private boolean debug;
    private String payClientId;
    private String payClientSecret;
    private int payKDT_ID;
    private String payToken;
    private long payTokenEndTime;
    private String sMSAppCode;
    private String sMSUrlHost;
    private String sMSUrlPath;
    private String sqlUrl;
    private String sqlUser;
    private String sqlPassWord;

    private long workerSqlRefreshDelay;
    private long workerRefreshPayTokenDelay;


    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getPayClientId() {
        return payClientId;
    }

    public void setPayClientId(String payClientId) {
        this.payClientId = payClientId;
    }

    public String getPayClientSecret() {
        return payClientSecret;
    }

    public void setPayClientSecret(String payClientSecret) {
        this.payClientSecret = payClientSecret;
    }

    public long getPayKDT_ID() {
        return payKDT_ID;
    }

    public void setPayKDT_ID(int payKDT_ID) {
        this.payKDT_ID = payKDT_ID;
    }

    public String getPayToken() {
        return payToken;
    }

    public void setPayToken(String payToken) {
        this.payToken = payToken;
    }

    public long getPayTokenEndTime() {
        return payTokenEndTime;
    }

    public void setPayTokenEndTime(long payTokenEndTime) {
        this.payTokenEndTime = payTokenEndTime;
    }

    public String getSMSAppCode() {
        return sMSAppCode;
    }

    public void setSMSAppCode(String sMSAppCode) {
        this.sMSAppCode = sMSAppCode;
    }

    public String getSMSUrlHost() {
        return sMSUrlHost;
    }

    public void setSMSUrlHost(String sMSUrlHost) {
        this.sMSUrlHost = sMSUrlHost;
    }

    public String getSMSUrlPath() {
        return sMSUrlPath;
    }

    public void setSMSUrlPath(String sMSUrlPath) {
        this.sMSUrlPath = sMSUrlPath;
    }

    public String getSqlUrl() {
        return sqlUrl;
    }

    public void setSqlUrl(String sqlUrl) {
        this.sqlUrl = sqlUrl;
    }

    public String getSqlUser() {
        return sqlUser;
    }

    public void setSqlUser(String sqlUser) {
        this.sqlUser = sqlUser;
    }

    public String getSqlPassWord() {
        return sqlPassWord;
    }

    public void setSqlPassWord(String sqlPassWord) {
        this.sqlPassWord = sqlPassWord;
    }

    public long getWorkerSqlRefreshDelay() {
        return workerSqlRefreshDelay;
    }

    public void setWorkerSqlRefreshDelay(long workerSqlRefreshDelay) {
        this.workerSqlRefreshDelay = workerSqlRefreshDelay;
    }

    public long getWorkerRefreshPayTokenDelay() {
        return workerRefreshPayTokenDelay;
    }

    public void setWorkerRefreshPayTokenDelay(long workerRefreshPayTokenDelay) {
        this.workerRefreshPayTokenDelay = workerRefreshPayTokenDelay;
    }

    @Override
    public String toString() {
        return "ApplicationConfig{" +
                "debug=" + debug +
                ", payClientId='" + payClientId + '\'' +
                ", payClientSecret='" + payClientSecret + '\'' +
                ", payKDT_ID=" + payKDT_ID +
                ", payToken='" + payToken + '\'' +
                ", payTokenEndTime=" + payTokenEndTime +
                ", sMSAppCode='" + sMSAppCode + '\'' +
                ", sMSUrlHost='" + sMSUrlHost + '\'' +
                ", sMSUrlPath='" + sMSUrlPath + '\'' +
                ", sqlUrl='" + sqlUrl + '\'' +
                ", sqlUser='" + sqlUser + '\'' +
                ", sqlPassWord='" + sqlPassWord + '\'' +
                '}';
    }
}
