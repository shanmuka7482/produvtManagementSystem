package com.example.productmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductViewActivity extends AppCompatActivity {
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private Button cartButton;
    private TextView cartCountTextView;
    private static List<CartItem> cartItems = new ArrayList<>(); // Make cartItems static to persist across activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        productRecyclerView = findViewById(R.id.productRecyclerView);
        cartButton = findViewById(R.id.cartButton);
        cartCountTextView = findViewById(R.id.cartCountTextView);

        // Initialize product list
        productList = new ArrayList<>();
        productList.add(new Product("Product 1", 10.99, "Description 1"));
        productList.add(new Product("Product 2", 20.99, "Description 2"));
        productList.add(new Product("Product 3", 30.99, "Description 3"));

        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // TODO: Implement product details view
            }

            @Override
            public void onAddToCart(Product product) {
                addToCart(product);
                updateCartCount();
                Toast.makeText(ProductViewActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerView.setAdapter(productAdapter);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductViewActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        updateCartCount();
    }

    private void addToCart(Product product) {
        // Check if product already exists in cart
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        // If product not in cart, add new item
        cartItems.add(new CartItem(product, 1));
    }

    private void updateCartCount() {
        int totalItems = 0;
        for (CartItem item : cartItems) {
            totalItems += item.getQuantity();
        }
        cartCountTextView.setText(String.valueOf(totalItems));
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void clearCart() {
        cartItems.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Intent intent = new Intent(this, GetStartedActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}