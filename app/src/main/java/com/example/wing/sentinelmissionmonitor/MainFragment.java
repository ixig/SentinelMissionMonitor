package com.example.wing.sentinelmissionmonitor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {
    private static final String LOG_TAG = "~MainFrag";

    private static final String PAGE_NUM = "pageNum";

    public static final int NUM_PAGES = 3;

    public static MainFragment createNewFragment(int pageNum) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_NUM, pageNum);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switch (getArguments().getInt("pageNum")) {
            case 0: return inflater.inflate(R.layout.fragment_create, container, false);
            case 1: return inflater.inflate(R.layout.fragment_read, container, false);
            case 2: return inflater.inflate(R.layout.fragment_help, container, false);
        }
        Log.e(LOG_TAG, "onCreateView: WTF?!!");
        return null;
    }
}
