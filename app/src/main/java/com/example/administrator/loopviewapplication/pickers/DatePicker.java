package com.example.administrator.loopviewapplication.pickers;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.administrator.loopviewapplication.R;
import com.example.administrator.loopviewapplication.loopview.LoopView;
import com.example.administrator.loopviewapplication.loopview.OnItemSelectedListener;
import com.example.administrator.loopviewapplication.utils.ConvertUtils;
import com.example.administrator.loopviewapplication.utils.global.AppGlobal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatePicker extends WheelPicker {

    private float weightWidth = 0.0f;
    private Context mContext;
    private int mYearSelect, mMonthSelect, mDaySelect;
    private List<String> mYearArray, mMonthArray, mDayArray;
    private LoopView mYearVew, mMonthVew, mDayVew;
    private static int START_YEAR = 1930, END_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    // 添加大小月月份并将其转换为list,方便之后的判断
    String[] months_big = {"1月", "3月", "5月", "7月", "8月", "10月", "12月"};
    String[] months_little = {"4月", "6月", "9月", "11月"};
    final List<String> list_big = Arrays.asList(months_big);
    final List<String> list_little = Arrays.asList(months_little);
    int thisYear = Calendar.getInstance().get(Calendar.YEAR);
    int thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    int thisDay = Calendar.getInstance().get(Calendar.DATE);

    public DatePicker(Context mContext) {
        super(mContext);
        this.mContext = mContext;
        this.mYearArray = AppGlobal.getYearArray(mContext, START_YEAR, END_YEAR);
        this.mMonthArray = AppGlobal.getMonthArray(mContext);
        this.mDayArray = AppGlobal.getDayArray(mContext, 1, 31);
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


    /**
     * 设置年月日滚轮默认选中
     */
    public void setYearSelected(String selected) {
        String[] result = selected.split("-");
        this.mYearSelect = Arrays.binarySearch(mYearArray.toArray(), result[0] + mContext.getString(R.string.year));
        this.mMonthSelect = Arrays.binarySearch(mMonthArray.toArray(), result[1] + mContext.getString(R.string.month));
        this.mDaySelect = Arrays.binarySearch(mDayArray.toArray(), result[2] + mContext.getString(R.string.day));
    }

    @Override
    @NonNull
    protected View makeCenterView() {
        if (mYearArray.size() == 0 || mMonthArray.size() == 0 || mDayArray.size() == 0) {
            throw new IllegalArgumentException("please initial items at first, can't be empty");
        }
        final LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        final LinearLayout.LayoutParams wheelParams = new LinearLayout.LayoutParams(WRAP_CONTENT, mItemsVisibleCount * 45);
        if (weightEnable) {
            layout.setWeightSum(1);
            //按权重分配宽度
            wheelParams.weight = weightWidth;
        }
        //年
        mYearVew = new LoopView(mContext);
        mYearVew.setItems(mYearArray);
        mYearVew.setLayoutParams(wheelParams);
        mYearVew.setTextSize(textSize);
        mYearVew.setCurrentPosition(mYearSelect);
        mYearVew.setItemsVisibleCount(mItemsVisibleCount);
        mYearVew.setCenterTextColor(textColorFocus);
        mYearVew.setOuterTextColor(textColorNormal);
        mYearVew.setDividerColor(lineDividerColor);
        layout.addView(mYearVew);

        //月
        mMonthVew = new LoopView(mContext);
        mMonthVew.setItems(mMonthArray);
        mMonthVew.setLayoutParams(wheelParams);
        mMonthVew.setTextSize(textSize);
        mMonthVew.setCurrentPosition(mMonthSelect);
        mMonthVew.setItemsVisibleCount(mItemsVisibleCount);
        mMonthVew.setCenterTextColor(textColorFocus);
        mMonthVew.setOuterTextColor(textColorNormal);
        mMonthVew.setDividerColor(lineDividerColor);
        layout.addView(mMonthVew);

        //日
        mDayVew = new LoopView(mContext);
        int year = ConvertUtils.removeContent(mYearArray.get(mYearSelect), mContext.getString(R.string.year));
        int month = ConvertUtils.removeContent(mMonthArray.get(mMonthSelect), mContext.getString(R.string.month));
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            mDayArray = AppGlobal.getDayArray(mContext, 1, 31);
        } else if (list_little.contains(String.valueOf(month + 1))) {
            mDayArray = AppGlobal.getDayArray(mContext, 1, 30);
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                mDayArray = AppGlobal.getDayArray(mContext, 1, 29);
            } else {
                mDayArray = AppGlobal.getDayArray(mContext, 1, 28);
            }
        }
        mDayVew.setItems(mDayArray);
        mDayVew.setLayoutParams(wheelParams);
        mDayVew.setTextSize(textSize);
        mDayVew.setCurrentPosition(mDaySelect);
        mDayVew.setItemsVisibleCount(mItemsVisibleCount);
        mDayVew.setCenterTextColor(textColorFocus);
        mDayVew.setOuterTextColor(textColorNormal);
        mDayVew.setDividerColor(lineDividerColor);
        layout.addView(mDayVew);

        mYearVew.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mYearSelect = index;
                int year_num = ConvertUtils.removeContent(mYearArray.get(mYearSelect), mContext.getString(R.string.year));
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(mMonthArray.get(mMonthSelect))) {
                    mDayArray = AppGlobal.getDayArray(mContext, 1, 31);
                    mDaySelect = 0;
                } else if (list_little.contains(mMonthArray.get(mMonthSelect))) {
                    mDayArray = AppGlobal.getDayArray(mContext, 1, 30);
                    mDaySelect = 0;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0) {
                        mDayArray = AppGlobal.getDayArray(mContext, 1, 29);
                        mDaySelect = 0;
                    } else {
                        mDayArray = AppGlobal.getDayArray(mContext, 1, 28);
                        mDaySelect = 0;
                    }
                }
                mDayVew.setItems(mDayArray);
                mDayVew.setLayoutParams(wheelParams);
                mDayVew.setTextSize(textSize);
                mDayVew.setCurrentPosition(mDaySelect);
                mDayVew.setItemsVisibleCount(mItemsVisibleCount);
                mDayVew.setCenterTextColor(textColorFocus);
                mDayVew.setOuterTextColor(textColorNormal);
                mDayVew.setDividerColor(lineDividerColor);
                layout.removeAllViews();
                layout.addView(mYearVew);
                layout.addView(mMonthVew);
                layout.addView(mDayVew);

            }
        });

        mMonthVew.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mMonthSelect = index;
                int year_num = ConvertUtils.removeContent(mYearArray.get(mYearSelect), mContext.getString(R.string.year));
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(mMonthArray.get(mMonthSelect))) {
                    mDayArray = AppGlobal.getDayArray(mContext, 1, 31);
                    mDaySelect = 0;
                } else if (list_little.contains(mMonthArray.get(mMonthSelect))) {
                    mDayArray = AppGlobal.getDayArray(mContext, 1, 30);
                    mDaySelect = 0;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0) {
                        mDayArray = AppGlobal.getDayArray(mContext, 1, 29);
                        mDaySelect = 0;
                    } else {
                        mDayArray = AppGlobal.getDayArray(mContext, 1, 28);
                        mDaySelect = 0;
                    }
                }
                mDayVew.setItems(mDayArray);
                mDayVew.setLayoutParams(wheelParams);
                mDayVew.setTextSize(textSize);
                mDayVew.setCurrentPosition(mDaySelect);
                mDayVew.setItemsVisibleCount(mItemsVisibleCount);
                mDayVew.setCenterTextColor(textColorFocus);
                mDayVew.setOuterTextColor(textColorNormal);
                mDayVew.setDividerColor(lineDividerColor);
                layout.removeAllViews();
                layout.addView(mYearVew);
                layout.addView(mMonthVew);
                layout.addView(mDayVew);

            }
        });
        mDayVew.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mDaySelect = index;
            }
        });

        return layout;
    }

    @Override
    public void onSubmit() {
        Toast.makeText(mContext, "您选择的时间是：" + getTime(), Toast.LENGTH_LONG).show();
    }

    public String getTime() {
        int year_num = ConvertUtils.removeContent(mYearArray.get(mYearSelect), mContext.getString(R.string.year));
        int month_num = ConvertUtils.removeContent(mMonthArray.get(mMonthSelect), mContext.getString(R.string.month));
        int day_num = ConvertUtils.removeContent(mDayArray.get(mDaySelect), mContext.getString(R.string.day));
        if (year_num > thisYear
                || (year_num == thisYear && month_num > thisMonth)
                || (year_num == thisYear && month_num == thisMonth && day_num > thisDay)) {
            Toast.makeText(mContext.getApplicationContext(), mContext.getString(R.string.select_time_tip), Toast.LENGTH_LONG).show();
            return thisYear + "-" + String.format("%02d", thisMonth) + "-" + String.format("%02d", thisDay);
        } else {
            return year_num + "-" + String.format("%02d", month_num) + "-" + String.format("%02d", day_num);
        }
    }

}
