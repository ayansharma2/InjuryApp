package com.example.injury_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Service_Running extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__running);
        sharedPreferences= getSharedPreferences("MySharedPreferences",MODE_PRIVATE);
    }

    public void stop_service(View view) {
        sharedPreferences.edit().clear().commit();
        stopService(new Intent(this,Background_Service.class));
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}