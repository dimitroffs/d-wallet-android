package com.ddimitroff.projects.dwallet.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ddimitroff.projects.dwallet.rest.cash.CashFlowRO;

public class DWalletPostCashRecordActivity extends DWalletActivity {

  private static final String DWALLET_POSTCASHRECORD_ACTIVITY_TAG = "D-Wallet-PostCashRecord-Activity";

  public static final int ADD_CASH_FLOW_REQUEST_CODE = 1;

  private static List<String> listResults = new ArrayList<String>();

  private ArrayAdapter<String> listViewAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (isOnline()) {
      setContentView(R.layout.post_cash_record);

      listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listResults);
      final ListView listView = (ListView) findViewById(R.id.cash_flow_list);
      listView.setAdapter(listViewAdapter);

      CashFlowRO newCashFlow = null;
      if (null != getIntent() && null != getIntent().getExtras()) {
        // newCashFlow = (CashFlowRO)
        // getIntent().getExtras().get(DWalletAddCashFlowActivity.ADD_CASH_FLOW_INTENT_PARAM);
        //
        // Log.i(DWALLET_POSTCASHRECORD_ACTIVITY_TAG, "GOTTTTTTTT ITTTTTTTTTTT"
        // + newCashFlow.getCashFlowSum());
        //
        // listResults.add(newCashFlow.getCashFlowType() + "-" +
        // newCashFlow.getCashFlowSum() + "-"
        // + newCashFlow.getCashFlowCurrency());
        // listViewAdapter.notifyDataSetChanged();
      }
      Log.e(DWALLET_POSTCASHRECORD_ACTIVITY_TAG, "NOTTTTTT   GOTTTTTTTT ITTTTTTTTTTT");

      Button btnAddCashFlow = (Button) findViewById(R.id.btn_new_cash_flow);
      btnAddCashFlow.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          Intent addCashFLowIntent = new Intent(DWalletPostCashRecordActivity.this, DWalletAddCashFlowActivity.class);
          DWalletPostCashRecordActivity.this.startActivityForResult(addCashFLowIntent, ADD_CASH_FLOW_REQUEST_CODE);
        }

      });

      Button btnPostCashRecord = (Button) findViewById(R.id.btn_post_cash_record);
      btnPostCashRecord.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          // UserRO user = new UserRO(txtEmail.getText().toString(),
          // txtPassword.getText().toString(), 0, 0);
          // new
          // DWalletLoginTask(DWalletPostCashRecordActivity.this).execute(user);
        }

      });
    } else {
      makeToast(R.string.no_connection);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    // TODO Auto-generated method stub
    super.onActivityResult(requestCode, resultCode, intent);
    if (resultCode == RESULT_OK && requestCode == ADD_CASH_FLOW_REQUEST_CODE) {
      CashFlowRO newCashFlow = (CashFlowRO) intent.getExtras().get(
          DWalletAddCashFlowActivity.ADD_CASH_FLOW_INTENT_PARAM);

      Log.i(DWALLET_POSTCASHRECORD_ACTIVITY_TAG, "GOTTTTTTTT ITTTTTTTTTTT" + newCashFlow.getCashFlowSum());

      listResults.add(newCashFlow.getCashFlowType() + "-" + newCashFlow.getCashFlowSum() + "-"
          + newCashFlow.getCashFlowCurrency());
      listViewAdapter.notifyDataSetChanged();
    }
  }

  @Override
  protected boolean validate() {
    // no fields for validation here
    return true;
  }

}