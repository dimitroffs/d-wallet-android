package com.ddimitroff.projects.dwallet.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ddimitroff.projects.dwallet.rest.user.UserRO;

public class DWalletActivity extends Activity {

	protected static final String DWALLET_ACTIVITY_TAG = "D-Wallet-Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

	}

}