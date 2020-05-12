package com.prospect.health;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.util.JsonMapper;

import java.io.File;


public class Profile extends AppCompatActivity {
    private TextView mTextViewName;
    String date2 = MainActivity.date1;
    private TextView mTextViewEmail;
    private TextView mTextViewWeight;
    private TextView mTextViewHeight;
    private TextView mTextDate;
    public static String id = "";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mTextViewName=(TextView) findViewById(R.id.textView4);
        mTextViewEmail=(TextView) findViewById(R.id.textView7);
        mTextViewWeight=(TextView) findViewById(R.id.textView6);
        mTextViewHeight=(TextView) findViewById(R.id.textView11);
        mTextDate=(TextView) findViewById(R.id.textView88);
        getUserInfo();
        getUserInfo2();
    }

    //Otener Informacion del usuario
    private void getUserInfo(){
        id= mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String email = dataSnapshot.child("correo").getValue().toString();
                    String height = dataSnapshot.child("height").getValue().toString();
                    String weight = dataSnapshot.child("weight").getValue().toString();
                    mTextViewName.setText(name);
                    mTextViewEmail.setText(email);
                    mTextViewHeight.setText(height);
                    mTextViewWeight.setText(weight);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    String datosVitales="datosVitales";
    String json;
    private void getUserInfo2(){
        id= mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    json=dataSnapshot.child(datosVitales).getValue().toString();
                    //DatosVitales objeto = JsonReader.new file (json.toString());
                    Log.d("mytag"," "+dataSnapshot.child(datosVitales).getValue().toString());
                    String date=dataSnapshot.child(datosVitales).getValue().toString();

                    mTextDate.setText(date);

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
                startActivity(new Intent(Profile.this, Profile.class ));
                return true;
            case R.id.action_salir:
                Login.Logintrue.signOut();
                startActivity(new Intent(Profile.this, Login.class ));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Results Screen
    public void return0(View v) {
        Intent return0 = new Intent(this, MainActivity.class);
        startActivity(return0);
    }
}
