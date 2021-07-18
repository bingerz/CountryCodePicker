package cn.bingerz.android.countrycodepicker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.bingerz.android.countrycodepicker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 *
 * @author hanbing
 */
public class CountryCodeActivity extends Activity {

    private ListView mListView;

    private ArrayList<CountryCode> mCountryCodes;

    protected CountryCodeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_code);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_actionbar_back_dark);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        initView();
        parseCountryCodeData();
    }

    private void initView() {
        SideBar sideBar = (SideBar) findViewById(R.id.sb_sidebar);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }
            }
        });

        mCountryCodes = new ArrayList<>();
        mAdapter = new CountryCodeAdapter(this);
        mListView = (ListView) findViewById(R.id.lv_list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CountryCode country = mCountryCodes.get(position);
                Intent intent = new Intent();
                intent.putExtra(CountryCodePicker.EXTRA_CODE, country);
                CountryCodeActivity.this.setResult(RESULT_OK, intent);
                CountryCodeActivity.this.finish();
            }
        });
    }

    private void parseCountryCodeData() {
        ArrayList<CountryCode> countryCodes = new ArrayList<>();
        try {
            for (int i = 0; i <= 238; i++) {
                String fileName = String.format(Locale.ENGLISH,"c%03d", i);
                int mResId = getResources().getIdentifier(fileName, "array", getPackageName());
                String[] codeArray = getResources().getStringArray(mResId);
                int code = Integer.parseInt(codeArray[2]);
                countryCodes.add(new CountryCode(i, codeArray[0], codeArray[1], codeArray[3], code));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        if (!countryCodes.isEmpty()) {
            for (CountryCode country : countryCodes) {
                if (Utils.getCountry(this).equals(Locale.CHINA.getCountry())) {
                    country.setSortLettersCn();
                } else {
                    country.setSortLettersEn();
                }
            }
            Collections.sort(countryCodes, new PinyinComparator());

            mCountryCodes.clear();
            mCountryCodes.addAll(countryCodes);
            mAdapter.notifyDateAll(countryCodes);
        }
    }

    static class PinyinComparator implements Comparator<CountryCode> {

        @Override
        public int compare(CountryCode o1, CountryCode o2) {
            if (o1.sortLetters.equals("@") || o2.sortLetters.equals("#")) {
                return -1;
            } else if (o1.sortLetters.equals("#") || o2.sortLetters.equals("@")) {
                return 1;
            } else {
                return o1.sortLetters.compareTo(o2.sortLetters);
            }
        }
    }
}
