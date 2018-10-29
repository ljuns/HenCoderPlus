package com.hencoder.a09_bitmap_drawable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.hencoder.a09_bitmap_drawable.ljuns.CustomEditText;

public class MainActivity extends AppCompatActivity {
    CustomEditText materialEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialEditText = findViewById(R.id.editText);
    }
}
