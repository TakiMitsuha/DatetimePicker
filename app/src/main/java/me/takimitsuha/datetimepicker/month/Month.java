package me.takimitsuha.datetimepicker.month;

/**
 * Created by Taki on 2017/1/26.
 */
public class Month {

    private int month;
    private int year;
    private boolean isCurrent;
    private String tag;

    public Month(int month, int year, String tag) {
        this.month = month;
        this.year = year;
        this.tag = tag;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}