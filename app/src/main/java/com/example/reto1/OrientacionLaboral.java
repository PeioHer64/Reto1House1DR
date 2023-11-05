package com.example.reto1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrientacionLaboral extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("Citas");
    DocumentReference docuRef = usersRef.document("Cita2");

    ObjetoCitas citaOL = new ObjetoCitas();
    String NombreCita= "Orientacion Laboral";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientacion_laboral);

        //Botones
        Button botonMostrarFechas = findViewById(R.id.buttonMostrarFechas2); // Muestra las fechas
        Button botonRadios = findViewById(R.id.buttonRadiosID); // Confirma la cita
        Button botonAtras=findViewById(R.id.atrasOrientacion); //  Para volver a la pantalla anterior

        //RadioButton donde iran las fechas
        RadioButton radioButtonB1 = findViewById(R.id.radioButtonB1);
        RadioButton radioButtonB2 = findViewById(R.id.radioButtonB2);
        RadioButton radioButtonB3 = findViewById(R.id.radioButtonB3);
        RadioButton radioButtonB4 = findViewById(R.id.radioButtonB4);
        RadioButton radioButtonB5 = findViewById(R.id.radioButtonB5);
        RadioGroup radioGroupBs = findViewById(R.id.RadioGroup);

        //Edit text
        EditText etMotivo = findViewById(R.id.editTextTextMotivo);

        //Intents para cambiar de pantalla
        Intent citas = new Intent(this, Citas.class); // Te lleva al menu citas
        Intent recibirDatos = getIntent();  //Recibe los datos enviados de otro intent
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo"); // Recoje el correo

        //Cambia la visibilidad de los RadioButton
        radioButtonB1.setVisibility(View.INVISIBLE);
        radioButtonB2.setVisibility(View.INVISIBLE);
        radioButtonB3.setVisibility(View.INVISIBLE);
        radioButtonB4.setVisibility(View.INVISIBLE);
        radioButtonB5.setVisibility(View.INVISIBLE);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citas.putExtra("variableCorreo", correoRecibido);//Pone el correo
                startActivity(citas); //Cambia de pantalla
            }

        });

        botonRadios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
                        ObjetoReserva reserv = new ObjetoReserva(); //Crea la reserva
                        //&&
                        if(radioButtonB1.isChecked()){ //Revisa si esta checkeado
                            //recoge las variables en el objeto
                            reserv.setCorreoObjeto(correoRecibido);
                            reserv.setCitaObjeto(NombreCita);
                            reserv.setFechaObjeto(radioButtonB1.getText().toString());
                            reserv.setMotivoCitaObjeto(etMotivo.getText().toString());
                        }
                        else if(radioButtonB2.isChecked()){
                            reserv.setCorreoObjeto(correoRecibido);
                            reserv.setCitaObjeto(NombreCita);
                            reserv.setFechaObjeto(radioButtonB2.getText().toString());
                            reserv.setMotivoCitaObjeto(etMotivo.getText().toString());

                        }
                        else if(radioButtonB3.isChecked()){
                            reserv.setCorreoObjeto(correoRecibido);
                            reserv.setCitaObjeto(NombreCita);
                            reserv.setFechaObjeto(radioButtonB3.getText().toString());
                            reserv.setMotivoCitaObjeto(etMotivo.getText().toString());

                        }
                        else if(radioButtonB4.isChecked()){
                            reserv.setCorreoObjeto(correoRecibido);
                            reserv.setCitaObjeto(NombreCita);
                            reserv.setFechaObjeto(radioButtonB3.getText().toString());
                            reserv.setMotivoCitaObjeto(etMotivo.getText().toString());

                        }
                        else if(radioButtonB5.isChecked()){
                            reserv.setCorreoObjeto(correoRecibido);
                            reserv.setCitaObjeto(NombreCita);
                            reserv.setFechaObjeto(radioButtonB5.getText().toString());
                            reserv.setMotivoCitaObjeto(etMotivo.getText().toString());
                        }
                        else{
                            //mensaje error
                        }

                        db.collection("Reservas")//Llama a la coleccion
                                .document("Reserva" + new Date()) //Crea un documento
                                .set(reserv) // Y le pone la informacion de la reserva
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) { //Borra los datos para una nueva reserva
                                        etMotivo.setText("");
                                        radioGroupBs.clearCheck();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error al guardar los datos
                                    }
                                });
                    }
                });
            }
        });

        botonMostrarFechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                citaOL.getArrayFechas().clear();
                usersRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {

                        for (QueryDocumentSnapshot document: querySnapshot){
                            if(document.getDate("fechaCitaOL1")!=null){ //Si la fecha no es null
                                // Log.d("TAG","OL fechas");
                                Date fechaCita = document.getDate("fechaCitaOL1");  //Recoge la fecha
                                citaOL.getArrayFechas().add(String.valueOf(fechaCita)); //La mete en un array de fechas

                                radioButtonB1.setVisibility(View.VISIBLE); //Cambia la visibilidad del radioButton
                                radioButtonB1.setText(String.valueOf(fechaCita)); // Y le pone la fecha
                            }
                            if(document.getDate("fechaCitaOL2")!=null){
                                Date fechaCita = document.getDate("fechaCitaOL2");
                                citaOL.getArrayFechas().add(String.valueOf(fechaCita));

                                radioButtonB2.setVisibility(View.VISIBLE);
                                radioButtonB2.setText(String.valueOf(fechaCita));
                            }
                            if(document.getDate("fechaCitaOL3")!=null){
                                Date fechaCita = document.getDate("fechaCitaOL3");
                                citaOL.getArrayFechas().add(String.valueOf(fechaCita));

                                radioButtonB3.setVisibility(View.VISIBLE);
                                radioButtonB3.setText(String.valueOf(fechaCita));
                            }
                            if(document.getDate("fechaCitaOL4")!=null){
                                Date fechaCita = document.getDate("fechaCitaOL4");
                                citaOL.getArrayFechas().add(String.valueOf(fechaCita));

                                radioButtonB4.setVisibility(View.VISIBLE);
                                radioButtonB4.setText(String.valueOf(fechaCita));
                            }
                            if(document.getDate("fechaCitaOL5")!=null){
                                Date fechaCita = document.getDate("fechaCitaOL5");
                                citaOL.getArrayFechas().add(String.valueOf(fechaCita));

                                radioButtonB5.setVisibility(View.VISIBLE);
                                radioButtonB5.setText(String.valueOf(fechaCita));
                            }

                        }


                    }

                });
            }
        });








    }


}