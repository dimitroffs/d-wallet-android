package com.ddimitroff.projects.dwallet.android;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ddimitroff.projects.dwallet.rest.cash.CashFlowRO;

public class DWalletAddCashFlowActivity extends DWalletActivity {

  private static final String DWALLET_ADDCASHFLOW_ACTIVITY_TAG = "D-Wallet-AddCashFlow-Activity";
  public static final String ADD_CASH_FLOW_INTENT_PARAM = "new-cash-flow";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (isOnline()) {
      //View viewToLoad = LayoutInflater.from(getParent()).inflate(R.layout.add_cash_flow, null);
      setContentView(R.layout.add_cash_flow);

      Resources res = getResources();

      String[] cashFlowTypes = res.getStringArray(R.array.cash_flow_types_array);

      ArrayAdapter<CharSequence> cashFlowTypesAdapter = new ArrayAdapter<CharSequence>(this,
          android.R.layout.simple_spinner_item, cashFlowTypes);
      cashFlowTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      final Spinner cashFlowTypeSpinner = (Spinner) findViewById(R.id.spinner_cash_flow_types);
      cashFlowTypeSpinner.setAdapter(cashFlowTypesAdapter);

      String[] cashFlowCurrencyTypes = res.getStringArray(R.array.cash_flow_currency_types_array);

      ArrayAdapter<CharSequence> cashFlowCurrencyTypesAdapter = new ArrayAdapter<CharSequence>(this,
          android.R.layout.simple_spinner_item, cashFlowCurrencyTypes);
      cashFlowCurrencyTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      final Spinner cashFlowCurrencyTypeSpinner = (Spinner) findViewById(R.id.spinner_cash_flow_currency_types);
      cashFlowCurrencyTypeSpinner.setAdapter(cashFlowCurrencyTypesAdapter);

      final TextView cashFlowSumView = (TextView) findViewById(R.id.txt_cash_flow_sum);

      Button btnAdd = (Button) findViewById(R.id.btn_cash_flow_ok);
      btnAdd.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          if (validate()) {
            final int cashFlowType = DWalletAndroidUtils.getCashFlowType((String) cashFlowTypeSpinner.getSelectedItem());
            final int cashFlowCurrency = DWalletAndroidUtils.getCashCurrency((String) cashFlowCurrencyTypeSpinner
                .getSelectedItem());
            final double cashFlowSum = DWalletAndroidUtils.getCashSum(cashFlowSumView.getText().toString());

            CashFlowRO currentCashFlow = new CashFlowRO(cashFlowType, cashFlowCurrency, cashFlowSum);

            Intent postRecordIntent = new Intent();
            postRecordIntent.putExtra(ADD_CASH_FLOW_INTENT_PARAM, currentCashFlow);
            setResult(RESULT_OK, postRecordIntent);
            //getParent().finishFromChild(null);
//finishActivity(DWalletPostCashRecordActivity.ADD_CASH_FLOW_REQUEST_CODE)
            finish();
            
//            getParent().startActivityForResult(postRecordIntent,
//                DWalletPostCashRecordActivity.ADD_CASH_FLOW_REQUEST_CODE);
            // DWalletAddCashFlowActivity.this.startActivity(postRecordIntent);
            // ((DWalletTabGroupActivity)
            // getParent()).startActivityFromChild(DWalletAddCashFlowActivity.this,
            // postRecordIntent,
            // DWalletPostCashRecordActivity.ADD_CASH_FLOW_REQUEST_CODE);
          }
        }

      });

      Button btnCancel = (Button) findViewById(R.id.btn_cash_flow_cancel);
      btnCancel.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          finish();
        }

      });
    } else {
      makeToast(R.string.no_connection);
    }
  }

  @Override
  protected boolean validate() {
    // TODO implement validation of fields
    return true;
  }

}