package com.example.administrator.loopviewapplication.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;


public abstract class BaseDialog<V extends View> implements DialogInterface.OnKeyListener{
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    protected Context mContext;
    protected int screenWidthPixels;
    protected int screenHeightPixels;
    private Dialog dialog;
    private FrameLayout contentLayout;
    private boolean isPrepared = false;

    public BaseDialog(Context mContext) {
        this.mContext = mContext;
        DisplayMetrics metrics = displayMetrics(mContext);
        screenWidthPixels = metrics.widthPixels;
        screenHeightPixels = metrics.heightPixels;
        initDialog();
    }

    private static DisplayMetrics dm = null;

    public static DisplayMetrics displayMetrics(Context context) {
        if (null != dm) {
            return dm;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    private void initDialog() {
        contentLayout = new FrameLayout(mContext);
        contentLayout.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        contentLayout.setFocusable(true);
        contentLayout.setFocusableInTouchMode(true);
        //contentLayout.setFitsSystemWindows(true);
        dialog = new Dialog(mContext);
        dialog.setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
        dialog.setCancelable(false);//按返回键取消窗体
        dialog.setOnKeyListener(this);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //AndroidRuntimeException: requestFeature() must be called before adding content
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setContentView(contentLayout);
        }
        setSize(screenWidthPixels, WRAP_CONTENT);
    }

    public int getScreenWidthPixels() {
        return screenWidthPixels;
    }

    public int getScreenHeightPixels() {
        return screenHeightPixels;
    }

    /**
     * 创建弹窗的内容视图
     *
     * @return the view
     */
    protected abstract V makeContentView();

    /**
     * 固定高度为屏幕的高
     *
     * @param fillScreen true为全屏
     */
    public void setFillScreen(boolean fillScreen) {
        if (fillScreen) {
            setSize(screenWidthPixels, (int) (screenHeightPixels * 0.85f));
        }
    }

    /**
     * 固定高度为屏幕的一半
     *
     * @param halfScreen true为半屏
     */
    public void setHalfScreen(boolean halfScreen) {
        if (halfScreen) {
            setSize(screenWidthPixels, screenHeightPixels / 2);
        }
    }

    /**
     * 位于屏幕何处
     *
     * @see Gravity
     */
    public void setGravity(int gravity) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(gravity);
        }
        if (gravity == Gravity.CENTER) {
            //居于屏幕正中间时，宽度不允许填充屏幕
            setWidth((int) (screenWidthPixels * 0.7f));
        }
    }

    /**
     * 设置弹窗的内容视图之前执行
     */
    protected void setContentViewBefore() {
    }

    /**
     * 设置弹窗的内容视图之后执行
     *
     * @param contentView 弹窗的内容视图
     */
    protected void setContentViewAfter(V contentView) {
    }

    public void setContentView(View view) {
        contentLayout.removeAllViews();
        contentLayout.addView(view);
    }

    public void setAnimationStyle(@StyleRes int animRes) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setWindowAnimations(animRes);
        }
    }

    public void setOnKeyListener(final DialogInterface.OnKeyListener onKeyListener) {
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                BaseDialog.this.onKey(dialog, keyCode, event);
                                return onKeyListener.onKey(dialog, keyCode, event);
                            }
         });
    }

    /**
     * 设置弹窗的宽和高
     *
     * @param width  宽
     * @param height 高
     */
    public void setSize(int width, int height) {
        if (width == MATCH_PARENT) {
            //360奇酷等手机对话框MATCH_PARENT时两边还会有边距，故强制填充屏幕宽
            width = screenWidthPixels;
        }
        if (width == 0 && height == 0) {
            width = screenWidthPixels;
            height = WRAP_CONTENT;
        } else if (width == 0) {
            width = screenWidthPixels;
        } else if (height == 0) {
            height = WRAP_CONTENT;
        }
        ViewGroup.LayoutParams params = contentLayout.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(width, height);
        } else {
            params.width = width;
            params.height = height;
        }
        contentLayout.setLayoutParams(params);
    }

    /**
     * 设置弹窗的宽
     *
     * @param width 宽
     * @see #setSize(int, int)
     */
    public void setWidth(int width) {
        setSize(width, 0);
    }

    /**
     * 设置弹窗的高
     *
     * @param height 高
     * @see #setSize(int, int)
     */
    public void setHeight(int height) {
        setSize(0, height);
    }

    /**
     * 设置是否需要重新初始化视图，可用于数据刷新
     */
    public void setPrepared(boolean prepared) {
        isPrepared = prepared;
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public final void show() {
        if (isPrepared) {
            dialog.show();
            showAfter();
            return;
        }
        setContentViewBefore();
        V view = makeContentView();
        setContentView(view);// 设置弹出窗体的布局
        setContentViewAfter(view);
        isPrepared = true;
        dialog.show();
        showAfter();
    }

    protected void showAfter() {
    }

    public void dismiss() {
        dialog.dismiss();
    }



    public boolean onBackPress() {
        dismiss();
        return false;
    }

    @Override
    public final boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPress();
        }
        return false;
    }

    public Context getContext() {
        return dialog.getContext();
    }

    public Window getWindow() {
        return dialog.getWindow();
    }

    /**
     * 弹框的内容视图
     */
    public View getContentView() {
        // IllegalStateException: The specified child already has a parent.
        // You must call removeView() on the child's parent first.
        return contentLayout.getChildAt(0);
    }

    /**
     * 弹框的根视图
     */
    public ViewGroup getRootView() {
        return contentLayout;
    }

}
