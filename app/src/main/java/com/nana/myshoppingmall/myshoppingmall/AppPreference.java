package com.nana.myshoppingmall.myshoppingmall;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 20/07/2016.
 */
public class AppPreference {

    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String PREF_NAME = "MyShoppingAppPrefs";
    private String KEY_USERNAME = "USERNAME";
    private String KEY_USERID = "USERID";

    public String getUserID() {
        return sharedPref.getString(KEY_USERID,"");
    }

    public void setUserID(String userID) {
        editor.putString(KEY_USERID, userID);
        editor.commit();
    }

    public String getUsername() {
        return sharedPref.getString(KEY_USERNAME,"");
    }

    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public AppPreference(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE); //MODE_PRIVATE hanya bisa diakses aplikasi ini saja
        editor = sharedPref.edit();
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }

}
