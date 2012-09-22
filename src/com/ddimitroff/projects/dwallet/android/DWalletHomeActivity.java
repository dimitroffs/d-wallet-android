package com.ddimitroff.projects.dwallet.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class DWalletHomeActivity extends DWalletTabActivity {

  private static final String DWALLET_HOME_ACTIVITY_TAG = "D-Wallet-Home-Activity";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);

    TabHost tabHost = getTabHost();

    TabSpec specHome = tabHost.newTabSpec("HOME");
    specHome.setIndicator("HOME");
    specHome.setContent(R.id.tab_home);

    tabHost.addTab(specHome);

    TabSpec specLogin = tabHost.newTabSpec("LOGIN");
    specLogin.setIndicator("LOGIN");
    Intent intentLogin = new Intent(this, DWalletTabGroupActivity.class);
    specLogin.setContent(intentLogin);

    tabHost.addTab(specLogin);

    TabSpec specAbout = tabHost.newTabSpec("ABOUT");
    specAbout.setIndicator("ABOUT");
    specAbout.setContent(R.id.tab_about);

    tabHost.addTab(specAbout);
  }

  @Override
  protected boolean validate() {
    // TODO implement validation of fields
    return true;
  }

}