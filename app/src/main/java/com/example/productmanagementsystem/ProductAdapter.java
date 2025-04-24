package com.example.productmanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
        void onAddToCart(Product product);
    }

    public ProductAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.format("$%.2f", product.getPrice()));
        holder.descriptionTextView.setText(product.getDescription());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(product));
        holder.addToCartButton.setOnClickListener(v -> listener.onAddToCart(product));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        TextView descriptionTextView;
        Button addToCartButton;

        ProductViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            priceTextView = itemView.findViewById(R.id.productPriceTextView);
            descriptionTextView = itemView.findViewById(R.id.productDescriptionTextView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
} 