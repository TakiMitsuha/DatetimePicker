package me.takimitsuha.datetimepicker;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import me.takimitsuha.datetimepicker.month.MonthLayout;
import me.takimitsuha.datetimepicker.utils.DeviceUtil;
import me.takimitsuha.datetimepicker.utils.RotateUtil;
import me.takimitsuha.datetimepicker.wheelview.ScreenInfo;
import me.takimitsuha.datetimepicker.wheelview.WheelMain;
import me.takimitsuha.datetimepicker.year.YearLayout;
import rx.functions.Action1;

/**
 * Created by Taki on 2017/1/26.
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayout llDateTimePick;
    private TextView tvDateTime;
    private ImageView ivArrow;

    private WheelMain wheelMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llDateTimePick = (LinearLayout) findViewById(R.id.ll_date_time_pick);
        tvDateTime = (TextView) findViewById(R.id.tv_date_time);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        tvDateTime.setText(year + "." + month + "." + day);

        tvDateTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(MainActivity.this, s.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RxView.clicks(llDateTimePick)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showPopupWindow(tvDateTime);
                    }
                });
    }

    private PopupWindow popWindow;

    private void showPopupWindow(View parent) {
        RotateUtil.rotateArrow(ivArrow, false);
        View view;
        if (popWindow == null) {
            view = View.inflate(getApplicationContext(), R.layout.datepicker, null);
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            initPop(view);
        }

        popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAsDropDown(parent);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                RotateUtil.rotateArrow(ivArrow, true);
            }
        });
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    public void initPop(final View view) {
        final YearLayout yearLayout = (YearLayout) view.findViewById(R.id.year_panel);
        final MonthLayout monthLayout = (MonthLayout) view.findViewById(R.id.month_panel);
        monthLayout.init(tvDateTime, popWindow, this);
        yearLayout.init(tvDateTime, popWindow, this);
        final TextView tvDay = (TextView) view.findViewById(R.id.tv_day);
        final TextView tvMonth = (TextView) view.findViewById(R.id.tv_month);
        final TextView tvYear = (TextView) view.findViewById(R.id.tv_year);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);

        tvDay.setTextColor(Color.parseColor("#FF5D3D"));
        tvMonth.setTextColor(Color.parseColor("#333333"));
        tvYear.setTextColor(Color.parseColor("#333333"));


        final LinearLayout llDate = (LinearLayout) view.findViewById(R.id.ll_date);

        llDate.setVisibility(View.VISIBLE);
        ScreenInfo screenInfo1 = new ScreenInfo(MainActivity.this);
        wheelMain = new WheelMain(llDate);
        wheelMain.screenheight = screenInfo1.getHeight();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        wheelMain.initDateTimePicker(year, month, day);

        llDate.setVisibility(View.VISIBLE);
        yearLayout.setVisibility(View.GONE);
        monthLayout.setVisibility(View.GONE);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String date = wheelMain.getTime().replace("-", ".");
                    tvDateTime.setText(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                popWindow.dismiss();
            }
        });


        screenWidth = DeviceUtil.getScreenWidth(this);
        int width = screenWidth / 3;
        mIndicator = view.findViewById(R.id.indicator);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIndicator.getLayoutParams();
        params.width = width;
        mIndicator.setLayoutParams(params);

        tvDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveIndicator(0);
                lastIndex = 0;
                ScreenInfo screenInfo1 = new ScreenInfo(MainActivity.this);
                wheelMain = new WheelMain(llDate);
                wheelMain.screenheight = screenInfo1.getHeight();
                Calendar calendar1 = Calendar.getInstance();
                int year1 = calendar1.get(Calendar.YEAR);
                int month1 = calendar1.get(Calendar.MONTH);
                int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
                wheelMain.initDateTimePicker(year1, month1, day1);

                llDate.setVisibility(View.VISIBLE);
                yearLayout.setVisibility(View.GONE);
                monthLayout.setVisibility(View.GONE);

                tvDay.setTextColor(Color.parseColor("#FF5D3D"));
                tvMonth.setTextColor(Color.parseColor("#333333"));
                tvYear.setTextColor(Color.parseColor("#333333"));
            }
        });
        tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveIndicator(1);
                lastIndex = 1;
                yearLayout.setVisibility(View.GONE);
                monthLayout.setVisibility(View.VISIBLE);
                llDate.setVisibility(View.GONE);

                tvDay.setTextColor(Color.parseColor("#333333"));
                tvMonth.setTextColor(Color.parseColor("#FF5D3D"));
                tvYear.setTextColor(Color.parseColor("#333333"));
            }
        });
        tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveIndicator(2);
                lastIndex = 2;
                yearLayout.setVisibility(View.VISIBLE);
                llDate.setVisibility(View.GONE);
                monthLayout.setVisibility(View.GONE);


                tvDay.setTextColor(Color.parseColor("#333333"));
                tvMonth.setTextColor(Color.parseColor("#333333"));
                tvYear.setTextColor(Color.parseColor("#FF5D3D"));
            }
        });
    }

    private View mIndicator;

    private int screenWidth;

    private int lastIndex;

    private void moveIndicator(int index) {
        long duration = 300;
        int moveWidth = screenWidth / 3;
        ObjectAnimator.ofFloat(mIndicator, "translationX", lastIndex * moveWidth, index * moveWidth)
                .setDuration(duration).start();
    }
}
