package com.example.duhos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Questions extends AppCompatActivity {

    private ArrayList<String> pitanja = new ArrayList<String>();
    private ArrayList<String> odgovori = new ArrayList<String>();
    private ArrayList<Boolean> heart = new ArrayList<>();
    private int count = 0, br = 0;
    private DrawerLayout drawerLayout;
    private ImageButton share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        final ImageButton share = (ImageButton) findViewById(R.id.share);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(this.getResources().getDrawable(R.color.siva));

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.siva));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        postaviPitanja();
        initRecyclerView();

//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void postaviPitanja() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance(); //instanciranje FireBase baze podataka
        DatabaseReference mDbRef; //referenca
        mDbRef = mDatabase.getReference("Pitanja"); //fokusiraj se na dogadjaje u bazi

        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String podatak = dataSnapshot.getValue().toString();
                podatak = podatak.replace("[null,", "");
                podatak = podatak.replace("null,", "");
                podatak = podatak.replace("]", "");
                podatak = podatak.replace("{", "§");
                podatak = podatak.replace("}", "§");
                podatak = podatak.replace("=", "");
                podatak = podatak.replace(",", "");
                podatak = podatak.replace("Question", "§");
                podatak = podatak.replace("Answer", "§");
                podatak = podatak.replace("§§", "§");
                podatak = podatak.replace(" §", "§");
                podatak = podatak.replace("§§", "§");
                podatak = podatak.replace("  §", "");
                for (int i = 0; i < podatak.length(); i++) { //prolazi podatkom dogadjaja i prebroj znakove '§'
                    if (podatak.charAt(i) == '§') {
                        count++;
                    }
                }

                for (int i = count - 1; i > 0; i = i - 2) {
                    odgovori.add(podatak.split("§")[i - 1]); //iza svakog četvrtog znaka '§' se nalazi datum
                    br++;
                }

                for (int i = count - 1; i > 1; i = i - 2) {
                    pitanja.add(podatak.split("§")[i]); //iza svakog četvrtog znaka '§' se nalazi vrijeme
                }

                for (int i = 0; i < pitanja.size(); i++) {
                    heart.add(false);//postavi sve srca da budu prazna
                }

                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(pitanja, odgovori, heart, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawerLayout);

        if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
            drawerLayout.closeDrawer(Gravity.RIGHT);
        else {
            Intent intent = new Intent(Questions.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    public void goBack(View view) {
        drawerLayout = findViewById(R.id.drawerLayout);

        if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
            drawerLayout.closeDrawer(Gravity.RIGHT);
        else {
            Intent intent = new Intent(Questions.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }
    }

    public void showMenu(View view) {
        drawerLayout = findViewById(R.id.drawerLayout);

        if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
            drawerLayout.closeDrawer(Gravity.RIGHT);
        else
            drawerLayout.openDrawer(Gravity.RIGHT);

    }

    public void askQuestion(View view) {
        Intent intent = new Intent(Questions.this, AskQuestion.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void shareContent(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "ovdje treba uhvatit pitanje i odgovor!";
        String shareSub = "Pitaj kapelana";
        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(intent.createChooser(intent, "Share using"));
    }

    public void goMolitva(View view) {
        Intent intent = new Intent(Questions.this, Molitva.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goKalendar(View view) {
        Intent intent = new Intent(Questions.this, CalendarClass.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goPitanja(View view) {
        Intent intent = new Intent(Questions.this, Questions.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goMultimedija(View view) {
        Intent intent = new Intent(Questions.this, Multimedia.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goPjesmarica(View view) {
        Intent intent = new Intent(Questions.this, Songs.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}