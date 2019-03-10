package com.dami.dailylights;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlsActivity extends AppCompatActivity {
    //Switch Declaration
    Switch LightOne;

    //Check Box Declaration
    CheckBox Check;

    //bluetooth address
    String address = null;

    //dialog when connecting
    private ProgressDialog progress;

    //bluetooth stuff
    BluetoothAdapter BluetoothAdap = null;
    BluetoothSocket BluetoothSoc = null;
    private boolean BooleanBT = false;

    //UUID stuff
    static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    //Map Creation
    Map<LocalDateTime, Boolean> timeMap = new HashMap<>();

    // Automode function boolean
    AtomicBoolean running = new AtomicBoolean(false);


    //Store time values
    String choice = "USER";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        //new intent
        Intent newint = getIntent();
        setContentView(R.layout.activity_controls);

        //get address from previous choice
        address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS);

        //start connection
        new ConnectBT().execute();

        //start automode logic;
        try {
            AutoMode();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Timers Declarations



        //Light One Switch Functions
        LightOne = (Switch) findViewById(R.id.Light1);
        LightOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){

                    if ( choice.equals("USER") ) {
                        LocalDateTime time1 = LocalDateTime.now();
                        timeMap.put(time1, true);
                        Log.d("HAMZA_APP","USER MODE ENABLED - Light will Turn ON at: " + time1);
                    }
                    sendMsg("1");
                    Toast.makeText(getBaseContext(),"Switch is On",Toast.LENGTH_SHORT).show();

                }
                else{

                    if ( choice.equals("USER") ) {
                        LocalDateTime time1 = LocalDateTime.parse("2019-03-10T19:58:00");
                        timeMap.put(time1, false);
                        Log.d("HAMZA_APP","USER MODE ENABLED - Light will Turn OFF at: " + time1);
                    }

                    sendMsg("0");
                    Toast.makeText(getBaseContext(),"Switch is Off",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Check Box Functions
        Check = (CheckBox) findViewById(R.id.checkBox);
        Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){

                    choice = "AUTO";
                    Toast.makeText(getBaseContext(),"Auto Mode Enabled",Toast.LENGTH_SHORT).show();
                        running.getAndSet(true);
                    try {
                        AutoMode();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("HAMZA_APP","AUTO MODE ENABLED");
                }
                else{

                    choice = "USER";

                    running.getAndSet(false);


                    Toast.makeText(getBaseContext(),"User Mode Enabled",Toast.LENGTH_SHORT).show();
                    Log.d("HAMZA_APP","USER MODE ENABLED");
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void AutoMode() throws InterruptedException {


        ExecutorService exec = Executors.newSingleThreadExecutor();

        exec.submit(() -> {
            while(running.get()) {
                int hour, minute, second; // variable declaration
                LocalDateTime timeNow = LocalDateTime.now();
                hour = timeNow.getHour(); // get system clock hour
                minute = timeNow.getMinute(); // get system clock minute
                second = timeNow.getSecond(); // get system clock second
                Log.d("HAMZA_APP", "RUNNING AUTO MODE TIME CHECK at: " + timeNow);
                Log.d("HAMZA_APP", "Stored TimeMap: " + timeMap);
                timeMap.forEach((date, booleanState) -> {
                    if (date.getMinute() == minute) {
                        Log.d("HAMZA_APP", "Found match in time map, stored time: " + date + ", actual time:  " + timeNow);
                        if (booleanState == true) {
                            Log.d("HAMZA_APP", "Changing Light State" + booleanState);
                            sendMsg("1");
                        }else{
                            sendMsg("0");
                        }
                    }
                });
                try {
                    Thread.sleep(1 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d("HAMZA_APP", "AUTO MODE WHILE LOOP EXIT");
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
