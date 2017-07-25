package com.example.administrator.loopviewapplication.pickers;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.loopviewapplication.loopview.LoopView;
import com.example.administrator.loopviewapplication.loopview.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimePicker extends WheelPicker {

    private List<String> hourItems = new ArrayList<>();
    private List<String> minItems = new ArrayList<>();
    private LoopView mHourLoopView, mMinLoopView;
    private float weightWidth = 0.0f;
    private int mHourSelected;
    private int mMinSelected;

    /**
     * 小时分钟
     */
    public TimePicker(Context mContext, String[] hourItems, String[] minItems) {
        this(mContext, Arrays.asList(hourItems), Arrays.asList(minItems));
    }

    /**
     * 小时分钟
     */
    public TimePicker(Context mContext, List<String> hourItems, List<String> minItems) {
        super(mContext);
        setHourItems(hourItems);
        setMinItems(minItems);
    }

    /**
     * 设置小时数据项
     */
    public void setHourItems(String[] items) {
        setHourItems(Arrays.asList(items));
    }

    /**
     * 设置数据项
     */
    public void setHourItems(List<String> items) {
        if (null == items || items.size() == 0) {
            return;
        }
        this.hourItems = items;
    }

    /**
     * 设置分钟数据项
     */
    public void setMinItems(String[] items) {
        setMinItems(Arrays.asList(items));
    }

    /**
     * 设置数据项
     */
    public void setMinItems(List<String> items) {
        if (null == items || items.size() == 0) {
            return;
        }
        this.minItems = items;
    }

    /**
     * 设置小时默认选中
     */
    public void setHourSelectedIndex(int selected) {
        this.mHourSelected = selected;
    }

    /**
     * 设置分钟默认选中
     */
    public void setMinSelectedIndex(int selected) {
        this.mMinSelected = selected;
    }


    /**
     * 设置view的权重，总权重数为1 ,weightWidth范围（0.0f-1.0f）
     */
    public void setWeightWidth(@FloatRange(from = 0, to = 1) float weightWidth) {
        if (weightWidth < 0) {
            weightWidth = 0;
        }
        this.weightWidth = weightWidth;
    }

    @Override
    @NonNull
    protected View makeCenterView() {
        if (hourItems.size() == 0 || minItems.size() == 0) {
            throw new IllegalArgumentException("please initial items at first, can't be empty");
        }
        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams wheelParams = new LinearLayout.LayoutParams(WRAP_CONTENT, mItemsVisibleCount * 45);
        if (weightEnable) {
            layout.setWeightSum(1);
            //按权重分配宽度
            wheelParams.weight = weightWidth;
        }
        mHourLoopView = new LoopView(mContext);
        mHourLoopView.setItems(hourItems);
        mHourLoopView.setLayoutParams(wheelParams);
        mHourLoopView.setTextSize(textSize);
        mHourLoopView.setCurrentPosition(mHourSelected);
        mHourLoopView.setItemsVisibleCount(mItemsVisibleCount);
        mHourLoopView.setCenterTextColor(textColorFocus);
        mHourLoopView.setOuterTextColor(textColorNormal);
        mHourLoopView.setDividerColor(lineDividerColor);
        layout.addView(mHourLoopView);
        //小时滚动监听
        mHourLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mHourSelected = index;
            }
        });

        TextView labelView = new TextView(mContext);
        labelView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        labelView.setTextColor(textColorFocus);
        labelView.setTextSize(textSize);
        labelView.setText(":");
        layout.addView(labelView);

        mMinLoopView = new LoopView(mContext);
        mMinLoopView.setItems(minItems);
        mMinLoopView.setLayoutParams(wheelParams);
        mMinLoopView.setTextSize(textSize);
        mMinLoopView.setCurrentPosition(mMinSelected);
        mMinLoopView.setItemsVisibleCount(mItemsVisibleCount);
        mMinLoopView.setCenterTextColor(textColorFocus);
        mMinLoopView.setOuterTextColor(textColorNormal);
        mMinLoopView.setDividerColor(lineDividerColor);
        layout.addView(mMinLoopView);
        //分钟滚动监听
        mMinLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mMinSelected = index;
            }
        });
        return layout;
    }

    @Override
    public void onSubmit() {
        Toast.makeText(mContext, "您选择的时间是：" + hourItems.get(mHourSelected) + ":" + minItems.get(mMinSelected), Toast.LENGTH_LONG).show();
    }

}
