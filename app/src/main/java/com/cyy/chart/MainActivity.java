package com.cyy.chart;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private HRChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        chart = (HRChart) findViewById(R.id.chart);
        onBtnClick2(null);
    }

    private int curCount = 1;

    public void onBtnClick2(View view) {
        chart.getSuitlines().setXySize(12);
        init(curCount = 1);
    }

    public void init(int count) {
        if (count <= 0) {
            count = 0;
        }
        if (count == 1) {
            List<Unit> lines = new ArrayList<>();
            lines.add(new Unit(0, "2017-09"));
            lines.add(new Unit(800, "2017-10"));
            lines.add(new Unit(100, "2017-11"));
            lines.add(new Unit(50, "2017-12"));
            lines.add(new Unit(750, "2018-01"));
            lines.add(new Unit(1501, "2018-02"));
            lines.add(new Unit(500, "2018-03"));
            lines.add(new Unit(600, "2018-04"));
            lines.add(new Unit(489, "2018-05"));
            chart.getSuitlines().feedWithAnim(lines);
            return;
        }
    }
}