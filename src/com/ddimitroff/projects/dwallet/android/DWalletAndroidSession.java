package com.ddimitroff.projects.dwallet.android;

import com.ddimitroff.projects.dwallet.rest.token.TokenRO;

public class DWalletAndroidSession {

	private static DWalletAndroidSession instance;
	private TokenRO token;

	private DWalletAndroidSession() {
	}

	public static synchronized DWalletAndroidSession get() {
		if (null == instance) {
			instance = new DWalletAndroidSession();
		}

		return instance;
	}

	public void invalidate() {
		this.token = null;
	}

	/**
	 * @return the token
	 */
	public TokenRO getToken() {
		return token;
	}

	/**
	 * @param token
	 *          the token to set
	 */
	public void setToken(TokenRO token) {
		this.token = token;
	}

}
