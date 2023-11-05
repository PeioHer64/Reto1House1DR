package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;


public class Reseñas extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("Reseñas");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escribir_resena);

        //Intents
        Intent paginaMostrarResena = new Intent(this, MostrarResena.class);//Te manda a mostrar reseña
        Intent recibirDatos = getIntent(); //Recoge los datos
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo"); //Recoge el correo

        //Buttons
        RatingBar rating = findViewById(R.id.ratingBar); //Para indicar las estrellas
        EditText infoResena=findViewById(R.id.editTextInfoResena);
        Button mostrarEstrellas = findViewById(R.id.buttonEstrellas); //Para recoger la cantidad de estrellas
        Button atras = findViewById(R.id.buttonAtrasResena); //Para volver a lapagina anterior

        //Strings
        String errorS=getString(R.string.error);
        String resenaVacia=getString(R.string.reseñaVacia);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paginaMostrarResena.putExtra("variableCorreo", correoRecibido); //Le pasa el correo a la pantalla indicada
                startActivity(paginaMostrarResena);//Cambia de pantalla
            }
        });
        mostrarEstrellas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float numEstrellas = rating.getRating();
                // Aquí colocas el código que se ejecutará cuando se haga clic en el botón.
                if(!infoResena.getText().toString().equals("")){ //si la reseña no esta vacia, recoje los datos
                ObjetoResena res = new ObjetoResena();
                res.setCorreoObjeto(correoRecibido);
                res.setResenaObjeto(infoResena.getText().toString());
                res.setPuntuacionObjeto(Float.valueOf(numEstrellas));
                res.setFechaObjeto(new Date());

                db.collection("Reseñas")//Entra en la coleccion, crea el documento y pone la informacion dentro
                        .document("Reseña" + new Date())
                        .set(res)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                infoResena.setText(""); //Resetea la pagina
                                rating.setRating(0);

                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error al guardar los datos
                            }
                        });
            }
                else{
                    mostrarPopup(errorS,resenaVacia);
                }
        }
        });
    }
    private void mostrarPopup(String errorS,String resenaVacia) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(errorS); // Título del pop-up
        builder.setMessage(resenaVacia); // Mensaje del pop-up
        // Mostrar el pop-up
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}