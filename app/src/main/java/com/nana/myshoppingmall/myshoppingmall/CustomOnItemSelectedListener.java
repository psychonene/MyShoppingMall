package com.nana.myshoppingmall.myshoppingmall;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * Created by user on 22/07/2016.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private OnItemSelectedCallback onItemSelectedCallback;
    private int pos;
    private TextView tvSubTotal;

    public CustomOnItemSelectedListener(int position, TextView tvSubTotal, OnItemSelectedCallback onItemSelectedCallback) {
        this.pos = position;
        this.tvSubTotal = tvSubTotal;
        this.onItemSelectedCallback = onItemSelectedCallback;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        onItemSelectedCallback.onItemSelected(view, tvSubTotal,pos,position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface OnItemSelectedCallback{
        void onItemSelected(View view, TextView textView, int itemRowPosition, int itemArrayPosition);
    }
}
