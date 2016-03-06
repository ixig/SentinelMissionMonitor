package com.example.wing.sentinelmissionmonitor;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
    private static final String LOG_TAG = "~MainActivity";

    private boolean isRunning = false;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ImageView mDrawerMenuIV;
    private ImageView mDrawerStartStopIV;
    private DrawerItemCustomAdapter mDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerMenuIV = (ImageView) findViewById(R.id.ic_drawer);
        mDrawerStartStopIV = (ImageView) findViewById(R.id.ic_startstop);

        mDrawerMenuIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    mDrawerMenuIV.setImageResource(R.drawable.animated_arrow_to_drawer);
                    ((Animatable) mDrawerMenuIV.getDrawable()).start();
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                    mDrawerMenuIV.setImageResource(R.drawable.animated_drawer_to_arrow);
                    ((Animatable) mDrawerMenuIV.getDrawable()).start();
                }
            }
        });

        mDrawerStartStopIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int flag = getWindow().getDecorView().getSystemUiVisibility();
                //getWindow().getDecorView().setSystemUiVisibility(flag ^ View.SYSTEM_UI_FLAG_FULLSCREEN);
                if (isRunning) {
                    mDrawerStartStopIV.setImageResource(R.drawable.animated_stop_to_run);
                    ((Animatable) mDrawerStartStopIV.getDrawable()).start();
                    isRunning = false;
                } else {
                    mDrawerStartStopIV.setImageResource(R.drawable.animated_run_to_stop);
                    ((Animatable) mDrawerStartStopIV.getDrawable()).start();
                    isRunning = true;
                }
            }
        });

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[3];
        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_action_copy, "Create");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_action_refresh, "Read");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_action_share, "Help");

        mDrawerAdapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerMenuIV.setImageResource(R.drawable.vector_drawable_arrow);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerMenuIV.setImageResource(R.drawable.vector_drawable_drawer);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        ((Animatable) mDrawerMenuIV.getDrawable()).start();
    }

    // navigation drawer click listener
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // update the main content by replacing fragments
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new CreateFragment();
                    break;
                case 1:
                    fragment = new ReadFragment();
                    break;
                case 2:
                    fragment = new HelpFragment();
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
            } else {
                Log.e(LOG_TAG, "Error in creating fragment");
            }
        }
    }

}
