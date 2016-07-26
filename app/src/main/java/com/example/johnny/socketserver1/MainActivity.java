package com.example.johnny.socketserver1;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {


    TextView txtPuerto;
    clsServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtPuerto = (TextView) findViewById(R.id.txtPuerto);
    }


    public void iniciarServidor(View view){
        int puerto = Integer.parseInt(txtPuerto.getText().toString());
        server = new clsServer(this,puerto);
        server.execute();
    }
}
