package com.example.finalwork.Activity.card;

import android.view.View;
import androidx.viewpager.widget.ViewPager.PageTransformer;

public class AlphaAndScalePageTransformer implements PageTransformer {
//    final float SCALE_MAX = 0.8F;
//    final float ALPHA_MAX = 0.5F;
//
//    public AlphaAndScalePageTransformer() {
//    }

    public void transformPage(View page, float position) {
        float scale = position < 0.0F ? 0.19999999F * position + 1.0F : -0.19999999F * position + 1.0F;
        float alpha = position < 0.0F ? 0.5F * position + 1.0F : -0.5F * position + 1.0F;
        if (position < 0.0F) {
            page.setPivotX((float)page.getWidth());
            page.setPivotY((float)(page.getHeight() / 2));
        } else {
            page.setPivotX(0.0F);
            page.setPivotY((float)(page.getHeight() / 2));
        }

        page.setScaleX(scale);
        page.setScaleY(scale);
        page.setAlpha(Math.abs(alpha));
    }
}

