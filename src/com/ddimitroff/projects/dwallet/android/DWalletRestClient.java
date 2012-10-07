package com.ddimitroff.projects.dwallet.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import android.util.Log;

import com.ddimitroff.projects.dwallet.json.DWalletJsonParser;
import com.ddimitroff.projects.dwallet.rest.DWalletRestUtils;
import com.ddimitroff.projects.dwallet.rest.cash.CashRecordRO;
import com.ddimitroff.projects.dwallet.rest.token.TokenRO;
import com.ddimitroff.projects.dwallet.rest.user.UserRO;

public class DWalletRestClient {

  private static final String TAG = "D-Wallet-RESTClient";

  private static HttpClient httpclient = new DefaultHttpClient();

  // TODO correct logs
  public static TokenRO loginUser(UserRO user) {
    if (null == user) {
      Log.e(TAG, "user is null");
      return null;
    }

    HttpPost post = new HttpPost(DWalletAndroidUtils.DWALLET_PROPERTY_SERVER_URL + "/users/login");
    post.setHeader("Content-Type", "application/json");
    post.addHeader(DWalletRestUtils.DWALLET_REQUEST_HEADER, DWalletAndroidUtils.DWALLET_PROPERTY_API_KEY);

    HttpEntity postEntity = null;
    try {
      postEntity = new StringEntity(DWalletJsonParser.get().mapUserToJson(user), "UTF-8");
      post.setEntity(postEntity);

      HttpResponse response = httpclient.execute(post);
      int responseCode = response.getStatusLine().getStatusCode();
      if (HttpStatus.SC_OK == responseCode) {
        InputStream responseStream = response.getEntity().getContent();
        TokenRO output = DWalletJsonParser.get().getTokenFromJson(
            DWalletAndroidUtils.convertStreamToString(responseStream));

        return output;
      } else {
        Log.e(TAG, "Error response code: " + responseCode);
      }
    } catch (JsonGenerationException jge) {
      Log.e(TAG, "Error while generating JSON request.", jge);
    } catch (JsonMappingException jme) {
      Log.e(TAG, "Error while mapping JSON to object.", jme);
    } catch (UnsupportedEncodingException uee) {
      Log.e(TAG, "Unsupported encoding found in JSON request body.", uee);
    } catch (IOException ioe) {
      Log.e(TAG, "Unable to generate JSON request.", ioe);
    }

    return null;
  }

  public static boolean registerUser(UserRO userToRegister) {
    if (null == userToRegister) {
      Log.e(TAG, "user to register is null");
      return false;
    }

    HttpPost post = new HttpPost(DWalletAndroidUtils.DWALLET_PROPERTY_SERVER_URL + "/users/register");
    post.setHeader("Content-Type", "application/json");
    post.addHeader(DWalletRestUtils.DWALLET_REQUEST_HEADER, DWalletAndroidUtils.DWALLET_PROPERTY_API_KEY);

    HttpEntity postEntity = null;
    try {
      postEntity = new StringEntity(DWalletJsonParser.get().mapUserToJson(userToRegister), "UTF-8");
      post.setEntity(postEntity);

      HttpResponse response = httpclient.execute(post);
      int responseCode = response.getStatusLine().getStatusCode();
      if (HttpStatus.SC_OK == responseCode) {
        Log.i(TAG, "user successfully registered");
        return true;
      } else {
        Log.e(TAG, "Error response code: " + responseCode);
      }
    } catch (JsonGenerationException jge) {
      Log.e(TAG, "Error while generating JSON request.", jge);
    } catch (JsonMappingException jme) {
      Log.e(TAG, "Error while mapping JSON to object.", jme);
    } catch (UnsupportedEncodingException uee) {
      Log.e(TAG, "Unsupported encoding found in JSON request body.", uee);
    } catch (IOException ioe) {
      Log.e(TAG, "Unable to generate JSON request.", ioe);
    }

    return false;
  }

  // TODO correct logs
  public static boolean logoutUser(TokenRO token) {
    if (null == token) {
      Log.e(TAG, "token is null");
      return false;
    }

    HttpPost post = new HttpPost(DWalletAndroidUtils.DWALLET_PROPERTY_SERVER_URL + "/users/logout");
    post.setHeader("Content-Type", "application/json");
    post.addHeader(DWalletRestUtils.DWALLET_REQUEST_HEADER, DWalletAndroidUtils.DWALLET_PROPERTY_API_KEY);

    HttpEntity postEntity = null;
    try {
      postEntity = new StringEntity(DWalletJsonParser.get().mapTokenToJson(token), "UTF-8");
      post.setEntity(postEntity);

      HttpResponse response = httpclient.execute(post);
      int responseCode = response.getStatusLine().getStatusCode();
      if (HttpStatus.SC_OK == responseCode) {
        return true;
      } else {
        Log.e(TAG, "Error response code: " + responseCode);
      }
    } catch (JsonGenerationException jge) {
      Log.e(TAG, "Error while generating JSON request.", jge);
    } catch (JsonMappingException jme) {
      Log.e(TAG, "Error while mapping JSON to object.", jme);
    } catch (UnsupportedEncodingException uee) {
      Log.e(TAG, "Unsupported encoding found in JSON request body.", uee);
    } catch (IOException ioe) {
      Log.e(TAG, "Unable to generate JSON request.", ioe);
    }

    return false;
  }

  // TODO correct logs
  public static boolean postCashRecord(CashRecordRO cashRecord) {
    if (null == cashRecord) {
      Log.e(TAG, "Unable to post NULL cash record.");
      return false;
    }

    HttpPost post = new HttpPost(DWalletAndroidUtils.DWALLET_PROPERTY_SERVER_URL + "/cash/post");
    post.setHeader("Content-Type", "application/json");
    post.addHeader(DWalletRestUtils.DWALLET_REQUEST_HEADER, DWalletAndroidUtils.DWALLET_PROPERTY_API_KEY);

    HttpEntity postEntity = null;
    try {
      postEntity = new StringEntity(DWalletJsonParser.get().mapCashRecordToJson(cashRecord), "UTF-8");
      post.setEntity(postEntity);

      HttpResponse response = httpclient.execute(post);
      int responseCode = response.getStatusLine().getStatusCode();
      if (HttpStatus.SC_OK == responseCode) {
        Log.i(TAG, "User successfully posted cash record to server.");
        return true;
      } else {
        Log.e(TAG, "Error response code: " + responseCode);
      }
    } catch (JsonGenerationException jge) {
      Log.e(TAG, "Error while generating JSON request.", jge);
    } catch (JsonMappingException jme) {
      Log.e(TAG, "Error while mapping JSON to object.", jme);
    } catch (UnsupportedEncodingException uee) {
      Log.e(TAG, "Unsupported encoding found in JSON request body.", uee);
    } catch (IOException ioe) {
      Log.e(TAG, "Unable to generate JSON request.", ioe);
    }

    return false;
  }

}
