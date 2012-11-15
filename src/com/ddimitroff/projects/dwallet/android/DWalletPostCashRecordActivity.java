package com.ddimitroff.projects.dwallet.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ddimitroff.projects.dwallet.enums.CashFlowCurrencyType;
import com.ddimitroff.projects.dwallet.enums.CashFlowType;
import com.ddimitroff.projects.dwallet.rest.cash.CashFlowRO;
import com.ddimitroff.projects.dwallet.rest.cash.CashRecordRO;
import com.ddimitroff.projects.dwallet.rest.token.TokenRO;

public class DWalletPostCashRecordActivity extends DWalletActivity {

  private static final String DWALLET_POSTCASHRECORD_ACTIVITY_TAG = "D-Wallet-PostCashRecord-Activity";

  public static final int ADD_CASH_FLOW_REQUEST_CODE = 1;

  private static List<String> listResults;

  private static ArrayList<CashFlowRO> cashRecordItems;

  private ArrayAdapter<String> listViewAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (isOnline()) {
      View viewToLoad = LayoutInflater.from(getParent()).inflate(R.layout.post_cash_record, null);
      setContentView(viewToLoad);

      listResults = new ArrayList<String>();

      cashRecordItems = new ArrayList<CashFlowRO>();

      listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listResults);
      final ListView listView = (ListView) findViewById(R.id.cash_flow_list);
      registerForContextMenu(listView);
      listView.setAdapter(listViewAdapter);
      listView.setOnLongClickListener(new OnLongClickListener() {

        public boolean onLongClick(View v) {
          openContextMenu(v);
          return true;
        }

      });

      Button btnAddCashFlow = (Button) findViewById(R.id.btn_new_cash_flow);
      btnAddCashFlow.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          Intent addCashFLowIntent = new Intent(getParent().getApplicationContext(), DWalletAddCashFlowActivity.class);
          ((DWalletTabGroupActivity) getParent()).startActivityForResult(addCashFLowIntent, ADD_CASH_FLOW_REQUEST_CODE);
          // DWalletPostCashRecordActivity.this.startActivityForResult(addCashFLowIntent,
          // ADD_CASH_FLOW_REQUEST_CODE);
        }

      });

      Button btnPostCashRecord = (Button) findViewById(R.id.btn_post_cash_record);
      btnPostCashRecord.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {
          TokenRO token = DWalletAndroidSession.get().getToken();
          if (null != token) {
            CashRecordRO cashRecord = new CashRecordRO(token, cashRecordItems);
            new DWalletPostCashRecordTask(getParent()).execute(cashRecord);

            // clear cash list view items
            listViewAdapter.clear();
            listViewAdapter.notifyDataSetChanged();
          } else {
            makeToast(R.string.no_token);
          }
        }

      });
    } else {
      makeToast(R.string.no_connection);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);

    CashFlowRO newCashFlow = (CashFlowRO) intent.getExtras().get(DWalletAddCashFlowActivity.ADD_CASH_FLOW_INTENT_PARAM);

    listResults.add(CashFlowType.getType(newCashFlow.getCashFlowType()) + "-" + newCashFlow.getCashFlowSum() + "-"
        + CashFlowCurrencyType.getCurrencyType(newCashFlow.getCashFlowCurrency()));
    listViewAdapter.notifyDataSetChanged();

    cashRecordItems.add(newCashFlow);
  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    menu.setHeaderTitle("TEST MENU"); // TODO load from strings
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.post_cash_record_menu, menu);
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    int itemAdapterPosition = info.position;

    switch (item.getItemId()) {
    case R.id.delete_cash_record: {
      listViewAdapter.remove(listViewAdapter.getItem(itemAdapterPosition));
      listViewAdapter.notifyDataSetChanged();
      cashRecordItems.remove(itemAdapterPosition); // TODO check if works
      return true;
    }
    case R.id.cash_record_cancel: {
      // do nothing
      return true;
    }
    default:
      return super.onContextItemSelected(item);
    }
  }

  @Override
  protected boolean validate() {
    // no fields for validation here
    return true;
  }

}