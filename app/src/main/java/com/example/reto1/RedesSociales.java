package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RedesSociales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redes_sociales);
        //Intents
        Intent recibirDatos = getIntent(); //Recibe los datos
        Intent menu = new Intent(this, MenuPrincipal.class); // Te lleva al menu principal
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo"); //recoge el correo

        //Botones
        ImageView instagram = findViewById(R.id.instagram); //Te manda a instagram
        ImageView facebook = findViewById(R.id.facebook); //Te manda a facebook
        ImageView twitter = findViewById(R.id.twitter); //Te manda a twitter
        Button botonAtras=findViewById(R.id.button); //te manda al menu principal
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.putExtra("variableCorreo", correoRecibido);//Pone el correo
                startActivity(menu); //Cambia de pantalla
            }

        });


            instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes definir lo que sucede cuando se hace clic en la imagen (acción de botón)
                // Por ejemplo, puedes abrir otra actividad o mostrar un mensaje.
                String instagramUrl = "https://www.instagram.com";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
                //intent.setPackage("com.google.android.youtube"); // Intenta abrir la aplicación de YouTube si está instalada

                startActivity(intent);

            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes definir lo que sucede cuando se hace clic en la imagen (acción de botón)
                // Por ejemplo, puedes abrir otra actividad o mostrar un mensaje.
                String facebookUrl = "https://www.facebook.com";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                //intent.setPackage("com.google.android.youtube"); // Intenta abrir la aplicación de YouTube si está instalada

                startActivity(intent);

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes definir lo que sucede cuando se hace clic en la imagen (acción de botón)
                // Por ejemplo, puedes abrir otra actividad o mostrar un mensaje.
                String twitterUrl = "https://www.twitter.com";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl));
                //intent.setPackage("com.google.android.youtube"); // Intenta abrir la aplicación de YouTube si está instalada

                startActivity(intent);

            }
        });
    }
}