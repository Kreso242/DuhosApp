package com.example.duhos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class AskQuestion extends AppCompatActivity {

    public EditText mEmail;
    public EditText mMessage;
    public ImageButton kapelan1;
    public ImageButton kapelan2;
    public ImageView kapelan1Krug;
    public ImageView kapelan2Krug;
    public String mailKapelan1="kresimirtomic1998@gmail.com";
    public String mailKapelan2="ktomic@etfos.hr";

    public boolean flag1=false;
    public boolean flag2=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar2);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(this.getResources().getDrawable(R.color.siva));

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.siva));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mEmail = (EditText)findViewById(R.id.mailID);
        mMessage = (EditText)findViewById(R.id.messageID);

        kapelan1Krug=(ImageView)findViewById(R.id.zeleniKrugKapelan1);
        kapelan2Krug=(ImageView)findViewById(R.id.zeleniKrugKapelan2);

        kapelan1=(ImageButton)findViewById(R.id.kapelan1Slika);
        kapelan2=(ImageButton)findViewById(R.id.kapelan2Slika);

        mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"E-mail kapelana se odabire pritiskom na njegovu sliku!",Toast.LENGTH_SHORT).show();
            }
        });

        kapelan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1==false) {
                    kapelan1Krug.setVisibility(View.VISIBLE);
                    kapelan2Krug.setVisibility(View.INVISIBLE);
                    flag1=true;
                    flag2=false;
                    mEmail.setText(mailKapelan1);
                }
                else {
                    flag1=false;
                    kapelan1Krug.setVisibility(View.INVISIBLE);
                    mEmail.setText("");
                }
            }
        });
        kapelan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag2==false) {
                    kapelan2Krug.setVisibility(View.VISIBLE);
                    kapelan1Krug.setVisibility(View.INVISIBLE);
                    flag2=true;
                    flag1=false;
                    mEmail.setText(mailKapelan2);
                }
                else {
                    flag2=false;
                    kapelan2Krug.setVisibility(View.INVISIBLE);
                    mEmail.setText("");
                }
            }
        });

        Button buttonPosalji=(Button)findViewById(R.id.posaljiButton);
        buttonPosalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    private void sendMail() {
        if(flag1==false && flag2==false){
            Toast.makeText(getApplicationContext(),"Odaberi kapelana!",Toast.LENGTH_SHORT).show();
        }
        else if(mMessage.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Upiši pitanje!",Toast.LENGTH_SHORT).show();
        }
        else {
            String mail = mEmail.getText().toString();
            String message = mMessage.getText().toString();
            String subject = "Pitanje za kapelana";

            //Send Mail
            JavaMailAPI javaMailAPI = new JavaMailAPI(this, mail, subject, message);
            javaMailAPI.execute();

            Intent intent = new Intent(AskQuestion.this, Questions.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(AskQuestion.this, Questions.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}