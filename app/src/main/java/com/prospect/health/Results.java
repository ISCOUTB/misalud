package com.prospect.health;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Results extends AppCompatActivity {

    private TextView mTextRate;
    private TextView mTextPresure;
    private TextView mTextSaturation;
    private TextView mTextTemperature;
    private TextView mTextSugar;
    private Button mButtonReturn;

    FirebaseDatabase database;
    DatabaseReference mDatabase;

    //Variables
    String rate="";
    String presure="";
    String saturation="";
    String temperature="";
    String sugar="";
    Boolean estado=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String date2 = MainActivity.date1;

        String id;
        if( Registre.registrotrue){
            id= Registre.Idtrue;
        }else{
            id = Login.id;
        }

        mTextRate = (TextView) findViewById(R.id.textRatem);
        mTextPresure = (TextView) findViewById(R.id.textPressurem);
        mTextSaturation = (TextView) findViewById(R.id.textSaturationm);
        mTextTemperature = (TextView) findViewById(R.id.textTemperaturem);
        mTextSugar = (TextView) findViewById(R.id.textSugarm);
        mButtonReturn = (Button) findViewById(R.id.buttonRetumm);

        Result();


        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Usuario/"+id+"/datosVitales/"+date2+"/Resultados");

       /* mButtonReturn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


            }
        });*/

        if(estado){
            saveData();
        }



    }

    //calcular y mostrar resultados
    public void Result(){

        //Rate
        int rate1 = Integer.parseInt(MainActivity.rate);
        int presure1= Integer.parseInt(MainActivity.presure);
        int saturation1= Integer.parseInt(MainActivity.saturation);
        int temperature1= Integer.parseInt(MainActivity.temperature);
        int sugar1= Integer.parseInt(MainActivity.sugar);

        if(rate1<=100 && rate1>=60 ){
            rate = "Normal";
        }else if(rate1<60 || rate1>100){
            rate= "Necesario ir al medico";
        }

        if(presure1<= 120){
            presure ="Normal";
        }else if(presure1> 120 && presure1 >=129){
            presure ="Alta";
        }else if(presure1> 130 && presure1 >=139){
            presure ="Hipertensión etapa 1";
        }else if(presure1>= 140){
            presure ="Hipertensión etapa 2";
        }else{
            presure="Necesario ir al medico";
        }

        if(saturation1>=95 && saturation1<=100){
            saturation="Normal";
        }else if(saturation1<90){
            saturation="Hipoxemia";
        }else{
            saturation="Necesario ir al medico";
        }

        if(temperature1>=36 && temperature1<=37){
            temperature="Normal";
        }else if(temperature1>38){
            temperature="Necesario ir al medico";
        }else{
            temperature="Necesario ir al medico";
        }

        if(sugar1>=70 && sugar1<=100){
            sugar="Normal";
        }else{
            sugar="Necesario ir al medico";
        }

        mTextRate.setText(rate);
        mTextPresure.setText(presure);
        mTextSaturation.setText(saturation);
        mTextTemperature.setText(temperature);
        mTextSugar.setText(sugar);

        estado=true;
    }


    private void saveData(){
        Map<String, Object> map = new HashMap<>();
        map.put( "Result Heart Rate", rate);
        map.put( "Result Blood Presure", presure);
        map.put( "Result Oxigen Saturation", saturation);
        map.put( "Result Temperature", temperature);
        map.put( "Result Sugar", sugar);

        mDatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if(task2.isSuccessful()){
                    //startActivity(new Intent(Results.this, MainActivity.class ));
                    // Log.d("myTag", "Su registro ha sido exitoso");
                    Toast.makeText( Results.this, "Sus datos han sido guardados!", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(Results.this, Profile.class ));
                return true;
            case R.id.action_salir:
                Login.Logintrue.signOut();
                startActivity(new Intent(Results.this, Login.class ));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void return0(View v){
        Intent return0 = new Intent(this, MainActivity.class);
        startActivity(return0);
    }


}