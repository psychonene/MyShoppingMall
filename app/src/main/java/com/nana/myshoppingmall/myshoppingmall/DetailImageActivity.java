package com.nana.myshoppingmall.myshoppingmall;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class DetailImageActivity extends AppCompatActivity {

    private ArrayList<String> listArr;
    private ViewPager vpPager;
    private  ImagePagerAdapter adapter;
    private int selectedPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        getSupportActionBar().hide();
        vpPager=(ViewPager) findViewById(R.id.vp_pager);

        listArr = getIntent().getStringArrayListExtra("url_images");
        selectedPos = getIntent().getIntExtra("position",0);

        adapter = new ImagePagerAdapter(getSupportFragmentManager());
        adapter.setListArr(listArr);
        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(selectedPos);
    }
}
