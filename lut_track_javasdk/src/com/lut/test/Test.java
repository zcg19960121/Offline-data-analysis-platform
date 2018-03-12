package com.lut.test;

import com.lut.ae.sdk.AnalyticsEngineSDK;

public class Test {

	public static void main(String[] args) {
		AnalyticsEngineSDK.onChargeSuccess("orderzcg123", "zcg19960121");
		AnalyticsEngineSDK.onChargeRefund("orderzcg456", "zcg19960121aaa");

	}

}
