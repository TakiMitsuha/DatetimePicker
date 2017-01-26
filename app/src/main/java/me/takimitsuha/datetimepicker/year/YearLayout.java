package me.takimitsuha.datetimepicker.year;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import me.takimitsuha.datetimepicker.R;

/**
 * Created by Taki on 2017/1/26.
 */
public class YearLayout extends RelativeLayout {

    private int EMITION_BOARD_HEIGHT = getResources().getDimensionPixelOffset(R.dimen.board_height);
    private List<Year> smileList;

    private int ROW_COUNT = 3;
    private int COLUMN_COUNT = 5;
    private int totalSmileCount;
    private int pageCount;

    private ViewPager viewPager;
    private TextView mTextView;

    private Context mContext;

    private MagicIndicator magicIndicator;

    public YearLayout(Context context) {
        super(context);
    }

    public YearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private PopupWindow popWindow;

    public void init(TextView textView, PopupWindow popWindow, Context context) {
        this.mContext = context;
        this.popWindow = popWindow;
        this.mTextView = textView;
        smileList = YearManager.getYearList();
        totalSmileCount = smileList.size();
        pageCount = totalSmileCount / (ROW_COUNT * COLUMN_COUNT) + 1;
        LayoutInflater.from(getContext()).inflate(R.layout.content_datepicker, this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        magicIndicator.setVisibility(GONE);
        viewPager.setId("vp".hashCode());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(pageCount);
        viewPager.setCurrentItem(pageCount);
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
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    AdapterView.OnItemClickListener onEmotionClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Year bean = (Year) view.getTag();
            if (bean.getIndex() < 2016) {
                return;
            }
            mTextView.setText(bean.getIndex() + "");
            popWindow.dismiss();
        }
    };

    PagerAdapter mPagerAdapter = new PagerAdapter() {

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            List<Year> pageSmileList = new ArrayList<>();
            for (int i = ROW_COUNT * COLUMN_COUNT * position; i <= ROW_COUNT * COLUMN_COUNT * (position + 1) - 1; i++) {
                if (i >= totalSmileCount)
                    break;
                pageSmileList.add(smileList.get(i));
            }
            GridView gridview = new GridView(getContext());
            gridview.setNumColumns(COLUMN_COUNT);
            gridview.setAdapter(new YearGridAdapter(getContext(), pageSmileList));
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