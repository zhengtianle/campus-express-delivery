package com.example.mrzheng.lanlanapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Extra.StatusBarUtils;
import com.example.mrzheng.lanlanapp.Guide.CirclePageIndicator;
import com.example.mrzheng.lanlanapp.Guide.ColorShades;
import com.example.mrzheng.lanlanapp.R;

/**
 * Created by mrzheng on 18-4-14.
 */

public class WelcomeActivity extends AppCompatActivity {
    private boolean isSliderAnimation = false;
    private boolean isEnd = false;
    private Button buttonStart;
    private View itemView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_gudie_welcome);

        //没有设置顶部状态栏颜色

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new ViewPagerAdapter(R.array.icons, R.array.titles, R.array.hints));

        CirclePageIndicator mIndicator  = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);

        viewPager.setPageTransformer(true, new CustomPageTransformer());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

                View landingBGView = findViewById(R.id.landing_backgrond);
                int colorBg[] = getResources().getIntArray(R.array.landing_bg);


                ColorShades shades = new ColorShades();
                shades.setFromColor(colorBg[position % colorBg.length])
                        .setToColor(colorBg[(position + 1) % colorBg.length])
                        .setShade(positionOffset);

                landingBGView.setBackgroundColor(shades.generate());


                if(position == 3){
                    buttonStart = (Button) findViewById(R.id.button_enter);
                    buttonStart.setVisibility(View.VISIBLE);
                    buttonStart.setTag("enter");
                    buttonStart.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            if (v.getTag().equals("enter")) {
                                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                return ;
                            }
                        }
                    } );
                }


            }

            public void onPageSelected(int position) {

            }

            public void onPageScrollStateChanged(int state) {
            }
        });

    }


    public class ViewPagerAdapter extends PagerAdapter {

        private int iconResId, titleArrayResId, hintArrayResId;

        public ViewPagerAdapter(int iconResId, int titleArrayResId, int hintArrayResId) {

            this.iconResId = iconResId;
            this.titleArrayResId = titleArrayResId;
            this.hintArrayResId = hintArrayResId;
        }

        @Override
        public int getCount() {
            return getResources().getIntArray(iconResId).length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Drawable icon = getResources().obtainTypedArray(iconResId).getDrawable(position);
            String title = getResources().getStringArray(titleArrayResId)[position];
            String hint = getResources().getStringArray(hintArrayResId)[position];


            itemView = getLayoutInflater().inflate(R.layout.item_preview, container, false);


            ImageView iconView = (ImageView) itemView.findViewById(R.id.landing_img_slide);
            TextView titleView = (TextView)itemView.findViewById(R.id.landing_txt_title);
            TextView hintView = (TextView)itemView.findViewById(R.id.landing_txt_hint);


            iconView.setImageDrawable(icon);
            titleView.setText(title);
            hintView.setText(hint);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);

        }
    }

    public class CustomPageTransformer implements ViewPager.PageTransformer {


        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            View imageView = view.findViewById(R.id.landing_img_slide);
            View contentView = view.findViewById(R.id.landing_txt_hint);
            View txt_title = view.findViewById(R.id.landing_txt_title);

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left
            } else if (position <= 0) { // [-1,0]
                // This page is moving out to the left

                // Counteract the default swipe
                setTranslationX(view,pageWidth * -position);
                if (contentView != null) {
                    // But swipe the contentView
                    setTranslationX(contentView,pageWidth * position);
                    setTranslationX(txt_title,pageWidth * position);

                    setAlpha(contentView,1 + position);
                    setAlpha(txt_title,1 + position);
                }

                if (imageView != null) {
                    // Fade the image in
                    setAlpha(imageView,1 + position);
                }

            } else if (position <= 1) { // (0,1]
                // This page is moving in from the right

                // Counteract the default swipe
                setTranslationX(view, pageWidth * -position);
                if (contentView != null) {
                    // But swipe the contentView
                    setTranslationX(contentView,pageWidth * position);
                    setTranslationX(txt_title,pageWidth * position);

                    setAlpha(contentView, 1 - position);
                    setAlpha(txt_title, 1 - position);

                }
                if (imageView != null) {
                    // Fade the image out
                    setAlpha(imageView,1 - position);
                }

            }
        }
    }

    /**
     * Sets the alpha for the view. The alpha will be applied only if the running android device OS is greater than honeycomb.
     * @param view - view to which alpha to be applied.
     * @param alpha - alpha value.
     */
    private void setAlpha(View view, float alpha) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && ! isSliderAnimation) {
            view.setAlpha(alpha);
        }
    }

    /**
     * Sets the translationX for the view. The translation value will be applied only if the running android device OS is greater than honeycomb.
     * @param view - view to which alpha to be applied.
     * @param translationX - translationX value.
     */
    private void setTranslationX(View view, float translationX) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && ! isSliderAnimation) {
            view.setTranslationX(translationX);
        }
    }

    public void onSaveInstanceState(Bundle outstate) {

        if(outstate != null) {
            outstate.putBoolean("SliderAnimationSavingState",isSliderAnimation);
        }

        super.onSaveInstanceState(outstate);
    }

    public void onRestoreInstanceState(Bundle inState) {

        if(inState != null) {
            isSliderAnimation = inState.getBoolean("SliderAnimationSavingState",false);
        }
        super.onRestoreInstanceState(inState);

    }


}
