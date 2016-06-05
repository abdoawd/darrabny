package com.nozom.darrabny.company;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nozom.darrabny.R;
import com.nozom.darrabny.adapter.TabsAdapter;
import com.nozom.darrabny.main.Constants;
import com.nozom.darrabny.main.MainActivity;
import com.nozom.darrabny.main.logout;
import com.nozom.darrabny.student.Student_home_page;

public class Company_home_page extends AppCompatActivity implements ActionBar.TabListener {
   private ViewPager pager=null;
    private android.support.v7.app.ActionBar bar;
    private BackendlessUser user;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home_page);
        user= Backendless.UserService.CurrentUser();
        pager= (ViewPager) findViewById(R.id.pager);
        FragmentManager manager=getSupportFragmentManager();
        TabsAdapter adapter=new TabsAdapter(manager);
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
        bar=getSupportActionBar();
        bar.setNavigationMode(bar.NAVIGATION_MODE_TABS);
        android.support.v7.app.ActionBar.Tab tab1=bar.newTab();
        tab1.setText(Constants.HOME);
        tab1.setTabListener(this);
        android.support.v7.app.ActionBar.Tab tab2=bar.newTab();
        tab2.setText(Constants.ADD_TRAINING);
        tab2.setTabListener(this);


        bar.addTab(tab1);
        bar.addTab(tab2);

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
            logout logout=new logout(this);
            logout.logOut(Backendless.UserService.CurrentUser());
        }
        return true;
    }
}
