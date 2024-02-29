package com.example.bluetoothapp.Adapter;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothapp.R;
import com.example.bluetoothapp.connect_details_Activity;
import com.example.itemClick_Interface;

import java.lang.reflect.Method;
import java.util.ArrayList;


public class Blutooth_Paired_Adapter extends RecyclerView.Adapter<Blutooth_Paired_Adapter.MyViewHolder> {
    public ArrayList<BluetoothDevice> bluetoothlist;
    private final itemClick_Interface itemClick_interface;
    BluetoothManager bluetoothManager;
    Context context;

    public Blutooth_Paired_Adapter(Context context, ArrayList<BluetoothDevice> Bluetoothlist, itemClick_Interface itemClick_interface) {
        this.bluetoothlist = Bluetoothlist;
        this.itemClick_interface = itemClick_interface;
        this.context = context;
        this.bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_paired, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BluetoothDevice device = bluetoothlist.get(position);

        if (pairDevice(device) && isConnected(device)) {
            holder.connect.setVisibility(View.VISIBLE);

        } else {
            holder.connect.setVisibility(View.GONE);

        }


        holder.name.setText(device.getName());
        holder.address.setText(device.getAddress());
        holder.unpair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick_interface.onDelete(position);
            }
        });

        String checktype = checktype(device);
        int bettery = bettery(device);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, connect_details_Activity.class);
                intent.putExtra("name", device.getName());
                intent.putExtra("address", device.getAddress());
                intent.putExtra("type", checktype);
                intent.putExtra("bettery", bettery);
                context.startActivity(intent);
            }
        });
    }

    int bettery(BluetoothDevice device) {
        int value;

        switch (device.getBluetoothClass().getDeviceClass()) {
            case BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES:
        }
        try {
            java.lang.reflect.Method method;
            method = device.getClass().getMethod("getBatteryLevel");
            value = (int) method.invoke(device);
            return value;
        } catch (Exception ex) {
            ex.getMessage();
            return 0;
        }

    }

    private boolean pairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("createBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    private String checktype(BluetoothDevice device) {

        switch (device.getBluetoothClass().getDeviceClass()) {
            case BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES:
                return "Headphone";
            case BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE:
                return "Handsfree";
            case BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER:
                return "Loudspeaker";
            case BluetoothClass.Device.COMPUTER_DESKTOP:
                return "desktop";
            default:
                return "Unknown!";
        }
    }

    public static boolean isConnected(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("isConnected", (Class[]) null);
            boolean connected = (boolean) m.invoke(device, (Object[]) null);
            return connected;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return bluetoothlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, connect;
        Button unpair;
        CardView detail;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            connect = (TextView) itemView.findViewById(R.id.connect);
            unpair = itemView.findViewById(R.id.un_pair);
            detail = itemView.findViewById(R.id.detail);
        }
    }
}