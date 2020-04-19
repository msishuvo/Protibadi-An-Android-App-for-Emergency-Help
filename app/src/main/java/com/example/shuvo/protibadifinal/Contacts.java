package com.example.shuvo.protibadifinal;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
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
import android.widget.TextView;
import android.widget.Toast;

public class Contacts extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button dc, sm;
    TextView tc;
    SharedPreferences pref;
    String con_Info;
    String num;
    String name;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tc = (TextView) findViewById(R.id.contactInfo);
        dc = (Button) findViewById(R.id.delContact);
        sm = (Button) findViewById(R.id.sendMess);

        pref = getSharedPreferences("contact_info", Context.MODE_PRIVATE);
        name = pref.getString("name","No Contact !!!");
        num = pref.getString("number","");

        con_Info = name +"\n"+num;

        tc.setText(con_Info);

        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tc.getText()=="No Contact !!!"){
                    Toast.makeText(getApplicationContext(),"Nothing To Delete",Toast.LENGTH_LONG).show();
                }

                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Contacts.this);

                    alert.setMessage("Do you want to remove the Contact?");
                    alert.setTitle("Contact will be removed !!");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Contact Removed", Toast.LENGTH_LONG).show();
                            getSharedPreferences("contact_info", 0).edit().clear().apply();
                            tc.setText("No Contact !!!");
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    alert.show();
                }
            }
        });

        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Contacts.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Contacts.this, Manifest.permission.SEND_SMS)) {
                    }
                    else {
                        ActivityCompat.requestPermissions(Contacts.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }
                }

                else if (ContextCompat.checkSelfPermission(Contacts.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(num, null, GlobalVars.msg, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
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
                    smsManager.sendTextMessage(num, null, GlobalVars.msg, null, null);
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
            startActivity(new Intent(Contacts.this,MainActivity.class));
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle Home Screen
            startActivity(new Intent(Contacts.this,MainActivity.class));
        } else if (id == R.id.nav_contacts) {
            startActivity(new Intent(Contacts.this,Contacts.class));

        } else if (id == R.id.nav_addCon) {
            startActivity(new Intent(Contacts.this, AddContact.class));

        } else if (id == R.id.nav_loc) {
            startActivity(new Intent(Contacts.this,MapsActivity.class));

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(Contacts.this,BluetoothConnect.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
