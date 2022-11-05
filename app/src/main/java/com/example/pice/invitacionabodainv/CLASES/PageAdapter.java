package com.example.pice.invitacionabodainv.CLASES;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pice.invitacionabodainv.FRAGMENTS.BebidaFragment;
import com.example.pice.invitacionabodainv.FRAGMENTS.BoletoFragment;
import com.example.pice.invitacionabodainv.FRAGMENTS.FotosFragment;
import com.example.pice.invitacionabodainv.FRAGMENTS.InicioFragment;
import com.example.pice.invitacionabodainv.FRAGMENTS.MesaFragment;
import com.example.pice.invitacionabodainv.FRAGMENTS.MusicaFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new InicioFragment();
            case 1:
                return new BoletoFragment();
            case 2:
                return new MesaFragment();
            case 3:
                return new FotosFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
