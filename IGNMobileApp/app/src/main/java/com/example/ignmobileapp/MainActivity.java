package com.example.ignmobileapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignmobileapp.FragmentsFile.ArticlesFragment;
import com.example.ignmobileapp.FragmentsFile.VideosFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout= findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setUpCustomTabIcons();
    }

    private void setUpCustomTabIcons() {
        TextView tabText1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabText1.setText("Articles");
        tabText1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_library_books_red_24dp, 0, 0, 0);
        tabText1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        tabLayout.getTabAt(0).setCustomView(tabText1);

        TextView tabText2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabText2.setText("Videos");
        tabText2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_slow_motion_video_black_24dp, 0, 0, 0);
        tabText2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));
        tabLayout.getTabAt(1).setCustomView(tabText2);

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimary));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                below code gives the current position of the tab
//                Toast.makeText(getApplicationContext(),"selected "+tab.getPosition(),Toast.LENGTH_LONG).show();

                if(tab.getPosition()==0)
                {
                    View view= tab.getCustomView();
                    TextView textView = view.findViewById(R.id.tabText);
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_library_books_red_24dp, 0, 0, 0);
                    textView.setCompoundDrawablePadding(2);
                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                }
                else if(tab.getPosition()==1)
                {
                    View view= tab.getCustomView();
                    TextView textView = view.findViewById(R.id.tabText);
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_slow_motion_video_red_24dp, 0, 0, 0);
                    textView.setCompoundDrawablePadding(2);
                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if(tab.getPosition()==0)
                {
                    View view= tab.getCustomView();
                    TextView textView = view.findViewById(R.id.tabText);
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_library_books_black_24dp, 0, 0, 0);
                    textView.setCompoundDrawablePadding(2);
                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));
                }
                else if(tab.getPosition()==1)
                {
                    View view= tab.getCustomView();
                    TextView textView = view.findViewById(R.id.tabText);
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_slow_motion_video_black_24dp, 0, 0, 0);
                    textView.setCompoundDrawablePadding(2);
                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setupViewPager(ViewPager viewPager)
    {

        ViewPageAdapter vpAdapter = new ViewPageAdapter(getSupportFragmentManager());
        vpAdapter.addFragment(new ArticlesFragment(),"Articles Frag");
        vpAdapter.addFragment(new VideosFragment(),"Videos Frag");

        viewPager.setAdapter(vpAdapter);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class ViewPageAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPageAdapter(android.support.v4.app.FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        public CharSequence getPageTitle(int position)
        {
            return null;
        }
    }

}
