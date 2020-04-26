package com.example.google3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Glucemia extends AppCompatActivity {

    private EditText mGlucosa;
    private Button mSave;
    private Button mCancel;
    private String glucosa;
    String id=Main2Activity.id;
    public int ano;
    public int mes;
    FirebaseDatabase mDatabase;
    DatabaseReference dDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucemia);

        Calendar fecha= new GregorianCalendar();
        ano = fecha.get(Calendar.YEAR);
        mes = fecha.get(Calendar.MONTH);
        int dia2 = fecha.get(Calendar.DAY_OF_MONTH);
        String fecha1=dia2+"-"+(mes+1)+"-"+ano;
        mDatabase= FirebaseDatabase.getInstance();
        dDatabase= mDatabase.getReference("Users/"+id+"/Glucemia "+fecha1);

        mGlucosa=(EditText) findViewById(R.id.editText);
        mSave=(Button) findViewById(R.id.button5);
        mCancel=(Button) findViewById(R.id.Cancelglucemia);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Glucemia.this, menuapp.class));
                finish();
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glucosa=mGlucosa.getText().toString();


                if(!glucosa.isEmpty()){
                    SaveData();
                }else{
                    Toast.makeText(Glucemia.this, "Debe Completar Los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void SaveData(){
        Map<String, Object> map=new HashMap<>();
        map.put("Glucosa",glucosa);

        dDatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if(task2.isSuccessful()){
                    startActivity(new Intent(Glucemia.this, menuapp.class));
                    Toast.makeText(Glucemia.this, "Datos Registrados", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Glucemia.this, "No se pudo crear los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
