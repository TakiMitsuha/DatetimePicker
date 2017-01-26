package me.takimitsuha.datetimepicker.month;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Taki on 2017/1/26.
 */
public class MonthManager {
    private static List<Month> sMonthList;
    private static Context sContext;

    public MonthManager(Context context) {
        this.sContext = context;
    }

    public static List<Month> getMonthList() {
        if (sMonthList == null) {
            sMonthList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR) + 1;
            for (int j = 2016; j < year; j++) {
                for (int k = 1; k < 13; k++) {
                    sMonthList.add(new Month(k, j, j + "." + k));
                }
            }
        }
        return sMonthList;
    }
}