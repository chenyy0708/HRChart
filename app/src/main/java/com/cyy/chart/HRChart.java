package com.cyy.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HRChart extends FrameLayout {

    private Context mContext;
    private SuitLines suitlines;
    private TextView tvMarker;
    private View diver;

    public HRChart(@NonNull Context context) {
        this(context, null);
    }

    public HRChart(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HRChart(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(mContext, R.layout.view_hrchart, this);
        suitlines = findViewById(R.id.suitlines);
        tvMarker = findViewById(R.id.tv_marker);

        suitlines.setOnChartTouchListener(new SuitLines.onChartTouchListener() {
            @Override
            public void OnChartTouchDown(float downX, float downY) {
                tvMarker.setVisibility(VISIBLE);
                diver.setVisibility(VISIBLE);
                diver.setX(downX - (diver.getWidth() / 2));
                tvMarker.setX(downX - (tvMarker.getWidth() / 2));
            }

            @Override
            public void OnChartTouchMove(float moveX, float moveY) {
                tvMarker.setTranslationX(moveX - (tvMarker.getWidth() / 2));
                diver.setTranslationX(moveX - (diver.getWidth() / 2));
            }

            @Override
            public void OnChartTouchUp(float upX, float upY) {
                tvMarker.setVisibility(INVISIBLE);
                diver.setVisibility(INVISIBLE);
            }
        });
        suitlines.setOnChartInitListener(new SuitLines.onChartInitListener() {
            @Override
            public void onChartInit(RectF linesArea) {
                // 添加一个1dp的竖直线
                if (diver == null && (linesArea.bottom - linesArea.top > 0)) {
                    diver = new View(mContext);
                    diver.setBackgroundColor(Color.parseColor("#6FBA2C"));
                    float height = linesArea.height();
                    FrameLayout.LayoutParams lpDriver = new FrameLayout.LayoutParams(Util.dip2px(1), (
                            (int) (linesArea.bottom - linesArea.top) - (Util.dip2px(35))
                    ));
                    lpDriver.gravity = Gravity.TOP;
                    lpDriver.setMargins(0, Util.dip2px(35) + Util.dip2px(10), 0, 0);
                    addView(diver, lpDriver);
                    diver.setVisibility(INVISIBLE);
                }
            }
        });

    }

    public SuitLines getSuitlines() {
        return suitlines;
    }

    public TextView getTvMarker() {
        return tvMarker;
    }
}
