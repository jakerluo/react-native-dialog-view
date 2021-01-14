package com.robbe.lib;

import android.app.Activity;
import android.app.ProgressDialog;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class ReactNativeProgressModule extends ReactContextBaseJavaModule {

    private ProgressDialog progressDialog;
    public static final String REACT_CLASS = "ReactNativeProgress";

    public ReactNativeProgressModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void show(ReadableMap readableMap, final Promise promise) {
        if (readableMap == null) {
            promise.reject("参数没有传入");
            return;
        }
        Activity context = this.getCurrentActivity();
        String title = readableMap.hasKey("title") ? readableMap.getString("title") : null;
        String message = readableMap.hasKey("message") ? readableMap.getString("message") : null;

        boolean isCancelable = readableMap.hasKey("isCancelable") ? readableMap.getBoolean("isCancelable") : false;

        if (this.progressDialog == null) {
            this.progressDialog = ProgressDialog.show(context, title, message, false, isCancelable);
        }

        if (!this.progressDialog.isShowing()) {
            this.progressDialog.show();
        }
        this.progressDialog.setTitle(title);
        this.progressDialog.setMessage(message);
        this.progressDialog.setCancelable(isCancelable);
        promise.resolve(true);
    }

    @ReactMethod
    public void hide(Promise promise) {
        if (this.progressDialog == null) {
            promise.resolve(true);
            return;
        }
        try {
            if (this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();
                this.progressDialog = null;
            }
            promise.resolve(true);
        } catch (Exception e) {
            promise.resolve(e);
        }
    }

    @ReactMethod
    public void changeMessage(String message, Promise promise) {
        if (this.progressDialog == null || !this.progressDialog.isShowing()) {
            promise.resolve(null);
            return;
        }
        this.progressDialog.setMessage(message);
        promise.resolve(true);
    }

}
