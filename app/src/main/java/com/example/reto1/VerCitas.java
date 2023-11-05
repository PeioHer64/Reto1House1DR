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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
public class VerCitas extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("Reservas");
    ArrayList<ObjetoReserva> listaReservas = new ArrayList<ObjetoReserva>();
    String texto="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_citas);

        //Intents
        Intent recibirDatos = getIntent();
        Intent citas = new Intent(this, Citas.class);
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo");

        //Buttons
        Button botonVerCitas = findViewById(R.id.buttonVerCitas2); // Te muestra las citas
        Button botonAtras = findViewById(R.id.atrasVerCitas); // Te manda a la pagina de citas
        TextView textoPrueba = findViewById(R.id.textViewVerCitas);
        botonVerCitas.setEnabled(true); //habilita el uso del boton
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                citas.putExtra("variableCorreo", correoRecibido); //Le pasa el correo a la pantalla indicada
                startActivity(citas);//Cambia de pantalla
            }
        });
        botonVerCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texto="";
                textoPrueba.setText("");
                // Quita el texto por si le clickan otra vez
                db.collection("Reservas")//LLama a la coleccion
                        .whereEqualTo("correoObjeto", correoRecibido) // Muestra solo las reservas que tengan el correo introducido
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {//Lee los documentos
                                        // Aquí puedes manejar los documentos que cumplen con el filtro
                                        // document.getData() te dará los datos del documento
                                        ObjetoReserva reservaCita = new ObjetoReserva();//Crea un objeto reserva

                                       //Mete todos los datos al objeto
                                        reservaCita.setFechaObjeto(document.getString("fechaObjeto"));
                                        reservaCita.setCitaObjeto(document.getString("citaObjeto"));
                                        reservaCita.setMotivoCitaObjeto(document.getString("motivoCitaObjeto"));
                                        reservaCita.setCorreoObjeto(document.getString("correoObjeto"));
                                        //Lo añade al array
                                        listaReservas.add(reservaCita);
                                    }
                                } else {

                                }
                            }
                        });
                //leo el array y lo visualizo
                for(int i=0;i<listaReservas.size();i++){
                    texto+= "Correo: "+listaReservas.get(i).getCorreoObjeto()+"\n"+"Nombre del servicio: "+listaReservas.get(i).getCitaObjeto()+"\n"+"Fecha: "+listaReservas.get(i).getFechaObjeto()+"\n"+"Motivo: "+listaReservas.get(i).getMotivoCitaObjeto()+"\n\n";
                }
                textoPrueba.setText(texto);
                listaReservas.clear();
                if(!textoPrueba.getText().equals("")) {
                    botonVerCitas.setEnabled(false); //deshabilita el uso del boton
                }
            }
        });
    }
}