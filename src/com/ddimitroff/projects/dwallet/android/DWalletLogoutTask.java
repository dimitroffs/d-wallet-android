package com.ddimitroff.projects.dwallet.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ddimitroff.projects.dwallet.rest.token.TokenRO;

public class DWalletLogoutTask extends AsyncTask<TokenRO, Void, Boolean> {

	public static final String TAG = "D-Wallet-Logout-Task";

	private ProgressDialog dialog = null;
	private Context context = null;
	private AlertDialog.Builder alert = null;

	public DWalletLogoutTask(Context ctx) {
		this.context = ctx;
		alert = new AlertDialog.Builder(context);
		alert.setTitle(R.string.app_name);
		alert.setPositiveButton(R.string.btn_ok, null);
	}

	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(context, "", context.getString(R.string.msg_loading));
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(TokenRO... token) {
		Boolean output = DWalletRestClient.logoutUser(token[0]);
		Log.i(TAG, output.toString());

		return output;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		dialog.show();
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Boolean successLogout) {
		dialog.dismiss();
		if (successLogout) {
			alert.setMessage("You have successfully logged out from application.");

			DWalletAndroidSession.get().invalidate();
		} else {
			alert.setMessage("Unable to log out from application. Please check the logs.");
		}

		alert.show();
	}

}
