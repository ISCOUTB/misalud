package com.prospect.health;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class RememberPassword extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonLogin;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    //Variables
    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember);
    }

    /*public void RememberPassword(){
        Firebase.Auth.FirebaseUser user = auth.CurrentUser;
        String newPassword = "SOME-SECURE-PASSWORD";
        if (user != null) {
            user.UpdatePasswordAsync(newPassword).ContinueWith(task => {
            if (task.IsCanceled) {
                Debug.LogError("UpdatePasswordAsync was canceled.");
                return;
            }
            if (task.IsFaulted) {
                Debug.LogError("UpdatePasswordAsync encountered an error: " + task.Exception);
                return;
            }

            Debug.Log("Password updated successfully.");
            });
        }
    }*/
}
