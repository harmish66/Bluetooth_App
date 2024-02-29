package com.example.bluetoothapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluetoothapp.Models.DetailsItems;

import java.util.ArrayList;

public class connect_details_Activity extends AppCompatActivity {


    TextView name, type, address, bettery;
    ImageView back;
    Context context;


    ArrayList<DetailsItems> details_item = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_connect_details);

        context = connect_details_Activity.this;

        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        address = findViewById(R.id.address);
        bettery = findViewById(R.id.bettery);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        name.setText(getIntent().getStringExtra("name"));
        address.setText(getIntent().getStringExtra("address"));
        type.setText(getIntent().getStringExtra("type"));
        bettery.setText("" + getIntent().getIntExtra("bettery", 0));
    }
}