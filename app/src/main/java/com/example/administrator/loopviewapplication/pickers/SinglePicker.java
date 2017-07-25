package com.example.administrator.loopviewapplication.pickers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.administrator.loopviewapplication.loopview.LoopView;
import com.example.administrator.loopviewapplication.loopview.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SinglePicker extends WheelPicker {

    private List<String> dataList = new ArrayList<>();
    private LoopView mSingleLoopView;
    private int mStringSelected;

    /**
     * 小时分钟
     */
    public SinglePicker(Context mContext, String[] items) {
        this(mContext, Arrays.asList(items));
    }

    /**
     * 小时分钟
     */
    public SinglePicker(Context mContext, List<String> items) {
        super(mContext);
        setItems(items);
    }

    /**
     * 设置数据项
     */
    public void setItems(String[] items) {
        setItems(Arrays.asList(items));
    }

    /**
     * 设置数据项
     */
    public void setItems(List<String> items) {
        if (null == items || items.size() == 0) {
            return;
        }
        this.dataList = items;
    }

    /**
     * 设置滚轮默认选中
     */
    public void setSelectedIndex(int selected) {
        this.mStringSelected = selected;
    }

    @Override
    @NonNull
    protected View makeCenterView() {
        if (dataList.size() == 0) {
            throw new IllegalArgumentException("please initial items at first, can't be empty");
        }
        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams wheelParams = new LinearLayout.LayoutParams(WRAP_CONTENT, mItemsVisibleCount * 45);
        mSingleLoopView = new LoopView(mContext);
        mSingleLoopView.setItems(dataList);
        mSingleLoopView.setLayoutParams(wheelParams);
        mSingleLoopView.setTextSize(textSize);
        mSingleLoopView.setCurrentPosition(mStringSelected);
        mSingleLoopView.setItemsVisibleCount(mItemsVisibleCount);
        mSingleLoopView.setCenterTextColor(textColorFocus);
        mSingleLoopView.setOuterTextColor(textColorNormal);
        mSingleLoopView.setDividerColor(lineDividerColor);
        layout.addView(mSingleLoopView);
        //小时滚动监听
        mSingleLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mStringSelected = index;
                Toast.makeText(mContext, "您选择的星座是：" + dataList.get(mStringSelected), Toast.LENGTH_LONG).show();
            }
        });
        return layout;
    }

    @Override
    public void onSubmit() {
        Toast.makeText(mContext, "您选择的星座是：" + dataList.get(mStringSelected), Toast.LENGTH_LONG).show();
    }


}
