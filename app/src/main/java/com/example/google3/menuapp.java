package com.example.google3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuapp extends AppCompatActivity {
    public Button regresar;
    private Button pArterial;
    private Button fCardiaca;
    private Button Glucemia;
    private Button tempCorporal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuapp);

        pArterial=(Button) findViewById(R.id.opciona);
        fCardiaca=(Button) findViewById(R.id.button2);
        Glucemia=(Button) findViewById(R.id.button3);
        tempCorporal=(Button) findViewById(R.id.button4);



        pArterial.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(getApplicationContext(), PersionArterial.class);
                startActivity(p);
            }
        });

        fCardiaca.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent p = new Intent(getApplicationContext(), FrecuenciaCardiaca.class);
                        startActivity(p);
                    }
                });

        Glucemia.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent p = new Intent(getApplicationContext(), Glucemia.class);
                        startActivity(p);
                    }
                });

        tempCorporal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent p = new Intent(getApplicationContext(), TempCorporal.class);
                        startActivity(p);
                    }
                });


        regresar = (Button) findViewById(R.id.atras);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menuapp.this, Main2Activity.class));
            }
        });
    }
}
