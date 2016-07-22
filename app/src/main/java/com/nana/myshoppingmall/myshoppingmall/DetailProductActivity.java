package com.nana.myshoppingmall.myshoppingmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nana.myshoppingmall.myshoppingmall.db.CartHelper;
import com.nana.myshoppingmall.myshoppingmall.db.CartItem;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvName, tvPrice;
    private Button btnCart;
    private ImageView imgDetail;
    private Spinner spnUkuran;
    private TextView tvDesc;
    private ImageView imgThumbA, imgThumbB,imgThumbC,imgThumbD;

    private int currentImagePos = 0;
    private CartHelper cartHelper;

    private TextView tvTitle, tvCart;
    private ImageView imgCart;

    private Toolbar toolbar;
    private Product product;

    @Override
    protected void onResume() {
        super.onResume();
        updateCartQty();
    }

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

        imgThumbA=(ImageView) findViewById(R.id.img_thumba);
        imgThumbB=(ImageView) findViewById(R.id.img_thumbb);
        imgThumbC=(ImageView) findViewById(R.id.img_thumbc);
        imgThumbD=(ImageView) findViewById(R.id.img_thumbd);

        imgThumbA.setOnClickListener(this);
        imgThumbB.setOnClickListener(this);
        imgThumbC.setOnClickListener(this);
        imgThumbD.setOnClickListener(this);
        imgDetail.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.tb_cart);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCart = (TextView) findViewById(R.id.tv_cart);
        imgCart = (ImageView) findViewById(R.id.img_cart);

        tvTitle.setText("Product Detail");

        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Product Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        product = getIntent().getParcelableExtra("PRODUCT");
        tvName.setText(product.getName());
        tvPrice.setText(product.getPrice());
        Glide.with(DetailProductActivity.this).load(product.getImageUrl()).into(imgDetail);

        String[] ukuran = new String[]{"Choose Size", "Kecil", "Medium", "Besar"};

        ArrayAdapter<String> adapterUkuran = new ArrayAdapter<String>(DetailProductActivity.this,android.R.layout.simple_dropdown_item_1line, android.R.id.text1,ukuran);
        spnUkuran.setAdapter(adapterUkuran);
        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

        tvDesc.setText(desc);

        Glide.with(DetailProductActivity.this).load(SampleData.charas[0][1]).into(imgThumbA); imgThumbA.setTag(new ImageThumb(SampleData.charas[0][1], 0)); //imgThumbA.setTag(0, "thumbnail"); imgThumbA.setTag(1, SampleData.charas[0][1]); imgThumbA.setTag(2, 0);
        Glide.with(DetailProductActivity.this).load(SampleData.charas[1][1]).into(imgThumbB); imgThumbB.setTag(new ImageThumb(SampleData.charas[1][1], 1));//imgThumbA.setTag(0, "thumbnail"); imgThumbB.setTag(1, SampleData.charas[1][1]); imgThumbB.setTag(2, 1);
        Glide.with(DetailProductActivity.this).load(SampleData.charas[2][1]).into(imgThumbC); imgThumbC.setTag(new ImageThumb(SampleData.charas[2][1], 2));//imgThumbA.setTag(0, "thumbnail"); imgThumbC.setTag(1, SampleData.charas[2][1]); imgThumbC.setTag(2, 2);
        Glide.with(DetailProductActivity.this).load(SampleData.charas[3][1]).into(imgThumbD); imgThumbD.setTag(new ImageThumb(SampleData.charas[3][1], 3));//imgThumbA.setTag(0, "thumbnail"); imgThumbD.setTag(1, SampleData.charas[3][1]); imgThumbD.setTag(2, 3);
        //imgThumbA = setImageThumbnail(imgThumbA, SampleData.charas[0][1]);
        //imgThumbB = setImageThumbnail(imgThumbB, SampleData.charas[1][1]);
        //imgThumbC = setImageThumbnail(imgThumbC, SampleData.charas[2][1]);
        //imgThumbD = setImageThumbnail(imgThumbD, SampleData.charas[3][1]);

        btnCart.setOnClickListener(this);
        cartHelper = new CartHelper(DetailProductActivity.this);



        imgCart.setOnClickListener(this);
        tvCart.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
    }

    protected ImageView setImageThumbnail(ImageView iv, String imgUri){
        Glide.with(DetailProductActivity.this).load(imgUri).into(iv);
        iv.setTag(0, "thumbnail");
        iv.setTag(1, imgUri);
        return iv;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String imgUri = "";

        switch (v.getId())
        {
            case R.id.img_detil:
                ArrayList<String> list = new ArrayList<>();
                for(int i=0; i<SampleData.charas.length; i++)
                    list.add(SampleData.charas[i][1]);

                Intent intent = new Intent(DetailProductActivity.this, DetailImageActivity.class);
                intent.putExtra("url_images", list);
                intent.putExtra("position", currentImagePos);
                startActivity(intent);
                break;
            case R.id.btn_addcart:
                if(cartHelper.isItemAlreadyExist((int) product.getId()))
                    Toast.makeText(DetailProductActivity.this, "This product is already in cart", Toast.LENGTH_SHORT).show();
                else {
                    cartHelper.create((int) product.getId(), product.getName(), product.getImageUrl(), 1, Double.parseDouble(product.getPrice()));
                    Toast.makeText(DetailProductActivity.this, "Added to the cart", Toast.LENGTH_SHORT).show();
                    updateCartQty();
                }
                break;
            case R.id.img_cart:
                intent = new Intent(DetailProductActivity.this, CartActivity.class);
                startActivity(intent);
                break;
            default:
                ImageThumb iThumb = (ImageThumb) ((ImageView) v).getTag();
                imgUri = iThumb.getUri();
                currentImagePos = iThumb.getPos();//(int) v.getTag(2);
                break;
        }

        //switch (v.getId())
        //{
        //    case R.id.img_thumba:
        //        imgUri = (String) ((ImageView) v).getTag();
        //        break;
        //    case R.id.img_thumbb:
        //        imgUri = (String) ((ImageView) v).getTag();
        //        break;
        //}

        if(!imgUri.isEmpty())
            Glide.with(DetailProductActivity.this).load(imgUri).into(imgDetail);
    }

    private void updateCartQty(){
        ArrayList<CartItem> list = cartHelper.getAll();
        tvCart.setVisibility(View.GONE);
        if(list != null)
            if(list.size()>0)
            {
                int qty = list.size();
                tvCart.setVisibility(View.VISIBLE);
                tvCart.setText(String.valueOf(qty));
            }
    }

    class ImageThumb{
        private String uri;
        private int pos;

        public String getUri() {
            return uri;
        }

        public int getPos() {
            return pos;
        }

        public ImageThumb(String uri, int pos) {
            this.uri = uri;
            this.pos = pos;
        }
    }
}
