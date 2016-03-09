package com.bingerz.android.countrycodepicker.example;

import com.bingerz.android.countrycodepicker.CountryCode;
import com.bingerz.android.countrycodepicker.CountryCodePicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_PICKER = 64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_start_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryCodePicker picker = new CountryCodePicker();
                picker.start(MainActivity.this, REQUEST_CODE_PICKER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICKER:
                if (data == null) {
                    Toast.makeText(this, "Country is null", Toast.LENGTH_SHORT).show();
                    return;
                }
                CountryCode countryCode = data.getParcelableExtra(CountryCodePicker.EXTRA_CODE);
                if (countryCode != null) {
                    Toast.makeText(this, countryCode.mCountryCode + "", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
