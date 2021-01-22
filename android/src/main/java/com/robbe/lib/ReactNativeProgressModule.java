package com.robbe.lib;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import javax.annotation.Nullable;

public class ReactNativeProgressModule extends ReactContextBaseJavaModule {

    public static final String TYPE_PROGRESS = "progress";
    public static final String REACT_CLASS = "ReactNativeProgress";
    private String type = "";
    private Integer maxProgress = 100;
    private ProgressDialog progressDialog;

    public ReactNativeProgressModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addLifecycleEventListener(new LifecycleEventListener() {
            @Override
            public void onHostResume() {

            }

            @Override
            public void onHostPause() {
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    progressDialog = null;
                }
            }

            @Override
            public void onHostDestroy() {

            }
        });
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

        type = readableMap.hasKey("type") ? readableMap.getString("type") : "";
        maxProgress = readableMap.hasKey("max") ? readableMap.getInt("max") : maxProgress;

        boolean isCancelable = readableMap.hasKey("isCancelable") ? readableMap.getBoolean("isCancelable") : false;

        if (this.progressDialog == null) {
            switch (type) {
                case TYPE_PROGRESS:
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMax(100);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle(title);
                    progressDialog.setMessage(message);
                    progressDialog.setCancelable(isCancelable);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setIndeterminate(false);
                    if (!this.progressDialog.isShowing()) {
                        this.progressDialog.show();
                    }
                    break;
                default:
                    progressDialog = ProgressDialog.show(context, title, message, false, isCancelable);
                    break;
            }

            this.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    progressDialog = null;
                }
            });
        }

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

    @Override
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        Log.d(REACT_CLASS, "onCatalystInstanceDestroy");
    }

    @ReactMethod
    public void changeProgress(Integer progress, Promise promise) {
        if (this.progressDialog == null || !this.progressDialog.isShowing() || progress > 100) {
            promise.resolve(null);
            return;
        }

        if (TYPE_PROGRESS.equals(type)) {
            this.progressDialog.setProgress(progress);
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
