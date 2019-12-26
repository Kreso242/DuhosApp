package com.example.duhos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CalendarClass extends AppCompatActivity {

    private static final String TAG ="TAG";
    int count = 0;
    int br=0;

    List<String> Datumi=new ArrayList<String>();
    List<String> Nazivi=new ArrayList<String>();
    List<String> Vremena=new ArrayList<String>();
    List<String> Mjesta=new ArrayList<String>();

    List<Date> dates=new ArrayList<Date>();


                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    getSupportActionBar().hide();

                    setContentView(R.layout.activity_calendar);
                    Calendar nextYear = Calendar.getInstance();
                    nextYear.add(Calendar.YEAR, 1);

                    CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
                    Date today = new Date();


                    Date mydate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String yesterday = dateFormat.format(mydate);
                    //Toast.makeText(getApplication().getApplicationContext(),yesterday.toString(),Toast.LENGTH_SHORT).show();

                    Date mydate2 = new Date();
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                    String today1 = dateFormat2.format(mydate2);

                    calendar.init(today, nextYear.getTime())
                            .withSelectedDate(today);

                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference mDbRef= FirebaseDatabase.getInstance().getReference()
                            .child("Događaji").child(yesterday);
                    mDbRef.removeValue();
                    mDbRef = mDatabase.getReference("Događaji/");

                    mDbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue().toString();

                            value = value.replace("{", "&");
                            value = value.replace("}", "&");
                            value = value.replace("=", "");
                            value = value.replace(",", "");
                            value = value.replace("Vrijeme", "&");
                            value = value.replace("Naziv", "&");
                            value = value.replace("Mjesto", "&");
                            value = value.replace("&&", "&");
                            value = value.replace(" &", "&");
                            value = value.replace("& ", "&");


                            for (int i = 0; i < value.length(); i++) {
                                if (value.charAt(i) == '&') {
                                    count++;
                                }
                            }

                            for (int i = 1; i < count; i = i + 4) {
                                Datumi.add(value.split("&")[i]);
                                br++;
                            }

                            for (int i = 2; i < count; i = i + 4) {
                                Vremena.add(value.split("&")[i]);
                            }

                            for (int i = 3; i < count; i = i + 4) {
                                Mjesta.add(value.split("&")[i]);
                            }

                            for (int i = 4; i < count; i = i + 4) {
                                Nazivi.add(value.split("&")[i]);
                            }

                            for (int i = 0; i < br; i++) {
                                String temp;
                                temp=Datumi.get(i).toString();
                                temp = temp.replace("-", "/");
                                Datumi.remove(i);
                                Datumi.add(i,temp);

                            }

                            for(int i=0;i<br;i++) {
                                Date date1 = new Date();
                                try {
                                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(Datumi.get(i).toString());
                                    System.out.println(date1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                dates.add(date1);
                                calendar.highlightDates(dates);
                            }

                            for(int i=0;i<br;i++){
                                if(Datumi.get(i).equals(today1))
                                    openDialog(Datumi.get(i).toString(),Nazivi.get(i).toString(),Mjesta.get(i).toString(),Vremena.get(i).toString());
                            }

                            calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
                                @Override
                                public void onDateSelected(Date date) {
                                    int flag=0;
                                    if(flag==0) {
                                        for (int i = 0; i < br; i++) {
                                            if (dates.get(i).equals(date)) {
                                                //Toast.makeText(getApplication().getApplicationContext(),Nazivi.get(i).toString(),Toast.LENGTH_SHORT).show();
                                                flag = 1;
                                                openDialog(Datumi.get(i).toString(),Nazivi.get(i).toString(),Mjesta.get(i).toString(),Vremena.get(i).toString());
                                            }
                                        }
                                    }
                                    if(flag==0){
                                        Toast.makeText(getApplication().getApplicationContext(),"Nema događaja",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onDateUnselected(Date date) {

                                }
                            });
                        }

            @Override
            public void onCancelled(DatabaseError error) {
// Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void openDialog(String datum,String naziv,String mjesto,String vrijeme) {
                    DialogClass dialogClass=new DialogClass();
                    dialogClass.show(getSupportFragmentManager(),datum + "&" + naziv + "&" + mjesto + "&" + vrijeme);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CalendarClass.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }



}
