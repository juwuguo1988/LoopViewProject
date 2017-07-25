package com.example.administrator.loopviewapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.example.administrator.loopviewapplication.R;
import com.example.administrator.loopviewapplication.loopview.LoopView;
import com.example.administrator.loopviewapplication.loopview.OnItemSelectedListener;
import com.example.administrator.loopviewapplication.utils.global.AppGlobal;
import java.util.Arrays;

public class InnerViewActivity extends AppCompatActivity {

    private LoopView lv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inner_activity);
        findViewById();
        initView();
    }


    public void findViewById() {
        lv_view = (LoopView) findViewById(R.id.lv_view);
    }

    private void initView() {
        lv_view.setItems(Arrays.asList(AppGlobal.getStringArray()));
        lv_view.setTextSize(18);
        lv_view.setItemsVisibleCount(7);
        lv_view.setCenterTextColor(getResources().getColor(R.color.c_2bb2ba));
        lv_view.setOuterTextColor(getResources().getColor(R.color.c_c5c5c5));
        lv_view.setCurrentPosition(5);
        //滚动监听
        lv_view.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(InnerViewActivity.this, "您选择的星座是：" + Arrays.asList(AppGlobal.getStringArray()).get(index), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


}
