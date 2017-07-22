package com.aaron.application.ssmarket_ad.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.application.ssmarket_ad.BaseApplication;
import com.aaron.application.ssmarket_ad.R;
import com.aaron.application.ssmarket_ad.adapter.CategoryAdapter;
import com.aaron.application.ssmarket_ad.common.Constant;
import com.aaron.application.ssmarket_ad.common.PreferenceManager;
import com.aaron.application.ssmarket_ad.network.RetrofitManager;
import com.aaron.application.ssmarket_ad.network.model.CategoryItem;
import com.aaron.application.ssmarket_ad.network.model.Root;
import com.aaron.application.ssmarket_ad.network.service.ItemList;
import com.aaron.application.ssmarket_ad.network.service.Login;
import com.aaron.application.ssmarket_ad.service.MacroService;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarCheckActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 1001);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setNumColumns(2);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final CategoryAdapter adapter = (CategoryAdapter) gridView.getAdapter();
                final Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 5);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // 선택된 날짜가 있으면 그대로 설정
                if(PreferenceManager.getInstance(getApplicationContext()).getLong(String.valueOf(adapter.getItemId(position)), 0) != 0) {
                    calendar.setTime(new Date(PreferenceManager.getInstance(getApplicationContext()).getLong(String.valueOf(adapter.getItemId(position)), 0)));
                }

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DATE, dayOfMonth);
                        adapter.setDate(position, calendar);
                        adapter.notifyDataSetChanged();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, MacroService.class);
                startService(serviceIntent);
            }
        });

        if(BaseApplication.TOKEN == null) {
            requestToken();
        } else {
            requestInitialData();
        }
    }

    private void requestToken() {
        Call<Root> call = RetrofitManager.getInstance(Constant.likeThatHost).create(Login.class)
                .requestLogin(45008,
                        "KT",
                        "kr",
                        Constant.userId,
                        "B278D52BFA3225E70001EA4BD9A9070F933B42A5F8153C17890320372210D4AA",
                        Constant.uuid);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if(response.body() != null) {
                    Root root = response.body();
                    if(root != null && root.getResult_code() == 0) {
                        BaseApplication.TOKEN = root.getInfo().getOauth_token();
                        PreferenceManager.getInstance(getApplicationContext()).put(PreferenceManager.PREFERENCE_TOKEN, BaseApplication.TOKEN);
                        requestInitialData();
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    private void requestInitialData() {
        // store 정보 로딩
        Call<Root> call = RetrofitManager.getInstance(Constant.ssmarketHost).create(Login.class)
                .requestStoreInfo(BaseApplication.TOKEN, Constant.userId, Constant.uuid);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if(response.body() != null) {
                    Root root = response.body();
                    if(root != null && root.getResult_code() == 200) {
                        ((TextView) findViewById(R.id.storeName)).setText(root.getInfo().getStoreName());
                        ((TextView) findViewById(R.id.storeLocation)).setText(root.getInfo().getLocation());
                        // profile image
                        Glide.with(getApplicationContext()).load(root.getInfo().getStoreImageUrl())
                                .crossFade()
                                .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 5, 5))
                                .placeholder(R.drawable.side_nav_bar)
                                .into((ImageView) findViewById(R.id.userImage));
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });

        // category 정보 로딩
        Call<Root> call1 = RetrofitManager.getInstance(Constant.ssmarketHost).create(ItemList.class)
                .requestGoodsList(24,0,0,0,Constant.userId,"DATE_DESC",Constant.uuid, BaseApplication.TOKEN);
        call1.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if(response.body() != null) {
                    Root root = response.body();
                    if(root != null && root.getResult_code() == 0) {
                        // check item list
                        if(root.getItems() != null) {
                            ArrayList<CategoryItem> items = new ArrayList<>();
                            int index = 0;
                            for(CategoryItem item : root.getItems()) {
                                if(item.getCategory() == 44) {
                                    items.add(item);
                                    if(++index > 3) {
                                        break;
                                    }
                                }
                            }

                            gridView.setAdapter(new CategoryAdapter(getApplicationContext(), items));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
