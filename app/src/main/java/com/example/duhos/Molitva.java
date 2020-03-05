/*
package com.example.duhos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hitomi.cmlibrary.CircleMenu;

public class Molitva extends AppCompatActivity {

    CircleMenu circleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_molitva);
        getSupportActionBar().hide();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Molitva.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
*/

package com.example.duhos;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;


public class Molitva extends AppCompatActivity {
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_molitva);
        myWebView = (WebView)findViewById(R.id.WebView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://bozanskicasoslov.wordpress.com/");
        myWebView.setWebViewClient(new WebViewClient());

        myWebView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        WebView molitvaLayout=findViewById(R.id.WebView);

        molitvaLayout.setOnTouchListener(new OnSwipeTouchListener(Molitva.this) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                Intent intent = new Intent(Molitva.this, CalendarClass.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            public void onSwipeLeft() {
                Intent intent = new Intent(Molitva.this, Multimedia.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            public void onSwipeBottom() {
            }

        });

    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()){
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void goMolitva(View view) {
        Intent intent = new Intent(Molitva.this, Molitva.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goKalendar(View view) {
        Intent intent = new Intent(Molitva.this, CalendarClass.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goPitanja(View view) {
        Intent intent = new Intent(Molitva.this, Questions.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goMultimedija(View view) {
        Intent intent = new Intent(Molitva.this, Multimedia.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goPjesmarica(View view) {
        Intent intent = new Intent(Molitva.this, Songs.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}