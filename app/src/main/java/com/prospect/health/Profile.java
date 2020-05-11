package com.prospect.health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
                startActivity(new Intent(Profile.this, Profile.class ));
                return true;
            case R.id.action_salir:
                Login.Logintrue.signOut();
                startActivity(new Intent(Profile.this, Login.class ));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Results Screen
    public void return0(View v) {
        Intent return0 = new Intent(this, MainActivity.class);
        startActivity(return0);
    }
}
