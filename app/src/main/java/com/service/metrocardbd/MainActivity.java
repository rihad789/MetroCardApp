package com.service.metrocardbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.service.metrocardbd.Metro_Card_Portal.Card_Portal_Layout_Manager;

public class MainActivity extends AppCompatActivity {

    AppCompatButton Register_Complaint_Btn,Metro_Card_Portal_Btn,Fare_And_Route_Btn;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Register_Complaint_Btn=findViewById(R.id.Register_Complaint_Btn);
        Metro_Card_Portal_Btn=findViewById(R.id.Metro_Card_Portal_Btn);
        Fare_And_Route_Btn=findViewById(R.id.Fare_And_Route_Btn);


        setupButtonAction();
    }

    public void setupButtonAction()
    {
        Register_Complaint_Btn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RegisterComplaintActivity.class);
            startActivity(i);
        });

        Metro_Card_Portal_Btn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, Card_Portal_Layout_Manager.class);
            startActivity(i);
        });

        Fare_And_Route_Btn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, Fare_And_Route_Activity.class);
            startActivity(i);

        });
    }
}