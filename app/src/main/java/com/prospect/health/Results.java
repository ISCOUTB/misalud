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
        if(estado){
            saveData();
        }
    }

    //calcular y mostrar resultados
    public void Result() {

        //Rate
        int rate1 = (MainActivity.rate != null) ? Integer.parseInt(MainActivity.rate) : 0;
        int presure1 = (MainActivity.presure != null) ? Integer.parseInt(MainActivity.presure) : 0;
        int presure11 = (MainActivity.presure1 != null) ? Integer.parseInt(MainActivity.presure1) : 0;
        int saturation1 = (MainActivity.saturation != null) ? Integer.parseInt(MainActivity.saturation) : 0;
        float temperature1 = Integer.parseInt(MainActivity.temperature);
        int sugar1 = (MainActivity.sugar != null) ? Integer.parseInt(MainActivity.sugar) : 0;

        //Frecuencia cardiaca-------------------------------//----------------------------------//
        if(rate1==0) {
            rate = "No ingreso dato";
        }else if(MainActivity.askint.get(0)==1){
            if(rate1<=200 && rate1>=80){
                rate = "Normal";
            }
        }else if(MainActivity.askint.get(2)==1){
            if(rate1<=100 && rate1>=50){
                rate = "Normal";
            }
        }else{
            if(rate1<=100 && rate1>=60 ){
                rate = "Normal";
            }else if((rate1<=60 && rate1>=1 || rate1>=100)){
                rate= "Necesario ir al medico";
            }
        }
        //-------------------------------//----------------------------------//

        //Presion Arterial -------------------------------//----------------------------------//
        if(presure1<= 90){
            if(presure11<=60){
                presure ="Hipotensión";
            }
        }else if(presure1>=91 && presure1<=119){
            if(presure11>=61 && presure11<=79){
                presure = "Normal";
            }
        }else if(presure1<=129 && presure1>=120){
            if(presure11<=80){
                presure = "Elevada";
            }
        }else if(presure1<=139 && presure1>=130){
            if(presure11<=89 && presure11>=80){
                presure = "Etapa 1 de hipertensión";
            }
        }else if(presure1>=140){
            if(presure11>=90){
                presure = "Etapa 2 de hipertensión";
            }
        }else if(presure1>=180){
            if(presure11>=120){
                presure = "Hipertensiva";
            }
        }
        if(presure1==0 || presure11 == 0) {
            presure = "No ingreso dato";
        }
        //-------------------------------//----------------------------------//

        //Saturacion-------------------------------//----------------------------------//
        if(saturation1>=95 && saturation1<=100){
            saturation="Normal";
        }else if(saturation1>=91 && saturation1<=94){
            saturation="Hipoxia leve";
        }else if(saturation1>=86 && saturation1<=90){
            saturation="Hipoxia moderada";
        }else if(saturation1<86){
            saturation="Hipoxia severa";
        }else if(saturation1==0) {
            saturation = "No ingreso dato";
        }else{
            saturation="Fuera de rango";
        }

        //-------------------------------//----------------------------------//

        //Temperatura-------------------------------//----------------------------------//
        if(temperature1>=35 && temperature1<=37){
            temperature="Normal";
        }else if(temperature1>=37.1 && temperature1<=37.9){
            temperature="estado febril o febrícula";
        }else if(temperature1>=38 && temperature1<40){
            temperature="hipertermia o fiebre";
        }
        else{
            temperature="Necesario ir al medico";
        }
        if(saturation1==0) {
            saturation = "No ingreso dato";
        }
        //-------------------------------//----------------------------------//

        //Azucar en la sangre-------------------------------//----------------------------------//
        if(MainActivity.askint.get(3)==1) {
            if (rate1 <= 100 && rate1 >= 70) {
                sugar = "Normal";
            }
        }else if(MainActivity.askint.get(4)==1) {
            if (rate1 <= 140 && rate1 >= 70) {
                sugar = "Normal";
            }
        }else if(sugar1>=70 && sugar1<=100){
            sugar="Normal";
        }else if(saturation1==0) {
            saturation = "No ingreso dato";
        }else{
            sugar="Fuera de rango";
        }
        //-------------------------------//----------------------------------//

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