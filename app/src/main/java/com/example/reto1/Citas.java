package com.example.reto1;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;


public class Citas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citas);

        //Botones
        Button botonAtencionMultilingue = findViewById(R.id.buttonAtencionMultilingue); // Te manda a atencion Multilingue
        Button botonOrientacionLaboral = findViewById(R.id.buttonOrientacionLaboral); // Te manda a orientacion laboral
        Button botonServicioTransporte = findViewById(R.id.buttonServicioTransporte); // Te manda a servicio transporter
        Button botonServicioCatering = findViewById(R.id.buttonServicioCatering); // Te manda a servicio catering
        Button botonIntegracionCultural = findViewById(R.id.buttonIntegracionCultural);
        Button botonVerCitas = findViewById(R.id.verCitas);// Te manda a ver citas
        Button botonAtras=findViewById(R.id.atrasCitas); // Te manda al menu principal

        //Intents para cambiar de pagina
        Intent recibirDatos = getIntent(); //Recibe los datos enviados de otro intent
        Intent atencionMultilingue = new Intent(this,AtencionMultilingue.class); //Te lleva a atencion atencion Multilingue
        Intent orientacionLaboral = new Intent(this,OrientacionLaboral.class);//Te lleva a orientacion laboral
        Intent servicioTransporte = new Intent(this,ServicioTransporte.class);//Te lleva a servicio transporte
        Intent servicioCatering = new Intent(this,ServicioCatering.class); //Te lleva a servicio catering
        Intent integracionCultural = new Intent(this,IntegracionCultural.class); //Te lleva a integracion cultural
        Intent verCitas = new Intent(this, VerCitas.class); //Te lleva a ver citas
        Intent menu = new Intent(this, MenuPrincipal.class); //Te lleva al menu
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo"); //Recoge el correo

        botonVerCitas.setOnClickListener(new View.OnClickListener() {
        @Override
         public void onClick(View view) {
            verCitas.putExtra("variableCorreo", correoRecibido); //Envia el correo
            startActivity(verCitas); //Cambia de pantalla
          }
        });
            botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.putExtra("variableCorreo", correoRecibido); //Envia el correo
                startActivity(menu); //Cambia de pantalla
            }

        });
        botonAtencionMultilingue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atencionMultilingue.putExtra("variableCorreo", correoRecibido);
                startActivity(atencionMultilingue);

            }
        });
        botonOrientacionLaboral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orientacionLaboral.putExtra("variableCorreo", correoRecibido);
                startActivity(orientacionLaboral);
            }
        });
        botonServicioTransporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicioTransporte.putExtra("variableCorreo", correoRecibido);
                startActivity(servicioTransporte);
            }
        });
        botonServicioCatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicioCatering.putExtra("variableCorreo", correoRecibido);
                startActivity(servicioCatering);
            }
        });
        botonIntegracionCultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integracionCultural.putExtra("variableCorreo", correoRecibido);
                startActivity(integracionCultural);
            }
        });






    }
}
