package com.example.mrzheng.lanlanapp.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.R;

/**
 * Created by mrzheng on 18-6-13.
 */

public class BottomDialog extends Dialog implements View.OnClickListener {

    private TextView photographBtn;
    private TextView localPhotosBtn;
    private TextView cancelBtn;

    private View.OnClickListener confirmListener;
    private View.OnClickListener cancelListener;
    private View.OnClickListener middleListener;

    private String confirmText;
    private String middleText;
    private String cancelText;

    /**
     * @param context
     */
    public BottomDialog(Context context) {
        super(context, R.style.BottomDialogStyle);
    }

    /**
     * @param context
     * @param theme
     */
    public BottomDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * @param context
     */
    public BottomDialog(Context context, String confirmText, String middleText) {
        super(context, R.style.BottomDialogStyle);
        this.confirmText = confirmText;
        this.middleText = middleText;
    }

    /**
     * @param context
     */
    public BottomDialog(Context context, String confirmText, String middleText, String cancelText) {
        super(context, R.style.BottomDialogStyle);
        this.confirmText = confirmText;
        this.middleText = middleText;
        this.cancelText = cancelText;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_menu);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置动画
        //        window.setWindowAnimations(R.style.DialogAnimation);

        photographBtn = (TextView) findViewById(R.id.photographBtn);
        localPhotosBtn = (TextView) findViewById(R.id.localPhotosBtn);
        cancelBtn = (TextView) findViewById(R.id.cancelBtn);

        if (!TextUtils.isEmpty(confirmText)) {
            photographBtn.setText(confirmText);
        }
        if (!TextUtils.isEmpty(middleText)) {
            localPhotosBtn.setText(middleText);
        }
        if (!TextUtils.isEmpty(cancelText)) {
            cancelBtn.setText(cancelText);
        }

        cancelBtn.setOnClickListener(this);
        photographBtn.setOnClickListener(this);
        localPhotosBtn.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        return true;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.photographBtn) {
            if (confirmListener != null) {
                confirmListener.onClick(v);
            }
            dismiss();
            return;
        }
        if (id == R.id.localPhotosBtn) {
            if (middleListener != null) {
                middleListener.onClick(v);
            }
            dismiss();
            return;
        }
        if (id == R.id.cancelBtn) {
            if (cancelListener != null) {
                cancelListener.onClick(v);
            }
            dismiss();
            return;
        }
    }

    public View.OnClickListener getConfirmListener() {
        return confirmListener;
    }

    public void setConfirmListener(View.OnClickListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public View.OnClickListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public View.OnClickListener getMiddleListener() {
        return middleListener;
    }

    public void setMiddleListener(View.OnClickListener middleListener) {
        this.middleListener = middleListener;
    }
}

