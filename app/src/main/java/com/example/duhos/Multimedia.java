package com.example.duhos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hitomi.cmlibrary.CircleMenu;

public class Multimedia extends AppCompatActivity {

    CircleMenu circleMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
        getSupportActionBar().hide();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Multimedia.this,MainActivity.class);
        startActivity(intent);//put your code here
    }
}
