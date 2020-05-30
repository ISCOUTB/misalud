package com.prospect.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText mEditTextRate;
    private EditText mEditTextPresure;
    private EditText mEditTextPresure1;
    private EditText mEditTextSaturation;
    private EditText mEditTextTemperature;
    private EditText mEditTextSugar;
    private Button mButtonAnalyze;

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    //Variables
    public static String rate="";
    public static String presure="";
    public static String presure1="";
    public static String saturation="";
    public static String temperature="";
    public static String sugar="";
    private GoogleApiClient googleApiClient;
    public static String date1;
    public static String dato=null;

    public static String id;

    boolean cancel = false;
    List<String> ask = new ArrayList<String>(Arrays.asList("Have you exercised the last 10 minutes?","Do you suffer from heart disease or sugar?","Did you wake up 10 minutes ago?","Have you had breakfast?","Have you eaten two hours or more?"));
    public static List<Integer> askint = new ArrayList<>();
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar date = new GregorianCalendar();
        int año = date.get(Calendar.YEAR);
        int mes = date.get(Calendar.MONTH);
        int dia = date.get(Calendar.DAY_OF_MONTH);
        date1 = dia+"-"+(mes+1)+"-"+año;

        Log.d("fecha", date1);


        if( Registre.registrotrue){
            id= Registre.Idtrue;
        }else{
            id = Login.id;
        }

        mEditTextRate = (EditText) findViewById(R.id.editRate);
        mEditTextPresure = (EditText) findViewById(R.id.editPressure);
        mEditTextPresure1 = (EditText) findViewById(R.id.editPressure1);
        mEditTextSaturation = (EditText) findViewById(R.id.editSaturation);
        mEditTextTemperature = (EditText) findViewById(R.id.editTemperature);
        mEditTextSugar = (EditText) findViewById(R.id.editSugar);
        mButtonAnalyze = (Button) findViewById(R.id.buttonAnalyze);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Usuario/"+id+"/datosVitales/"+date1);
        boolean tem=false;
        mButtonAnalyze.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){

                if(cancel){cancel=false;}
                ModalAsk(ask.get(i));
            }
        });
    }
    private void ModalAsk(final String dato){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setTitle("Questions");
        builder.setMessage(dato)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        askint.add(1);
                        terminar();
                        ModalAsk(ask.get(i));
                    }
                })
                .setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        askint.add(0);
                        terminar();
                        ModalAsk(ask.get(i));
                    }
                });
        if(!cancel){
            builder.show();
            i++;
        }
    }

    private void terminar(){
        if(i==5){
            cancel = true;
            i=0;
            proceso();
        }
    }
    private void proceso(){
        rate = mEditTextRate.getText().toString();
        presure = mEditTextPresure.getText().toString();
        presure1 = mEditTextPresure1.getText().toString();
        saturation = mEditTextSaturation.getText().toString();
        temperature = mEditTextTemperature.getText().toString();
        sugar = mEditTextSugar.getText().toString();
        saveData();
        //mButtonAnalyze.setBackgroundColor(Color.RED);
    }

    //Almacenar datos
    private void saveData(){
        Map<String, Object> map = new HashMap<>();
        map.put( "Heart_Rate", rate);
        map.put("Blood_Presure_Sistólica", presure);
        map.put("Blood_Presure_Diastólica ", presure1);
        map.put( "Oxigen_Saturation", saturation);
        map.put( "Temperature", temperature);
        map.put( "Sugar", sugar);

        mDatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if(task2.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, Results.class ));
                   // Log.d("myTag", "Su registro ha sido exitoso");
                    Toast.makeText( MainActivity.this, "analyzing data!", Toast.LENGTH_SHORT).show();

                    finish();
                }
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
                startActivity(new Intent(MainActivity.this, Profile.class ));
                return true;
            case R.id.action_salir:
                Login.Logintrue.signOut();
                startActivity(new Intent(MainActivity.this, Login.class ));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Set Alarma
    public void proAlarm(View  v){
        Intent proAlarm = new Intent(this, Reminder.class);
        startActivity(proAlarm);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}