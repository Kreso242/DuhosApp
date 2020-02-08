package com.example.duhos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;


public class MainActivity extends AppCompatActivity {

    CircleMenu circleMenu;
    private int delay=800;
    ImageButton duhosButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        duhosButton = (ImageButton) findViewById(R.id.idDuhosButton);
        circleMenu = (CircleMenu) findViewById(R.id.circle_menu);


        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.duhos, R.mipmap.icon_cancel);
        circleMenu.addSubMenu(Color.parseColor("#258CFF"), R.mipmap.prayer)
                .addSubMenu(Color.parseColor("#30A400"), R.mipmap.multimedia)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.calendar)
                .addSubMenu(Color.parseColor("#8A39FF"), R.mipmap.questions)
                .addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.songs);

        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {

                                                 @Override
                                                 public void onMenuSelected(int index) {
                                                     Handler h =new Handler() ;
                                                     switch (index) {
                                                         case 0:
                                                             h.postDelayed(new Runnable() {
                                                                 public void run() {
                                                                     Intent intent = new Intent(MainActivity.this,Molitva.class);
                                                                     startActivity(intent);
                                                                     overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                                 }

                                                             }, delay);

                                                             break;
                                                         case 1:
                                                             h.postDelayed(new Runnable() {
                                                                 public void run() {
                                                                     Intent intent = new Intent(MainActivity.this,Multimedia.class);
                                                                     startActivity(intent);
                                                                     overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                                                 }

                                                             }, delay);
                                                             break;
                                                         case 2:
                                                             h.postDelayed(new Runnable() {
                                                                 public void run() {
                                                                     Intent intent = new Intent(MainActivity.this, CalendarClass.class);
                                                                     startActivity(intent);
                                                                     overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                                                 }

                                                             }, delay);
                                                             break;
                                                         case 3:
                                                             h.postDelayed(new Runnable() {
                                                                 public void run() {
                                                                     Intent intent = new Intent(MainActivity.this,Questions.class);
                                                                     startActivity(intent);
                                                                     overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                                                 }

                                                             }, delay);
                                                             break;
                                                         case 4:
                                                             h.postDelayed(new Runnable() {
                                                                 public void run() {
                                                                     Intent intent = new Intent(MainActivity.this,Songs.class);
                                                                     startActivity(intent);
                                                                     overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                                                 }

                                                             }, delay);
                                                             break;
                                                     }
                                                 }

                                             }

        );

        duhosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleMenu.isOpened()) {
                    circleMenu.closeMenu();
                    duhosButton.setScaleX((float) 0.5);
                    duhosButton.setScaleY((float) 0.5);
                }
                else {
                    circleMenu.openMenu();
                    duhosButton.setScaleX((float) 0.45);
                    duhosButton.setScaleY((float) 0.45);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
       if (circleMenu.isOpened())
            circleMenu.closeMenu();
        else
            finish();
    }

}
