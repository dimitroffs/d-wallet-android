package com.ddimitroff.projects.dwallet.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.ddimitroff.projects.dwallet.rest.token.TokenRO;
import com.ddimitroff.projects.dwallet.rest.user.UserRO;

public class DWalletLoginTask extends AsyncTask<UserRO, Void, TokenRO> {

  public static final String TAG = "D-Wallet-Login-Task";

  private ProgressDialog dialog = null;
  private Context context = null;
  private AlertDialog.Builder alert = null;

  public DWalletLoginTask(Context ctx) {
    this.context = ctx;
    alert = new AlertDialog.Builder(context);
    alert.setTitle(R.string.app_name);
    alert.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int which) {
        Intent postRecordIntent = new Intent(context, DWalletPostCashRecordActivity.class);
        DWalletTabGroupActivity activity = (DWalletTabGroupActivity) context;
        activity.startChildActivity("DWalletPostCashRecordActivity", postRecordIntent);
      }

    });
  }

  @Override
  protected void onPreExecute() {
    dialog = ProgressDialog.show(context, "", context.getString(R.string.msg_loading));
    super.onPreExecute();
  }

  @Override
  protected TokenRO doInBackground(UserRO... user) {
    TokenRO output = DWalletRestClient.loginUser(user[0]);
    // Log.i(TAG, output.toString());
    // Log.i(TAG, "logout? " + DWalletRestClient.logoutUser(output));
    return output;
  }

  @Override
  protected void onProgressUpdate(Void... values) {
    dialog.show();
    super.onProgressUpdate(values);
  }

  @Override
  protected void onPostExecute(TokenRO token) {
    dialog.dismiss();

    DWalletAndroidSession.get().setToken(token);

    alert
        .setMessage("Well done! Token [id=" + token.getTokenId() + "] successfully added to D-Wallet Android session.");
    alert.show();
  }

}
