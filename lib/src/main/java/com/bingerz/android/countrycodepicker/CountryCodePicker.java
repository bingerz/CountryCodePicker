package com.bingerz.android.countrycodepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by hanbing on 16/3/8.
 */
public class CountryCodePicker {

    private Intent mIntent;

    public static String EXTRA_CODE = "countryCode";

    public Intent getIntent(Context context) {
        mIntent = new Intent();
        mIntent.setClass(context, CountryCodeActivity.class);
        return mIntent;
    }

    public void start(Activity activity, int requestCode) {
        activity.startActivityForResult(getIntent(activity), requestCode);
    }
}
