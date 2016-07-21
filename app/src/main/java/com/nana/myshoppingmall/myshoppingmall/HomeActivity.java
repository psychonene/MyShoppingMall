package com.nana.myshoppingmall.myshoppingmall;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nana.myshoppingmall.myshoppingmall.api.request.GetAllProductsRequest;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, GetAllProductsRequest.OnGetAllProductRequestListener {
    private ListView lvItem;
    private ProgressBar progressBar;
    private ProductAdapter adapter;
    private GetAllProductsRequest mGetAllProductsRequest;

    private ArrayList<Product> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lvItem = (ListView)findViewById(R.id.lv_item);
        progressBar = (ProgressBar)findViewById(R.id.pb_progress);
        adapter = new ProductAdapter(HomeActivity.this);
        listItem = new ArrayList<>();
        adapter.setListItem(listItem);

        lvItem.setOnItemClickListener(this);
        lvItem.setAdapter(adapter);

        mGetAllProductsRequest = new GetAllProductsRequest();
        mGetAllProductsRequest.setOnGetAllProductRequestListener(this);

        lvItem.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        mGetAllProductsRequest.callApi();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = null;

        int id = item.getItemId();

        if (id == R.id.nav_category) {
            intent = new Intent(HomeActivity.this, CategoryActivity.class);
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {
            showLogoutAlertDialog();
        }

        if (intent != null) startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLogoutAlertDialog(){
        AlertDialog alert = new AlertDialog.Builder(HomeActivity.this).setTitle("Logout").setMessage("Do You want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppPreference appPref = new AppPreference(HomeActivity.this);
                        appPref.clear();

                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();

        alert.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent mIntent = new Intent(HomeActivity.this, DetailProductActivity.class);
        mIntent.putExtra("PRODUCT", listItem.get(position));
        startActivity(mIntent);
    }

    @Override
    public void onGetAllProductSuccess(ArrayList<Product> listProduct) {
        lvItem.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        listItem.addAll(listProduct);
        adapter.setListItem(listItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllProductFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
