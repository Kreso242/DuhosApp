package com.example.duhos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hitomi.cmlibrary.CircleMenu;

public class Multimedia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
        getSupportActionBar().hide();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Multimedia.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public void goMolitva(View view) {
        Intent intent = new Intent(Multimedia.this, Molitva.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goKalendar(View view) {
        Intent intent = new Intent(Multimedia.this, CalendarClass.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goPitanja(View view) {
        Intent intent = new Intent(Multimedia.this, Questions.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goMultimedija(View view) {
        Intent intent = new Intent(Multimedia.this, Multimedia.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goPjesmarica(View view) {
        Intent intent = new Intent(Multimedia.this, Songs.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
