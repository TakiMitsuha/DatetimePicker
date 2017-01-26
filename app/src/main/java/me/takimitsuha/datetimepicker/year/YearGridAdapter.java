package me.takimitsuha.datetimepicker.year;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Taki on 2017/1/26.
 */
public class YearGridAdapter extends BaseAdapter {

    private List<Year> mYearList;
    private static Context sContext;

    public YearGridAdapter(Context context, List<Year> yearList) {
        this.mYearList = yearList;
        this.sContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView tvIndex = new TextView(sContext);
        tvIndex.setTextColor(0xFF000000);
        tvIndex.setText(mYearList.get(position).getIndex() + "");
        if (mYearList.get(position).getIndex() < 2016) {
            tvIndex.setTextColor(Color.parseColor("#999999"));
        }
        tvIndex.setGravity(Gravity.CENTER);
        LinearLayout layout = new LinearLayout(sContext);
        layout.setGravity(Gravity.CENTER);
        layout.setTag(mYearList.get(position));
        layout.addView(tvIndex, dp2px(40), dp2px(28 + 20));
        return layout;
    }

    public int getCount() {
        return mYearList.size();
    }

    @Override
    public Object getItem(int position) {
        return mYearList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static int dp2px(float dpValue) {
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}