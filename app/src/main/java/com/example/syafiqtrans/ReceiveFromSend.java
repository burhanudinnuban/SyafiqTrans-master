package com.example.syafiqtrans;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveFromSend extends AppCompatActivity {
    TextView username;
    TextView password;
    TextView email;
    TextView telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_from_send);
        username = findViewById(R.id.tv_username);
        password = findViewById(R.id.tv_password);
        email = findViewById(R.id.tv_email);
        telepon = findViewById(R.id.tv_telepon);

        String username1 = getIntent().getStringExtra("name");
        String password1 = getIntent().getStringExtra("password");
        String email1 = getIntent().getStringExtra("email");
        String telepon1 = getIntent().getStringExtra("telepon");

        username.setText(""+username1);
        password.setText(""+password1);
        email.setText(""+email1);
        telepon.setText(""+telepon1);




    }
}
