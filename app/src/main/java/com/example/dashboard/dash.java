package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import de.nitri.gauge.Gauge;


public class dash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dash);

        final Gauge speedg = (Gauge) findViewById(R.id.gauge);
        final Gauge rpmg = (Gauge) findViewById(R.id.gauge4);

        Runnable myRunnable = new Runnable() {
            public void run() {
                byte[] ogBuf= new byte[92];

                DatagramSocket sock = null;
                DatagramPacket udpPack = new DatagramPacket(ogBuf, 92);

                try
                {
                    sock = new DatagramSocket(30000);

                    while(true)
                    {
                        sock.receive(udpPack);

                        int bits = 0;
                        int idx = 12;
                        for(int shiftBy = 0; shiftBy < 4; shiftBy++)
                        {
                            bits |= ((int)ogBuf[idx++] & 0xFF) << (shiftBy * 8);
                        }

                        float sp = Float.intBitsToFloat(bits);
                        if (!(sp > 0)){
                            sp=0;
                        }
                        float speed= (sp*3.6f);
                        /*speed*/
                        int bitsr = 0;
                        int idxr = 16;
                        for(int shiftByt = 0; shiftByt < 4; shiftByt++)
                        {
                            bitsr |= ((int)ogBuf[idxr++] & 0xFF ) << (shiftByt * 8);
                        }
                        float rpmm = Float.intBitsToFloat(bitsr);
                        if(!(rpmm >0)){
                            rpmm =0;
                        }
                        float rpm = rpmm/1000;
                        if (speed<0){
                            speed=0;
                        }
                        if (rpm<0){
                            rpm=0;
                        }
                        speedg.moveToValue(speed);
                        rpmg.moveToValue(rpm);

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
