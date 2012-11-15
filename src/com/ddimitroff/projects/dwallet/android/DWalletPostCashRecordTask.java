package com.ddimitroff.projects.dwallet.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.ddimitroff.projects.dwallet.rest.cash.CashRecordRO;

public class DWalletPostCashRecordTask extends AsyncTask<CashRecordRO, Void, Boolean> {

  public static final String TAG = "D-Wallet-Post-Cash-Record-Task";

  private ProgressDialog dialog = null;
  private Context context = null;
  private AlertDialog.Builder alert = null;

  public DWalletPostCashRecordTask(Context ctx) {
    this.context = ctx;
    alert = new AlertDialog.Builder(context);
    alert.setTitle(R.string.app_name);
    alert.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int which) {
        // TODO nothing for now
        // Intent postRecordIntent = new Intent(context,
        // DWalletPostCashRecordActivity.class);
        // DWalletTabGroupActivity activity = (DWalletTabGroupActivity) context;
        // activity.startChildActivity("DWalletPostCashRecordActivity",
        // postRecordIntent);
      }

    });
  }

  @Override
  protected void onPreExecute() {
    dialog = ProgressDialog.show(context, "", context.getString(R.string.msg_loading));
    super.onPreExecute();
  }

  @Override
  protected Boolean doInBackground(CashRecordRO... cashRecords) {
    Boolean output = DWalletRestClient.postCashRecord(cashRecords[0]);
    // Log.i(TAG, output.toString());
    // Log.i(TAG, "logout? " + DWalletRestClient.logoutUser(output));

    // clear cash record items
    cashRecords[0].getCashFlows().clear();

    return output;
  }

  @Override
  protected void onProgressUpdate(Void... values) {
    dialog.show();
    super.onProgressUpdate(values);
  }

  @Override
  protected void onPostExecute(Boolean successPost) {
    dialog.dismiss();

    if (successPost) {
      alert.setMessage("You have successfully posted cash record.");
    } else {
      alert.setMessage("Unable to post cash record. Please check the logs.");
    }

    alert.show();
  }

}
