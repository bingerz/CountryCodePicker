package com.bingerz.android.countrycodepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by hanbing on 16/3/8.
 */
public class CountryCodePicker {

    public static String EXTRA_CODE = "countryCode";

    public final static int REQUEST_CODE_PICKER = 604;

    public Intent getIntent(Context context) {
        Intent mIntent = new Intent();
        mIntent.setClass(context, CountryCodeActivity.class);
        return mIntent;
    }

    public void start(Activity activity) {
        activity.startActivityForResult(getIntent(activity), REQUEST_CODE_PICKER);
    }

    public void start(Fragment fragment) {
        fragment.startActivityForResult(getIntent(fragment.getActivity()), REQUEST_CODE_PICKER);
    }
}
