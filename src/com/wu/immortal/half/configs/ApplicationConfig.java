package com.wu.immortal.half.configs;

import com.google.gson.Gson;
import com.wu.immortal.half.sql.dao.utils.DaoUtil;

import java.io.IOException;
import java.util.Map;

public class ApplicationConfig {

    private static final String CONFIG_PROPERTIES_NAME = "ApplicationConfig.properties";
    private static final String CONFIG_PROPERTIES_PATH = "configs/" + CONFIG_PROPERTIES_NAME;

    private static ApplicationConfig applicationConfigBean;

    public static void init() throws IOException {
        if (applicationConfigBean == null) {
            synchronized (ApplicationConfig.class) {
                String json = PropertieUtil.propertieToJson(CONFIG_PROPERTIES_PATH);
                applicationConfigBean = new Gson().fromJson(json, ApplicationConfig.class);
            }
        }

    }

    public static ApplicationConfig instance() {
        return applicationConfigBean;
    }

    public synchronized void uploadToFile() {

        if (applicationConfigBean == null) {
            return;
        }
        Map<String, Object> stringObjectMap = DaoUtil.object2Map(applicationConfigBean);
        PropertieUtil.mapToPropertie(stringObjectMap, CONFIG_PROPERTIES_PATH);
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

}
