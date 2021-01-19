package com.robbe.lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;


public class ReactNativeAlertModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "ReactNativeAlert";
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public ReactNativeAlertModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void show(ReadableMap readableMap, final Callback confirmCallback, final Callback cancelCallback) {
        Activity activity = this.getCurrentActivity();
        String title = readableMap.hasKey("title") ? readableMap.getString("title") : null;
        String message = readableMap.hasKey("message") ? readableMap.getString("message") : null;
        String cancelText = readableMap.hasKey("cancelText") ? readableMap.getString("cancelText") : null;
        String okText = readableMap.hasKey("okText") ? readableMap.getString("okText") : null;
        boolean cancelable = readableMap.hasKey("cancelable") ? readableMap.getBoolean("cancelable") : false;

        if (this.builder == null) {
            this.builder = new AlertDialog.Builder(activity, R.style.ThemeOverlay_AppCompat_MaterialAlertDialog);
            if (message != null) {
                this.builder.setMessage(message);
            }
            if (title != null) {
                View view = View.inflate(activity, R.layout.diglog_alert_module, null);
                TextView textView = view.findViewById(R.id.title);
                textView.setText(title);
                this.builder.setCustomTitle(view);
            }
            if (okText != null) {
                this.builder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (confirmCallback != null) {
                            confirmCallback.invoke();
                        }
                    }
                });
            }

            if (cancelCallback != null) {
                this.builder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (cancelCallback != null) {
                            cancelCallback.invoke();
                        }
                    }
                });
            }

            this.builder.setCancelable(cancelable);
        }

        this.alertDialog = this.builder.create();
        this.alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                button.setFocusable(true);
                button.setFocusableInTouchMode(true);
                button.requestFocus();
            }
        });
        this.alertDialog.show();
        Window dialogWindow = this.alertDialog.getWindow();

        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();

        WindowManager windowManager = activity.getWindowManager();

        DisplayMetrics metrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(metrics);
        layoutParams.width = (int) (metrics.widthPixels * 0.4);

        dialogWindow.setAttributes(layoutParams);
    }
}
