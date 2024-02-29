package com.example.bluetoothapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluetoothapp.Adapter.Blutooth_Paired_Adapter;
import com.example.itemClick_Interface;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class Paired_Devices_Activity extends AppCompatActivity implements itemClick_Interface {
    private BluetoothAdapter bluetoothAdapter;

    public ArrayList<BluetoothDevice> pairedDeviceslist;

    RecyclerView recyclerView;
    TextView empty;
    ImageView back;
    Blutooth_Paired_Adapter bluetooth_adapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_paired_devices);

        context = Paired_Devices_Activity.this;

        recyclerView = findViewById(R.id.recy);
        empty = findViewById(R.id.empty);
        back = findViewById(R.id.back);
        list();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
    }

    void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void list() {
        pairedDeviceslist = new ArrayList<>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            pairedDeviceslist.add(device);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        bluetooth_adapter = new Blutooth_Paired_Adapter(context,pairedDeviceslist, Paired_Devices_Activity.this);
        recyclerView.setAdapter(bluetooth_adapter);
    }


    @Override
    public void onDelete(int position) {

        unpairDevice(pairedDeviceslist.get(position));
        pairedDeviceslist.remove(position);

        bluetooth_adapter.notifyDataSetChanged();
    }
}