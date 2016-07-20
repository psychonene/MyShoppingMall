package com.nana.myshoppingmall.myshoppingmall;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashscreenActivity extends AppCompatActivity {

    private AppPreference appPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getSupportActionBar().hide();
        appPref=new AppPreference(SplashscreenActivity.this);

        new DelayAsync().execute();
    }

    class DelayAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Thread.sleep(3000);
            }catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent=null;
            if(appPref.getUsername().equals("")) intent = new Intent(SplashscreenActivity.this, LoginActivity.class);
            else intent = new Intent(SplashscreenActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
    }
}
