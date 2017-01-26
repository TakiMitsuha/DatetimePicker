package me.takimitsuha.datetimepicker.year;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Taki on 2017/1/26.
 */
public class YearManager {
    private static List<Year> sYearList;
    private static Context sContext;

    public YearManager(Context context) {
        this.sContext = context;
    }

    public static List<Year> getYearList() {
        if (sYearList == null) {
            sYearList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR) + 1;
            for (int j = 2005; j < year; j++) {
                sYearList.add(new Year(j, j + ""));
            }
        }
        return sYearList;
    }
}