package com.bingerz.android.countrycodepicker;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

/**
 *
 * @author hanbing
 */
public class Utils {

    public static String getCountry(Context context) {
        try {
            Locale locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = context.getResources().getConfiguration().getLocales().get(0);
            } else {
                locale = context.getResources().getConfiguration().locale;
            }
            return locale.getCountry();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Locale.getDefault().getCountry();
    }
}
