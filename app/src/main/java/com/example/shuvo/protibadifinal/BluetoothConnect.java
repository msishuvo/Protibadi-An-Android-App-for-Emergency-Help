package com.example.shuvo.protibadifinal;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import static com.example.shuvo.protibadifinal.R.id.test;


public class BluetoothConnect extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context contexto;
    private EditText enviarTextBox;
    private Menu mainMenu;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button outSig;


    SharedPreferences pref;
    String bnum;


    public void mensaje(String paramString1, String paramString2, String paramString3)
    {
        new AlertDialog.Builder(this).setTitle(paramString2).setMessage(paramString1).setPositiveButton(paramString3, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
        }).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pref = getSharedPreferences("contact_info", Context.MODE_PRIVATE);
        bnum = pref.getString("number","");

        GlobalVars.recibido = (TextView) findViewById(R.id.txtRec);
        GlobalVars.contexto = this;
        this.contexto = this;
        outSig = (Button) findViewById(R.id.buttonTest);

        try
        {
            GlobalVars.bluetooth = new BluetoothUtils();
        }
        catch (Exception localException2)
        {

        }


        GlobalVars.recibido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (ContextCompat.checkSelfPermission(BluetoothConnect.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(BluetoothConnect.this, Manifest.permission.SEND_SMS)) {
                    }
                    else {
                        ActivityCompat.requestPermissions(BluetoothConnect.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }
                }

                else if (ContextCompat.checkSelfPermission(BluetoothConnect.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(bnum, null, GlobalVars.map, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        outSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GlobalVars.bluetooth.isConnected())
                {
                    Toast.makeText(getApplicationContext(),"Connect to Bluetooth First",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    //GlobalVars.bluetooth.send("Unable to send Message");
                    Toast.makeText(getApplicationContext(),"Output Signal Sent",Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });




    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(bnum, null, GlobalVars.map, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Sending failed, Please Try Again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(BluetoothConnect.this,MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bluetooth_connect, menu);
        this.mainMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            try
            {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(BluetoothConnect.this.contexto);
                String[] arrayOfString = GlobalVars.bluetooth.getNames();
                localBuilder.setTitle(BluetoothConnect.this.getResources().getString(R.string.devices));
                localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        if (GlobalVars.bluetooth.connect(paramAnonymous2Int)) {
                            Toast.makeText(BluetoothConnect.this.contexto, "Connected", Toast.LENGTH_LONG).show();
                        }
                        paramAnonymous2DialogInterface.cancel();
                    }
                });
                localBuilder.create().show();
            }
            catch (NullPointerException localNullPointerException) {}catch (Exception localException) {}



            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle Home Screen
            startActivity(new Intent(BluetoothConnect.this,MainActivity.class));
        } else if (id == R.id.nav_contacts) {
            startActivity(new Intent(BluetoothConnect.this,Contacts.class));

        } else if (id == R.id.nav_addCon) {
            startActivity(new Intent(BluetoothConnect.this, AddContact.class));

        } else if (id == R.id.nav_loc) {
            startActivity(new Intent(BluetoothConnect.this,MapsActivity.class));

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
