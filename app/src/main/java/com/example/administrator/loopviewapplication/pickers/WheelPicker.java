package com.example.administrator.loopviewapplication.pickers;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import com.example.administrator.loopviewapplication.R;
import com.example.administrator.loopviewapplication.common.ConfirmDialog;


public abstract class WheelPicker extends ConfirmDialog<View> {
    protected int textSize = 18;
    protected int mItemsVisibleCount = 5;
    protected int textColorNormal = mContext.getResources().getColor(R.color.c_c5c5c5);
    protected int textColorFocus = mContext.getResources().getColor(R.color.c_2bb2ba);
    protected int lineDividerColor = mContext.getResources().getColor(R.color.tab_cut_line);
    private View contentView;
    protected boolean weightEnable = false;
    public WheelPicker(Context mContext) {
        super(mContext);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setItemsVisibleCount(int count) {
        this.mItemsVisibleCount = count;
    }

     /**
     * 设置未选中文字颜色
     */
    public void setUnSelectedTextColor(@ColorInt int unSelectedTextColor) {
        this.textColorNormal = unSelectedTextColor;
    }
    /**
     * 设置选中文字颜色
     */
    public void setSelectedTextColor(@ColorInt int selectedTextColor) {
        this.textColorFocus = selectedTextColor;
    }

    /**
     * 设置分割线颜色
     */
    public void setDividerLineColor(@ColorInt int lineDividerColor) {
        this.lineDividerColor = lineDividerColor;
    }

    /**
     *
     * 线性布局设置是否启用权重
     * true 启用 false 自适应width
     */
    public void setWeightEnable(boolean weightEnable) {
        this.weightEnable = weightEnable;
    }

    /**
     * 得到选择器视图，可内嵌到其他视图容器
     */
    @Override
    public View getContentView() {
        if (null == contentView) {
            contentView = makeCenterView();
        }
        return contentView;
    }

}
