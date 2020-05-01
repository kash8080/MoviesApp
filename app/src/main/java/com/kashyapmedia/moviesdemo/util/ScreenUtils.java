package com.kashyapmedia.moviesdemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenUtils {


    public static DisplayMetrics getDisplayMetrics(Context context){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics;
    }
    public static int[] getscreenSize(Context context){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int size[]=new int[2];
        size[0]=displaymetrics.widthPixels;
        size[1]=displaymetrics.heightPixels;
        return size;
    }
    public static int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }
    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float getDistanceInDp(Context context, float x1, float y1, float x2, float y2){
        float x1Dp=ScreenUtils.convertPixelsToDp(x1,context);
        float y1Dp=ScreenUtils.convertPixelsToDp(y1,context);
        float x2Dp=ScreenUtils.convertPixelsToDp(x2,context);
        float y2Dp=ScreenUtils.convertPixelsToDp(y2,context);

        float xDiff= Math.abs(x2Dp-x1Dp);
        float yDiff= Math.abs(y2Dp-y1Dp);
        return (float) (Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2)));

    }

    public static float getDistance(float[] p1, float[] p2){
        return getDistance(p1[0],p1[1],p2[0],p2[1]);
    }

    public static float getDistance(float[] p1, float x, float y){
        return getDistance(p1[0],p1[1],x,y);
    }
    public static float getDistance(float x1, float y1, float x2, float y2){
        float xDiff= Math.abs(x2-x1);
        float yDiff= Math.abs(y2-y1);
        return Math.abs((float) (Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2))));

    }

    public static float getAngleOfDirection(float[] positionCurrent,float[] positionDest){
        return (float) Math.toDegrees(Math.atan2(positionDest[1] - positionCurrent[1], positionDest[0] - positionCurrent[0]));
    }


    public static void hideKeyboard(AppCompatActivity context){
        View view = ((AppCompatActivity)context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                if(imm!=null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            if(imm!=null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
