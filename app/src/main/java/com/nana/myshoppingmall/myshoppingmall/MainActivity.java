package com.nana.myshoppingmall.myshoppingmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvKategori;
    private String[] kategori = new String[]{"Animal", "Chara", "Heroes"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("HOME");

        lvKategori = (ListView) findViewById(R.id.lv_kategori);
        lvKategori.setOnItemClickListener(this);
        ArrayAdapter<String> adapterKategori = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, kategori);
        lvKategori.setAdapter(adapterKategori);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "Item clicked : " + kategori[position], Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra("KATEGORI", kategori[position]);
        startActivity(intent);
    }
}
