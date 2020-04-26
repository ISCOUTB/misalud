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

import java.util.HashMap;
import java.util.Map;

public class Setting extends AppCompatActivity {

    public Button acepto;
    private EditText mEdad;
    private EditText mPeso;
    private EditText mEstatura;
    private String edad;
    private String peso;
    private String estatura;
    String id=Main2Activity.id;
    FirebaseDatabase mDatabase;
    DatabaseReference dDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mEdad=(EditText) findViewById(R.id.Edad);
        mPeso=(EditText) findViewById(R.id.Peso);
        mEstatura=(EditText) findViewById(R.id.Estatura);

        mDatabase= FirebaseDatabase.getInstance();
        dDatabase= mDatabase.getReference("Users/"+id+"/RegistroFisicio");



        acepto=(Button) findViewById(R. id.SaveSetting);
        acepto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edad=mEdad.getText().toString();
                peso=mPeso.getText().toString();
                estatura=mEstatura.getText().toString();

                if(!edad.isEmpty() && !peso.isEmpty() && !estatura.isEmpty()){
                    SaveData();
                }else{
                    Toast.makeText(Setting.this, "Debe Completar Los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void SaveData(){
        Map<String, Object> map=new HashMap<>();
        map.put("EDAD",edad);
        map.put("PESO",peso);
        map.put("ESTATURA",estatura);

        dDatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if(task2.isSuccessful()){
                    startActivity(new Intent(Setting.this, Main2Activity.class));
                    Toast.makeText(Setting.this, "Datos Registrados", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Setting.this, "No se pudo crear los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
