package com.example.syafiqtrans;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;


public class Screen_Flash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //menghilangkan ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.screen_flash);

        final Handler handler = new Handler();
        handler.postDelayed(

                new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), login_activity.class));
                finish();
            }
        }, 2000L); //3000 L = 3 detik
    }
}