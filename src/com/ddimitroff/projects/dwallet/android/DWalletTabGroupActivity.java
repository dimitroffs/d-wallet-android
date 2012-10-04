package com.ddimitroff.projects.dwallet.android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

/**
 * The purpose of this Activity is to manage the activities in a tab. Note:
 * Child Activities can handle Key Presses before they are seen here.
 * 
 * @author Eric Harlow
 * @author ddimitrov
 */
public class DWalletTabGroupActivity extends ActivityGroup {

  private ArrayList<String> activityIdList;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (activityIdList == null) {
      activityIdList = new ArrayList<String>();
    }

    Intent loginIntent = new Intent(this, DWalletLoginActivity.class);
    startChildActivity("DWalletLoginActivity", loginIntent);

    // set orientation for all activities
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }

  /**
   * This is called when a child activity of this one calls its finish method.
   * This implementation calls {@link LocalActivityManager#destroyActivity} on
   * the child activity and starts the previous activity. If the last child
   * activity just called finish(),this activity (the parent), calls finish to
   * finish the entire group.
   */
  @Override
  public void finishFromChild(Activity child) {
    LocalActivityManager manager = getLocalActivityManager();
    int index = activityIdList.size() - 1;

    if (index < 1) {
      finish();
      return;
    }

    manager.destroyActivity(activityIdList.get(index), true);
    activityIdList.remove(index);
    index--;
    String lastId = activityIdList.get(index);
    Intent lastIntent = manager.getActivity(lastId).getIntent();
    Window newWindow = manager.startActivity(lastId, lastIntent);
    setContentView(newWindow.getDecorView());
  }

  /**
   * Starts an Activity as a child Activity to this.
   * 
   * @param Id
   *          Unique identifier of the activity to be started.
   * @param intent
   *          The Intent describing the activity to be started.
   * @throws android.content.ActivityNotFoundException.
   */
  public void startChildActivity(String Id, Intent intent) {
    Window window = getLocalActivityManager().startActivity(Id, intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    if (window != null) {
      activityIdList.add(Id);
      setContentView(window.getDecorView());
    }
  }

  /**
   * The primary purpose is to prevent systems before
   * android.os.Build.VERSION_CODES.ECLAIR from calling their default
   * KeyEvent.KEYCODE_BACK during onKeyDown.
   */
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      // preventing default implementation previous to
      // android.os.Build.VERSION_CODES.ECLAIR
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  /**
   * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that all
   * systems call onBackPressed().
   */
  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      onBackPressed();
      return true;
    }

    return super.onKeyUp(keyCode, event);
  }

  /**
   * If a Child Activity handles KeyEvent.KEYCODE_BACK. Simply override and add
   * this method.
   */
  @Override
  public void onBackPressed() {
    Log.i("TESTTTTTT", "size of list:" + activityIdList.size());
    int length = activityIdList.size();
    if (length > 1) {
      Activity current = getLocalActivityManager().getActivity(activityIdList.get(length - 1));
      current.finish();
    }
  }

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
