package me.takimitsuha.datetimepicker.year;

/**
 * Created by Taki on 2017/1/26.
 */
public class Year {

    private int index;
    private String tag;

    public Year(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTag() {
        return this.tag;
    }
}
