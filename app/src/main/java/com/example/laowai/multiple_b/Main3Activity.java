package com.example.laowai.multiple_b;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Main3Activity extends AppCompatActivity {

    // Layout Views
    Button btn_On,btn_Off,btn_List,btn_Connect,btn_Send,btn_Recieve;
    EditText edt_Send,edt_Recieve;
    TextView txt_DeviceName,txt_ConnectionStatus,final_text_box;
    ListView list_Receive;
    ArrayList<BluetoothDevice> deviceNameModels;
    ArrayList<String> deviceDetailModels;
    private UUID myUUID;

    private String myName;
    ArrayAdapter<String> pairedDeviceAdapter;
    ThreadConnectBTdevice myThreadConnectBTdevice;
    ThreadConnected myThreadConnected;
    ThreadBeConnected myThreadBeConnected;
    int position;
    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





//hide the keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
         /*Layout Id's*/
        btn_On=(Button)findViewById(R.id.btn_On);
        btn_Off=(Button)findViewById(R.id.btn_Off);
        btn_List=(Button)findViewById(R.id.btn_List);
        btn_Connect=(Button)findViewById(R.id.btn_Connect);
        btn_Send=(Button)findViewById(R.id.btn_Send);
        btn_Recieve=(Button)findViewById(R.id.btn_Recieve);
        edt_Send=(EditText)findViewById(R.id.edt_Send);
        edt_Recieve=(EditText)findViewById(R.id.edt_Recieve);
        list_Receive=(ListView)findViewById(R.id.list_Receive);
        txt_DeviceName=(TextView)findViewById(R.id.txt_DeviceName);
        txt_ConnectionStatus=(TextView)findViewById(R.id.txt_ConnectionStatus);

        final_text_box = (TextView)findViewById(R.id.txt_view_data);

        //
        //btn_save=(Button)findViewById(R.id.btn_save);
        //Initialize arraylist
        deviceNameModels=new ArrayList<>();
        deviceDetailModels=new ArrayList<>();

        //UUID
        myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        //myUUID = UUID.fromString("ec79da00-853f-11e4-b4a9-0800200c9a66");
        myName = myUUID.toString();
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        /*Click listener for Turn on Bluetooth*/
        btn_On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 0);
                    Toast.makeText(getApplicationContext(),"Turned on",Toast.LENGTH_LONG).show();
                }{
                    Toast.makeText(getApplicationContext(),"Already on", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*Click listener for Turn off Bluetooth*/
        btn_Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBluetoothAdapter.disable();
                Toast.makeText(getApplicationContext(),"Turned off" ,Toast.LENGTH_LONG).show();
            }
        });

        /*Click listener for Bluetooth names*/
        btn_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list_stuff();
            }
        });

        /*Click listener for text receive*/
        list_Receive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                //change the background color of the selected element
                for (int j = 0; j < adapterView.getChildCount(); j++)
                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                txt_ConnectionStatus.setBackgroundColor(Color.TRANSPARENT);
                txt_ConnectionStatus.setText("None");
                txt_ConnectionStatus.setTextColor(Color.BLACK);
                view.setBackgroundColor(Color.LTGRAY);
                //pos = 0; you can use this to just save the position
//                BluetoothDevice device =
//                        (BluetoothDevice)deviceNameModels.get(pos);
////                Toast.makeText(MainActivity.this,
////                        "Name: " + device.getName() + "\n"
////                               // + "Address: " + device.getAddress() + "\n"
////                                + "Address2: " + device + "\n"
////                                + "BondState: " + device.getBondState() + "\n"
////                                + "BluetoothClass: " + device.getBluetoothClass() + "\n"
////                                + "Class: " + device.getClass(),
////                        Toast.LENGTH_LONG).show();
//                position=pos;
//                txt_DeviceName.setText
//                ("Position: "+String.valueOf(position)+" = "+String.valueOf(device.getName())+
//                "\n"+String.valueOf(device.getAddress()));
//                txt_DeviceName.setBackgroundColor(Color.LTGRAY);
                //saves the position to a text file
                save_stuff("my_app_data.txt",Integer.toString(pos));//private void save_stuff(String local_file_name, int local_data){
                set_blue_tooth_position(pos);
                //Integer.toString(deviceNameModels.size())
                //final_text_box.setText("Max Index Pos = "+Integer.toString(deviceNameModels.size()-1));
//save last clicked address here
//save_stuff("my_app_data.txt",device.getAddress());
//final_text_box.setText
//("Position: "+String.valueOf(position)+" = "+String.valueOf(device.getName())+"\n"+String.valueOf(device.getAddress()));
            }
        });

        /*Click listener for text Device connect*/
        btn_Connect.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            @Override
            public void onClick(View view) {
                try {
                    txt_ConnectionStatus.setBackgroundColor(Color.YELLOW);
                    BluetoothDevice device = deviceNameModels.get(position);
                    //(BluetoothDevice) deviceNameModels.get(position);
                    //String bbb = "20:16:06:22:41:74"
                    txt_ConnectionStatus.setText("start ThreadConnectBTdevice");
                    myThreadBeConnected = new ThreadBeConnected();
                    myThreadBeConnected.start();
                    myThreadConnectBTdevice = new ThreadConnectBTdevice(device);
                    myThreadConnectBTdevice.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        /*Click listener for text Device connect*/
        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myThreadConnected!=null){
                    byte[] bytesToSend = edt_Send.getText().toString().getBytes();
                    myThreadConnected.write(bytesToSend);
                    Toast.makeText(getApplicationContext(),"Message Sent",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Device Not Connected",Toast.LENGTH_LONG).show();
                }
            }
        });
//end of on create




 ///////



        list_stuff();
        final_text_box.setText("Max Index Pos = "+Integer.toString(deviceNameModels.size()-1));
        Boolean file_true_false = fileExists(this,"my_app_data.txt");
        //Toast.makeText(this, ""+file_true_false, Toast.LENGTH_SHORT).show();
        if (file_true_false){
            Toast.makeText(this, "File Exists", Toast.LENGTH_SHORT).show();

            String hold_position = read_stuff("my_app_data.txt");
            Toast.makeText(this, "sting to in position = \n"+String.valueOf(hold_position), Toast.LENGTH_SHORT).show();
            //key this sets the position of the Bluettooth adapter
            //myNum = Integer.parseInt(myString);
            //!@#$% becuse this is not an  integer//
//use try catch-will crash if the number is higher than the amoutnt of availbale bonded devices
            position = Integer.parseInt(hold_position);
            if(position <= deviceNameModels.size()-1){
                set_blue_tooth_position(position);
            }else {
                Toast.makeText(this, "Position file not set select manually first time", Toast.LENGTH_SHORT).show();
            }



        }

    }
    //end of on create



    //To connect available bluetooth devices
    private class ThreadConnectBTdevice extends Thread {
        private BluetoothSocket bluetoothSocket = null;
        private final BluetoothDevice bluetoothDevice;


        @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
        public ThreadConnectBTdevice(BluetoothDevice device) {
            bluetoothDevice = device;

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
                txt_ConnectionStatus.setText("bluetoothSocket: \n" + bluetoothSocket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        ////////////// ???
        public  void close_test_erase(){
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try{
                boolean success = false;
                try {
                    bluetoothSocket.connect();
                    success = true;
                } catch (IOException e) {
                    e.printStackTrace();

                    final String eMessage = e.getMessage();
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            txt_ConnectionStatus.setText("something wrong bluetoothSocket.connect(): \n" + eMessage);
                            txt_ConnectionStatus.setBackgroundColor(Color.RED);
                            Toast.makeText(getApplicationContext(),"Device Not Connected",Toast.LENGTH_LONG).show();
                        }});

                    try {
                        bluetoothSocket.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

                if(success){
                    //connect successful
                    final String msgconnected = "connect successful:\n"
                            + "BluetoothSocket: " + bluetoothSocket + "\n"
                            + "BluetoothDevice: " + bluetoothDevice;

                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            txt_ConnectionStatus.setText(msgconnected);
                            txt_ConnectionStatus.setBackgroundColor(Color.GREEN);
                            txt_ConnectionStatus.setTextColor(Color.BLACK);
                            Toast.makeText(getApplicationContext(),msgconnected,Toast.LENGTH_LONG).show();

                        }});

                    startThreadConnected(bluetoothSocket);
                }else{
                    //fail
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void cancel() {

            Toast.makeText(getApplicationContext(),
                    "close bluetoothSocket",
                    Toast.LENGTH_LONG).show();

            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    //Called in ThreadConnectBTdevice once connect successed
    //to start ThreadConnected
    private void startThreadConnected(BluetoothSocket socket){
        myThreadConnected = new ThreadConnected(socket);
        myThreadConnected.start();
    }

    private class ThreadBeConnected extends Thread {

        private BluetoothServerSocket bluetoothServerSocket = null;

        public ThreadBeConnected() {
            try {
                bluetoothServerSocket =
                        mBluetoothAdapter.listenUsingRfcommWithServiceRecord(myName, myUUID);

                txt_ConnectionStatus.setText("Waiting\n"
                        + "bluetoothServerSocket :\n"
                        + bluetoothServerSocket);
                txt_ConnectionStatus.setBackgroundColor(Color.YELLOW);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            BluetoothSocket bluetoothSocket = null;

            if(bluetoothServerSocket!=null){
                try {
                    bluetoothSocket = bluetoothServerSocket.accept();

                    BluetoothDevice remoteDevice = bluetoothSocket.getRemoteDevice();

                    final String strConnected = "Connected:\n" +
                            remoteDevice.getName() + "\n" +
                            remoteDevice.getAddress();

                    //connected
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            txt_ConnectionStatus.setText(strConnected);
                            txt_ConnectionStatus.setBackgroundColor(Color.GREEN);
                        }});
                    startThreadConnected(bluetoothSocket);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                    final String eMessage = e.getMessage();
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            txt_ConnectionStatus.setText("something wrong: \n" + eMessage);
                            txt_ConnectionStatus.setBackgroundColor(Color.RED);
                        }});
                }
            }else{
                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        txt_ConnectionStatus.setText("bluetoothServerSocket == null");
                        txt_ConnectionStatus.setBackgroundColor(Color.RED);
                    }});
            }
        }

        public void cancel() {

            Toast.makeText(getApplicationContext(),
                    "close bluetoothServerSocket",
                    Toast.LENGTH_LONG).show();

            try {
                bluetoothServerSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /*
       ThreadConnectBTdevice:
       Background Thread to handle BlueTooth connecting
       */
    private class ThreadConnected extends Thread {
        private final BluetoothSocket connectedBluetoothSocket;
        private final InputStream connectedInputStream;
        private final OutputStream connectedOutputStream;

        public ThreadConnected(BluetoothSocket socket) {
            connectedBluetoothSocket = socket;
            InputStream in = null;
            OutputStream out = null;

            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            connectedInputStream = in;
            connectedOutputStream = out;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = connectedInputStream.read(buffer);
                    final String strReceived = new String(buffer, 0, bytes);
                    /*final String msgReceived = String.valueOf(bytes) +
                            " bytes received:\n"
                            + strReceived;*/

                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            edt_Recieve.setText(strReceived);
                            Toast.makeText(getApplicationContext(),"Message Received",Toast.LENGTH_SHORT).show();
                        }});

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                    final String msgConnectionLost = "Connection lost:\n"
                            + e.getMessage();
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            txt_ConnectionStatus.setText(msgConnectionLost);
                        }});
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                connectedOutputStream.write(buffer);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                connectedBluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    //=============================================================================
    Button jjj,rrr;
//public void save_this(View view){Toast.makeText(this, "save_this", Toast.LENGTH_SHORT).show();}
//public void save_this(View view){save_stuff("my_app_data.txt",textmsg.getText().toString());}

    //String get_mac = txt_DeviceName.getText().toString();
//will throw exception if null !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void save_this(View view){
        String timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "";
        //save_stuff("my_app_data.txt", Integer.parseInt(timeStamp));
        save_stuff("my_app_data.txt", "4456");
        final_text_box.setText("save_this--save_this");
    }
    //was a string now an integer
    private void save_stuff(String local_file_name, String local_data){
        try{
            FileOutputStream fileOut=openFileOutput(local_file_name,MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileOut);
            outputWriter.write(local_data);
            outputWriter.close();
            Toast.makeText(this, "File saved successfully! = "+local_data, Toast.LENGTH_SHORT).show();
        } catch (Exception e){e.printStackTrace();}
    }
    //!@#$%^& on startup if file does not exist then ALERT might need try catch
//public void read_this(View view){Toast.makeText(this, "read_this", Toast.LENGTH_SHORT).show();}

    String global_text_file_data = "Ready";
    public void read_this(View view){
        String hold_me = read_stuff("my_app_data.txt");
        Toast.makeText(this, "++++++++++++++\n"+hold_me, Toast.LENGTH_SHORT).show();
    }
    //Pass it a file name it returns with the value of the text file


    public String read_stuff(String local_file_name){
        String final_sss = "final_sss";
        try{
            FileInputStream fileIn=openFileInput(local_file_name);
            InputStreamReader InputRead=new InputStreamReader(fileIn);
            final int READ_BLOCK_SIZE = 100;
            char[] inputBuffer=new char[READ_BLOCK_SIZE];
            String sss="";int charRead = 0;
            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                sss +=readstring;}
            InputRead.close();
            //Toast.makeText(this, sss,Toast.LENGTH_SHORT).show();
            final_sss = sss;
        } catch (Exception e) {e.printStackTrace();
        }
        return String.valueOf(final_sss);
        //Toast.makeText(this, sss,Toast.LENGTH_SHORT).show();
    }
    //=============================================================================
        /*Click listener for Bluetooth names*/
    public void list_but_auto(View view) {
        list_stuff();
    }

    private void list_stuff() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        deviceNameModels.clear();
        deviceDetailModels.clear();
        if (pairedDevices.size() > 0) {
            deviceNameModels = new ArrayList<BluetoothDevice>();

            for (BluetoothDevice device : pairedDevices) {
                deviceNameModels.add(device);
                deviceDetailModels.add(device.getName()+ "\n"+device.getAddress()+ "\n");
            }
        }
        pairedDeviceAdapter = new ArrayAdapter<String>(Main3Activity.this,
                android.R.layout.simple_list_item_1, deviceDetailModels);
        list_Receive.setAdapter(pairedDeviceAdapter);
        //Toast.makeText(this, "list_stuff --list_stuff", Toast.LENGTH_SHORT).show();
    }

    public void set_blue_tooth_position(int pos){
        BluetoothDevice device = deviceNameModels.get(pos);
        position=pos;
        txt_DeviceName.setText
                ("Position: "+String.valueOf(position)+" = "+String.valueOf(device.getName())+
                        "\n"+String.valueOf(device.getAddress()));
        txt_DeviceName.setBackgroundColor(Color.LTGRAY);
    }
    public boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {return false;}
        return true;
    }
}
