package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditarUsuario extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_usuario);

        //Texto
        EditText contra=findViewById(R.id.editTextEditarContra);
        EditText contraDeNuevo=findViewById(R.id.editTextEditarContra2);
        TextView errorContra=findViewById(R.id.errorContra);

        //Botones
        Button botonConfirmarEditar=findViewById(R.id.buttonConfirmarEditar); // Confirma el cambio
        Button botonAtras= findViewById(R.id.atrasEditar); // Te lleva a la pantalla anterior

        //Strings
        String errorContraCoinciden= getString(R.string.errorContraConciden);
        String errorS=getString(R.string.error);
        String campoVacio=getString(R.string.campoVacio);
        String errorContraTexto=getString(R.string.errorContra);

        //Intents
        Intent recibirDatos = getIntent(); //Recoge los datos
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo"); //Recibe el correo
        Intent atras = new Intent(this, AjustesUsuario.class); // Te manda a la pantalla anterior
        botonAtras.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                atras.putExtra("variableCorreo", correoRecibido); //Le pasa el correo a la pantalla indicada
                startActivity(atras); //Cambia de pantalla
            }
        });

        botonConfirmarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (contra.getText().toString().equals(contraDeNuevo.getText().toString())) { //Si las contraseñas son iguales

                        String nuevaContra = contra.getText().toString();

                        user.updatePassword(nuevaContra) //Actualiza la contraseña
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            contra.setText(""); //Borra lo escrito
                                            contraDeNuevo.setText("");
                                            errorContra.setText("");
                                        } else {
                                            errorContra.setText(errorContraTexto);
                                            contra.setText(""); //Borra lo escrito
                                            contraDeNuevo.setText("");
                                        }

                                    }
                                });
                    } else {
                        errorContra.setText(errorContraCoinciden);
                        contra.setText(""); //Borra lo escrito
                        contraDeNuevo.setText("");
                    }
                }catch(Exception e)
                {
                    mostrarPopup(campoVacio,errorS); // Muestra un PopUp indicando que ha habido un error
                }
            }
        });
    }
    private void mostrarPopup(String campoVacio,String errorS) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Crea el PopUp
        builder.setTitle(errorS); // Título del pop-up
        builder.setMessage(campoVacio); // Mensaje del pop-up
        // Mostrar el pop-up
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}