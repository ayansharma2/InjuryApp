package com.example.injury_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;

public class injured_activity extends AppCompatActivity {
    TextView textView;
    boolean isClicked;
    Button button;
    String phone,message;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView=findViewById(R.id.textView);
        setContentView(R.layout.activity_injured_activity);
        isClicked=false;
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        start_timer();
        sharedPreferences=getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        phone=sharedPreferences.getString("Phone_Number","");
        message=sharedPreferences.getString("Message","");
    }
    public void start_timer()
    {
        new CountDownTimer(60100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("Click Button in "+millisUntilFinished/1000+" To ensure you are ok");
                if(isClicked)
                {
                    textView.setVisibility(View.INVISIBLE);
                    Intent intent=new Intent(getApplicationContext(),Service_Running.class);
                    startActivity(intent);
                    cancel();

                }
            }

            @Override
            public void onFinish() {
                Intent intent=new Intent(getApplicationContext(),Background_Service.class);
                stopService(intent);
                Start_Whatsapp();
                sharedPreferences.edit().clear().commit();
                System.exit(0);
            }
        }.start();
    }

    public void Button_Clicked(View view) {
        isClicked=true;
    }
    public void Start_Whatsapp()
    {
        PackageManager packageManager = this.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone=+91"+ phone+"&text="+message+" Send by Safety App";
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                this.startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}