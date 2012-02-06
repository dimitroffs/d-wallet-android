package com.ddimitroff.projects.dwallet.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ddimitroff.projects.dwallet.rest.token.TokenRO;
import com.ddimitroff.projects.dwallet.rest.user.UserRO;

public class DWalletTask extends AsyncTask<UserRO, Void, TokenRO> {

	public static final String TAG = "D-Wallet-Task";

	private ProgressDialog dialog = null;
	private Context context = null;
	private AlertDialog.Builder alert = null;

	public DWalletTask(Context ctx) {
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
	protected TokenRO doInBackground(UserRO... user) {
		TokenRO output = DWalletRestClient.loginUser(user[0]);
		Log.i(TAG, output.toString());
		Log.i(TAG, "logout? " + DWalletRestClient.logoutUser(output));
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
		alert.setMessage("Well done! tokenId: " + token.getTokenId());
		alert.show();
	}

}
