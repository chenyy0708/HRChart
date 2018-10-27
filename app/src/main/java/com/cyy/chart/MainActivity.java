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

    SuitLines suitLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        suitLines = (SuitLines) findViewById(R.id.suitlines);
        onBtnClick2(null);
//        onBtnClick101(null);
    }

    public void onBtnClick(View view) {
        suitLines.anim();
    }

    private boolean enable;

    public void onBtnClick1(View view) {
        suitLines.setCoverLine(enable = !enable);
    }

    public void onBtnClick13(View view) {
        int[] colors = new int[2];
        colors[0] = color[new SecureRandom().nextInt(7)];
        colors[1] = Color.WHITE;
        suitLines.setDefaultOneLineColor(colors);
    }

    private int curCount = 1;

    public void onBtnClick2(View view) {
        suitLines.setXySize(12);
        init(curCount = 1);
    }

    public void onBtnClick3(View view) {
        init(++curCount);
    }

    public void onBtnClick4(View view) {
        if (curCount <= 1) {
            curCount = 1;
        }
        init(--curCount);
    }

    public void onBtnClick5(View view) {
        suitLines.setLineForm(!suitLines.isLineFill());
    }


    public void onBtnClick6(View view) {
        suitLines.setLineStyle(suitLines.isLineDashed() ? SuitLines.SOLID : SuitLines.DASHED);
    }

    public void onBtnClick7(View view) {
//        suitLines.setLineType(suitLines.getLineType() == SuitLines.CURVE ? SuitLines.SEGMENT : SuitLines.CURVE);
    }

    public void onBtnClick8(View view) {
        suitLines.disableEdgeEffect();
    }

    public void onBtnClick9(View view) {
        suitLines.setEdgeEffectColor(color[new SecureRandom().nextInt(7)]);
    }

    public void onBtnClick10(View view) {
        suitLines.setXyColor(color[new SecureRandom().nextInt(7)]);
    }

    private float textSize = 8;

    public void onBtnClick11(View view) {
        suitLines.setXySize(++textSize);
    }

    public void onBtnClick12(View view) {
        if (textSize < 6) {
            textSize = 6;
        }
        suitLines.setXySize(--textSize);
    }

    public void onBtnClick14(View view) {
        suitLines.disableClickHint();
    }

    public void onBtnClick15(View view) {
        suitLines.setHintColor(color[new SecureRandom().nextInt(7)]);
    }

    private int[] color = {Color.RED, Color.GRAY, Color.BLACK, Color.BLUE, 0xFFF76055, 0xFF9B3655, 0xFFF7A055};

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
            suitLines.feedWithAnim(lines);
            return;
        }

        SuitLines.LineBuilder builder = new SuitLines.LineBuilder();
        for (int j = 0; j < count; j++) {
            List<Unit> lines = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                lines.add(new Unit(new SecureRandom().nextInt(128), "" + i));
            }
            builder.add(lines, Color.parseColor("#6FBA2C"));
        }
        builder.build(suitLines, true);

    }

    private boolean setShowYGrid;

    public void onBtnClick101(View view) {
        suitLines.setShowYGrid(setShowYGrid = !setShowYGrid);
    }
}