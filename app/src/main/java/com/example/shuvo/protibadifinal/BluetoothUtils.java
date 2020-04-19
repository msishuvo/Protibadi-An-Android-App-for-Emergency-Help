package com.example.shuvo.protibadifinal;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.telephony.SmsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by Shuvo on 30-Mar-17.
 */
public class BluetoothUtils {

    private static final String UUID_CODE = "00001101-0000-1000-8000-00805F9B34FB";
    private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private ArrayList<BluetoothDevice> devices = new ArrayList();
    private BluetoothSocket socket;

    public BluetoothUtils()
    {
        if (this.adapter == null) {}
        for (;;)
        {

            Iterator localIterator = this.adapter.getBondedDevices().iterator();
            while (localIterator.hasNext())
            {
                BluetoothDevice localBluetoothDevice = (BluetoothDevice)localIterator.next();
                this.devices.add(localBluetoothDevice);
            }
            return;
        }
    }

    public String[] getNames()
    {
        String[] arrayOfString = new String[this.devices.size()];
        for (int i = 0;; i++)
        {
            if (i >= this.devices.size()) {
                return arrayOfString;
            }
            arrayOfString[i] = ((BluetoothDevice)this.devices.get(i)).getName();
        }
    }

    public boolean connect(int paramInt)
    {
        if ((paramInt < 0) || (paramInt >= this.devices.size())) {
            return false;
        }
        try
        {
            this.socket = ((BluetoothDevice)this.devices.get(paramInt)).createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            this.socket.connect();
            GlobalVars.emisor = new ConnectedThread(this.socket);
            GlobalVars.emisor.start();

            return true;
        }
        catch (NullPointerException localNullPointerException)
        {
            return false;
        }
        catch (IOException localIOException)
        {
            return false;
        }
        catch (Exception localException) {}
        return false;
    }


    public boolean isConnected()
    {
        if (this.socket == null) {
            return false;
        }
        return this.socket.isConnected();
    }

    public void send(String paramString)
    {
        if (this.socket == null) {
            return;
        }
        try
        {
            this.socket.getOutputStream().write(paramString.getBytes());
            return;
        }
        catch (NullPointerException localNullPointerException)
        {

        }
        catch (Exception localException)
        {

        }
    }

}
