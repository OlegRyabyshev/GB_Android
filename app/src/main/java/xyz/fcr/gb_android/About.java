package xyz.fcr.gb_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class About extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button buttonReturn = findViewById(R.id.button_return);

        buttonReturn.setOnClickListener((view) -> {
            this.startActivity(new Intent(About.this, MainActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(About.this, MainActivity.class));
        finish();
    }
}