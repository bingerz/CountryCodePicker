package com.bingerz.android.countrycodepicker;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class CountryCodeAdapter extends BaseAdapter implements SectionIndexer {

    private Context ctx;

    private LayoutInflater mInflater;

    private ArrayList<CountryCode> mDataList = new ArrayList<>();

    public CountryCodeAdapter(Context ctx) {
        this.ctx = ctx;
        mInflater = LayoutInflater.from(this.ctx);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_country_code_list, null);
            holder = new ViewHolder();
            holder.tvCatalog = (TextView) convertView.findViewById(R.id.tv_catalog);
            holder.ivCountryIcon = (ImageView) convertView.findViewById(R.id.iv_list_country_icon);
            holder.tvCountryName = (TextView) convertView.findViewById(R.id.tv_list_country_name);
            holder.tvCountryCode = (TextView) convertView.findViewById(R.id.tv_list_country_code);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CountryCode country = mDataList.get(position);

        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.tvCatalog.setVisibility(View.VISIBLE);
            holder.tvCatalog.setText(country.sortLetters);
        } else {
            holder.tvCatalog.setVisibility(View.GONE);
        }
        String fileName = String.format("f%03d", country.mFlagId);
        int mResId = ctx.getResources().getIdentifier(fileName, "drawable", ctx.getPackageName());
        holder.ivCountryIcon.setImageResource(mResId);
        if (Locale.getDefault().getCountry().equals(Locale.CHINA.getCountry())) {
            holder.tvCountryName.setText(country.mNameCn);
        } else {
            holder.tvCountryName.setText(country.mNameEn);
        }
        holder.tvCountryCode.setText(country.getCountryCodeStr());
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    static final class ViewHolder {

        public TextView tvCatalog;

        public ImageView ivCountryIcon;

        public TextView tvCountryName;

        public TextView tvCountryCode;
    }

    public void notifyDateAll(ArrayList<CountryCode> countries) {
        if (countries == null || countries.isEmpty()) {
            return;
        }
        mDataList.clear();
        mDataList.addAll(0, countries);
        notifyDataSetChanged();
    }

    @Override
    public int getSectionForPosition(int position) {
        return mDataList.get(position).sortLetters.charAt(0);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mDataList.get(i).sortLetters;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
