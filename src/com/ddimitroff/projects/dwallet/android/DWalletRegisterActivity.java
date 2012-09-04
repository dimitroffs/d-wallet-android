package com.ddimitroff.projects.dwallet.android;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ddimitroff.projects.dwallet.rest.user.UserRO;

public class DWalletRegisterActivity extends DWalletActivity {

  private static final String DWALLET_REGISTER_ACTIVITY_TAG = "D-Wallet-Register-Activity";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (isOnline()) {
      setContentView(R.layout.register);

      final TextView txtEmail = (TextView) findViewById(R.id.txtbox_email);
      final TextView txtPassword = (TextView) findViewById(R.id.txtbox_passwd);
      final TextView txtConfirmPassword = (TextView) findViewById(R.id.txtbox_confirm_passwd);

      Resources res = getResources();
      String[] registerCurrencyTypes = res.getStringArray(R.array.cash_flow_currency_types_array);

      ArrayAdapter<CharSequence> registerCurrencyTypesAdapter = new ArrayAdapter<CharSequence>(this,
          android.R.layout.simple_spinner_item, registerCurrencyTypes);
      registerCurrencyTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      final Spinner registerCurrencyTypeSpinner = (Spinner) findViewById(R.id.spinner_register_currency_types);
      registerCurrencyTypeSpinner.setAdapter(registerCurrencyTypesAdapter);

      final TextView registerSumView = (TextView) findViewById(R.id.txt_register_sum);

      Button btnRegister = (Button) findViewById(R.id.btn_reg);
      btnRegister.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          if (validate()) {
            final int registerCurrency = DWalletAndroidUtils.getCashCurrency((String) registerCurrencyTypeSpinner
                .getSelectedItem());
            final double registerSum = DWalletAndroidUtils.getCashSum(registerSumView.getText().toString());

            UserRO userToRegister = new UserRO(txtEmail.getText().toString(), txtPassword.getText().toString(),
                registerCurrency, registerSum);
            new DWalletRegisterTask(DWalletRegisterActivity.this).execute(userToRegister);
          } else {
            makeToast(R.string.msg_register_fields_not_valid);
          }
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