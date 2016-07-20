package com.nana.myshoppingmall.myshoppingmall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailProductActivity extends AppCompatActivity {

    private TextView tvName, tvPrice;
    private Button btnCart;
    private ImageView imgDetail;
    private Spinner spnUkuran;
    private TextView tvDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        imgDetail = (ImageView) findViewById(R.id.img_detil);
        btnCart = (Button) findViewById(R.id.btn_addcart);
        spnUkuran = (Spinner) findViewById(R.id.spn_ukuran);
        tvDesc = (TextView) findViewById(R.id.tv_desc);

        getSupportActionBar().setTitle("Product Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Product product = getIntent().getParcelableExtra("PRODUCT");
        tvName.setText(product.getName());
        tvPrice.setText(product.getPrice());
        Glide.with(DetailProductActivity.this).load(product.getImageUrl()).into(imgDetail);

        String[] ukuran = new String[]{"Choose Size", "Kecil", "Medium", "Besar"};

        ArrayAdapter<String> adapterUkuran = new ArrayAdapter<String>(DetailProductActivity.this,android.R.layout.simple_dropdown_item_1line, android.R.id.text1,ukuran);
        spnUkuran.setAdapter(adapterUkuran);
        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

        tvDesc.setText(desc);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
