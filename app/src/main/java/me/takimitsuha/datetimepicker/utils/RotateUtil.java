package me.takimitsuha.datetimepicker.utils;

import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by Taki on 2017/1/26.
 */
public class RotateUtil {

    private RotateUtil() {
    }

    /**
     * 根据当前的状态来旋转箭头
     *
     * @param arrow 旋转的视图
     * @param flag  旋转状态true则向上
     */
    public static void rotateArrow(ImageView arrow, boolean flag) {
        float pivotX = arrow.getWidth() / 2f;
        float pivotY = arrow.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        if (flag) {
            fromDegrees = 180f;
            toDegrees = 360f;
        } else {
            fromDegrees = 0f;
            toDegrees = 180f;
        }
        RotateAnimation animation = new RotateAnimation(fromDegrees/*旋转的开始角度*/, toDegrees/*旋转的结束角度*/,
                pivotX/*pivotX伸缩值*/, pivotY/*pivotY轴伸缩值*/);
        animation.setDuration(300);
        //设置重复次数
        //animation.setRepeatCount(int repeatCount);
        //动画终止时停留在最后一帧
        animation.setFillAfter(true);
        arrow.startAnimation(animation);
    }
}