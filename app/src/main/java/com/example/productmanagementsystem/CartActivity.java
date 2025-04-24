package com.example.productmanagementsystem;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private TextView totalTextView;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalTextView = findViewById(R.id.totalTextView);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Get cart items from ProductViewActivity
        cartItems = ProductViewActivity.getCartItems();

        cartAdapter = new CartAdapter(cartItems, new CartAdapter.OnItemClickListener() {
            @Override
            public void onQuantityChanged(CartItem item, int newQuantity) {
                if (newQuantity <= 0) {
                    cartItems.remove(item);
                } else {
                    item.setQuantity(newQuantity);
                }
                updateTotal();
                cartAdapter.notifyDataSetChanged();
            }
        });

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);

        checkoutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Clear the cart
            ProductViewActivity.clearCart();

            // Show thank you message
            Toast.makeText(CartActivity.this, "Thank you for your purchase!", Toast.LENGTH_LONG).show();

            // Go back to product view
            finish();
        });

        updateTotal();
    }

    private void updateTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        totalTextView.setText(String.format("Total: $%.2f", total));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}