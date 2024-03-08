package com.masumb2k2.fuelfiller.android.bluetoothpump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import soup.neumorphism.NeumorphButton;
import soup.neumorphism.NeumorphCardView;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class MainActivity extends AppCompatActivity {

    private NeumorphCardView petrol_btn;
    private NeumorphCardView octane_btn;
    private NeumorphCardView diesel_btn;
    private Button right_btn;
    private EditText editText;
    private Button connect_btn;
    private NeumorphCardView credit;
    private int count=1000;
    private TextView creditview;
    private RelativeLayout chooselayout,chooselayou2,countlayout;
    private NeumorphButton onebtn,twobtn,threebtn,fourbtn,fivebtn,sixbtn,sevenbtn,eightbtn,ninebtn,tenbtn;

    private int REQUEST_ENABLE_BT = 1;
    private ArrayList<String> bluetoothList = new ArrayList<>();
    private int REQUEST_BLUETOOTH_PERMISSION = 1;
    private static final String BLUETOOTH_PERMISSION = Manifest.permission.BLUETOOTH;
    private Map<String, BluetoothDevice> bluetoothDevices;
    private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");    // default value for bluetooth module
    private OutputStream outputStream = null;
    private String selectedItem;
    private boolean buttonIsPressed = false;
    private BluetoothSocket btSocket;
    private boolean idle = false;

    private int val=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //top bar remove
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_main);
        bluetoothDevices = new HashMap<String, BluetoothDevice>();
        selectedItem = null;

        // Request Bluetooth permission if not granted already
        if (ContextCompat.checkSelfPermission(this, BLUETOOTH_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{BLUETOOTH_PERMISSION}, REQUEST_BLUETOOTH_PERMISSION);
        } else {

        }
        bluetoothList.add("Select Device");

        initBluetoothList(bluetoothList);
        initMovementButtons();

        handleAllButtons(false);
    }


    private void handleAllButtons(boolean state){
        runOnUiThread(() -> {
            petrol_btn.setEnabled(state);
            octane_btn.setEnabled(state);
            diesel_btn.setEnabled(state);
            right_btn.setEnabled(state);
            onebtn.setEnabled(state);
            twobtn.setEnabled(state);
            threebtn.setEnabled(state);
            fourbtn.setEnabled(state);
            fivebtn.setEnabled(state);
            sixbtn.setEnabled(state);
            sevenbtn.setEnabled(state);
            eightbtn.setEnabled(state);
            ninebtn.setEnabled(state);

        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initMovementButtons() {
        /**
         * Initializes all the movement button click listeners.
         */
        petrol_btn = findViewById(R.id.petrol_btn);
        octane_btn = findViewById(R.id.octane_btn);
        diesel_btn = findViewById(R.id.diesel_btn);
        right_btn = findViewById(R.id.right_button);
        connect_btn = findViewById(R.id.connect_button);
        onebtn=findViewById(R.id.onebtn);
        twobtn=findViewById(R.id.twobtn);
        threebtn=findViewById(R.id.threebtn);
        fourbtn=findViewById(R.id.fourbtn);
        fivebtn=findViewById(R.id.fivebtn);
        sixbtn=findViewById(R.id.sixbtn);
        sevenbtn=findViewById(R.id.sevenbtn);
        eightbtn=findViewById(R.id.eightbtn);
        ninebtn=findViewById(R.id.ninebtn);
        credit=findViewById(R.id.credit);
        creditview=findViewById(R.id.creditview);

        chooselayout=findViewById(R.id.chooselayout);
        chooselayou2=findViewById(R.id.chooselayou2);
        countlayout=findViewById(R.id.countlayout);
//================================================================================================-

        petrol_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Button pressed
                        //startAction('F');
                        val=1;
                        Toast.makeText(MainActivity.this,"PETROL SELECTED",Toast.LENGTH_SHORT).show();
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Button released
                        //stopAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        // Button moved while pressed
                        return true;
                }
                return false;
            }
        });
        octane_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Button pressed
                        //startAction('F');
                        val=2;
                        Toast.makeText(MainActivity.this,"OCTANE SELECTED",Toast.LENGTH_SHORT).show();
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Button released
                        //stopAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        // Button moved while pressed
                        return true;
                }
                return false;
            }
        });

        diesel_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Button pressed
                        //startAction('B');
                        val=3;
                        Toast.makeText(MainActivity.this,"DIESEL SELECTED",Toast.LENGTH_SHORT).show();
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Button released
                        //stopAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        // Button moved while pressed
                        return true;
                }
                return false;
            }
        });

        right_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Button pressed
                        startAction('R');
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Button released
                        stopAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        // Button moved while pressed
                        return true;
                }
                return false;
            }
        });

        //=========================================================================================
        onebtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(val==1){

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('A');
                            count=count-10;
                            creditview.setText(""+count);

                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==2){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('B');
                            count=count-20;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==3){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('C');
                            count=count-30;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                return false;
            }
        });
        twobtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(val==1){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('D');
                            count=count-20;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==2){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('E');
                            count=count-40;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==3){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('F');
                            count=count-60;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                return false;
            }
        });
        threebtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(val==1){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('G');
                            count=count-30;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==2){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('H');
                            count=count-60;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==3){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('I');
                            count=count-90;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                return false;
            }
        });
        fourbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(val==1){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('J');
                            count=count-40;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==2){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('K');
                            count=count-80;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==3){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('L');
                            count=count-120;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                return false;
            }
        });
        fivebtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(val==1){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('M');
                            count=count-50;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==2){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('N');
                            count=count-100;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==3){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('O');
                            count=count-150;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                return false;
            }
        });
        sixbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(val==1){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('P');
                            count=count-60;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==2){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('Q');
                            count=count-120;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==3){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('R');
                            count=count-180;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                return false;
            }
        });
        sevenbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(val==1){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('S');
                            count=count-70;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==2){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('T');
                            count=count-140;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==3){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('U');
                            count=count-210;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                return false;
            }
        });
        eightbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(val==1){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('V');
                            count=count-80;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==2){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('W');
                            count=count-160;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==3){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('X');
                            count=count-240;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                return false;
            }
        });
        ninebtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(val==1){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('Y');
                            count=count-90;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==2){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('Z');
                            count=count-180;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                if(val==3){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Button pressed
                            startAction('1');
                            count=count-270;
                            creditview.setText(""+count);
                            return true;
                        case MotionEvent.ACTION_UP:
                            // Button released
                            stopAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            // Button moved while pressed
                            return true;
                    }
                }
                return false;
            }
        });

        creditview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });



        // ====================================================

        connect_btn.setOnClickListener(v -> {
            if (connect_btn.getText().equals("Disconnect")){
                try {
                    stopIdle();
                    btSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                connect_btn.setText("Connect");
                handleAllButtons(false);
                return;
            }
            if (selectedItem == null) {
                return;
            }
            BluetoothDevice b = bluetoothDevices.get(selectedItem);
            if (b != null) {
                connectToAddress(b.getAddress());
            }
            //alert
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("ARE YOU CONFIRM?")
                    .setMessage("ARE YOU SURE THAT FUEL PUMP VULVE IS PROPERLY ATTACHED WITH CAR FUEL TANK?")
                    .setNegativeButton("NO,NOT CONNECTED", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            chooselayout.setVisibility(View.INVISIBLE);
                            chooselayou2.setVisibility(View.INVISIBLE);
                            countlayout.setVisibility(View.INVISIBLE);
                            right_btn.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this,"CONNECT THE VULVE",Toast.LENGTH_LONG).show();
                        }
                    })
                    .setPositiveButton("YES,CONNECTED", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();

        });

    }

    @SuppressLint("ClickableViewAccessibility")

    private void startAction(char c) {
        buttonIsPressed = true;
        idle = false;
        new Thread(new Runnable() {
            public void run() {
                while (buttonIsPressed) {
                    // Execute your action repeatedly here
                    sendCharToArduino(c);
                    System.out.println("Button pressed " + c);
                    try {
                        Thread.sleep(100); // Wait for 200 milliseconds before executing again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void stopAction() {
        buttonIsPressed = false;
        if (btSocket.isConnected()) {
            startIdle();
        }
        // Stop executing the action here
        System.out.println("Button released");
    }

    private void startIdle() {
        idle = true;
        new Thread(new Runnable() {
            public void run() {
                while (idle) {
                    // Execute your action repeatedly here
                   // sendCharToArduino('S');
                    try {
                        Thread.sleep(100); // Wait for 200 milliseconds before executing again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void stopIdle() {
        idle = false;
        // Stop executing the action here
        System.out.println("Button released");
    }



    private void connectToAddress(String deviceAddress) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress); // deviceAddress is the address of the Bluetooth device
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        btSocket = null; // uuid is the UUID of the Bluetooth service that the device uses
        try {
            ParcelUuid[] uuids = bluetoothDevice.getUuids();
            if (uuids != null) {
                for (ParcelUuid u : uuids) {
                    uuid = u.getUuid();
                }
            }
            btSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //bluetoothAdapter.cancelDiscovery(); // cancel discovery as it will slow down the connection
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            Toast.makeText(MainActivity.this, "Connecting...", Toast.LENGTH_SHORT).show();
            btSocket.connect(); // connect to the device
            Toast.makeText(MainActivity.this, "Connected to device", Toast.LENGTH_SHORT).show();
            handleAllButtons(true);
            outputStream = null;
            try {
                outputStream = btSocket.getOutputStream();
                startIdle();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(btSocket.getInputStream()));
                new Thread(() -> {
                    while (true) {
                        try {
                            Thread.sleep(1000); // Wait for 1 second before checking the connection
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Check if the connection is lost
                        try {
                            String receivedData = bufferedReader.readLine();
                            try {
                                JSONObject jsonObject = new JSONObject(receivedData);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (IOException e) {
                            runOnUiThread(() -> {
                                if (idle) { // if still idle & error in inputStream -> connection lost.
                                    Toast.makeText(MainActivity.this, "Connection Lost.", Toast.LENGTH_SHORT).show();
                                    stopIdle();
                                    connect_btn.setText("Connect");
                                    handleAllButtons(false);
                                }
                            });
                            break;
                        }
                    }
                }).start();
                connect_btn.setText("Disconnect");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Could not connect to device", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Method to send a character to Arduino via Bluetooth
    public void sendCharToArduino(char c) {
        if (outputStream == null) {
            return;
        }
        try {
            outputStream.write(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initBluetoothList(ArrayList<String> bluetoothList) {
        Spinner spinner = findViewById(R.id.bluet_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, bluetoothList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // Get the selected item
                selectedItem = (String) adapterView.getItemAtPosition(i);
                // Do something with the selected item, such as connecting to the Bluetooth device
                connectBluetooth(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }

    private void connectBluetooth(final String selectedItem) {
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                        REQUEST_BLUETOOTH_PERMISSION);
            } else {
                // Start a new thread to connect to the selected Bluetooth device

                BluetoothDevice selectedDevice = null;
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    bluetoothList.clear(); // Clear the list before adding new devices
                    for (BluetoothDevice device : pairedDevices) {
                        bluetoothList.add(device.getName());
                        bluetoothDevices.put(device.getName(), device);
                    }
                }

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Spinner spinner = findViewById(R.id.bluet_list);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, bluetoothList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            } else {
                // Permission denied, inform the user
                Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void handleButtons(Switch s, boolean enable) {
        /**
         * Method to enable - disable all the buttons and a y switch (use when switch x is pressed).
         * Param: Switch s: the y switch to be disabled.
         *        boolean enable: if true, enables all buttons and y switch. Else, disables them.
         */
        petrol_btn.setEnabled(enable);
        octane_btn.setEnabled(enable);
        right_btn.setEnabled(enable);
        diesel_btn.setEnabled(enable);
        s.setEnabled(enable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopIdle();
    }
}
