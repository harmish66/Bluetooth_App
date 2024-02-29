package com.example.bluetoothapp;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.BLUETOOTH_ADVERTISE;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_BLUETOOTH_PERMISSION_NEW = 100;
    private static final int REQUEST_BLUETOOTH_PERMISSION_FOR_PAIRED = 101;


    String[] NEW_PERMISSION = new String[]{BLUETOOTH, BLUETOOTH_ADMIN, BLUETOOTH_SCAN, ACCESS_FINE_LOCATION, BLUETOOTH_ADVERTISE, BLUETOOTH_CONNECT};
    String[] OLD_PERMISSION = new String[]{BLUETOOTH, BLUETOOTH_ADMIN, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};


    ConstraintLayout new_list, paired_list;
    Context context;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        new_list = findViewById(R.id.new_list);
        paired_list = findViewById(R.id.paired_list);

        back= findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });


        paired_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissionsPaired();

            }
        });

        new_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissionsNew();

            }
        });
    }


    private boolean checkPermission() {
        // in this method we are checking if the permissions are granted or not and returning the result.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            for (String s : NEW_PERMISSION) {
                if (ContextCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } else {
            for (String s : OLD_PERMISSION) {
                if (ContextCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void requestPermissionsPaired() {
        if (checkPermission()) {
            Intent i = new Intent(getApplicationContext(), Paired_Devices_Activity.class);
            startActivity(i);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissionBluetooth(NEW_PERMISSION, REQUEST_BLUETOOTH_PERMISSION_FOR_PAIRED);
                //requestPermissionBluetoothNewDevice(NEW_PERMISSION);
            } else {
                requestPermissionBluetooth(OLD_PERMISSION, REQUEST_BLUETOOTH_PERMISSION_FOR_PAIRED);
                //requestPermissionBluetoothNewDevice(OLD_PERMISSION);
            }
        }
    }

    private void requestPermissionsNew() {
        if (checkPermission()) {
            Intent i = new Intent(getApplicationContext(), New_Devices_Activity.class);
            startActivity(i);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissionBluetooth(NEW_PERMISSION, REQUEST_BLUETOOTH_PERMISSION_NEW);
            } else {
                requestPermissionBluetooth(OLD_PERMISSION, REQUEST_BLUETOOTH_PERMISSION_NEW);
            }
        }
    }

    private void requestPermissionBluetooth(String[] PERMISSIONS, int requestCode) {
        ActivityCompat.requestPermissions(this, PERMISSIONS, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("TAG", "onRequestPermissionsResult: "+requestCode);


        switch (requestCode) {
            case REQUEST_BLUETOOTH_PERMISSION_NEW:
                if (grantResults.length > 0 && checkPermission()) {
                    Intent i = new Intent(getApplicationContext(), New_Devices_Activity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Permissions denied, Permissions are required to use the app..", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_BLUETOOTH_PERMISSION_FOR_PAIRED:
                if (grantResults.length > 0 && checkPermission()) {
                    Intent i = new Intent(getApplicationContext(), Paired_Devices_Activity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Permissions denied, Permissions are required to use the app..", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}