package com.ddimitroff.projects.dwallet.android;

import java.io.InputStream;
import java.util.Scanner;

public class DWalletAndroidUtils {

  public static final String DWALLET_PROPERTY_SERVER_URL = "http://dwallet.cloudfoundry.com/rest";
  public static final String DWALLET_PROPERTY_API_KEY = "testAPIkey1";

  private static final String CASH_FLOW_PROFIT = "Profit";
  private static final String CASH_FLOW_COST = "Cost";
  private static final String CASH_FLOW_CURRENCY_BGN = "BGN";
  private static final String CASH_FLOW_CURRENCY_USD = "USD";
  private static final String CASH_FLOW_CURRENCY_EUR = "EUR";

  public static final String convertStreamToString(InputStream is) {
    return new Scanner(is).useDelimiter("\\A").next();
  }

  public static int getCashFlowType(String cashFlowType) {
    if (null != cashFlowType) {
      if (CASH_FLOW_PROFIT.equals(cashFlowType)) {
        return 1;
      }

      if (CASH_FLOW_COST.equals(cashFlowType)) {
        return 2;
      }

      return -1;
    }

    return -1;
  }

  public static int getCashFlowCurrency(String selectedItem) {
    if (null != selectedItem) {
      if (CASH_FLOW_CURRENCY_BGN.equals(selectedItem)) {
        return 1;
      }

      if (CASH_FLOW_CURRENCY_USD.equals(selectedItem)) {
        return 2;
      }

      if (CASH_FLOW_CURRENCY_EUR.equals(selectedItem)) {
        return 3;
      }

      return -1;
    }

    return -1;
  }

  public static double getCashFlowSum(String cashFlowSum) {
    return Double.parseDouble(cashFlowSum);
  }

}
