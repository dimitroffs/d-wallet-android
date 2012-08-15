package com.ddimitroff.projects.dwallet.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ddimitroff.projects.dwallet.rest.token.TokenRO;
import com.ddimitroff.projects.dwallet.rest.user.UserRO;

public class DWalletHomeActivity extends DWalletActivity {

  private static final String DWALLET_HOME_ACTIVITY_TAG = "D-Wallet-Home-Activity";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (isOnline()) {
      setContentView(R.layout.main);

      final TextView txtEmail = (TextView) findViewById(R.id.txtbox_email);
      final TextView txtPassword = (TextView) findViewById(R.id.txtbox_passwd);

      Button btnLogin = (Button) findViewById(R.id.btn_login);
      btnLogin.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          if (validate()) {
            UserRO user = new UserRO(txtEmail.getText().toString(), txtPassword.getText().toString(), 0, 0);
            new DWalletLoginTask(DWalletHomeActivity.this).execute(user);
            // Log.i(DWALLET_ACTIVITY_TAG, "token: " + token.getTokenId());
          }
        }

      });

      Button btnRegister = (Button) findViewById(R.id.btn_reg);
      btnRegister.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          Intent registerIntent = new Intent(DWalletHomeActivity.this, DWalletRegisterActivity.class);
          DWalletHomeActivity.this.startActivity(registerIntent);
        }

      });

      Button btnLogout = (Button) findViewById(R.id.btn_logout);
      btnLogout.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          TokenRO token = DWalletAndroidSession.get().getToken();
          if (null != token) {
            new DWalletLogoutTask(DWalletHomeActivity.this).execute(token);
          } else {
            makeToast(R.string.no_token);
          }
        }

      });
    } else {
      makeToast(R.string.no_connection);
    }
  }

  @Override
  protected boolean validate() {
    // TODO implement validation of fields
    return true;
  }

}