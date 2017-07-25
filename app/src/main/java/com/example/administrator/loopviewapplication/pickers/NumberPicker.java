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

public class NumberPicker extends WheelPicker {

    private List<String> dataList = new ArrayList<>();
    private LoopView mNumberLoopView;
    private int mStringSelected;

    public NumberPicker(Context mContext, String[] items) {
        this(mContext, Arrays.asList(items));
    }


    public NumberPicker(Context mContext, List<String> items) {
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
    public void setSelectedIndex(String selected) {
        this.mStringSelected = Arrays.binarySearch(dataList.toArray(), selected);
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
        mNumberLoopView = new LoopView(mContext);
        mNumberLoopView.setItems(dataList);
        mNumberLoopView.setLayoutParams(wheelParams);
        mNumberLoopView.setTextSize(textSize);
        mNumberLoopView.setCurrentPosition(mStringSelected);
        mNumberLoopView.setItemsVisibleCount(mItemsVisibleCount);
        mNumberLoopView.setCenterTextColor(textColorFocus);
        mNumberLoopView.setOuterTextColor(textColorNormal);
        mNumberLoopView.setDividerColor(lineDividerColor);
        layout.addView(mNumberLoopView);
        //小时滚动监听
        mNumberLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mStringSelected = index;
            }
        });
        return layout;
    }

    @Override
    public void onSubmit() {
        Toast.makeText(mContext, "您选择的身高是：" + dataList.get(mStringSelected), Toast.LENGTH_LONG).show();
    }

}
