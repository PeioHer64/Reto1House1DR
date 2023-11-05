package com.example.reto1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class MostrarResena extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("Reseñas");
    String correo;
    String mensaje;
    Double puntuacion;
    Date fecha;
    ArrayList<String> Resultados = new ArrayList<String>();
    String datos="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_resena);

        //Botones
        Button botonMostrar = findViewById(R.id.buttonMostrar); // Te muestra las reseñas
        Button botonAnadir = findViewById(R.id.buttonAnadirResena); //
        Button botonAtras = findViewById(R.id.buttonAtrasMostrarResena);

        //Intents
        Intent paginaPrincipal = new Intent(this, MenuPrincipal.class);// Te manda a la pagina principal
        Intent paginaAnadirResena = new Intent(this, Reseñas.class); //Te manda a la pagina de escribir reseña
        Intent recibirDatos = getIntent(); //Recoje los datos
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo"); //Recoge el correo

        //Strings
        String correoS=getString(R.string.correo);
        String resenaS=getString(R.string.resena);
        String puntuacionS=getString(R.string.puntuacion);
        String fechaS=getString(R.string.fecha);
        String aviso= getString(R.string.aviso);
        String inicioNecesario=getString(R.string.inicioNecesario);
        TextView textoResena=findViewById(R.id.textViewMostrarResena);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paginaPrincipal.putExtra("variableCorreo", correoRecibido);
                startActivity(paginaPrincipal);
            }
        });

        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
                        datos="";
                        for (QueryDocumentSnapshot document: querySnapshot){
                            correo = document.getString("correoObjeto");
                            mensaje=document.getString("resenaObjeto");
                            puntuacion=document.getDouble("puntuacionObjeto");
                            fecha=document.getDate("fechaObjeto");

                            Resultados.add(correo);
                            Resultados.add(mensaje);
                            Resultados.add(puntuacion.toString());
                            Resultados.add(fecha.toString());

                        }
                        int i=0;
                        int resenaMax=0;
                        for(i=Resultados.size()-12;i<Resultados.size()&&resenaMax<3;i=i+4){
                            datos+= correoS+": " + ""+Resultados.set(i,correo)+"\n"+""+resenaS +": "+Resultados.set(i+1,mensaje)+"\n"+""+puntuacionS+": "+Resultados.set(i+2,puntuacion.toString())+"\n"+""+fechaS +": "+Resultados.set(i+3,fecha.toString())+"\n"+"\n";
                            resenaMax++;
                        }
                        i=Resultados.size()-12;
                            textoResena.setText(datos);
                        Resultados.clear();
                    }

                });

            }
        });
        botonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!correoRecibido.isEmpty()) {
                    paginaAnadirResena.putExtra("variableCorreo", correoRecibido);
                    startActivity(paginaAnadirResena);
                }
                else{
                    mostrarPopup(aviso, inicioNecesario);
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