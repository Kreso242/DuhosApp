package com.example.duhos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class DialogClass extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String tag=getTag();
        String datum=tag.split("&")[0];
        String naziv=tag.split("&")[1];
        String mjesto=tag.split("&")[2];
        String vrijeme=tag.split("&")[3];

        int calendarUnicode = 0x1F4C5;
        int locationUnicode = 0x1F4CD;
        int timeUnicode = 0x1F552;
        int handUnicode = 0x1F44B;
        String calendarEmoji=new String(Character.toChars(calendarUnicode));
        String locationEmoji=new String(Character.toChars(locationUnicode));
        String timeEmoji=new String(Character.toChars(timeUnicode));
        String handEmoji=new String(Character.toChars(handUnicode));

        builder.setTitle(naziv);
        builder.setIcon(R.mipmap.duhos);
        builder.setMessage(calendarEmoji+" Datum: "+datum+"\n"+locationEmoji+" Lokacija: "+mjesto+"\n"+timeEmoji+" Vrijeme: "+vrijeme+"h \n"+handEmoji+" Vidimo se!")
                .setPositiveButton("Uredu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}