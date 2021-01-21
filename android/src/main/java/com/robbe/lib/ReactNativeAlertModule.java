package com.robbe.lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;


public class ReactNativeAlertModule extends ReactContextBaseJavaModule {
  public static final String REACT_CLASS = "ReactNativeAlert";
  private AlertDialog.Builder mBuilder;
  private AlertDialog mAlertDialog;
  private View mDialogAlertContent;
  private EditText mEditText;
  private int second = 30;
  private int maxNum = 120;

  public ReactNativeAlertModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @ReactMethod
  public void hide(Promise promise) {
    if (this.mAlertDialog == null) {
      promise.resolve(true);
      return;
    }
    try {
      if (this.mAlertDialog.isShowing()) {
        this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
        this.mBuilder = null;
      }
      promise.resolve(true);
    } catch (Exception e) {
      promise.resolve(e);
    }
  }

  @ReactMethod
  public void show(ReadableMap readableMap, final Callback confirmCallback, final Callback cancelCallback) {
    Activity activity = this.getCurrentActivity();
    String title = readableMap.hasKey("title") ? readableMap.getString("title") : null;
    String message = readableMap.hasKey("message") ? readableMap.getString("message") : null;
    String cancelText = readableMap.hasKey("cancelText") ? readableMap.getString("cancelText") : null;
    String okText = readableMap.hasKey("okText") ? readableMap.getString("okText") : null;
    String minusText = readableMap.hasKey("minusText") ? readableMap.getString("minusText") : null;
    String additionText = readableMap.hasKey("additionText") ? readableMap.getString("additionText") : null;
    String descText = readableMap.hasKey("descText") ? readableMap.getString("descText") : null;
    Integer initialNum = readableMap.hasKey("initialNum") ? readableMap.getInt("initialNum") : null;
    boolean cancelable = readableMap.hasKey("cancelable") ? readableMap.getBoolean("cancelable") : true;
    boolean showInput = readableMap.hasKey("showInput") ? readableMap.getBoolean("showInput") : false;
    boolean showTitleIcon = readableMap.hasKey("showTitleIcon") ? readableMap.getBoolean("showTitleIcon") : false;
    final boolean customTemplate = readableMap.hasKey("customTemplate") ? readableMap.getBoolean("customTemplate") : false;

    if (initialNum != null) {
      this.second = initialNum;
    }

    if (this.mBuilder == null) {
      this.mBuilder = new AlertDialog.Builder(activity, R.style.ThemeOverlay_AppCompat_MaterialAlertDialog);

      if (message != null) {
        this.mBuilder.setMessage(message);
      }

      if (title != null) {
        View view = View.inflate(activity, R.layout.diglog_alert_title, null);
        TextView textView = view.findViewById(R.id.title);
        textView.setText(title);
        if (!showTitleIcon) {
          textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        this.mBuilder.setCustomTitle(view);
      }

      if (!customTemplate) {
        if (okText != null) {
          this.mBuilder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              if (confirmCallback != null) {
                confirmCallback.invoke();
              }
              mAlertDialog.dismiss();
              mAlertDialog = null;
              mBuilder = null;
            }
          });
        }

        if (cancelCallback != null) {
          this.mBuilder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              if (cancelCallback != null) {
                cancelCallback.invoke();
              }
              mAlertDialog.dismiss();
              mAlertDialog = null;
              mBuilder = null;
            }
          });
        }
      }

      if (customTemplate) {

        mDialogAlertContent = View.inflate(activity, R.layout.dialog_alert_content, null);
        mEditText = mDialogAlertContent.findViewById(R.id.editinput);
        final LinearLayout mCustomTemplate = mDialogAlertContent.findViewById(R.id.customTemplate);
        final LinearLayout mDefaultTemplate = mDialogAlertContent.findViewById(R.id.defaultTemplate);
        final Button mAdditionBtn = mDialogAlertContent.findViewById(R.id.addition);
        final Button mMinusBtn = mDialogAlertContent.findViewById(R.id.minus);
        final TextView mDescText = mDialogAlertContent.findViewById(R.id.descText);

        if (showInput) {
          ((ViewManager) (mDefaultTemplate).getParent()).removeView(mDefaultTemplate);
          mEditText.setText(String.valueOf(this.second));
          mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if (Integer.parseInt(String.valueOf(charSequence)) > maxNum) {
                mEditText.setText(String.valueOf(maxNum));
              }
              second = Integer.parseInt(String.valueOf(mEditText.getText()), 10);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
          });
          if (minusText != null) {
            mMinusBtn.setText(minusText);
          }
          mMinusBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              addition(false);
            }
          });

          if (additionText != null) {
            mAdditionBtn.setText(additionText);
          }
          mAdditionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              addition(true);
            }
          });

          if (descText != null) {
            mDescText.setText(descText);
          }
        } else {
          ((ViewManager) (mCustomTemplate).getParent()).removeView(mCustomTemplate);
          this.mEditText = null;
        }

        Button okBtn = mDialogAlertContent.findViewById(R.id.okBtn);
        if (okText != null) {
          okBtn.setText(okText);
        }
        okBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            if (confirmCallback != null) {
              confirmCallback.invoke(second);
            }
            mAlertDialog.dismiss();
            mAlertDialog = null;
            mBuilder = null;
          }
        });

        Button cancelBtn = mDialogAlertContent.findViewById(R.id.cancel);
        if (cancelText != null) {
          cancelBtn.setText(cancelText);
        }
        cancelBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (cancelCallback != null) {
              cancelCallback.invoke(second);
            }
            mAlertDialog.dismiss();
            mAlertDialog = null;
            mBuilder = null;
          }
        });

        this.mBuilder.setView(mDialogAlertContent);
      }
      this.mBuilder.setCancelable(cancelable);
    }

    this.mAlertDialog = this.mBuilder.create();

    this.mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

      @Override
      public void onDismiss(DialogInterface dialogInterface) {
        mAlertDialog = null;
        mBuilder = null;
      }
    });

    this.mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
      @Override
      public void onShow(DialogInterface dialogInterface) {
        if (customTemplate && mEditText != null) {
          mEditText = mDialogAlertContent.findViewById(R.id.editinput);
          mEditText.setFocusable(true);
          mEditText.setFocusableInTouchMode(true);
          mEditText.requestFocus();
          mEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
      }
    });
    this.mAlertDialog.show();
    Window dialogWindow = this.mAlertDialog.getWindow();

    WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();

    WindowManager windowManager = activity.getWindowManager();

    DisplayMetrics metrics = new DisplayMetrics();

    windowManager.getDefaultDisplay().getMetrics(metrics);
    layoutParams.width = (int) (metrics.widthPixels * 0.4);

    dialogWindow.setAttributes(layoutParams);
  }

  private boolean checkSecond() {
    if (this.maxNum > this.second) {
      return true;
    }
    return false;
  }

  private void addition(boolean addition) {
    boolean checkSecond = this.checkSecond();
    if (!checkSecond) {
      return;
    }

    if (addition) {
      this.second += 1;

    } else {
      this.second -= 1;
    }
    if (this.mEditText != null) {
      this.mEditText.setText(String.valueOf(this.second));
    }
  }
}
