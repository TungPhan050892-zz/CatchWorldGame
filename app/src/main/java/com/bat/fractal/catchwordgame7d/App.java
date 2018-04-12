package com.bat.fractal.catchwordgame7d;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bat.fractal.catchwordgame7d.common.SharePref;
import com.bat.fractal.catchwordgame7d.common.Strings;
import com.bat.fractal.catchwordgame7d.di.component.AppComponent;
import com.bat.fractal.catchwordgame7d.di.component.DaggerAppComponent;
import com.bat.fractal.catchwordgame7d.di.module.NetworkModule;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by tungphan on 4/8/17.
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();
    private static App appInstance;
    private AppComponent appComponent;
    private SharedPreferences sharedPreferences;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        appInstance = this;
        initInjector();
        sharedPreferences = getSharedPreferences(SharePref.NAME, MODE_PRIVATE);
    }

    public static App getInstance() {
        return appInstance;
    }

    public SharedPreferences sharedPreference() {
        return sharedPreferences;
    }

    public void saveStringToSharePref(String key, String string) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, string);
        editor.apply();
    }

    public void saveIntToSharePref(String key, int number) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, number);
        editor.apply();
    }

    public String getStringFromSharePref(String key) {
        return sharedPreferences.getString(key, Strings.EMPTY);
    }

    public int getIntFromSharePref(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    private void initInjector() {
        this.appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(this))
                .build();
    }

    public static AppComponent getAppComponent(Context context) {
        return ((App) context.getApplicationContext()).appComponent;
    }

    public boolean isConnectToNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager
                = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void gotoAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void gotoSystemSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
