package com.example.shuvo.protibadifinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

public class AddContact extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText en, ec;
    Button ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        en = (EditText) findViewById(R.id.name);
        ec = (EditText) findViewById(R.id.contact);
        ac = (Button) findViewById(R.id.add);


        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder rp = new StringBuilder();
                SharedPreferences prefp = getSharedPreferences("contact_info", Context.MODE_PRIVATE);

                String con_name = en.getText().toString();
                String con_number = ec.getText().toString();

                rp.append("Name: "+con_name);
                rp.append("\nContact Number: "+con_number);
                rp.append("\nAdded !!!");

                SharedPreferences.Editor ep = prefp.edit();
                ep.putString("name",con_name);
                ep.putString("number",con_number);
                ep.apply();

                Toast.makeText(getApplicationContext(),rp.toString(),Toast.LENGTH_LONG).show();

            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(AddContact.this,MainActivity.class));
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle Home Screen
            startActivity(new Intent(AddContact.this,MainActivity.class));
        } else if (id == R.id.nav_contacts) {
            startActivity(new Intent(AddContact.this,Contacts.class));

        } else if (id == R.id.nav_addCon) {
            startActivity(new Intent(AddContact.this,AddContact.class));

        } else if (id == R.id.nav_loc) {
            startActivity(new Intent(AddContact.this,MapsActivity.class));

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(AddContact.this,BluetoothConnect.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
