package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoEmpresa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_empresa);

        //Intents para cambiar de pantalla
        Intent recibirDatos = getIntent();  //Recoge los datos
        Intent atras = new Intent(this, MenuPrincipal.class);// Te manda a la pantalla anterior
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo");//Recibe el correo

        //Botones
        Button botonAtras=findViewById(R.id.buttonAtrasInfo);// Te lleva a la pantalla anterior

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras.putExtra("variableCorreo", correoRecibido); //Le pasa el correo a la pantalla indicada
                startActivity(atras); //Cambia de pantalla
            }
        });
}}