package com.ddimitroff.projects.dwallet.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.ddimitroff.projects.dwallet.rest.user.UserRO;

public class DWalletRegisterTask extends AsyncTask<UserRO, Void, Boolean> {

  public static final String TAG = "D-Wallet-Register-Task";

  private ProgressDialog dialog = null;
  private Context context = null;
  private AlertDialog.Builder alert = null;

  public DWalletRegisterTask(Context ctx) {
    this.context = ctx;
    alert = new AlertDialog.Builder(context);
    alert.setTitle(R.string.app_name);
    alert.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int which) {
        DWalletTabGroupActivity parent = (DWalletTabGroupActivity) context;
        parent.finishFromChild(null);
      }

    });
  }

  @Override
  protected void onPreExecute() {
    dialog = ProgressDialog.show(context, "", context.getString(R.string.msg_loading));
    super.onPreExecute();
  }

  @Override
  protected Boolean doInBackground(UserRO... user) {
    Boolean output = DWalletRestClient.registerUser(user[0]);
    Log.i(TAG, output.toString());

    return output;
  }

  @Override
  protected void onProgressUpdate(Void... values) {
    dialog.show();
    super.onProgressUpdate(values);
  }

  @Override
  protected void onPostExecute(Boolean successRegister) {
    dialog.dismiss();
    if (successRegister) {
      alert.setMessage("You have successfully registered new user.");
    } else {
      alert.setMessage("Unable to register new user. Please check the logs.");
    }

    alert.show();
  }

}
