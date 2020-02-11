package com.example.duhos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitomi.cmlibrary.CircleMenu;

import java.util.ArrayList;

public class Questions extends AppCompatActivity {

    private ArrayList<String> pitanja=new ArrayList<String>();
    private ArrayList<String> odgovori=new ArrayList<String>();
    private int count=0,br=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        getSupportActionBar().hide();

        postaviPitanja();
    }

    private void postaviPitanja(){
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance(); //instanciranje FireBase baze podataka
        DatabaseReference mDbRef; //referenca
        mDbRef = mDatabase.getReference("Pitanja"); //fokusiraj se na dogadjaje u bazi

        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String podatak = dataSnapshot.getValue().toString();
                podatak = podatak.replace("[null,", "");
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
                for (int i = 0; i < podatak.length(); i++) { //prolazi podatkom dogadjaja i prebroj znakove '§'
                    if (podatak.charAt(i) == '§') {
                        count++;
                    }
                }

                for (int i = 1; i < count; i = i + 2) {
                    odgovori.add(podatak.split("§")[i]); //iza svakog četvrtog znaka '§' se nalazi datum
                    br++;
                }

                for (int i = 2; i < count; i = i + 2) {
                    pitanja.add(podatak.split("§")[i]); //iza svakog četvrtog znaka '§' se nalazi vrijeme
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void initRecyclerView(){
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(pitanja,odgovori,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Questions.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
