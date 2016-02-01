package com.example.weeamawad.parking.Utility;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class Utils {

    private static final String EMPTY = "";
    private static final String NULL = "null";

    /**
     * Method checks if String value is empty
     *
     * @param str
     * @return string
     */
    public static boolean isStringEmpty(String str) {
        return str == null || str.length() == 0 || EMPTY.equals(str.trim()) || NULL.equals(str);
    }

    /**
     * Method is used to check if objects are null
     *
     * @param objectToCheck
     * @param <T>
     * @return true if objectToCheck is null
     */
    public static <T> boolean checkIfNull(T objectToCheck) {
        return objectToCheck == null;
    }

    public static void animateInViewFromFromSide(View view, boolean isFromLeft) {
        if (isFromLeft) {
            Animation anim = AnimationUtils.makeInAnimation(view.getContext(), true);
            view.startAnimation(anim);
        } else {
            Animation anim = AnimationUtils.makeInAnimation(view.getContext(), false);
            view.startAnimation(anim);
        }
        view.setVisibility(View.VISIBLE);
    }

    public static void animateOutViewFromFromSide(View view, boolean toRight) {
        if (toRight) {
            Animation anim = AnimationUtils.makeOutAnimation(view.getContext(), true);
            view.startAnimation(anim);
        } else {
            Animation anim = AnimationUtils.makeOutAnimation(view.getContext(), false);
            view.startAnimation(anim);
        }
        view.setVisibility(View.GONE);
    }
}
