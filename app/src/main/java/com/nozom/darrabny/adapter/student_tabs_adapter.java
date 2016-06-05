package com.nozom.darrabny.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nozom.darrabny.student.Search;
import com.nozom.darrabny.student.Notification;
import com.nozom.darrabny.student.Student_home;

/**
 * Created by abdelgawad on 26/03/16.
 */
public class student_tabs_adapter extends FragmentPagerAdapter {
    public student_tabs_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new Student_home();
        }else if (position==1){
            return new Search();
        }else {
            return new Notification();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
