package com.example.duhos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class Alarm extends AppCompatActivity {

    Button alarmButton;
    TimePicker timePicker;
    int sati,minute; //spremam u to vrijeme sa sata
    Realm realm; //baza podataka
    List<DatesSelected>helpList; //pomocna lista mojih objekata

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm); //prikazi activity za alarm
        getSupportActionBar().hide(); //sakrij ime aplikaicije u activityu

        DatesSelected date = new DatesSelected(); //objekt sa datumom i vremenom
        ArrayList<DatesSelected> list=new ArrayList<DatesSelected>(); //lista  DatesSelected objekata

        realm=Realm.getDefaultInstance(); //inicijalizacije Realm-a
        realm.executeTransaction(realm1 -> {
            helpList=realm1.where(DatesSelected.class).findAll();
            list.addAll(helpList); //puni listu prema podatcima iz baze podataka
        });

        String datum=getIntent().getStringExtra("datum"); //spremi kliknuti datum koji je poslan putem itenta
        List<String>datumi=getIntent().getStringArrayListExtra("lista"); //spremi listu datuma koja je poslana putem itenta

        alarmButton=findViewById(R.id.idAlarmButton);
        timePicker=findViewById(R.id.idTimePicker);

        int flag=0;
        for(int i=0;i<datumi.size();i++){  //ako oznaceni datum postoji u listi datuma postavi zastavu na 1
            if(datumi.get(i).equals(datum))
                flag=1;
        }

        if(flag==0) { //ako nema oznacenog datuma u listi
            alarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //na klik
                    sati = timePicker.getHour(); //spremi sate
                    minute = timePicker.getMinute(); //spremi minute
                    realm = Realm.getDefaultInstance(); //pokreni bazu
                    realm.beginTransaction();
                    date.setDatum(datum); //spremi u objekt datum
                    date.setSati(sati); //spremi u objekt sate
                    date.setMinute(minute); //spremi u objekt minute
                    realm.copyToRealm(date); //spremi objekt u bazu
                    realm.commitTransaction();
                    realm.close();
                    Intent intent = new Intent(Alarm.this, CalendarClass.class); //otvori kalendar
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); //fade_in prijelaz

                }
            });
        }

        if(flag==1){ //ako je oznaceni datum u listi
            alarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!realm.isEmpty()){ //ako baza nije prazna
                        RealmResults<DatesSelected> dates = realm
                                .where(DatesSelected.class)
                                .findAll();

                        DatesSelected userdatabase = dates
                                .where()
                                .equalTo("datum",datum) //nadji gdje se nalazi taj datum sa starim vremenom
                                .findFirst();

                        if(userdatabase!=null) {

                            if (!realm.isInTransaction()) {
                                realm.beginTransaction();
                            }

                            userdatabase.deleteFromRealm(); //izbrisi iz baze
                            helpList=dates;
                            realm.commitTransaction();
                        }

                    }
                    //nakon sto je datum izbrisan dodaj ga ponovno sa novim vremenom
                    sati = timePicker.getHour();
                    minute = timePicker.getMinute();
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    DatesSelected date = new DatesSelected();
                    date.setDatum(datum);
                    date.setSati(sati);
                    date.setMinute(minute);
                    realm.copyToRealm(date);
                    realm.commitTransaction();
                    realm.close();
                    Intent intent = new Intent(Alarm.this, CalendarClass.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
            });
        }


        /*
        int year,month,day,hour,minutes;
        day=Integer.parseInt(datum.split("/")[0]);
        month=Integer.parseInt(datum.split("/")[1]);
        year=Integer.parseInt(datum.split("/")[2]);
        hour=;
        minutes=minute;
        */


    }
}
