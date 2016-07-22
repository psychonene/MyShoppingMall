package com.nana.myshoppingmall.myshoppingmall;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.nana.myshoppingmall.myshoppingmall.db.CartHelper;
import com.nana.myshoppingmall.myshoppingmall.db.CartItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 22/07/2016.
 */
public class CartAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<CartItem> list;
    private CartHelper cartHelper;

    private String[] qtyOptions = new String[]{"1","2","3","4","5"};

    public CartAdapter(Activity activity) {
        this.activity = activity;
        cartHelper = new CartHelper(activity);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<CartItem> getList() {
        return list;
    }

    public void setList(ArrayList<CartItem> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return getList().size();
    }

    @Override
    public Object getItem(int position) {
        return getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return getList().get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_row_cart, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();

        CartItem item = (CartItem) getItem(position);
        holder.tvProdname.setText(item.getName());
        holder.tvProdprice.setText(String.valueOf(item.getPrice()));
        Glide.with(activity).load(item.getImage()).into(holder.imgProduct);
        int subTotal = getSubTotal(item.getQty(), item.getPrice());
        holder.tvProdsubtotal.setText(String.valueOf(subTotal));

        holder.spnQty.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, qtyOptions));
        int selectedQtyPosition = 0;
        for (int i = 0; i < qtyOptions.length; i++) {
            if(Integer.parseInt(qtyOptions[i]) == item.getQty())
            {
                selectedQtyPosition = i;
                break;
            }
        }
        holder.spnQty.setSelection(selectedQtyPosition);

        holder.imgDelete.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                cartHelper.delete(getList().get(position).getId());
                getList().remove(position);
                notifyDataSetChanged();
                Toast.makeText(activity, "Successfully Deleted", Toast.LENGTH_SHORT).show();

                RefreshCartEvent event = new RefreshCartEvent();
                event.setEventMessage("Item Deleted");
                EventBus.getDefault().post(event);
            }
        }));

        holder.spnQty.setOnItemSelectedListener(new CustomOnItemSelectedListener(position, holder.tvProdsubtotal, new CustomOnItemSelectedListener.OnItemSelectedCallback() {
            @Override
            public void onItemSelected(View view, TextView textView, int itemRowPosition, int itemArrayPosition) {
                int selectedQty = Integer.parseInt(qtyOptions[itemArrayPosition]);
                int productQty = getList().get(itemRowPosition).getId();

                if(selectedQty != productQty){
                    cartHelper.update(Integer.parseInt(qtyOptions[itemArrayPosition]), getList().get(itemRowPosition).getId());
                    int updatedSubtotal = getSubTotal(selectedQty, (int) getList().get(itemRowPosition).getPrice());
                    textView.setText("Sub total = " + updatedSubtotal);

                    RefreshCartEvent event = new RefreshCartEvent();
                    event.setEventMessage("Update qty " + selectedQty + " on productId : " + getList().get(itemRowPosition).getId());
                    EventBus.getDefault().post(event);

                }
            }
        }));

        return convertView;
    }

    private int getSubTotal(int subTotalQty, double price)
    {
        return subTotalQty * (int) price;
    }

    static class ViewHolder {
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.tv_prodname)
        TextView tvProdname;
        @BindView(R.id.tv_prodprice)
        TextView tvProdprice;
        @BindView(R.id.spn_qty)
        Spinner spnQty;
        @BindView(R.id.tv_prodsubtotal)
        TextView tvProdsubtotal;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
