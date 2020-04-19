package com.example.shuvo.protibadifinal;

/**
 * Created by Shuvo on 30-Mar-17.
 */

import android.app.Application;
import android.content.Context;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.InputStream;

public class GlobalVars
        extends Application
{
    public static BluetoothUtils bluetooth;
    public static Context contexto;
    public static ConnectedThread emisor;
    public static TextView recibido;
    public static String msg = "I am in TROUBLE!!! PLEASE HELP !!!";
    public static String map;


}