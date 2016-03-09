package com.bingerz.android.countrycodepicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by hanbing on 15/5/13.
 */
public class CountryCodeActivity extends Activity {

    private SideBar sideBar;

    private ListView mListView;

    private ArrayList<CountryCode> mCountryCodes;

    protected CountryCodeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_code);
        initView();
        new parseCountryCodeTask().execute("countryCode.json");
    }

    private void initView() {
        sideBar = (SideBar) findViewById(R.id.sb_sidebar);
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

    private ArrayList<CountryCode> getCountryCodeListFromJsonString(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        Type listType = new TypeToken<ArrayList<CountryCode>>(){}.getType();
        return new Gson().fromJson(data, listType);
    }

    private class parseCountryCodeTask extends AsyncTask<String, Void, ArrayList<CountryCode>> {

        @Override
        protected ArrayList<CountryCode> doInBackground(String[] params) {
            StringBuilder text = new StringBuilder();
            BufferedReader reader = null;
            try {
                InputStreamReader in = new InputStreamReader(getAssets().open(params[0]), "UTF-8");
                reader = new BufferedReader(in);
                String line;
                while ((line = reader.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
            } catch (IOException e) {
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
                ArrayList<CountryCode> data = getCountryCodeListFromJsonString(text.toString());
                if (data != null && !data.isEmpty()) {
                    for (CountryCode country : data) {
                        if (Locale.getDefault().getCountry().equals(Locale.CHINA.getCountry())) {
                            country.setSortLettersCn();
                        } else {
                            country.setSortLettersEn();
                        }
                    }
                    Collections.sort(data, new PinyinComparator());
                }
                return data;
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<CountryCode> countries) {
            if (countries != null && !countries.isEmpty()) {
                mCountryCodes.clear();
                mCountryCodes.addAll(countries);
                mAdapter.notifyDateAll(countries);
            }
        }
    }

    class PinyinComparator implements Comparator<CountryCode> {

        public int compare(CountryCode o1, CountryCode o2) {
            if (o1.sortLetters.equals("@")
                    || o2.sortLetters.equals("#")) {
                return -1;
            } else if (o1.sortLetters.equals("#")
                    || o2.sortLetters.equals("@")) {
                return 1;
            } else {
                return o1.sortLetters.compareTo(o2.sortLetters);
            }
        }
    }
}
