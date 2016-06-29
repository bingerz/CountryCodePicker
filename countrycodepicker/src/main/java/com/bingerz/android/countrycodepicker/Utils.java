package com.bingerz.android.countrycodepicker;

import android.content.Context;

import java.util.Locale;

/**
 * Created by hanbing on 16/6/29.
 */
public class Utils {

    public static String getCountry(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().locale;
            return locale.getCountry();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Locale.getDefault().getCountry();
    }
}
