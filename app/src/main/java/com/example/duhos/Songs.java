package com.example.duhos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hitomi.cmlibrary.CircleMenu;
import com.squareup.timessquare.CalendarPickerView;

public class Songs extends AppCompatActivity {

    CircleMenu circleMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        getSupportActionBar().hide();


        TextView pjesmaricaLayout=findViewById(R.id.pjesmarica_layout);

        pjesmaricaLayout.setOnTouchListener(new OnSwipeTouchListener(Songs.this) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                Intent intent = new Intent(Songs.this, Questions.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            public void onSwipeLeft() {
                Intent intent = new Intent(Songs.this, CalendarClass.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            public void onSwipeBottom() {
            }

        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Songs.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public void goMolitva(View view) {
        Intent intent = new Intent(Songs.this, Molitva.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goKalendar(View view) {
        Intent intent = new Intent(Songs.this, CalendarClass.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goPitanja(View view) {
        Intent intent = new Intent(Songs.this, Questions.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goMultimedija(View view) {
        Intent intent = new Intent(Songs.this, Multimedia.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goPjesmarica(View view) {
        Intent intent = new Intent(Songs.this, Songs.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
