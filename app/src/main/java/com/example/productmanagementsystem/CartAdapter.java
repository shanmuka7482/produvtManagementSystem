package com.example.productmanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onQuantityChanged(CartItem item, int newQuantity);
    }

    public CartAdapter(List<CartItem> cartItems, OnItemClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
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
        CartItem cartItem = cartItems.get(position);
        Product product = cartItem.getProduct();
        int quantity = cartItem.getQuantity();

        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.format("$%.2f", product.getPrice()));
        holder.quantityTextView.setText(String.valueOf(quantity));

        holder.increaseButton.setOnClickListener(v -> {
            int newQuantity = quantity + 1;
            listener.onQuantityChanged(cartItem, newQuantity);
        });

        holder.decreaseButton.setOnClickListener(v -> {
            int newQuantity = quantity - 1;
            listener.onQuantityChanged(cartItem, newQuantity);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        TextView quantityTextView;
        Button increaseButton;
        Button decreaseButton;

        CartViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.cartItemNameTextView);
            priceTextView = itemView.findViewById(R.id.cartItemPriceTextView);
            quantityTextView = itemView.findViewById(R.id.cartItemQuantityTextView);
            increaseButton = itemView.findViewById(R.id.increaseQuantityButton);
            decreaseButton = itemView.findViewById(R.id.decreaseQuantityButton);
        }
    }
} 