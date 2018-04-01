package com.aaron.application.ssmarket_ad.adapter;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.application.ssmarket_ad.R;
import com.aaron.application.ssmarket_ad.activity.MainActivity;
import com.aaron.application.ssmarket_ad.common.AdType;
import com.aaron.application.ssmarket_ad.common.PreferenceManager;
import com.aaron.application.ssmarket_ad.network.model.CategoryItem;
import com.aaron.application.ssmarket_ad.service.MacroService;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CategoryAdapter extends BaseAdapter {
    private AlarmManager alarmMgr;
    private Context mContext;
    private PreferenceManager pref;
    private ArrayList<CategoryItem> items = new ArrayList<>();

    public CategoryAdapter(Context context, ArrayList<CategoryItem> items) {
        mContext = context;
        this.items = items;

        pref = PreferenceManager.getInstance(mContext);
        alarmMgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
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

        final CategoryItem item = items.get(position);

        View.OnClickListener dateSettingListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 5);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // 선택된 날짜가 있으면 그대로 설정
                if(pref.getLong(String.valueOf(item.getIdx()), 0) != 0) {
                    calendar.setTime(new Date(pref.getLong(String.valueOf(item.getIdx()), 0)));
                }

                DatePickerDialog dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DATE, dayOfMonth);
                        setDate(item, calendar);
                        notifyDataSetChanged();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        };

        // profile image
        Glide.with(mContext).load(item.getImageUrl().concat(item.getListImage()))
                .crossFade()
                .placeholder(R.mipmap.ic_image_default)
                .fitCenter()
                .into(holder.getImgCategory());

        // date text
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final long adTime = pref.getLong(String.valueOf(item.getIdx()), 0);
        holder.getTvCategoryDate().setText(adTime == 0 ? "-" : format.format(new Date(adTime)));

        // click event 등록
        holder.getImgCategory().setOnClickListener(dateSettingListener);
        holder.getTvCategoryDate().setOnClickListener(dateSettingListener);
        holder.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adTime == 0) {
                    Log.d("CategoryAdapter", "날짜를 설정해주세요.");
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(adTime);
                Log.d("CategoryAdapter", "calendar time : " + calendar.get(Calendar.MONTH) + "month " + calendar.get(Calendar.DAY_OF_MONTH) + "date "
                        + calendar.get(Calendar.HOUR_OF_DAY) + "hour " + calendar.get(Calendar.MINUTE) + "min");

                alarmMgr.set(AlarmManager.RTC_WAKEUP,
                        adTime - 3 * 60 * 1000, // start time
//                        Calendar.getInstance().getTimeInMillis() + 3000,  // test 용 현재시간 3초뒤 실행
                        getAlarmIntent(AdType.getType(item.getName())));
            }
        });
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CategoryAdapter", "광고 매크로 종료");
                alarmMgr.cancel(getAlarmIntent(AdType.getType(item.getName())));
            }
        });
        return convertView;
    }

    private PendingIntent getAlarmIntent(AdType type) {
        Intent serviceIntent = new Intent(mContext, MacroService.class);
        serviceIntent.putExtra("adType", type.getTypeName());
        return PendingIntent.getService(mContext, type.getRequestCode(), serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void setDate(CategoryItem item, Calendar calendar) {
        AdType adType = AdType.getType(item.getName());
        String idx = String.valueOf(item.getIdx());
        calendar.set(Calendar.MINUTE, adType == AdType.A ? 5 : adType == AdType.B ? 10 : 15);
        pref.put(adType.getPrefKey(), idx);
        pref.put(idx, calendar.getTimeInMillis());
    }

    private class CategoryHolder {
        private ImageView imgCategory;
        private TextView tvCategoryDate;
        private View startButton;
        private View cancelButton;

        CategoryHolder(View view) {
            imgCategory = (ImageView) view.findViewById(R.id.img_category);
            tvCategoryDate = (TextView) view.findViewById(R.id.tv_category_date);
            startButton = view.findViewById(R.id.btn_action);
            cancelButton = view.findViewById(R.id.btn_cancel);
        }

        ImageView getImgCategory() {
            return imgCategory;
        }

        TextView getTvCategoryDate() {
            return tvCategoryDate;
        }
    }
}
