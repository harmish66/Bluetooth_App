package com.example.bluetoothapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluetoothapp.Adapter.Bluetooth_New_Adapter;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class New_Devices_Activity extends AppCompatActivity implements pair_Unpair_Interface {
    private BluetoothAdapter bluetoothAdapter;
    public ArrayList<BluetoothDevice> newDeviceslist = new ArrayList<>();
    RecyclerView recyclerView;
    TextView empty;
    ImageView back;
    IntentFilter filter, pairfilter;
    Bluetooth_New_Adapter blutooth_adapter;
    ProgressBar progressBar;
    Context context;
    BluetoothDevice device;
    Bluetooth_New_Adapter.MyViewHolder holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_devices);

        context = New_Devices_Activity.this;

        recyclerView = findViewById(R.id.recy);
        empty = findViewById(R.id.empty);
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progress);

        pairfilter = new IntentFilter();
        pairfilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);


        registerReceiver(onClivkReceiver, pairfilter);


        list();
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        blutooth_adapter = new Bluetooth_New_Adapter(context, newDeviceslist, New_Devices_Activity.this);
        recyclerView.setAdapter(blutooth_adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                bluetoothAdapter.cancelDiscovery();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bluetoothAdapter.cancelDiscovery();
    }

    public void list() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.startDiscovery();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
        bluetoothAdapter.cancelDiscovery();

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!newDeviceslist.contains(device)) {
                    newDeviceslist.add(device);
                }
                blutooth_adapter.notifyDataSetChanged();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                progressBar.setVisibility(View.GONE);
            }
        }
    };


    private void pairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("createBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Log.e("pairDevice", e.getMessage());
        }
    }

    void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnClick(Bluetooth_New_Adapter.MyViewHolder holder, BluetoothDevice device) {
        this.holder = holder;
        this.device = device;
        Set<BluetoothDevice> deviceArrayList = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (deviceArrayList.contains(device)) {
            unpairDevice(device);
        } else {
            pairDevice(device);
        }

    }

    private final BroadcastReceiver onClivkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device1 = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device1.getBondState() == BluetoothDevice.BOND_BONDED) {
                    if (New_Devices_Activity.this.holder != null && New_Devices_Activity.this.holder.pair != null) {
                        New_Devices_Activity.this.holder.pair.setText("Unpair");
                    }
                } else if (device1.getBondState() == BluetoothDevice.BOND_BONDING) {

                } else if (device1.getBondState() == BluetoothDevice.BOND_NONE) {
                    if (New_Devices_Activity.this.holder != null && New_Devices_Activity.this.holder.pair != null) {
                        New_Devices_Activity.this.holder.pair.setText("Pair");
                    }
                }
            }
        }
    };
}