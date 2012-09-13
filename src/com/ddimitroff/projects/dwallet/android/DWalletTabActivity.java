package com.ddimitroff.projects.dwallet.android;

import android.app.TabActivity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public abstract class DWalletTabActivity extends TabActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // set orientation for all activities
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }

  protected abstract boolean validate();

  /**
   * A method for checking if current user is connected to Internet
   * 
   * @return {@code true} if it is connected to Internet, {@code false}
   *         otherwise
   */
  public boolean isOnline() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
      return true;
    }
    return false;
  }

  /**
   * A method for writing {@link Toast} strings on the screen
   * 
   * @param resId
   *          - resource id
   */
  public void makeToast(int resId) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
  }

}
