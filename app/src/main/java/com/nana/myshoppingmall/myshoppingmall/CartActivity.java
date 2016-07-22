package com.nana.myshoppingmall.myshoppingmall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nana.myshoppingmall.myshoppingmall.db.CartHelper;
import com.nana.myshoppingmall.myshoppingmall.db.CartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.lv_cart)
    ListView lvCart;
    @BindView(R.id.tv_total_qty)
    TextView tvTotalQty;
    @BindView(R.id.tv_total_pay)
    TextView tvTotalPay;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.ln_cartsummary)
    LinearLayout lnCartsummary;

    private ArrayList<CartItem> list;
    private CartHelper cartHelper;
    private CartAdapter adapter;

    //private TextView tvTotalQty, tvTotalPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartHelper = new CartHelper(CartActivity.this);
        list = cartHelper.getAll();
        lnCartsummary.setVisibility(View.GONE);

        adapter = new CartAdapter(CartActivity.this);
        if (list != null) {
            if (list.size() > 0) {
                lnCartsummary.setVisibility(View.VISIBLE);
                adapter.setList(list);
            } else {
                adapter.setList(new ArrayList<CartItem>());
                Toast.makeText(CartActivity.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            adapter.setList(new ArrayList<CartItem>());
            Toast.makeText(CartActivity.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
        }
        lvCart.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private void showTotalTransactionInfo(){
        lnCartsummary.setVisibility(View.GONE);
        ArrayList<CartItem> list = cartHelper.getAll();

        if(list != null)
            if(list.size() > 0) {
                lnCartsummary.setVisibility(View.VISIBLE);
                int totalQty = 0;
                int totalPay = 0;

                for (CartItem item : list) {
                    totalQty += item.getQty();
                    totalPay += (item.getQty() * item.getPrice());

                    tvTotalQty.setText("Quantity : " + totalQty);
                    tvTotalPay.setText("Pay : " + totalPay);

                }
            }
    }

    @Subscribe
    public void onEvent(RefreshCartEvent event)
    {
        showTotalTransactionInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
