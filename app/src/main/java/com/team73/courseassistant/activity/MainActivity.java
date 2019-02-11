package com.team73.courseassistant.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.team73.courseassistant.R;
import com.team73.courseassistant.adapters.courseFragmentPgAdapter;

public class MainActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mTopToolbar = findViewById(R.id.toolbar);
        mTopToolbar.setTitle("");
        setSupportActionBar(mTopToolbar);

        ViewPager viewPager = findViewById(R.id.view_pager);
        courseFragmentPgAdapter adapter =
                new courseFragmentPgAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);

        setTab(tabLayout);
    }

    private void setTab(TabLayout tabLayout){
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_foreground);
        Drawable drawable = tabLayout.getTabAt(0).getIcon();
        setSelectedColor(drawable);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_average_foreground);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_profile_foreground);
    }

    private void setSelectedColor(Drawable drawable){
        if (drawable != null){
            drawable.setColorFilter( 0xFF000000, PorterDuff.Mode.MULTIPLY );
        }
    }

    private void setUnselectedColor(Drawable drawable){
        drawable.setColorFilter( 0xffffffff, PorterDuff.Mode.MULTIPLY );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            onSearchRequested();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Drawable drawable = tab.getIcon();
        setSelectedColor(drawable);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Drawable drawable = tab.getIcon();
        setUnselectedColor(drawable);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Drawable drawable = tab.getIcon();
        setSelectedColor(drawable);
    }
}
