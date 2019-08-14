package com.module.ads.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.chungld.module.ads.R;
import com.module.ads.model.ads.Type;

/**
 * Created by Le Duc Chung on 2019-07-23
 * Time created: 11:42 AM
 * Project: MovieSeries
 * Coding for Life
 */
public class AdsContainerHolderView extends LinearLayout {

    private RelativeLayout mBannerViewHolder;

    public AdsContainerHolderView(Context context) {
        super(context);
        initialization();
    }

    public AdsContainerHolderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialization();
    }

    public AdsContainerHolderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialization();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AdsContainerHolderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialization();
    }

    private void initialization() {
        setOrientation(VERTICAL);
        LayoutParams containerLinearLayout = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        FrameLayout containerHolder = new FrameLayout(getContext());
        containerHolder.setLayoutParams(containerLinearLayout);
        containerHolder.setId(R.id.fragment_holder_container);
        this.addView(containerHolder);
    }

    public void setBannerAds(View adsView, int position) {
        mBannerViewHolder = mBannerViewHolder == null ? new RelativeLayout(getContext()) : mBannerViewHolder;
        mBannerViewHolder.removeAllViews();
        this.removeView(mBannerViewHolder);
        RelativeLayout.LayoutParams bannerParameters = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mBannerViewHolder.addView(adsView, bannerParameters);
        switch (position) {
            case Type.BOTTOM:
                this.addView(mBannerViewHolder);
                break;
            case Type.TOP:
                this.addView(mBannerViewHolder, 0);
                break;
            default:
                this.addView(mBannerViewHolder);
        }
    }
}
