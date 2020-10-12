package com.example.injury_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText phone;
    EditText message;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = (EditText) findViewById(R.id.editTextPhone2);
        message = (EditText) findViewById(R.id.editTextTextPersonName);
        sharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startService(View view) {
        String phone_number=phone.getText().toString();
        String Message=message.getText().toString();
        if(phone_number.length()==10 && Message.length()!=0)
        {
            Intent intent=new Intent(this,Service_Running.class);
            startActivity(intent);
            startForegroundService(new Intent(this,Background_Service.class));
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("Phone_Number",phone_number);
            editor.putString("Message",Message);
            editor.commit();
        }
        else
        {
            Toast.makeText(this,"Either you number you entered is incorrect or your message field is empty",Toast.LENGTH_LONG).show();
        }
    }
}