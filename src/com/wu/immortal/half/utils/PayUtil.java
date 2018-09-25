package com.wu.immortal.half.utils;

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
        if (yzClient == null) {
            yzClient = new DefaultYZClient();
        }
        yzClient.setAuth(token);
    }

    public synchronized YouzanPayQrcodeCreateResult createPayQR(String qrName, String money) {
        // todo 支付接口封装
        YouzanPayQrcodeCreate youzanPayQrcodeCreate = new YouzanPayQrcodeCreate();
        YouzanPayQrcodeCreateParams youzanPayQrcodeCreateParams = new YouzanPayQrcodeCreateParams();
        youzanPayQrcodeCreateParams.setQrName(qrName);
        youzanPayQrcodeCreateParams.setQrPrice(money);
        //二维码类型. QR_TYPE_FIXED_BY_PERSON ：无金额二维码，扫码后用户需自己输入金额； QR_TYPE_NOLIMIT ： 确定金额二维码，可以重复支付; QR_TYPE_DYNAMIC：确定金额二维码，只能被支付一次
        youzanPayQrcodeCreateParams.setQrType("QR_TYPE_DYNAMIC");

        youzanPayQrcodeCreate.setAPIParams(youzanPayQrcodeCreateParams);

        YouzanPayQrcodeCreateResult result = yzClient.invoke(youzanPayQrcodeCreate);
        LogUtil.i("生成支付二维码成功。" + "qrName = " + qrName + "  money = " + money + "  二维码数据 = " + JsonUtils.toJson(result));
        return result;
    }

    /**
     * 刷新token， 放出给外部使用
     */
    public synchronized void refreshToken() {
        initToken();
        initClient();
        LogUtil.i("刷新payToken成功");
    }

}
