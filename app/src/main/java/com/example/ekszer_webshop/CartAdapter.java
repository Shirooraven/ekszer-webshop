package com.example.ekszer_webshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartList;

    public CartAdapter(List<CartItem> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartList.get(position);

        holder.textName.setText(item.getName());
        holder.textCategory.setText(item.getCategory());
        holder.textQuantity.setText("Mennyiség: " + item.getQuantity());

        if (item.isSale()) {
            long discounted = (long)(item.getPrice() * 0.85);
            holder.textPrice.setText("Akciós ár: " + discounted + " Ft");
            holder.textSale.setVisibility(View.VISIBLE);
        } else {
            holder.textPrice.setText("Ár: " + item.getPrice() + " Ft");
            holder.textSale.setVisibility(View.GONE);
        }

        Glide.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textCategory, textPrice, textQuantity, textSale;
        ImageView imageView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textCartName);
            textCategory = itemView.findViewById(R.id.textCartCategory);
            textPrice = itemView.findViewById(R.id.textCartPrice);
            textQuantity = itemView.findViewById(R.id.textCartQuantity);
            textSale = itemView.findViewById(R.id.textCartSale); // új!
            imageView = itemView.findViewById(R.id.imageCartProduct);
        }
    }
}
