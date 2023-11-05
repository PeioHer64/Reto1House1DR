package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
        //Boton
        Button botonAtras = findViewById(R.id.buttonAtras); // Te manda a la pantalla de iniciar sesion

        //Imagenes
        ImageView botonInfo = findViewById(R.id.i); //Imagen que te manda a la pantalla de informacion
        ImageView pedirCita = findViewById(R.id.cita); //Imagen que te manda a la pantalla de pedir cita
        ImageView resenas = findViewById(R.id.resenas); //Imagen que te manda a la pantalla de reseñas
        ImageView redesSociales = findViewById(R.id.instagram); //Imagen que te manda a la pantalla de redes sociales
        ImageView ajustes = findViewById(R.id.ajustes); //Imagen que te manda a la pantalla de ajustes

        //Intents
        Intent redSocial = new Intent(this, RedesSociales.class); //Te redirige a la pantalla de redes sociales
        Intent atras = new Intent(this, Login.class); //Te redirige a la pantalla de login
        Intent paginaCitas = new Intent(this, Citas.class); //Te redirige a la pantalla de citas
        Intent paginaResenas = new Intent(this, MostrarResena.class); //Te redirige a la pantalla de reseñas
        Intent paginaAjustes = new Intent(this, AjustesUsuario.class); //Te redirige a la pantalla de ajustes
        Intent paginaInfo = new Intent(this,InfoEmpresa.class); //Te redirige a la pantalla de informacion

        Intent recibirDatos = getIntent(); //Recoge los datos
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo"); //Recoge el correo

        //Strings
        String aviso= getString(R.string.aviso);
        String inicioNecesario=getString(R.string.inicioNecesario);
        botonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paginaInfo.putExtra("variableCorreo", correoRecibido); //Le pasa el correo a la pantalla indicada
                startActivity(paginaInfo); //Cambia de pantalla
            }
        });
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(atras);
            } //Cambia de pantalla
        });
        redesSociales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redSocial.putExtra("variableCorreo", correoRecibido);//Le pasa el correo a la pantalla indicada
                startActivity(redSocial); //Cambia de pantalla

            }
        });
        pedirCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!correoRecibido.isEmpty()){ //Si se ha iniciado sesion
                    paginaCitas.putExtra("variableCorreo", correoRecibido);//Le pasa el correo a la pantalla indicada
                    startActivity(paginaCitas); //Cambia de pantalla
                }
                else{ //Si no se ha iniciado sesion
                    mostrarPopup(aviso, inicioNecesario); //Muestra PopUp (Mensaje de error)
                }
            }
        });
        resenas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paginaResenas.putExtra("variableCorreo", correoRecibido);//Le pasa el correo a la pantalla indicada
                startActivity(paginaResenas); //Cambia de pantalla

            }
        });
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!correoRecibido.isEmpty()) {//Si se ha iniciado sesion
                    paginaAjustes.putExtra("variableCorreo", correoRecibido);//Le pasa el correo a la pantalla indicada
                    startActivity(paginaAjustes); //Cambia de pantalla
                }
                else{
                    mostrarPopup(aviso, inicioNecesario);//Muestra PopUp (Mensaje de error)
                }

            }
        });
    }
    private void mostrarPopup(String aviso,String inicioNecesario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(aviso); // Título del pop-up
        builder.setMessage(inicioNecesario); // Mensaje del pop-up
        // Mostrar el pop-up
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}