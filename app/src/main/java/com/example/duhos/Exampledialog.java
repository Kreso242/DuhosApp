package com.example.duhos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class Exampledialog extends AppCompatDialogFragment{

    private TextView textViewDatum;
    private TextView textViewMjesto;
    private TextView textViewVrijeme;
    private TextView textViewVidimoSe;
    private TextView textViewPodsjetiMe;
    private TextView textViewSati;
    private Switch aSwitch;
    int j=-1;
    Boolean checked=false;
    Realm realm;
    ArrayList<String>datumi=new ArrayList<String>();
    ArrayList<Integer>sati=new ArrayList<Integer>();
    ArrayList<Integer>minute=new ArrayList<Integer>();
    List<DatesSelected>resultList;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_layout,null);
        aSwitch=view.findViewById(R.id.idSwitch);
        checked=false;

        ArrayList<DatesSelected>list=new ArrayList<DatesSelected>();
        realm=Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
           resultList=realm1.where(DatesSelected.class).findAll();
            list.addAll(resultList);
        });

        if(!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                datumi.add(list.get(i).datum);
                sati.add(list.get(i).sati);
                minute.add(list.get(i).minute);
            }
        }

        String tag=getTag();
        String datum=tag.split("§")[0];
        String naziv=tag.split("§")[1];
        String mjesto=tag.split("§")[2];
        String vrijeme=tag.split("§")[3];


        for(int i=0;i<list.size();i++){
            if(datumi.get(i).equals(datum)) {
                checked = true;
                j=i;
            }
        }
        if(checked==true)
            aSwitch.setChecked(true);

        int calendarUnicode = 0x1F4C5;
        int locationUnicode = 0x1F4CD;
        int timeUnicode = 0x1F552;
        int handUnicode = 0x1F44B;
        int alarmUnicode = 0x23F0;
        int starUnicode = 0x2B50;

        String calendarEmoji=new String(Character.toChars(calendarUnicode));
        String locationEmoji=new String(Character.toChars(locationUnicode));
        String timeEmoji=new String(Character.toChars(timeUnicode));
        String handEmoji=new String(Character.toChars(handUnicode));
        String alarmEmoji=new String(Character.toChars(alarmUnicode));
        String starEmoji=new String(Character.toChars(starUnicode));

        textViewDatum=view.findViewById(R.id.idTexDatum);
        textViewMjesto=view.findViewById(R.id.idTextLokacija);
        textViewVrijeme=view.findViewById(R.id.idTextVrijeme);
        textViewVidimoSe=view.findViewById(R.id.idTextVidimoSe);
        textViewPodsjetiMe=view.findViewById(R.id.idTextPodsjetime);
        textViewSati=view.findViewById(R.id.idSat);

        textViewDatum.setText(calendarEmoji+" Datum: "+datum);
        textViewMjesto.setText(locationEmoji+" Lokacija: "+mjesto);
        textViewVrijeme.setText(timeEmoji+" Vrijeme: "+vrijeme+"h");
        textViewVidimoSe.setText(handEmoji+" Vidimo se!\n");
        textViewPodsjetiMe.setText(alarmEmoji+" Podsjeti me: ");


        if(!list.isEmpty()) {
            if(j!=-1) {
                boolean isDoubleDigitSati = (sati.get(j) > 9 && sati.get(j) < 100);
                boolean isDoubleDigitMinute = (minute.get(j) > 9 && minute.get(j) < 100);

                if (isDoubleDigitSati && isDoubleDigitMinute)
                    textViewSati.setText(starEmoji + " Obavijest dolazi istoga dana u: " + sati.get(j) + ":" + minute.get(j) + "h");
                if (isDoubleDigitSati && !isDoubleDigitMinute)
                    textViewSati.setText(starEmoji + " Obavijest dolazi istoga dana u: " + sati.get(j) + ":0" + minute.get(j) + "h");
                if (!isDoubleDigitSati && isDoubleDigitMinute)
                    textViewSati.setText(starEmoji + " Obavijest dolazi istoga dana u: 0" + sati.get(j) + ":" + minute.get(j) + "h");
                if (!isDoubleDigitSati && !isDoubleDigitMinute)
                    textViewSati.setText(starEmoji + " Obavijest dolazi istoga dana u: 0" + sati.get(j) + ":0" + minute.get(j) + "h");
            }

            if(j==-1) {
                textViewSati.setText("");
            }
        }


        builder.setView(view)
                .setView(view).setTitle(naziv)
                .setIcon(R.mipmap.duhos)
                .setNegativeButton("Izlaz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Uredu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(aSwitch.isChecked()){
                                Intent intent = new Intent(getContext(), Alarm.class);
                                intent.putExtra("datum",datum);
                                intent.putExtra("naziv",naziv);
                                intent.putExtra("vrijeme",vrijeme);
                                intent.putExtra("lista",datumi);
                                 intent.putExtra("lokacija",mjesto);
                                getContext().startActivity(intent);
                        }
                        if(!aSwitch.isChecked()){
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
                        }

                    }
                });

        return builder.create();

    }


}
