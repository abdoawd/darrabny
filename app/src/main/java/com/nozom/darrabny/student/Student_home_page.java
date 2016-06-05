package com.nozom.darrabny.student;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nozom.darrabny.R;
import com.nozom.darrabny.adapter.student_tabs_adapter;
import com.nozom.darrabny.company.Company_home_page;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.main.MainActivity;
import com.nozom.darrabny.main.logout;

/**
 * Created by abdelgawad on 26/03/16.
 */
public class Student_home_page extends AppCompatActivity implements ActionBar.TabListener {
    private ViewPager pager = null;
    private android.support.v7.app.ActionBar bar;
    private ProgressDialog progress;
    private BackendlessUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        user = Backendless.UserService.CurrentUser();

        pager = (ViewPager) findViewById(R.id.student_pager);
        FragmentManager manager = getSupportFragmentManager();
        student_tabs_adapter adapter = new student_tabs_adapter(manager);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bar = getSupportActionBar();
        bar.setNavigationMode(bar.NAVIGATION_MODE_TABS);
        android.support.v7.app.ActionBar.Tab tab1 = bar.newTab();
        tab1.setText(Constants.HOME);
        tab1.setTabListener(this);
        android.support.v7.app.ActionBar.Tab tab2 = bar.newTab();
        tab2.setText(Constants.SEARCH);
        tab2.setTabListener(this);
        android.support.v7.app.ActionBar.Tab tab3 = bar.newTab();
        tab3.setText(Constants.NOTIFICATION);
        tab3.setTabListener(this);
        bar.addTab(tab1);
        bar.addTab(tab2);
        bar.addTab(tab3);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logout logout = new logout(this);
            logout.logOut(user);
        }
        return true;
    }
}