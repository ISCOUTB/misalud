package com.prospect.health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TerminosCondiciones extends AppCompatActivity {

    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_terminos_condiciones);
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
                startActivity(new Intent(TerminosCondiciones.this, Profile.class ));
                return true;
            case R.id.action_salir:
                Login.Logintrue.signOut();
                startActivity(new Intent(TerminosCondiciones.this, Login.class ));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void returnn(View v){
        Intent returnn = new Intent(this, Registre.class);
        startActivity(returnn);
    }

    public void DOONE(View v) {
        Intent Register = new Intent(this, Registre.class);
        startActivity(Register);
    }

}