package com.aaron.application.ssmarket_ad.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.aaron.application.ssmarket_ad.R;
import com.aaron.application.ssmarket_ad.adapter.CalendarAdapter;
import com.aaron.application.ssmarket_ad.common.PreferenceManager;


public class CalendarCheckActivity extends AppCompatActivity {
    CalendarAdapter mAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_navi_back);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.lv_calendar);
        mAdapter = new CalendarAdapter(this);
        listView.setAdapter(mAdapter);
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
        if (id == R.id.action_apply) {
            PreferenceManager.getInstance(this).put(PreferenceManager.PREFERENCE_CALENDAR, mAdapter.getItems());
            setResult(RESULT_OK);
            finish();
            return true;
        }

        return true;
    }
}
