package com.prospect.health;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registre extends AppCompatActivity {

    private EditText mEdiTextName;
    private EditText mEditTextCorreo;
    private EditText mEdiTextSurnames;
    private EditText mEdiTextWeight;
    private EditText mEditTextHeight;
    private EditText mEdiTextDate;
    private EditText mEdiTextPassword;
    private Button mButtonRegister;

    public static boolean registrotrue=false;
    public static String Idtrue="";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    //Variables
    private String name="";
    private String email="";
    private String surnames="";
    private String weight="";
    private String height="";
    private String date="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEdiTextName = (EditText) findViewById(R.id.nombre);
        mEditTextCorreo = (EditText) findViewById(R.id.editTextEmail);
        //mEdiTextSurnames = (EditText) findViewById(R.id.apellidos);
        mEdiTextWeight = (EditText) findViewById(R.id.peso);
        mEditTextHeight = (EditText) findViewById(R.id.altura);
        //mEdiTextDate = (EditText) findViewById(R.id.date);
        mEdiTextPassword = (EditText) findViewById(R.id.ingrese_su_password);
        mButtonRegister = (Button) findViewById(R.id.ButtonSendRegister);

        mButtonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                name = mEdiTextName.getText().toString();
                email = mEditTextCorreo.getText().toString();
                //surnames = mEdiTextSurnames.getText().toString();
                weight = mEdiTextWeight.getText().toString();
                height = mEditTextHeight.getText().toString();
                //date = mEdiTextDate.getText().toString();
                password = mEdiTextPassword.getText().toString();
                /*name = "Prueba1";
                mDatabase.child("Usuario").setValue(name);*/
            }
        });

    }


    private void registerUser(){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put( "name", name);
                    map.put("email", email);
                    map.put( "password", password);
                    //map.put( "surnames", surnames);
                    map.put( "weight", weight);
                    map.put( "height", height);
                    //map.put( "date", date);

                    String id= mAuth.getCurrentUser().getUid();
                    mDatabase.child("Usuario").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(Registre.this, MainActivity.class ));
                                registrotrue=true;
                                Idtrue= mAuth.getCurrentUser().getUid();
                                Log.d("myTag", "Su registro ha sido exitoso");
                                Toast.makeText( Registre.this, "Su registro ha sido exitoso", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Log.d("myTag", "EL rgistro fallo");
                                Toast.makeText( Registre.this, "EL registro fallo", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }else{
                    Toast.makeText( Registre.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();

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
                startActivity(new Intent(Registre.this, Profile.class ));
                return true;
            case R.id.action_salir:
                Login.Logintrue.signOut();
                startActivity(new Intent(Registre.this, Login.class ));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Results Screen
    /*public void return0(View v) {
        Intent return0 = new Intent(this, MainActivity.class);
        startActivity(return0);
    }*/
    //Results Screen
    public void term(View v) {
        Intent term = new Intent(this, TerminosCondiciones.class);
        startActivity(term);
    }
}