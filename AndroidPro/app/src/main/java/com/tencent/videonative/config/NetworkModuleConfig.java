package com.tencent.videonative.config;

import android.content.Context;

import com.qq.taf.jce.JceStruct;
import com.tencent.videonative.network.NetworkConfig;
import com.tencent.videonative.route.RequestTaskInfo;
import com.tencent.videonative.route.jce.QUA;
import com.tencent.videonative.route.jce.RequestHead;
import com.tencent.videonative.route.jce.ResponseHead;
import com.tencent.videonative.utils.AndroidUtils;
import com.tencent.videonative.utils.DeviceUtils;
import com.tencent.videonative.dimpl.input.jce.VNJCECmd;


/**
 * Created by oscarjiang on 2017/11/6.
 * TODO: 1.APPID暂时用的是腾讯视频的APPID，是否需要独立出来？
 * TODO: 2.如果主程序里面已经有了JCE通道代码，我们这些代码是否还需要？
 */

public class NetworkModuleConfig {
    private static int dpi;

    public static final int APPID = 1000005;
    public static final int MTA_APPID = 1100214736;

    public static void init(Context context) {
//        String remoteConfigDomain = AppConfig.getConfig(AppConfig.SharedPreferencesKey.REMOTE_CONFIG_DOMAIN,null);
//        String remoteConfigIp = AppConfig.getConfig(AppConfig.SharedPreferencesKey.JCE_Service_Default_IP,null);
//        boolean enable = AppConfig.getConfig(AppConfig.SharedPreferencesKey.NETWORK_RC_BLACK_LIST_ENABLE ,1) == 1;  //动态IP 黑名单开关
//        RouteConfig.setDefaultServerAddress(null,null,true);
        NetworkConfig.setup(context, VNJCECmd.class, new NetworkConfig.NetworkConfigCallback() {
            @Override
            public RequestHead createRequestHead(int cmdId, int requestId, int isAuto) {
                return createRepHead(cmdId, requestId, isAuto);
            }

            @Override
            public long getLoginQQUin() {
                long uin = 0;
//                String strUin = LoginManager.getInstance().getQQUin();
//                if (TextUtils.isEmpty(strUin) == false && TextUtils.isDigitsOnly(strUin) == true) {
//                    try {
//                        uin = Long.parseLong(strUin);
//                    } catch (NumberFormatException e) {
//
//                    }
//                }
                return uin;
            }

            @Override
            public int onNetworkRequestFinish(int requestId, int errCode, Throwable exception, RequestTaskInfo requestTaskInfo, JceStruct response, ResponseHead responseHead, JceStruct request) {
//                int testNetWorkStatus = NetworkReporter.getNetWorkTestStatus(errCode);
//                NetworkReporter.reportLog(requestTaskInfo, requestId, errCode, exception, testNetWorkStatus, request, response);
                return 0;
            }
        });
    }

    private static RequestHead createRepHead(int nCmdId, int nRequestId, int isAuto) {
        RequestHead reqHead = new RequestHead();
        reqHead.requestId = nRequestId;
        reqHead.cmdId = nCmdId;
        reqHead.appId = String.valueOf(APPID);
//        reqHead.guid = GUIDManager.getInstance().getGUID();            //传递GUID
        reqHead.qua = createQUA();
//        reqHead.token = createTokenList();
//        reqHead.logReport = createLogReport(isAuto);
//        reqHead.oemPlatform = BuildConfig.OEM_PLATFORM;

        return reqHead;
    }

    private static QUA createQUA()
    {
        QUA qua = new QUA();
        qua.platformVersion = DeviceUtils.platformVersion;		//平台信息，model，sdk版本，release版本
        qua.screenWidth = DeviceUtils.currentDeviceWidth;		//屏幕宽度
        qua.screenHeight = DeviceUtils.currentDeviceHeight;		//屏幕高度
        qua.versionCode = DeviceUtils.appVersionCode;			//app版本号
        qua.versionName = DeviceUtils.appVersionName;			//app 版本名称
        qua.platform = 3;										//3 表示 aphone
        qua.markerId = 1;										//渠道编号，视频为 1
//        qua.networkMode = NetworkUtil.getGroupNetType();
        qua.imei = DeviceUtils.getDeviceIMEI();
        qua.imsi = DeviceUtils.getDeviceIMSI();
        if (dpi == 0) {
            try {
                dpi = AndroidUtils.getCurrentApplication().getApplicationContext().getResources().getDisplayMetrics().densityDpi;
            }
            catch (Exception e) {
                //曾经看到过QQLiveApplication代码里说getResource()极端情况下会返回空，所以这里catch了一下
            }
        }
        qua.densityDpi = dpi;
//        qua.channelId = ChannelConfig.getInstance().getChannelID() + "";	//渠道id
//        qua.omgId = DeviceUtils.getOmgID();
//        qua.extentData = createExtentData();
        qua.deviceId = DeviceUtils.getAndroidId();
        qua.deviceModel  = DeviceUtils.getModel();
        qua.deviceType = DeviceUtils.isPad() ? 2 : 1;
        qua.mac = DeviceUtils.getDeviceMacAddr();
//        qua.coordinates = DeviceUtils.getCoordinates();
//        qua.areaMode = AreaModeManager.getInstance().getAreaMode();
        return qua;
    }



//    //统一标识位，[0x01]是否调试状态标识0表示否（不要下发调试信息）
//    private static ExtentData createExtentData(){
//        ExtentData extentData = new ExtentData();
//        ExtentData sourceExtent = ABTestConfig.getInstance().getExtentData();
//        if(!ProtocolPackageHelper.clone(sourceExtent,extentData)){
//            extentData.checkFlag = sourceExtent.checkFlag;
//            extentData.flagByte = sourceExtent.flagByte;
//            extentData.extra = sourceExtent.extra;
//            if(sourceExtent.bucketInfo!=null) {
//                extentData.bucketInfo = new BucketConfig();
//                extentData.bucketInfo.bucketId = sourceExtent.bucketInfo.bucketId;
//                extentData.bucketInfo.extra = sourceExtent.bucketInfo.extra;
//            }
//        }
//        complementBucketInfoExtra(extentData.bucketInfo);
//        return extentData;
//    }
//
//    private static void complementBucketInfoExtra(BucketConfig bucketConfig) {
//        if(QQLiveDebug.isDebug() && !TextUtils.isEmpty(DebugView.LOCAL_SET_RECOMMEND_DATA_BUCKETID)) {
//            if(bucketConfig == null){
//                bucketConfig = new BucketConfig();
//            }
//            if (!TextUtils.isEmpty(bucketConfig.extra)) {
//                bucketConfig.extra += "&";
//            }
//            //recom_bucket=xxxxxx,debug情况下在个人中心设置的推荐分桶数据
//            bucketConfig.extra += DebugView.LOCAL_SET_RECOMMEND_DATA_BUCKETID;
//        }
//    }
//
//    //将系统内的登陆信息打包
//    private static ArrayList<LoginToken> createTokenList() {
//        ArrayList<LoginToken> tokens = new ArrayList<LoginToken>();
//        //如果 wx 登陆了
//        if (LoginManager.getInstance().isWXLogined()) {
//            WXUserAccount account = LoginManager.getInstance().getWXUserAccount();
//            if (account != null) {
//                String openId = account.getOpenId();
//                String accessToken = account.getAccessToken();
//
//                if (TextUtils.isEmpty(openId) == false && TextUtils.isEmpty(accessToken) == false) {
//                    LoginToken token = new LoginToken();
//                    token.TokenAppID = BuildConfig.WX_APP_ID;
//                    token.TokenKeyType = ProtocolPackage.TokenKeyType_WX;
//                    token.TokenUin = openId;
//                    token.TokenValue = accessToken.getBytes();
//                    token.IsMainLogin = LoginManager.getInstance().getMajorLoginType() == LoginConst.MGJOR_LOGINTYPE_WX;
//                    tokens.add(token);
//                }
//            }
//        }
//
//        //内部票据
//        if (LoginManager.getInstance().isLogined()) {
//            LoginToken token = new LoginToken();
//            token.TokenAppID = String.valueOf(BuildConfig.QQ_APP_ID);
//            token.TokenKeyType = ProtocolPackage.TokenKeyType_Circle;
//            token.TokenUin = LoginManager.getInstance().getUserId();
//            token.TokenValue = LoginManager.getInstance().getUserSession().getBytes();
//            token.IsMainLogin = false;
//            tokens.add(token);
//        }
//
//        //QQ登陆了，需要传递两个票据
//        if (LoginManager.getInstance().isQQLogined()) {
//            QQUserAccount account = LoginManager.getInstance().getQQUserAccount();
//            if (account != null) {
//                String uin = account.getUin();
//                //skey
//                String skey = account.getsKey();
//                if (TextUtils.isEmpty(skey) == false) {
//                    LoginToken token = new LoginToken();
//                    token.TokenAppID = String.valueOf(BuildConfig.QQ_APP_ID);
//                    token.TokenKeyType = ProtocolPackage.TokenKeyType_SKEY;
//                    token.TokenUin = uin;
//                    token.TokenValue = skey.getBytes();
//                    token.IsMainLogin = LoginManager.getInstance().getMajorLoginType() == LoginConst.MGJOR_LOGINTYPE_QQ;
//                    tokens.add(token);
//                }
//
//                //lsk
//                String lskey = account.getLsKey();
//                if (TextUtils.isEmpty(lskey) == false) {
//                    LoginToken token = new LoginToken();
//                    token.TokenAppID = String.valueOf(BuildConfig.QQ_APP_ID);
//                    token.TokenKeyType = ProtocolPackage.TokenKeyType_LSKEY;
//                    token.TokenUin = uin;
//                    token.TokenValue = lskey.getBytes();
//                    token.IsMainLogin = LoginManager.getInstance().getMajorLoginType() == LoginConst.MGJOR_LOGINTYPE_QQ;
//                    tokens.add(token);
//                }
//            }
//        }
//
//        return tokens;
//    }
//
//    ////1 主动，2自动
//    private static LogReport createLogReport(int isAuto) {
//        LogReport log = new LogReport();
//
//        log.callType = CriticalPathLog.getCallType();        //唤起方式
//        log.from = CriticalPathLog.getFrom();                //唤起url里面的渠道信息
//        log.pageId = CriticalPathLog.getPageId();
//        log.refPageId = CriticalPathLog.getRefPageId();
//        log.pageStep = CriticalPathLog.getPageStep();
//        log.vid = CriticalPathLog.getVid();
//        log.pid = CriticalPathLog.getPid();
//        log.isAuto = isAuto;
//        log.channelId = ChannelConfig.getInstance().getChannelID() + "";    //带上渠道id信息
//        log.mid = CriticalPathLog.getMid();
//
//        return log;
//    }
}
