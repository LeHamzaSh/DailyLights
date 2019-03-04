package com.dami.dailylights;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class ControlsActivity extends AppCompatActivity {

    Switch LightOne;

    //bluetooth address
    String address = null;

    //dialog when connecting
    private ProgressDialog progress;

    //bluetooth stuff
    BluetoothAdapter BluetoothAdap = null;
    BluetoothSocket BluetoothSoc = null;
    private boolean BooleanBT = false;
    static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        //new intent
        Intent newint = getIntent();
        setContentView(R.layout.activity_controls);

        //get address from previous choise
        address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS);

        //start connection
        new ConnectBT().execute();

        LightOne = (Switch) findViewById(R.id.Light1);
        LightOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    sendMsg("1");
                    Toast.makeText(getBaseContext(),"Switch is On",Toast.LENGTH_SHORT).show();
                }
                else{
                    sendMsg("0");
                    Toast.makeText(getBaseContext(),"Switch is Off",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccessfull = true;

        @Override
        protected void onPreExecute()
        {
            //showing progress dialog
            progress = ProgressDialog.show(ControlsActivity.this, "Disassembling your CPU...", "Joking, just connecting...");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (BluetoothSoc == null || !BooleanBT)
                {
                    //connecting
                    BluetoothAdap = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice pairedDevice = BluetoothAdap.getRemoteDevice(address);
                    BluetoothSoc = pairedDevice.createInsecureRfcommSocketToServiceRecord(uuid);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    BluetoothSoc.connect();
                }
            }
            catch (IOException e)
            {
                //error
                ConnectSuccessfull = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccessfull)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                //connection done
                msg("Connected.");
                BooleanBT = true;
            }
            progress.dismiss();
        }
    }

    private void msg(String s){
        //create a toast - shortcut
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private void sendMsg(String msg)
    {
        //send a message to the Arduino
        //when it's time, use this to send the turn on/off message
        if (BluetoothSoc!=null)
        {
            try
            {
                BluetoothSoc.getOutputStream().write(msg.getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
}
