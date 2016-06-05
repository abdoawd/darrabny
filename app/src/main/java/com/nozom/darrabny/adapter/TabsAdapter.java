package com.nozom.darrabny.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nozom.darrabny.company.Add_train;
import com.nozom.darrabny.company.Home;


/**
 * Created by abdo on 14/03/16.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        if (position==0){
            return new Home();
        }else {
            return new Add_train();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
