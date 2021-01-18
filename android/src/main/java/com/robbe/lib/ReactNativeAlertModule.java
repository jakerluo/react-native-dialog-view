package com.robbe.lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.robbe.lib.interfaces.ReadableCustomMap;


public class ReactNativeAlertModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "ReactNativeAlert";
    private AlertDialog dialog;

    public ReactNativeAlertModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void show(ReadableCustomMap readableMap, Promise promise) {
        Activity activity = this.getCurrentActivity();
        String title = readableMap.hasKey("title") ? readableMap.getString("title") : null;
        String message = readableMap.hasKey("message") ? readableMap.getString("message") : null;
        String cancelText = readableMap.hasKey("cancelText") ? readableMap.getString("cancelText") : null;
        final Callback cancelBack = readableMap.hasKey("cancelBack") ? (Callback) readableMap.getCallback("cancelBack") : null;

        if (this.dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View view = View.inflate(activity, R.layout.diglog_alert_module, null);
            builder.setView(view);
            builder.setCancelable(false);

            if (title != null) {
                TextView textView = (TextView) view.findViewById(R.id.title);
                textView.setText(title);
            }

            Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
            if (cancelText != null) {
                btnCancel.setText(cancelText);
            }
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog != null) dialog.dismiss();
                    if (cancelBack != null) cancelBack.invoke();
                }
            });

            if (message != null) {
                TextView textView = (TextView) view.findViewById(R.id.text_msg);
                textView.setText(message);
            }


            this.dialog = builder.create();
            this.dialog.setCancelable(false);
        }

        this.dialog.show();

        Window dialogWindow = this.dialog.getWindow();

        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();

        WindowManager windowManager = activity.getWindowManager();

        DisplayMetrics metrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(metrics);

        layoutParams.width = (int) (metrics.widthPixels * 0.8);

        dialogWindow.setAttributes(layoutParams);
    }
}
