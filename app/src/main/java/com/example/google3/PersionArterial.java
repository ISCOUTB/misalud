package com.example.google3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class PersionArterial extends AppCompatActivity {
    String id=Main2Activity.id;
    private EditText mSIS;
    private EditText mDIA;
    private Button mSaveP;
    public String name;
    public String email;
    public String auxName;
    public String auxEmail;
    private TextView mTextviewName;
    private TextView mTextviewEmail;

    private String sis;
    private String dia2;
    private String correo="";

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
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        String fecha1=dia+"-"+(mes+1)+"-"+ano;
        mSIS=(EditText)findViewById(R.id.SIS);
        mDIA=(EditText)findViewById(R.id.DIA);
        mSaveP=(Button)findViewById(R.id.SavePresion);


        mDatabase= FirebaseDatabase.getInstance();
        dDatabase= mDatabase.getReference("Users/"+id+"/PresionArterial "+fecha1);


        mSaveP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sis=mSIS.getText().toString();
                dia2=mDIA.getText().toString();
                SaveData();
            }
        });


    }


public void SaveData(){
        Map<String, Object>map=new HashMap<>();
        map.put("SIS",sis);
        map.put("DIA",dia2);

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
