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
    private TextView mXBgView;
    /**
     * 第一次触摸偏移量
     */
    private boolean isFirstInit = true;

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
        // 图表触摸事件
        suitlines.setOnChartTouchListener(new SuitLines.onChartTouchListener() {
            @Override
            public void OnChartTouchDown(float downX, float downY) {
                tvMarker.setVisibility(VISIBLE);
                diver.setVisibility(VISIBLE);
                int minX = suitlines.getmXTextPoint().get(0).x;
                int maxX = suitlines.getmXTextPoint().get(suitlines.getDatas().get(0).size() - 1).x;
                if (downX <= minX) {
                    tvMarker.setX(minX - (tvMarker.getWidth() / 2));
                    diver.setX(minX - (diver.getWidth() / 2));
                } else if (downX >= maxX) {
                    tvMarker.setX(maxX - (tvMarker.getWidth() / 2));
                    diver.setX(maxX - (diver.getWidth() / 2));
                } else {
                    tvMarker.setX(downX - (tvMarker.getWidth() / 2));
                    diver.setX(downX - (diver.getWidth() / 2));
                }
                onTap(downX);
            }

            @Override
            public void OnChartTouchMove(float moveX, float moveY) {
                int minX = suitlines.getmXTextPoint().get(0).x;
                int maxX = suitlines.getmXTextPoint().get(suitlines.getDatas().get(0).size() - 1).x;
                if (moveX <= minX) {
                    tvMarker.setTranslationX(minX - (tvMarker.getWidth() / 2));
                    diver.setTranslationX(minX - (diver.getWidth() / 2));
                } else if (moveX >= maxX) {
                    tvMarker.setTranslationX(maxX - (tvMarker.getWidth() / 2));
                    diver.setTranslationX(maxX - (diver.getWidth() / 2));
                } else {
                    tvMarker.setTranslationX(moveX - (tvMarker.getWidth() / 2));
                    diver.setTranslationX(moveX - (diver.getWidth() / 2));
                }
                onTap(moveX);
            }

            @Override
            public void OnChartTouchUp(float upX, float upY) {
                mXBgView.setVisibility(INVISIBLE);
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
                // 添加底部X轴文字选中背景
                if (mXBgView == null) {
                    mXBgView = new TextView(mContext);
                    mXBgView.setTextSize(12);
                    mXBgView.setTextColor(Color.WHITE);
                    mXBgView.setBackgroundResource(R.drawable.shape_marker);
                    mXBgView.setGravity(Gravity.CENTER);
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(Util.dip2px(80), Util.dip2px(25));
                    addView(mXBgView, lp);
                    mXBgView.setVisibility(INVISIBLE);
                }
            }
        });

    }

    private void onTap(float upX) {
        // 第一次触摸偏移量
        int offstX = isFirstInit ? Util.dip2px(11.8f) : 0;
        if (suitlines.getDatas().isEmpty()) {
            return;
        }
        float index = (upX - suitlines.getLinesArea().left) / suitlines.getRealBetween();
        int realIndex = -1;
        if ((index - (int) index) > 0.6f) {
            realIndex = (int) index + 1;
        } else if ((index - (int) index) < 0.4f) {
            realIndex = (int) index;
        }
        if (realIndex != -1 && realIndex < suitlines.getDatas().get(0).size()) {
            mXBgView.setVisibility(VISIBLE);
            // 测量文字的宽高
            int width = (int) Util.getTextWidth(suitlines.getXyPaint(), suitlines.getDatas().get(0).get(realIndex).getExtX());
            int heigth = (int) Util.getTextHeight(suitlines.getXyPaint());
            FrameLayout.LayoutParams lp = (LayoutParams) mXBgView.getLayoutParams();
            lp.width = width + Util.dip2px(6) * 2;
            lp.height = heigth + Util.dip2px(5) * 2;
            mXBgView.setLayoutParams(lp);
            mXBgView.setText(suitlines.getDatas().get(0).get(realIndex).getExtX());
            if (realIndex == 0) {
                mXBgView.setX(suitlines.getmXTextPoint().get(realIndex).x - (mXBgView.getWidth() / 2) + width / 2 + offstX);
            } else if (realIndex == suitlines.getDatas().get(0).size() - 1) {
                mXBgView.setX(suitlines.getmXTextPoint().get(realIndex).x - (mXBgView.getWidth() / 2) - width / 2 + offstX);
            } else {
                mXBgView.setX(suitlines.getmXTextPoint().get(realIndex).x - (mXBgView.getWidth() / 2) + offstX);
            }
            tvMarker.setText(String.valueOf((int) suitlines.getDatas().get(0).get(realIndex).getValue()));
//            mXBgView.setX(suitlines.getmXTextPoint().get(realIndex).x - (mXBgView.getWidth() / 2));
            mXBgView.setY(suitlines.getmXTextPoint().get(realIndex).y + suitlines.getBasePadding() + Util.dip2px(2) + Util.dip2px(10));
            isFirstInit = false;
        }
    }

    public SuitLines getSuitlines() {
        return suitlines;
    }

    public TextView getTvMarker() {
        return tvMarker;
    }
}
