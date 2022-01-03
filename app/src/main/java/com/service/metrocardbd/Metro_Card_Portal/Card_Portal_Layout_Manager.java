package com.service.metrocardbd.Metro_Card_Portal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.service.metrocardbd.R;
import com.service.metrocardbd.SessionManager;

import java.util.Objects;

public class Card_Portal_Layout_Manager extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem;
    FrameLayout fragment_container;
    SessionManager sessionManager;
    ViewPager viewPager;

    @SuppressLint({"ClickableViewAccessibility", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_portal_layout);

        sessionManager = new SessionManager(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        /* Assigning Feilds to ID */
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Metro Card Portal");
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        //Initializing viewPager
        viewPager = findViewById(R.id.view_container);

        //Initializing the bottomNavigationView

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home_button:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.history_button:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.profile_button:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return false;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Disable ViewPager Swipe

        viewPager.setOnTouchListener(((v, event) -> true));

        setupViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        sessionManager.Check_Login();
    }


    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new Fragment_Card_Home());
        adapter.addFragment(new Fragment_Card_Recharge_History());
        adapter.addFragment(new Fragment_Card_User_Profile());
        viewPager.setAdapter(adapter);
    }

}