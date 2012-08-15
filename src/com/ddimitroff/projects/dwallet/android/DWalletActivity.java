package com.ddimitroff.projects.dwallet.android;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public abstract class DWalletActivity extends Activity {

  protected abstract boolean validate();

  public boolean isOnline() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
      return true;
    }
    return false;
  }

  public void makeToast(int resId) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
  }

}