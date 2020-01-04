package com.example.duhos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Alarm extends AppCompatActivity {

    Button alarmButton;
    TimePicker timePicker;
    int sati,minute;
    Realm realm;
    List<DatesSelected>resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getSupportActionBar().hide();
        DatesSelected date = new DatesSelected();
        ArrayList<DatesSelected> list=new ArrayList<DatesSelected>();
        realm=Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            resultList=realm1.where(DatesSelected.class).findAll();
            list.addAll(resultList);
        });

        String datum=getIntent().getStringExtra("datum");
        List<String>datumi=getIntent().getStringArrayListExtra("lista");

        alarmButton=findViewById(R.id.idAlarmButton);
        timePicker=findViewById(R.id.idTimePicker);

        int flag=0;
        for(int i=0;i<datumi.size();i++){
            if(datumi.get(i).equals(datum))
                flag=1;
        }

        if(flag==0) {
            alarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sati = timePicker.getHour();
                    minute = timePicker.getMinute();
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
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

        if(flag==1){
            alarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!realm.isEmpty()){
                        RealmResults<DatesSelected> dates = realm
                                .where(DatesSelected.class)
                                .findAll();

                        DatesSelected userdatabase = dates
                                .where()
                                .equalTo("datum",datum)
                                .findFirst();

                        if(userdatabase!=null) {

                            if (!realm.isInTransaction()) {
                                realm.beginTransaction();
                            }

                            userdatabase.deleteFromRealm();
                            resultList=dates;
                            realm.commitTransaction();
                        }

                    }

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
