package cn.bingerz.android.countrycodepicker.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingerz.android.countrycodepicker.CountryCode;
import cn.bingerz.android.countrycodepicker.CountryCodePicker;

import cn.bingerz.android.countrycodepicker.example.R;

/**
 * Created by hanbing on 16/6/14.
 */
public class ChooseCountryCodeFragment extends Fragment implements View.OnClickListener{

    private TextView tvCountry;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_code, container, false);
        tvCountry = (TextView) view.findViewById(R.id.tv_country);
        tvCountry.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_country:
                new CountryCodePicker().start(this);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CountryCodePicker.REQUEST_CODE_PICKER:
                if (data == null) {
                    Toast.makeText(getActivity(), "Country is null", Toast.LENGTH_SHORT).show();
                    return;
                }
                CountryCode countryCode = data.getParcelableExtra(CountryCodePicker.EXTRA_CODE);
                if (countryCode != null) {
                    Toast.makeText(getActivity(), "Fragment:" + countryCode.mCountryCode, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
