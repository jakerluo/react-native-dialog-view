package com.robbe.lib;

import android.app.Activity;
import android.app.AlertDialog;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class ReactNativeAlertModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "ReactNativeAlert";
    private AlertDialog.Builder dialog;

    public ReactNativeAlertModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void show(ReadableMap readableMap, Promise promise) {
        Activity context = this.getCurrentActivity();

        if (this.dialog == null) {
            this.dialog = new AlertDialog.Builder(context);
            this.dialog.setTitle("退出程序？");
            this.dialog.create();
        }
        this.dialog.show();
    }
}
