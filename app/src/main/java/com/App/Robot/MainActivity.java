package com.App.Robot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    String in = "00000000";

    Button FL;
    Button F;
    Button FR;
    Button L;
    Button ST;
    Button Re;
    Button BL;
    Button B;
    Button BR;
    Button opn;
    Button clos;
    Button up;
    Button dwn;
    EditText txtip;
    WebView myWebView;
    EditText txtcip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        myWebView = (WebView) findViewById(R.id.webv);

        //media/?action=stream
        Button btn1 = (Button)findViewById(R.id.conbtn);
        FL = (Button)findViewById(R.id.BtnFL);
        F = (Button)findViewById(R.id.BtnF);
        FR = (Button)findViewById(R.id.BtnFR);
        L = (Button)findViewById(R.id.BtnL);
        ST = (Button)findViewById(R.id.BtnST);
        Re = (Button)findViewById(R.id.BtnR);
        BL = (Button)findViewById(R.id.BtnBL);
        B = (Button)findViewById(R.id.BtnB);
        BR = (Button)findViewById(R.id.BtnBR);
        opn = (Button)findViewById(R.id.Btnopn);
        clos = (Button)findViewById(R.id.Btnclos);
        up = (Button)findViewById(R.id.Btnup);
        dwn = (Button)findViewById(R.id.Btndwn);
        txtip = (EditText)findViewById(R.id.iptxt);
        txtcip = (EditText)findViewById(R.id.ciptxt);
        txtip.setText("192.168.255.101");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myWebView.loadUrl("http://admin:@"+txtcip.getText().toString()+"/video/livemb.asp");
                myWebView.loadUrl("http://admin:@"+txtcip.getText().toString()+"/media/?action=stream");
                myWebView.getSettings().setUseWideViewPort(true);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.setInitialScale(1);
                cnx();
            }
        });
    }
    public void cnx(){

        System.out.println("cnx");
        Runnable myRunnable = new Runnable() {
            public void run() {

                DatagramSocket udpSocket;
                InetAddress serverAddress;
                try
                {
                    serverAddress = InetAddress.getByName(txtip.getText().toString());
                    udpSocket = new DatagramSocket(30000);

                    while (true) {
                        in = "00000000";
                        char[] pre = in.toCharArray();
                        //py
                        //py
                        if (B.isPressed()) {
                            pre[0] = '1';
                            pre[1] = '0';
                            pre[2] = '1';
                            pre[3] = '0';
                        }
                        if (F.isPressed()) {
                            pre[0] = '0';
                            pre[1] = '1';
                            pre[2] = '0';
                            pre[3] = '1';
                        }
                        if (L.isPressed()) {
                            pre[0] = '0';
                            pre[1] = '1';
                            pre[2] = '1';
                            pre[3] = '0';
                        }
                        if (Re.isPressed()) {
                            pre[0] = '1';
                            pre[1] = '0';
                            pre[2] = '0';
                            pre[3] = '1';
                        }
                        if (dwn.isPressed()) {
                            pre[4] = '1';
                            pre[5] = '0';
                        }
                        if (up.isPressed()) {
                            pre[4] = '0';
                            pre[5] = '1';
                        }
                        if (opn.isPressed()) {
                            pre[6] = '1';
                            pre[7] = '0';
                        }
                        if (clos.isPressed()) {
                            pre[6] = '0';
                            pre[7] = '1';
                        }
                        if (BL.isPressed()) {
                            pre[0] = '1';
                            pre[1] = '0';
                            pre[2] = '0';
                            pre[3] = '0';
                        }
                        if (BR.isPressed()) {
                            pre[0] = '0';
                            pre[1] = '0';
                            pre[2] = '1';
                            pre[3] = '0';
                        }
                        if (FL.isPressed()) {
                            pre[0] = '0';
                            pre[1] = '1';
                            pre[2] = '0';
                            pre[3] = '0';
                        }
                        if (FR.isPressed()) {
                            pre[0] = '0';
                            pre[1] = '0';
                            pre[2] = '0';
                            pre[3] = '1';
                        }
                        //py
                        in = String.valueOf(pre);
                        DatagramPacket p = new DatagramPacket(
                                in.getBytes(), in.getBytes().length, serverAddress, 30000);
                        udpSocket.send(p);
                        Thread.sleep(20);
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }


        };
        Thread myThread = new Thread(myRunnable);
        myThread.start();
    }
}
