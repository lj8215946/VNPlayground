package com.tencent.videonative.demo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.tencent.videonative.VideoNative;
import com.tencent.videonative.config.NetworkModuleConfig;
import com.tencent.videonative.dimpl.injector.DefaultInjector;
import com.tencent.videonative.dimpl.input.app.VNJceAppUpgradeInfoBuilder;
import com.tencent.videonative.dimpl.input.page.VNJcePageInfoBuilder;
import com.tencent.videonative.network.NetworkConfig;

/**
 * Created by ashercai on 2018-四月-24.
 */

public class DemoApplication extends Application {
	//暂时先用播放器sdk的demo key来解决播放问题
	private static final String PLAYER_KEY = "DOs9oBYrdSslxfom4zO1c5YebNZsSotAtZkgsV0Axp4UhdWLBkQzbqe5BOIEbuWSPkcQBN1kgvCmYeUB2bcb1LLtyTw6nf4AHdofpXGPtOrORgDcYWHY3HvQdVOErt8kk0HFAHOYKQOwWTLpI5XtuEvqtN3yaM75PWAygsngr3tEGBUs31yQzUjI+20/heFnKg5iOnA1/gCanQNy0V6tmMip85waZcyZBC1vANb5ObTotqyu8tjHsWBgBN96xAlkNVPwZoygF/pdwAKRXGH2a38fUD3z8SWpm1bgzn8CfRC4sNXAXno2t9kCma7Iu+t8bOVlFO61FeFuPaj94IcobQ==";

	@Override
	public void onCreate() {
		super.onCreate();
		initVideoNative();
		NetworkModuleConfig.init(this);
	}

	private void initVideoNative() {
		VideoNative.getInstance()
				.setPageInfoBuilder(new VNJcePageInfoBuilder())
				.setAppUpgradeInfoBuilder(new VNJceAppUpgradeInfoBuilder())
				.setInjector(new DefaultInjector())
//				.setIVNQrCodeDecoder(new CameraDecoder())
				.initPlayer(PLAYER_KEY);
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
