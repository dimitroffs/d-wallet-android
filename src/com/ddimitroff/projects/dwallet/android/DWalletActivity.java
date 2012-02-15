package com.ddimitroff.projects.dwallet.android;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ddimitroff.projects.dwallet.rest.user.UserRO;

public class DWalletActivity extends Activity {

	protected static final String DWALLET_ACTIVITY_TAG = "D-Wallet-Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.main);

			final TextView txtEmail = (TextView) findViewById(R.id.txtbox_email);
			final TextView txtPassword = (TextView) findViewById(R.id.txtbox_passwd);

			Button btnLogin = (Button) findViewById(R.id.btn_login);
			btnLogin.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					UserRO user = new UserRO(txtEmail.getText().toString(), txtPassword.getText().toString());
					new DWalletTask(DWalletActivity.this).execute(user);
					// Log.i(DWALLET_ACTIVITY_TAG, "token: " + token.getTokenId());
				}

			});
		} else {
			makeToast(R.string.no_connection);
		}

	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private void makeToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

}