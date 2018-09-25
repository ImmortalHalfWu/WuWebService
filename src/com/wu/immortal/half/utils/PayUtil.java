package com.wu.immortal.half.utils;

import com.google.gson.JsonObject;
import com.wu.immortal.half.configs.ApplicationConfig;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.client.oauth.OAuth;
import com.youzan.open.sdk.client.oauth.OAuthContext;
import com.youzan.open.sdk.client.oauth.OAuthFactory;
import com.youzan.open.sdk.client.oauth.OAuthType;
import com.youzan.open.sdk.client.oauth.model.OAuthToken;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanPayQrcodeCreate;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanPayQrcodeCreateParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanPayQrcodeCreateResult;
import com.youzan.open.sdk.util.json.JsonUtils;

public class PayUtil {

    private static PayUtil payUtil;
    private YZClient yzClient;

    private PayUtil() {
        initToken();
        initClient();
        LogUtil.i("PayUtil初始化成功");
    }

    public static void init() {
        if (payUtil == null) {
            synchronized (PayUtil.class) {
                payUtil = new PayUtil(
                );
            }
        }
    }

    public static PayUtil getInstance() {
        return payUtil;
    }

    private void initToken() {
        // todo token刷新问题
        ApplicationConfig applicationConfig = ApplicationConfig.instance();
        // 若找到token， 并有效期内，则直接返回
        if (applicationConfig.getPayToken() != null
                &&  applicationConfig.getPayTokenEndTime() > DataUtil.getDelayTimeForDay(1)) {
            LogUtil.i("PayToken有效");
            return;
        }

        LogUtil.i("PayToken失效， 重新申请。");

        // 刷新token
        OAuth authorizationCode = OAuthFactory.create(
                OAuthType.SELF,
                new OAuthContext(
                        applicationConfig.getPayClientId(),
                        applicationConfig.getPayClientSecret(),
                        applicationConfig.getPayKDT_ID()
                )
        );

        OAuthToken token = authorizationCode.getToken();

        LogUtil.i("PayToken申请成功：" + token);
        applicationConfig.setPayToken(token.getAccessToken());
        applicationConfig.setPayTokenEndTime(token.getExpiresIn() * 1000 + DataUtil.getNowTimeToLong());
        LogUtil.i("更新至配置文件" );
        applicationConfig.uploadToFile();

    }

    private void initClient() {
        Token token = new Token(ApplicationConfig.instance().getPayToken());
        yzClient = new DefaultYZClient();
        yzClient.setAuth(token);
    }

    private void payTest() {
        // todo 支付接口封装  + 与刷新token并发同步的问题
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lab1", "11");
        jsonObject.addProperty("lab2", "22");

        YouzanPayQrcodeCreate youzanPayQrcodeCreate = new YouzanPayQrcodeCreate();
        YouzanPayQrcodeCreateParams youzanPayQrcodeCreateParams = new YouzanPayQrcodeCreateParams();
        youzanPayQrcodeCreateParams.setLabelIds("[7777777,666,55555]");
        youzanPayQrcodeCreateParams.setQrName("测试生成收款二维码");
        youzanPayQrcodeCreateParams.setQrPrice("1");
        youzanPayQrcodeCreateParams.setQrSource("this is QR source");
        //二维码类型. QR_TYPE_FIXED_BY_PERSON ：无金额二维码，扫码后用户需自己输入金额； QR_TYPE_NOLIMIT ： 确定金额二维码，可以重复支付; QR_TYPE_DYNAMIC：确定金额二维码，只能被支付一次
        youzanPayQrcodeCreateParams.setQrType("QR_TYPE_DYNAMIC");

        youzanPayQrcodeCreate.setAPIParams(youzanPayQrcodeCreateParams);

        YouzanPayQrcodeCreateResult result = yzClient.invoke(youzanPayQrcodeCreate);
        System.out.println(JsonUtils.toJson(result));
    }

    /**
     * 刷新token， 放出给外部使用
     */
    public void refreshToken() {
        initToken();
        initClient();
        LogUtil.i("刷新payToken成功");
    }

}
