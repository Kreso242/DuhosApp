package com.example.duhos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarClass extends AppCompatActivity {

    private static final String TAG ="TAG";
    int count = 0; //brojac znakova '§'
    int br=0; //brojac dogadjaja

    //liste za događaje
    List<String> Datumi=new ArrayList<String>();
    List<String> Nazivi=new ArrayList<String>();
    List<String> Vremena=new ArrayList<String>();
    List<String> Mjesta=new ArrayList<String>();

    List<Date> dates=new ArrayList<Date>(); //lista datuma u obliku datuma

    List<String> exDatesString=new ArrayList<String>(); //lista proslih datuma unazad jednu godinu

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    getSupportActionBar().hide();

                    setContentView(R.layout.activity_calendar);

                    CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
                    Date today = new Date(); //danasnji datum
                    Calendar nextYear = Calendar.getInstance();
                    nextYear.add(Calendar.YEAR, 1); //prikazi kalendar za jednu godinu unaprijed
                    calendar.init(today, nextYear.getTime()) //kalendar od danas do sljedece godine
                            .withSelectedDate(today);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); //format datuma koji trebamo

                    for(int i=1;i<366;i++) { //dodaj 365 vrijednosti u listu
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(new Date());
                        calendar1.add(Calendar.DATE, -i); //po jedan dan unazad dodaj u listu
                        String yesterdayAsString = dateFormat.format(calendar1.getTime());
                        exDatesString.add(yesterdayAsString);
                    }

                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                    String danas = dateFormat2.format(today); //danasnji datum u formatu koji trebamo

                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance(); //instanciranje FireBase baze podataka
                    DatabaseReference mDbRef; //referenca

                    for(int i=0;i<365;i++) { //za 365 dana
                    DatabaseReference mDbRefDogadjaj = FirebaseDatabase.getInstance().getReference("Događaji");
                    DatabaseReference mDbRefDate = mDbRefDogadjaj.child(exDatesString.get(i));//gledaj jel postoji dijete u bazi koje se zove kao datum koji je prosao
                    mDbRefDate.removeValue();                                                 //ako ima izbrisi iz baze
                    }

                    mDbRef = mDatabase.getReference("Događaji/"); //fokusiraj se na dogadjaje u bazi

                    mDbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String podatakDogadjaja = dataSnapshot.getValue().toString(); //svi događaji su predsatvljeni jednim stringom i nekim znakovima

                            //uredi string tako da zamjenimo nepotrebne znakove i lakse procitamo zapis
                            podatakDogadjaja = podatakDogadjaja.replace("{", "§");
                            podatakDogadjaja = podatakDogadjaja.replace("}", "§");
                            podatakDogadjaja = podatakDogadjaja.replace("=", "");
                            podatakDogadjaja = podatakDogadjaja.replace(",", "");
                            podatakDogadjaja = podatakDogadjaja.replace("Vrijeme", "§");
                            podatakDogadjaja = podatakDogadjaja.replace("Naziv", "§");
                            podatakDogadjaja = podatakDogadjaja.replace("Mjesto", "§");
                            podatakDogadjaja = podatakDogadjaja.replace("§§", "§");
                            podatakDogadjaja = podatakDogadjaja.replace(" §", "§");
                            podatakDogadjaja = podatakDogadjaja.replace("§ ", "§");


                            for (int i = 0; i < podatakDogadjaja.length(); i++) { //prolazi podatkom dogadjaja i prebroj znakove '§'
                                if (podatakDogadjaja.charAt(i) == '§') {
                                    count++;
                                }
                            }

                            for (int i = 1; i < count; i = i + 4) {
                                Datumi.add(podatakDogadjaja.split("§")[i]); //iza svakog četvrtog znaka '§' se nalazi datum
                                br++;
                            }

                            for (int i = 2; i < count; i = i + 4) {
                                Vremena.add(podatakDogadjaja.split("§")[i]); //iza svakog četvrtog znaka '§' se nalazi vrijeme
                            }

                            for (int i = 3; i < count; i = i + 4) {
                                Mjesta.add(podatakDogadjaja.split("§")[i]); //iza svakog četvrtog znaka '§' se nalazi mjesto
                            }

                            for (int i = 4; i < count; i = i + 4) {
                                Nazivi.add(podatakDogadjaja.split("§")[i]); //iza svakog četvrtog znaka '§' se nalazi naziv
                            }

                            for (int i = 0; i < br; i++) { //svim datumima u listi promijeni format ('-' -> '/')
                                String temp;
                                temp=Datumi.get(i).toString();
                                temp = temp.replace("-", "/");
                                Datumi.remove(i);
                                Datumi.add(i,temp);

                            }

                            for(int i=0;i<br;i++) {
                                Date noviDatum = new Date();
                                try {
                                    noviDatum = new SimpleDateFormat("dd/MM/yyyy").parse(Datumi.get(i).toString()); //pretvori String u Date
                                    System.out.println(noviDatum);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                dates.add(noviDatum); //napuni listu Date-ova
                                calendar.highlightDates(dates); //oznaci na kalendari sve datume iz liste dates
                            }

                            /*
                            for(int i=0;i<br;i++){
                                if(Datumi.get(i).equals(danas)) //ako je kliknuti datum danas, otvori automatski njegove informacije
                                    openDialog(Datumi.get(i).toString(),Nazivi.get(i).toString(),Mjesta.get(i).toString(),Vremena.get(i).toString());
                            }
                            */

                            calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() { //na kliknuti datum
                                @Override
                                public void onDateSelected(Date datum) {
                                    int flag=0;
                                        for (int i = 0; i < br; i++) {
                                            if (dates.get(i).equals(datum)) {  //ako kliknuti datum postoji u listi koji imaju dogadjaje otvori informacije
                                                flag = 1;
                                                openDialog(Datumi.get(i).toString(),Nazivi.get(i).toString(),Mjesta.get(i).toString(),Vremena.get(i).toString());
                                            }
                                        }

                                    if(flag==0){ //ako kliknuti datum ne postoji u listi koji imaju dogadjaje napisi da nema dogadjaja
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
            // ako se ne uspije procitat iz baze
                Log.w(TAG, "Greška u čitanju iz baze podataka", error.toException());
            }
        });

    }

    //otvori dialog
    private void openDialog(String datum,String naziv,String mjesto,String vrijeme) {
                    Exampledialog exampledialog=new Exampledialog();
                    exampledialog.show(getSupportFragmentManager(),datum + "§" + naziv + "§" + mjesto + "§" + vrijeme+"§");//kao tag stavi informacije kliknutog dogadjaja da se mogu dohvatiti iz dialoga
    }

    @Override
    public void onBackPressed() { //ako se stisne tipka unazad vrati se na MainActivity
        Intent intent = new Intent(CalendarClass.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); //efekt

    }


}
