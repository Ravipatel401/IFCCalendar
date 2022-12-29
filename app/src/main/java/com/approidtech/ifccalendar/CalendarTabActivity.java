package com.approidtech.ifccalendar;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.approidtech.ifccalendar.Fragment.IFCFragment;
import com.approidtech.ifccalendar.Fragment.NormalCalendarFragment;
import com.approidtech.ifccalendar.utils.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class CalendarTabActivity extends AppCompatActivity  implements TabLayoutMediator.TabConfigurationStrategy {

    private ViewPager2 pager2;
    private TabLayout tabLayout;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarview);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){e.printStackTrace();}
        pager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        titles = new ArrayList<String>();
        titles.add("365");
        titles.add("364");
        setViewPagerAdapter();
        new TabLayoutMediator(tabLayout, pager2, this).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("Tab", "Selected"+tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e("Tab", "UnSelected"+tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("Tab", "ReSelected"+tab.getPosition());
            }
        });
        //String myID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    public void setViewPagerAdapter() {
        FragmentAdapter fragmentAdapter = new
                FragmentAdapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>(); //creates an ArrayList of Fragments
        fragmentList.add(new NormalCalendarFragment());
        fragmentList.add(new IFCFragment());

        fragmentAdapter.setData(fragmentList); //sets the data for the adapter
        pager2.setAdapter(fragmentAdapter);

    }
    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));
    }

}
