package com.robbe.lib;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatButton;

public class TVButton extends AppCompatButton {
    public TVButton(Context context) {
        super(context);
    }

    public TVButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TVButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            this.animate().scaleX(0.9f).scaleY(0.9f).setDuration(300);
        } else {
            this.animate().scaleX(0.8f).scaleY(0.8f).setDuration(300);
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
}
