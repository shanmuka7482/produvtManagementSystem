package com.example.productmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private Button submitButton;
    private String authMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        submitButton = findViewById(R.id.submitButton);

        authMode = getIntent().getStringExtra("auth_mode");
        
        if (authMode.equals("login")) {
            nameEditText.setVisibility(View.GONE);
            submitButton.setText(R.string.login);
        } else {
            nameEditText.setVisibility(View.VISIBLE);
            submitButton.setText(R.string.register);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                
                if (authMode.equals("register")) {
                    String name = nameEditText.getText().toString();
                    if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(AuthActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // TODO: Implement registration logic
                    Toast.makeText(AuthActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                } else {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(AuthActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // TODO: Implement login logic
                    Toast.makeText(AuthActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                }
                
                Intent intent = new Intent(AuthActivity.this, ProductViewActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
} 