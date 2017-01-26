package me.takimitsuha.datetimepicker.month;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.takimitsuha.datetimepicker.R;

/**
 * Created by Taki on 2017/1/26.
 */
public class MonthLayout extends RelativeLayout {

    private int EMITION_BOARD_HEIGHT = getResources().getDimensionPixelOffset(R.dimen.board_height);
    private List<Month> mMonthList;

    private int ROW_COUNT = 2;
    private int COLUMN_COUNT = 6;
    private int totalSmileCount;
    private int pageCount;

    private ViewPager viewPager;
    private TextView mTextView;

    private Context mContext;

    private Map<String, Month> map = new HashMap<String, Month>();

    private List<Month> dataList;

    public MonthLayout(Context context) {
        super(context);
    }

    public MonthLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonthLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private PopupWindow popWindow;

    public void init(TextView textView, PopupWindow popWindow, Context context) {
        this.mContext = context;
        this.popWindow = popWindow;
        mMonthList = MonthManager.getMonthList();
        totalSmileCount = mMonthList.size();

        for (Month month : mMonthList) {
            if (map.isEmpty()) {
                map.put(month.getYear() + "", month);
            } else {
                if (map.containsKey(month.getYear() + "")) {

                } else {
                    map.put(month.getYear() + "", month);
                }
            }
        }

        dataList = new ArrayList<>(map.values());
        Collections.sort(dataList, new Comparator<Month>() {

            @Override
            public int compare(Month month1, Month month2) {
                int i = month1.getYear() - month2.getYear();
                return i;
            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) + 1;
        pageCount = (year - 2016);
        this.mTextView = textView;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        LayoutInflater.from(getContext()).inflate(R.layout.content_datepicker, this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setId("vp".hashCode());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(pageCount);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initMagicIndicator();
        viewPager.setCurrentItem(pageCount);
    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.parseColor("#FFFFFF"));
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return dataList == null ? 0 : dataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(mContext);
                simplePagerTitleView.setText(dataList.get(index).getYear() + "");
                simplePagerTitleView.setNormalColor(Color.parseColor("#9e9e9e"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FF5D3D"));
                simplePagerTitleView.setGravity(Gravity.CENTER);
                simplePagerTitleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 6));
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#FF5D3D"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }


    AdapterView.OnItemClickListener onEmotionClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Month bean = (Month) view.getTag();
            String str = bean.getTag();
            mTextView.setText(str);
            popWindow.dismiss();
        }
    };

    PagerAdapter mPagerAdapter = new PagerAdapter() {

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            List<Month> pageSmileList = new ArrayList<>();
            for (int i = ROW_COUNT * COLUMN_COUNT * position; i <= ROW_COUNT * COLUMN_COUNT * (position + 1) - 1; i++) {
                if (i >= totalSmileCount)
                    break;
                pageSmileList.add(mMonthList.get(i));
            }
            GridView gridview = new GridView(getContext());
            gridview.setNumColumns(COLUMN_COUNT);
            gridview.setAdapter(new MonthGridAdapter(getContext(), pageSmileList));
            gridview.setOnItemClickListener(onEmotionClickListener);
            container.addView(gridview, LayoutParams.MATCH_PARENT, EMITION_BOARD_HEIGHT);
            return gridview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return pageCount;
        }
    };

}