package com.prospect.health;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonLogin;

    public static FirebaseAuth Logintrue;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    //Variables
    private SignInButton signInButton;
    public static final int SIGN_IN_CODE=777;
    private GoogleApiClient googleApiClient;
    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        Logintrue = mAuth;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEditTextEmail = (EditText) findViewById(R.id.editEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editPass);
        mButtonLogin = (Button) findViewById(R.id.buttonSingup);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mProgress.message
                email=mEditTextEmail.getText().toString();
                password=mEditTextPassword.getText().toString();
                if (!email.isEmpty()&&!password.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(Login.this, "Completa los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        GoogleSignInOptions gso= new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE);
            }
        });



    }


    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==SIGN_IN_CODE){
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            goMainScreen();
        }else{
            Toast.makeText(this,"No se pudo iniciar sesion",Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static String id ="";
    private void loginUser(){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    id= mAuth.getCurrentUser().getUid();
                    Log.d("hola id", id);
                    startActivity(new Intent(Login.this,MainActivity.class));
                    Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this, "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //id= mAuth.getCurrentUser().getUid();
        //Log.d("hola id", id);
    }

   public void forgetPassword(View v) {
        Intent term = new Intent(this, RememberPassword.class);
        startActivity(term);
    }

   /*public void Google(){

   }*/

    //Results Screen
    public void Registro(View v) {
        Intent Registro = new Intent(this, Registre.class);
        startActivity(Registro);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
