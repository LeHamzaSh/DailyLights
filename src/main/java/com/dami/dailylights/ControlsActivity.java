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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiresApi(api = Build.VERSION_CODES.O)
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
    Map<Integer, Boolean> timeMap = new HashMap<>();

    Map<LocalDateTime, Boolean> StoredTimeValues = new HashMap<>();

    Map<Integer, Boolean> Day1 = new HashMap<>();
    Map<Integer, Boolean> Day2 = new HashMap<>();
    Map<Integer, Boolean> Day3 = new HashMap<>();

    // Automode function boolean
    AtomicBoolean isAutoModeEnabled = new AtomicBoolean(false);
    AtomicBoolean isUltraSoundSensorModeEnabled = new AtomicBoolean(false);


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
                if (isChecked == true) {

                    if (choice.equals("USER")) {
                        int time1 = LocalDateTime.now().getSecond();
                        timeMap.put(time1, true);
                        Log.d("HAMZA_APP", "USER MODE ENABLED - Light will Turn ON at: " + time1);
                    }
                    sendMsg("1");
                    Toast.makeText(getBaseContext(), "Switch is On", Toast.LENGTH_SHORT).show();

                } else {

                    if (choice.equals("USER")) {
                        int time1 = LocalDateTime.now().getSecond();
                        timeMap.put(time1, false);
                        Log.d("HAMZA_APP", "USER MODE ENABLED - Light will Turn OFF at: " + time1);
                    }

                    sendMsg("0");
                    Toast.makeText(getBaseContext(), "Switch is Off", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Check Box Functions
        Check = (CheckBox) findViewById(R.id.checkBox);
        Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {

                    choice = "AUTO";
                    Toast.makeText(getBaseContext(), "Auto Mode Enabled", Toast.LENGTH_SHORT).show();
                    isAutoModeEnabled.getAndSet(true);
                    Toast.makeText(getBaseContext(), "UltraSound Sensor Mode Enabled", Toast.LENGTH_SHORT).show();
                    isUltraSoundSensorModeEnabled.getAndSet(true);
                    try {
                        AutoMode();
                        UltraSoundMode();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("HAMZA_APP", "AUTO MODE ENABLED");
                    Log.d("HAMZA_APP", "ULTRASOUND SENSOR MODE ENABLED");
                } else {

                    choice = "USER";

                    isAutoModeEnabled.getAndSet(false);
                    isUltraSoundSensorModeEnabled.getAndSet(false);


                    Toast.makeText(getBaseContext(), "User Mode Enabled", Toast.LENGTH_SHORT).show();
                    Log.d("HAMZA_APP", "USER MODE ENABLED");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void printUSSensorVlalues() {
        try {
            InputStream is = readUSValue();
            try {
                if (is == null) {
                    Log.d("HAMZA_APP", "Ultrasound Sensor Not Returning Data, BT input stream null");
                }

                int counter = 0;// set counter to 0
                int readValue = 0; //set readValue to 0

                Day1.clear(); // clear day 1 hash map
                Day2.clear(); // clear day 2 hash map
                Day3.clear(); // clear day 3 hash map

                while (readValue != -1 && isUltraSoundSensorModeEnabled.get()) {
                    //Log.d("HAMZA_APP", "Read US Value: " + readValue);
                    readValue = is.read();
                    counter++;

                    // Day 1 Conditions
                    if (counter < 60) {
                        Log.d("HAMZA_APP", "Counter Value Day 1: " + counter);
                        if (readValue <= 20) {
                            Day1.put(LocalDateTime.now().getSecond(), true);
                            Log.d("HAMZA_APP", "Boolean True Time: " + Day1);
                        }
                    }

                    // Day 2 Conditions
                    if (counter >= 60 && counter < 120) {
                        Log.d("HAMZA_APP", "Counter Value Day 2: " + counter);
                        if (readValue <= 20) {
                            Day2.put(LocalDateTime.now().getSecond(), true);
                            Log.d("HAMZA_APP", "Boolean True Time: " + Day2);
                        }
                    }

                    // Day 3 Conditions
                    if (counter >= 120 && counter < 180) {
                        Log.d("HAMZA_APP", "Counter Value Day 3: " + counter);
                        if (readValue <= 20) {
                            Day3.put(LocalDateTime.now().getSecond(), true);
                            Log.d("HAMZA_APP", "Boolean True Time: " + Day3);
                        }
                    }


                    if (counter >= 180) {
                        // day1, day2, day3
                        //int highestTime = FindHighestTimeFrom3Days(Day1, Day2, Day3);
                        TimeStampLogic timeStampLogic = new TimeStampLogic();
                        Map<Integer, Boolean> NewProcessedTime = timeStampLogic.dataProcess(Day1, Day2, Day3);
                        counter = 0; //reset counter back to zero
                        Day1.clear();// Clear Day 1 hash map
                        Day2.clear();// Clear Day 2 hash map
                        Day3.clear();// Clear Day 3 hash map
                        timeMap.clear();
                        timeMap.putAll(NewProcessedTime);
                        Log.d("HAMZA_APP","Processed Time: " +timeMap);
                    }
                }

            } finally {
                //is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        private void UltraSoundMode () {
            ExecutorService exec = Executors.newFixedThreadPool(1);

            exec.submit(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    printUSSensorVlalues();
                }
            });
        }

        private void AutoMode () throws InterruptedException {


            ExecutorService exec = Executors.newSingleThreadExecutor();

            exec.submit(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    while (isAutoModeEnabled.get()) {
                        int second; // variable declaration
                        LocalDateTime timeNow = LocalDateTime.now();
                        second = timeNow.getSecond(); // get system clock second
                        //Log.d("HAMZA_APP", "RUNNING AUTO MODE TIME CHECK at: " + timeNow);
                        //Log.d("HAMZA_APP", "Stored TimeMap: " + timeMap);
                        timeMap.forEach((date, booleanState) -> {
                            if (date.equals(second)) {
                                Log.d("HAMZA_APP", "Found match in time map, stored time: " + date + ", actual time:  " + timeNow);
                                if (booleanState == true) {
                                    Log.d("HAMZA_APP", "Changing Light State" + booleanState);
                                    ControlsActivity.this.sendMsg("1");
                                } else {
                                    ControlsActivity.this.sendMsg("0");
                                }
                            }
                        });
                        try {
                            Thread.sleep(1 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("HAMZA_APP", "AUTO MODE WHILE LOOP EXIT");
                }
            });
        }

        private class ConnectBT extends AsyncTask<Void, Void, Void> {
            private boolean ConnectSuccessfull = true;

            @Override
            protected void onPreExecute() {
                //showing progress dialog
                progress = ProgressDialog.show(ControlsActivity.this, "Disassembling your CPU...", "Joking, just connecting...");
            }

            @Override
            protected Void doInBackground(Void... devices) {
                try {
                    if (BluetoothSoc == null || !BooleanBT) {
                        //connecting
                        BluetoothAdap = BluetoothAdapter.getDefaultAdapter();
                        BluetoothDevice pairedDevice = BluetoothAdap.getRemoteDevice(address);
                        BluetoothSoc = pairedDevice.createInsecureRfcommSocketToServiceRecord(uuid);
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        BluetoothSoc.connect();
                    }
                } catch (IOException e) {
                    //error
                    ConnectSuccessfull = false;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                if (!ConnectSuccessfull) {
                    msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                    finish();
                } else {
                    //connection done
                    msg("Connected.");
                    BooleanBT = true;
                }
                progress.dismiss();
            }
        }

        private void msg (String s){
            //create a toast - shortcut
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }

        private void sendMsg (String msg)
        {
            //send a message to the Arduino
            //when it's time, use this to send the turn on/off message
            if (BluetoothSoc != null) {
                try {
                    BluetoothSoc.getOutputStream().write(msg.getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
            }
        }

        private InputStream readUSValue ()
        {
            //send a message to the Arduino
            //when it's time, use this to send the turn on/off message
            if (BluetoothSoc != null) {
                try {
                    return BluetoothSoc.getInputStream();
                } catch (IOException e) {
                    msg("Error");
                    Log.d("HAMZA_APP", "Error while reading UltraSound Sensor value: \n" + e);
                }
            }
            return null;
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            sendMsg("0");
        }

    private int[] convertMapIntegerKeyToArray(Map<Integer, Boolean> day1) {
        int[] result = new int[day1.size()];

        Iterator<Integer> keySetIterator = day1.keySet().iterator();
        int i = 0;
        while (keySetIterator.hasNext()) {
            result[i] = keySetIterator.next();
            ++i;
        }
        return result;
    }
}