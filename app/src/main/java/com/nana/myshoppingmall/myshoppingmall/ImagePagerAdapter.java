package com.nana.myshoppingmall.myshoppingmall;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by user on 20/07/2016.
 */
public class ImagePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> listArr;

    public ArrayList<String> getListArr() {
        return listArr;
    }

    public void setListArr(ArrayList<String> listArr) {
        this.listArr = listArr;
    }



    public ImagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ImageFragment iFrag = new ImageFragment();
        iFrag.setImageUri(getListArr().get(position));

        return iFrag;
    }

    @Override
    public int getCount() {
        return getListArr().size();
    }
}
