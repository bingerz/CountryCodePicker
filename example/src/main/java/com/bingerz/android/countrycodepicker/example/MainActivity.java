package com.bingerz.android.countrycodepicker.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bingerz.android.countrycodepicker.CountryCode;
import com.bingerz.android.countrycodepicker.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_start_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryCodePicker picker = new CountryCodePicker();
                picker.start(MainActivity.this);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, new ChooseCountryCodeFragment(), "choose").commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CountryCodePicker.REQUEST_CODE_PICKER:
                if (data == null) {
                    Toast.makeText(this, "Country is null", Toast.LENGTH_SHORT).show();
                    return;
                }
                CountryCode countryCode = data.getParcelableExtra(CountryCodePicker.EXTRA_CODE);
                if (countryCode != null) {
                    Toast.makeText(this, "Activity:" + countryCode.mCountryCode, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
