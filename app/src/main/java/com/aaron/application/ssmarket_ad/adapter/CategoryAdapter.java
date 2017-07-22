package com.aaron.application.ssmarket_ad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.application.ssmarket_ad.R;
import com.aaron.application.ssmarket_ad.common.PreferenceManager;
import com.aaron.application.ssmarket_ad.network.model.CategoryItem;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private PreferenceManager pref;
    private ArrayList<CategoryItem> items = new ArrayList<>();

    public CategoryAdapter(Context context, ArrayList<CategoryItem> items) {
        mContext = context;
        this.items = items;

        pref = PreferenceManager.getInstance(mContext);
    }

    public void setDate(int position, Calendar calendar) {
        String name = items.get(position).getName();
        if(name.contains("(T)")) {
            calendar.set(Calendar.MINUTE, 0);
            pref.put(PreferenceManager.PREFERENCE_AD_T, String.valueOf(items.get(position).getIdx()));
            pref.put(String.valueOf(items.get(position).getIdx()), calendar.getTimeInMillis());

            calendar.set(Calendar.MINUTE, 10);
            pref.put(PreferenceManager.PREFERENCE_AD_A, String.valueOf(items.get(position-1).getIdx()));
            pref.put(String.valueOf(items.get(position-1).getIdx()), calendar.getTimeInMillis());
        } else if(name.contains("(a)")) {
            calendar.set(Calendar.MINUTE, 10);
            pref.put(PreferenceManager.PREFERENCE_AD_A, String.valueOf(items.get(position).getIdx()));
            pref.put(String.valueOf(items.get(position).getIdx()), calendar.getTimeInMillis());

            calendar.set(Calendar.MINUTE, 0);
            pref.put(PreferenceManager.PREFERENCE_AD_T, String.valueOf(items.get(position+1).getIdx()));
            pref.put(String.valueOf(items.get(position+1).getIdx()), calendar.getTimeInMillis());
        } else if(name.contains("(b)")) {
            calendar.set(Calendar.MINUTE, 0);
            pref.put(PreferenceManager.PREFERENCE_AD_B, String.valueOf(items.get(position).getIdx()));
            pref.put(String.valueOf(items.get(position).getIdx()), calendar.getTimeInMillis());

            calendar.set(Calendar.MINUTE, 10);
            pref.put(PreferenceManager.PREFERENCE_AD_C, String.valueOf(items.get(position-1).getIdx()));
            pref.put(String.valueOf(items.get(position-1).getIdx()), calendar.getTimeInMillis());
        } else if(name.contains("(c)")) {
            calendar.set(Calendar.MINUTE, 10);
            pref.put(PreferenceManager.PREFERENCE_AD_C, String.valueOf(items.get(position).getIdx()));
            pref.put(String.valueOf(items.get(position).getIdx()), calendar.getTimeInMillis());

            calendar.set(Calendar.MINUTE, 0);
            pref.put(PreferenceManager.PREFERENCE_AD_B, String.valueOf(items.get(position+1).getIdx()));
            pref.put(String.valueOf(items.get(position+1).getIdx()), calendar.getTimeInMillis());
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CategoryItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getIdx();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_view_category_item, null);
            holder = new CategoryHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryHolder) convertView.getTag();
        }

        CategoryItem item = items.get(position);

        // profile image
        Glide.with(mContext).load(item.getImageUrl().concat(item.getListImage()))
                .crossFade()
                .placeholder(R.mipmap.ic_image_default)
                .fitCenter()
                .into(holder.getImgCategory());

        // date text
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        holder.getTvCategoryDate().setText(pref.getLong(String.valueOf(item.getIdx()), 0) == 0 ? "-" : format.format(new Date(pref.getLong(String.valueOf(item.getIdx()), 0))));
        return convertView;
    }

    private class CategoryHolder {
        private ImageView imgCategory;
        private TextView tvCategoryDate;

        CategoryHolder(View view) {
            imgCategory = (ImageView) view.findViewById(R.id.img_category);
            tvCategoryDate = (TextView) view.findViewById(R.id.tv_category_date);
        }

        ImageView getImgCategory() {
            return imgCategory;
        }

        TextView getTvCategoryDate() {
            return tvCategoryDate;
        }
    }
}
