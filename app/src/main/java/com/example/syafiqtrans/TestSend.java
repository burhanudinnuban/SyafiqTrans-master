package com.example.syafiqtrans;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TestSend extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText email;
    EditText telepon;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_send);
        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);
        email = findViewById(R.id.input_email);
        telepon = findViewById(R.id.input_telepon);
        create = findViewById(R.id.create_btn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kirim = new Intent(TestSend.this,ReceiveFromSend.class);
                kirim.putExtra("name",username.getText().toString());
                kirim.putExtra("password",password.getText().toString());
                kirim.putExtra("email",email.getText().toString());
                kirim.putExtra("telepon",telepon.getText().toString());
                startActivity(kirim);
            }
        });
    }
}
