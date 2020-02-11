package com.example.duhos;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class ReminderBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        int handUnicode = 0x1F44B;
        int locationUnicode = 0x1F4CD;
        int alarmUnicode = 0x23F0;
        int prayUnicode =0x1F64F;

        String handEmoji=new String(Character.toChars(handUnicode));
        String locationEmoji=new String(Character.toChars(locationUnicode));
        String alarmEmoji=new String(Character.toChars(alarmUnicode));
        String prayEmoji=new String(Character.toChars(prayUnicode));
        String naziv= intent.getStringExtra("naziv");
        String vrijeme= intent.getStringExtra("vrijeme");
        String lokacija= intent.getStringExtra("lokacija");
        String sve=prayEmoji+" "+naziv + " u "+vrijeme+"h "+"\n"+locationEmoji+" "+lokacija+"\n"+handEmoji+" Vidimo se!";


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notify")
                .setSmallIcon(R.mipmap.duhos)
                .setContentTitle(alarmEmoji+" Duhos obavijest")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(sve))
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200,builder.build());
    }

}
