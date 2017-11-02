package com.aaron.application.ssmarket_ad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.aaron.application.ssmarket_ad.R;
import com.aaron.application.ssmarket_ad.common.PreferenceManager;

import java.util.ArrayList;

public class CalendarAdapter extends BaseAdapter {
    private ArrayList<Boolean> items = new ArrayList<>();
    private Context mContext;
    private String lastKey;

    public CalendarAdapter(Context context) {
        mContext = context;

        setItems(PreferenceManager.PREFERENCE_CALENDAR_T);
    }

    public void setItems(String key) {
        lastKey = key;
        ArrayList<String> arrayList = PreferenceManager.getInstance(mContext).getStringArray(key, null);
        items.clear();
        for(int i=0 ; i<27 ; i++) {
            items.add(false);
        }

        if(arrayList != null) {
            for(String item : arrayList) {
                items.set(Integer.parseInt(item), true);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<String> getItems() {
        ArrayList<String> resultArray = new ArrayList<>();
        int index = 0;
        for(Boolean item : items) {
            if(item != null && item) {
                resultArray.add(String.valueOf(index));
            }
            index++;
        }
        return resultArray;
    }

    public String getLastKey() {
        return lastKey;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Boolean getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CalendarHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_view_calendar_select, parent, false);
            holder = new CalendarHolder(convertView);
        } else {
            holder = (CalendarHolder) convertView.getTag();
        }

        holder.getDate().setText("매월 " + String.valueOf(position+1) + "번째 일");
        holder.getCheckBox().setChecked(items.get(position));
        holder.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.set(position, !items.get(position));
            }
        });

        convertView.setTag(holder);
        return convertView;
    }

    private class CalendarHolder {
        TextView date;
        CheckBox checkBox;

        private CalendarHolder(View convertView) {
            setDate((TextView) convertView.findViewById(R.id.tv_date));
            setCheckBox((CheckBox) convertView.findViewById(R.id.ck_date));
        }

        TextView getDate() {
            return date;
        }

        void setDate(TextView date) {
            this.date = date;
        }

        CheckBox getCheckBox() {
            return checkBox;
        }

        void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }
    }
}
