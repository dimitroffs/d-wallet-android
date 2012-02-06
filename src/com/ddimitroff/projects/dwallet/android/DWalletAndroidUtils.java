package com.ddimitroff.projects.dwallet.android;

import java.io.InputStream;
import java.util.Scanner;

public class DWalletAndroidUtils {

	public static final String DWALLET_PROPERTY_SERVER_URL = "http://77.70.35.220/d-wallet-web/rest";
	public static final String DWALLET_PROPERTY_API_KEY = "testAPIkey1";

	public static final String convertStreamToString(InputStream is) {
		return new Scanner(is).useDelimiter("\\A").next();
	}

}
