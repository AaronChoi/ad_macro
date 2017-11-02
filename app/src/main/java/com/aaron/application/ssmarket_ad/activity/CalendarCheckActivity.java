package com.aaron.application.ssmarket_ad.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.aaron.application.ssmarket_ad.R;
import com.aaron.application.ssmarket_ad.adapter.CalendarAdapter;
import com.aaron.application.ssmarket_ad.common.PreferenceManager;

import java.util.ArrayList;


public class CalendarCheckActivity extends AppCompatActivity {
    CalendarAdapter mAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_navi_back);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("광고 희망일자 선택");

        ListView listView = (ListView) findViewById(R.id.lv_calendar);
        mAdapter = new CalendarAdapter(this);
        listView.setAdapter(mAdapter);

        RadioGroup typeOptions = (RadioGroup) findViewById(R.id.typeOptions);
        typeOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 마지막에 보던 페이지의 데이터를 저장하고 변경해줌
                saveCalendarPref();

                String key;
                switch (checkedId) {
                    case R.id.typeA:
                        key = PreferenceManager.PREFERENCE_CALENDAR_A;
                        break;
                    case R.id.typeB:
                        key = PreferenceManager.PREFERENCE_CALENDAR_B;
                        break;
                    case R.id.typeC:
                        key = PreferenceManager.PREFERENCE_CALENDAR_C;
                        break;
                    case R.id.typeT:
                    default:
                        key = PreferenceManager.PREFERENCE_CALENDAR_T;
                        break;
                }
                mAdapter.setItems(key);
            }
        });
        typeOptions.check(R.id.typeT);
    }

    private void saveCalendarPref() {
        PreferenceManager.getInstance(this).put(mAdapter.getLastKey(), mAdapter.getItems());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar, menu);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            PreferenceManager.getInstance(this).put(mAdapter.getLastKey(), new ArrayList<String>());
            mAdapter.setItems(mAdapter.getLastKey());
            return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveCalendarPref();
    }
}
