package com.example.bluetoothapp.Adapter;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothapp.R;
import com.example.bluetoothapp.pair_Unpair_Interface;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Bluetooth_New_Adapter extends RecyclerView.Adapter<Bluetooth_New_Adapter.MyViewHolder> {


    public ArrayList<BluetoothDevice> bluetoothlist;
    Context context;
    IntentFilter filter;

    private final pair_Unpair_Interface pair_unpair_interface;


    public Bluetooth_New_Adapter(Context context, ArrayList<BluetoothDevice> allDevices, pair_Unpair_Interface pair_unpair_interface) {
        this.bluetoothlist = allDevices;
        this.context = context;
        this.pair_unpair_interface = pair_unpair_interface;

        filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

    }


    @NonNull
    @Override
    public Bluetooth_New_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_new, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Bluetooth_New_Adapter.MyViewHolder holder, int position) {

        BluetoothDevice item = bluetoothlist.get(position);


        holder.name.setText(item.getName());
        holder.address.setText(item.getAddress());

        holder.pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pair_unpair_interface.OnClick(holder, item);
            }
        });
    }


    @Override
    public int getItemCount() {
        return bluetoothlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, address;
        public Button pair;

        public MyViewHolder(View v) {
            super(v);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            pair = (Button) itemView.findViewById(R.id.pair);
        }
    }


}
