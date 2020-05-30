package com.prospect.health;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Alarm extends AppCompatActivity {

    private TextView textNameMedicine;

    public static String id = "";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String l;
    private String m;
    private String x;
    private String j;
    private String v;
    private String s;
    private String d;

    List<String> nameMedicamentos = new ArrayList<String>();
    private String medicamento="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        textNameMedicine = (TextView) findViewById(R.id.textNameMedicine);
        getUserInfo();

    }

    //Otener Informacion del usuario
    private void getUserInfo() {
        //id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String data="";
                    DateAlarm datea = new DateAlarm();
                    //datea = dataSnapshot.child("Alarma").getValue().toString();
                    /*for(DataSnapshot ds: dataSnapshot.getChildren()){
                        data = ds.child("/Alarma").getValue().toString();
                        //nameMedicamentos.contains(data);
                    }*/
                    Log.d("myTag", "lista alarma: "+data);
                    /*l = dataSnapshot.child("/Alarma/1").getKey();
                    m = dataSnapshot.child("email").getValue().toString();
                    textNameMedicine.setText(medicamento);*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_profile:
                startActivity(new Intent(Alarm.this, Profile.class ));
                return true;
            case R.id.action_salir:
                Login.Logintrue.signOut();
                startActivity(new Intent(Alarm.this, Login.class ));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void returnn(View v){
        Intent returnn = new Intent(this, MainActivity.class);
        startActivity(returnn);
    }


}