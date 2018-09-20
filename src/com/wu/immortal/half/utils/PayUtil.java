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
//    private String CLIENT_ID = "afcd2eb5b88fbdbd90";
//    private String CLIENT_SECRET = "6f2d78752dd8aaded87ae9e3b027c058";
//    private long KDT_ID = 41461397;
//    // 0917-0924
//    private String payToken = "9de830f1a71835c4950871a84e0c9d20";
//    private long endTime;
    private YZClient yzClient;

    private PayUtil() {
        initToken();
        initClient();
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
        yzClient = new DefaultYZClient(new Token(ApplicationConfig.instance().getPayToken()));
        LogUtil.i("PayUtil初始化成功");
    }

    private void payTest() {
        // todo 支付接口封装
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lab1", "11");
        jsonObject.addProperty("lab2", "22");

        YouzanPayQrcodeCreate youzanPayQrcodeCreate = new YouzanPayQrcodeCreate();
        YouzanPayQrcodeCreateParams youzanPayQrcodeCreateParams = new YouzanPayQrcodeCreateParams();
        youzanPayQrcodeCreateParams.setLabelIds("[1,2,3]");
        youzanPayQrcodeCreateParams.setQrName("测试生成收款二维码");
        youzanPayQrcodeCreateParams.setQrPrice("1");
        youzanPayQrcodeCreateParams.setQrSource("this is QR source");
        youzanPayQrcodeCreateParams.setQrType("QR_TYPE_DYNAMIC");

        youzanPayQrcodeCreate.setAPIParams(youzanPayQrcodeCreateParams);

        YouzanPayQrcodeCreateResult result = yzClient.invoke(youzanPayQrcodeCreate);
        System.out.println(JsonUtils.toJson(result));
    }

}
