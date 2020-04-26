package com.example.google3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class PersionArterial extends AppCompatActivity {
    String id=Main2Activity.id;
    private EditText mSIS;
    private EditText mDIA;
    private Button mSaveP;
    private Button mCancelP;
    private EditText mPULSE;
    public String name;


    private String sis;
    private String dia;
    private String pulse;

    public int ano;
    public int mes;


    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference dDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_arterial);

        Calendar fecha= new GregorianCalendar();
        ano = fecha.get(Calendar.YEAR);
        mes = fecha.get(Calendar.MONTH);
        int dia2 = fecha.get(Calendar.DAY_OF_MONTH);

        String fecha1=dia2+"-"+(mes+1)+"-"+ano;
        mSIS=(EditText)findViewById(R.id.SYS);
        mDIA=(EditText)findViewById(R.id.DIA);
        mPULSE=(EditText)findViewById(R.id.PULSE);
        mSaveP=(Button)findViewById(R.id.SavePresion);
        mCancelP=(Button)findViewById(R.id.CancelPresion);


        mDatabase= FirebaseDatabase.getInstance();
        dDatabase= mDatabase.getReference("Users/"+id+"/PresionArterial "+fecha1);

        mCancelP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersionArterial.this, menuapp.class));
            }
        });

        mSaveP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sis=mSIS.getText().toString();
                dia=mDIA.getText().toString();
                pulse=mPULSE.getText().toString();

                if(!sis.isEmpty() && !dia.isEmpty() && !pulse.isEmpty()){
                    SaveData();
                }else{
                    Toast.makeText(PersionArterial.this, "Debe Completar Los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


public void SaveData(){
        Map<String, Object>map=new HashMap<>();
        map.put("SIS",sis);
        map.put("DIA",dia);

        dDatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if(task2.isSuccessful()){
                    startActivity(new Intent(PersionArterial.this, menuapp.class));
                    Toast.makeText(PersionArterial.this, "Datos Registrados", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(PersionArterial.this, "No se pudo crear los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
}



}
