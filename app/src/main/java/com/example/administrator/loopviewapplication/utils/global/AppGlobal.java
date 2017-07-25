package com.example.administrator.loopviewapplication.utils.global;

import android.content.Context;
import com.example.administrator.loopviewapplication.R;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/23.
 * 全局方法
 */
public class AppGlobal {

    public static String[] getHourArray() {
        String[] hourArray = new String[24];
        for (int i = 0; i < 24; i++) {
            hourArray[i] = String.format("%02d", i);
        }
        return hourArray;
    }

    public static String[] getMinArray() {
        String[] minArray = new String[60];
        for (int i = 0; i < 60; i++) {
            minArray[i] = String.format("%02d", i);
        }
        return minArray;
    }


    public static List<String> getYearArray(Context mContext, int start, int end) {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < end - start + 1; i++) {
            dataList.add((i + start) + mContext.getString(R.string.year));
        }
        return dataList;
    }

    public static List<String> getMonthArray(Context mContext) {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            dataList.add((i + 1) + mContext.getString(R.string.month));
        }
        return dataList;
    }

    public static List<String> getDayArray(Context mContext, int start, int end) {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < end - start + 1; i++) {
            dataList.add((i + start) + mContext.getString(R.string.day));
        }
        return dataList;
    }


    public static String[] getStringArray() {
        String[] dayArray = new String[]{
                "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
                "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"
        };
        return dayArray;
    }

    public static List<String> getHeightArray(Context mContext) {
        List<String> dataList = new ArrayList<>();
        for (int i = 100; i < 250; i++) {
            dataList.add(i + mContext.getString(R.string.cm));
        }
        return dataList;
    }

}
