package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getip();
        Button btn1 = (Button)findViewById(R.id.conbtn);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                testt();
            }
        });
    }

    public String ip;
    public int port;
    public void getip(){
        EditText txtip = (EditText)findViewById(R.id.ipText);
        EditText txtport = (EditText)findViewById(R.id.portText);
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        int ip = wm.getConnectionInfo().getIpAddress();
        String ipStr = String.format("%d.%d.%d.%d",
                        (ip & 0xff),
                        (ip >> 8 & 0xff),
                        (ip >> 16 & 0xff),
                        (ip >> 24 & 0xff));
        txtip.setText(ipStr);
        port= Integer.parseInt(txtport.getText().toString());
    }
    public void testt(){
        Intent myIntent = new Intent(this, dash.class);
        myIntent.putExtra("port",port);//Optional parameters
        this.startActivity(myIntent);
    }

}

