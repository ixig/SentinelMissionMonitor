package com.example.wing.sentinelmissionmonitor;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
    private static final String LOG_TAG = "~MainActivity";
    private static final int NUM_SCREENS = 5;

    private boolean isRunning = false;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ImageView drawerMenuIV;
    private ImageView drawerStartStopIV;
    private View[] drawerScreenIconsV = new View[NUM_SCREENS];
    private String[] drawerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        // If we're being restored from a previous state, then we don't need to do anything
        // or else we could end up with overlapping fragments.
        if (savedInstanceState == null) {
            MainFragment fragment = MainFragment.createNewFragment(0);
            //fragment.setArguments(getIntent().getExtras());
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();

        } else {
            Log.i(LOG_TAG, "onCreate: savedInstanceState NOT NULL");
        }

        drawerItems = getResources().getStringArray(R.array.drawer_items);
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, drawerItems));

        registerListeners();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerMenuIV.setImageResource(drawerLayout.isDrawerOpen(drawerList) ?
                R.drawable.vector_drawable_arrow : R.drawable.vector_drawable_drawer);
    }

    private void findViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerMenuIV = (ImageView) findViewById(R.id.ic_drawer);
        drawerStartStopIV = (ImageView) findViewById(R.id.ic_startstop);

        drawerScreenIconsV[0] = findViewById(R.id.ic_screen1);
        drawerScreenIconsV[1] = findViewById(R.id.ic_screen2);
        drawerScreenIconsV[2] = findViewById(R.id.ic_screen3);
        drawerScreenIconsV[3] = findViewById(R.id.ic_screen4);
        drawerScreenIconsV[4] = findViewById(R.id.ic_screen5);
    }

    private void registerListeners() {
        for (int i = 0; i < NUM_SCREENS; i++) {
            final int ii = i;
            drawerScreenIconsV[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    drawerScreenIconsV[ii].setAlpha(0.2F);
                    return true;
                }
            });
        }

        drawerMenuIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(drawerList)) {
                    drawerLayout.closeDrawer(drawerList);
                    drawerMenuIV.setImageResource(R.drawable.animated_arrow_to_drawer);
                    ((Animatable) drawerMenuIV.getDrawable()).start();
                } else {
                    drawerLayout.openDrawer(drawerList);
                    drawerMenuIV.setImageResource(R.drawable.animated_drawer_to_arrow);
                    ((Animatable) drawerMenuIV.getDrawable()).start();
                }
            }
        });

        drawerStartStopIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    drawerStartStopIV.setImageResource(R.drawable.animated_stop_to_run);
                    ((Animatable) drawerStartStopIV.getDrawable()).start();
                    isRunning = false;
                } else {
                    drawerStartStopIV.setImageResource(R.drawable.animated_run_to_stop);
                    ((Animatable) drawerStartStopIV.getDrawable()).start();
                    isRunning = true;
                }
            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerMenuIV.setImageResource(R.drawable.vector_drawable_arrow);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerMenuIV.setImageResource(R.drawable.vector_drawable_drawer);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = MainFragment.createNewFragment(0);
                    drawerScreenIconsV[1].setAlpha(1.0F);
                    break;
                case 1:
                    fragment = MainFragment.createNewFragment(1);
                    drawerScreenIconsV[2].setAlpha(1.0F);
                    break;
                case 2:
                    fragment = MainFragment.createNewFragment(2);
                    break;
            }

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();

            drawerList.setItemChecked(position, true);
            drawerLayout.closeDrawer(drawerList);
        }
    }

}

/* Hide Status Bar */
//int flag = getWindow().getDecorView().getSystemUiVisibility();
//getWindow().getDecorView().setSystemUiVisibility(flag ^ View.SYSTEM_UI_FLAG_FULLSCREEN);
