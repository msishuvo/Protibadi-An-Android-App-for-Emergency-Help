package com.example.shuvo.protibadifinal;

import android.Manifest;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Shuvo on 30-Mar-17.
 */
public class ConnectedThread extends Thread {
    public String str;

    private static final int MESSAGE_READ = 2;


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;



    private  Handler mHandler = new Handler()
    {

        public void handleMessage(Message paramAnonymousMessage)
        {


            switch (paramAnonymousMessage.what)
            {

            }

            str = new String((byte[])paramAnonymousMessage.obj, 0, paramAnonymousMessage.arg1);
            GlobalVars.recibido.setText("Incoming Signal: "+str);
        }

    };


    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;

    public ConnectedThread (BluetoothSocket paramBluetoothSocket)
    {

        InputStream tmpIn = null;
        this.mmSocket = paramBluetoothSocket;
        try
        {


            tmpIn = paramBluetoothSocket.getInputStream();

        }
        catch (NullPointerException localNullPointerException)
        {
            for (;;)
            {
                //localInputStream1 = null;
            }
        }
        catch (IOException localIOException)
        {
            for (;;)
            {
                //localInputStream1 = null;
            }
        }
        catch (Exception localException)
        {
            for (;;)
            {
                //localInputStream1 = null;
            }
        }
        //this.mmInStream = localInputStream1;
        this.mmInStream = tmpIn;
    }


    public void run()
    {
        byte[] arrayOfByte = new byte[1024];
        try
        {
            for (;;)
            {
                int i = this.mmInStream.read(arrayOfByte);
                mHandler.obtainMessage(2, i, -1, arrayOfByte).sendToTarget();

                return;
            }

        }
        catch (NullPointerException localNullPointerException)
        {
            return;
        }
        catch (IOException localIOException)
        {
            return;
        }
        catch (Exception localException) {}
    }


}
