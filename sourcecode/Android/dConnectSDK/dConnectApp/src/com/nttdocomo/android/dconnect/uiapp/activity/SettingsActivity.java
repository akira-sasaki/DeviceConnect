package com.nttdocomo.android.dconnect.uiapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.nttdocomo.android.dconnect.uiapp.R;

/**
 * 設定画面アクティビティ.
 */
public class SettingsActivity extends FragmentActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    /**
     * アクセストークンを取得する.
     * アクセストークンがない場合にはnullを返却する。
     * @return アクセストークン
     */
    public String getAccessToken() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String accessToken = prefs.getString(
                getString(R.string.key_settings_dconn_access_token), null);
        return accessToken;
    }

    /**
     * クライアントIDを取得する.
     * @return クライアントID
     */
    public String getClientId() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String clientId = prefs.getString(
                getString(R.string.key_settings_dconn_client_id), null);
        return clientId;
    }

    /**
     * クライアントシークレットを取得する.
     * @return クライアントシークレット
     */
    public String getClientSecret() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String clientSecret = prefs.getString(
                getString(R.string.key_settings_dconn_client_secret), null);
        return clientSecret;
    }
}
