package com.prospect.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class TerminosCondiciones extends AppCompatActivity {
    private WebView miWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos_condiciones);
        miWebView=findViewById(R.id.wv_main);
        miWebView.getSettings().setJavaScriptEnabled(true);
        miWebView.setWebViewClient(new WebViewClient());
        miWebView.loadUrl("https://docs.google.com/document/u/2/d/e/2PACX-1vTnxh74CE6JFofs5duKV7fqeFRP5WpfeB_AuyWep6x_KKMy_PDnKfnbwPFqsvlNku4cCsmE66ztwn4m/pub");

    }


    public void returnn(View v){
        Intent returnn = new Intent(this, Login.class);
        startActivity(returnn);
    }

    public void acept(View v){
        Intent returnn = new Intent(this, Registre.class);
        startActivity(returnn);
    }
}