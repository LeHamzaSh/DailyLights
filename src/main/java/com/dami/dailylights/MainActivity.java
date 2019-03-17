package com.dami.dailylights;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //UserInterference Things
    Button Pair;
    ListView ArrayOfList;

    //BlueTooth Things
    private BluetoothAdapter BluetoothAdap = null;
    private Set<BluetoothDevice> PairedDevice;

    //address of the device
    public static String EXTRA_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pair = (Button)findViewById(R.id.PairButton);
        ArrayOfList = (ListView)findViewById(R.id.DeviceList);


        BluetoothAdap = BluetoothAdapter.getDefaultAdapter();
        if(BluetoothAdap == null){
            //no bt on the device
            Toast.makeText(getApplicationContext(), "Bluetooth Device not available", Toast.LENGTH_LONG).show();
            finish();
        }else{
            if (BluetoothAdap.isEnabled()) {


            }else{
                //ask to enable bt
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            }
        }

        //connect button
        Pair.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });
    }

    private void pairedDevicesList(){

        //creating the devices list
        PairedDevice = BluetoothAdap.getBondedDevices();
        ArrayList list = new ArrayList();

        if(PairedDevice.size() > 0){
            for(BluetoothDevice bt : PairedDevice){
                list.add(bt.getName() + "\n" + bt.getAddress());
            }
        }else{
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices found", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        ArrayOfList.setAdapter(adapter);
        ArrayOfList.setOnItemClickListener(myListClickListener);
    }

    //choosing the device
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView av, View v, int arg2, long arg3){
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            Intent i = new Intent(MainActivity.this, ControlsActivity.class);
            i.putExtra(EXTRA_ADDRESS, address);
            startActivity(i);

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
