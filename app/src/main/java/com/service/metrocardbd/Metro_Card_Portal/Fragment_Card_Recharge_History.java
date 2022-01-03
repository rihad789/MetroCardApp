package com.service.metrocardbd.Metro_Card_Portal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.service.metrocardbd.APIs;
import com.service.metrocardbd.Line_Pojo_Class;
import com.service.metrocardbd.R;
import com.service.metrocardbd.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment_Card_Recharge_History extends Fragment {

    SessionManager sessionManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment

//        tabLayout = view.findViewById(R.id.history_tab_layout);
//        viewPager = view.findViewById(R.id.history_viewpager);


        return inflater.inflate(R.layout.fragment_card_portal_recharge_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = view.findViewById(R.id.history_tab_layout);
        ViewPager viewPager = view.findViewById(R.id.history_viewpager);

        tabLayout.setupWithViewPager(viewPager);


        //create viewpager adapter
        //here we will create inner class for adapter
        HistoryViewPagerAdapter viewPagerAdapter = new HistoryViewPagerAdapter(getChildFragmentManager());
        //add fragments and set the adapter
        viewPagerAdapter.addFrag(new FragmentRechargeHistory(),"Recharge");
        viewPagerAdapter.addFrag(new Fragment_Travel_History(),"Travel");

        viewPager.setAdapter(viewPagerAdapter);

    }

}