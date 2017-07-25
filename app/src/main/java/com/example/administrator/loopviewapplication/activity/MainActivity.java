package com.example.administrator.loopviewapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import com.example.administrator.loopviewapplication.R;
import com.example.administrator.loopviewapplication.pickers.DatePicker;
import com.example.administrator.loopviewapplication.pickers.NumberPicker;
import com.example.administrator.loopviewapplication.pickers.SinglePicker;
import com.example.administrator.loopviewapplication.pickers.TimePicker;
import com.example.administrator.loopviewapplication.utils.global.AppGlobal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TimePicker mTimePicker;
    private SinglePicker mSinglePicker;
    private DatePicker mDatePicker;
    private NumberPicker mNumberPicker;
    private RelativeLayout rl_inner_area, rl_constellation_area, rl_time_area, rl_date_area, rl_height_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setListener();
    }


    public void findViewById() {
        rl_inner_area = (RelativeLayout) findViewById(R.id.rl_inner_area);
        rl_constellation_area = (RelativeLayout) findViewById(R.id.rl_constellation_area);
        rl_time_area = (RelativeLayout) findViewById(R.id.rl_time_area);
        rl_date_area = (RelativeLayout) findViewById(R.id.rl_date_area);
        rl_height_area = (RelativeLayout) findViewById(R.id.rl_height_area);
    }

    public void setListener() {
        rl_inner_area.setOnClickListener(this);
        rl_constellation_area.setOnClickListener(this);
        rl_time_area.setOnClickListener(this);
        rl_date_area.setOnClickListener(this);
        rl_height_area.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_inner_area:
                startActivity(new Intent(this, InnerViewActivity.class));
                break;
            case R.id.rl_constellation_area:
                //星座选择
                mSinglePicker = new SinglePicker(this, AppGlobal.getStringArray());
                mSinglePicker.setSelectedTextColor(getResources().getColor(R.color.c_2bb2ba));
                mSinglePicker.setUnSelectedTextColor(getResources().getColor(R.color.c_c5c5c5));
                mSinglePicker.setDividerLineColor(getResources().getColor(R.color.tab_cut_line));
                mSinglePicker.setSelectedIndex(5);
                mSinglePicker.setItemsVisibleCount(7);
                mSinglePicker.show();
                break;
            case R.id.rl_time_area:
                //时间选择
                mTimePicker = new TimePicker(this, AppGlobal.getHourArray(), AppGlobal.getMinArray());
                mTimePicker.setWeightEnable(true);
                mTimePicker.setWeightWidth(0.5f);
                mTimePicker.setItemsVisibleCount(7);
                mTimePicker.setSelectedTextColor(getResources().getColor(R.color.c_2bb2ba));
                mTimePicker.setUnSelectedTextColor(getResources().getColor(R.color.c_c5c5c5));
                mTimePicker.setDividerLineColor(getResources().getColor(R.color.tab_cut_line));
                mTimePicker.setHourSelectedIndex(0);
                mTimePicker.setMinSelectedIndex(0);
                mTimePicker.show();
                break;
            case R.id.rl_date_area:
                //日期选择
                mDatePicker = new DatePicker(this);
                mDatePicker.setWeightEnable(true);
                mDatePicker.setWeightWidth(0.3f);
                mDatePicker.setItemsVisibleCount(7);
                mDatePicker.setSelectedTextColor(getResources().getColor(R.color.c_2bb2ba));
                mDatePicker.setUnSelectedTextColor(getResources().getColor(R.color.c_c5c5c5));
                mDatePicker.setDividerLineColor(getResources().getColor(R.color.tab_cut_line));
                mDatePicker.setYearSelected("2017-7-24");
                mDatePicker.show();
                break;
            case R.id.rl_height_area:
                mNumberPicker = new NumberPicker(this, AppGlobal.getHeightArray(MainActivity.this));
                mNumberPicker.setSelectedTextColor(getResources().getColor(R.color.c_2bb2ba));
                mNumberPicker.setUnSelectedTextColor(getResources().getColor(R.color.c_c5c5c5));
                mNumberPicker.setDividerLineColor(getResources().getColor(R.color.tab_cut_line));
                mNumberPicker.setItemsVisibleCount(7);
                mNumberPicker.setSelectedIndex("160cm");
                mNumberPicker.show();
                break;
        }

    }
}
