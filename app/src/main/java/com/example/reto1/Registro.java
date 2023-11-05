package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView error;
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        Intent volverlogin = new Intent(this, Login.class);

        //Buttons
        Button registroButton = findViewById(R.id.botonRegistro); // Te registra en la base de datos
        Button atrasButton = findViewById(R.id.botonAtras); //Te lleva al login

        //EditText
        EditText correoEditText = findViewById(R.id.editTextNombre);
        error = findViewById(R.id.errorRegistro);
        error.setVisibility(View.INVISIBLE);
        EditText contraseñaEditText = findViewById(R.id.editTextContra);
        EditText correo = correoEditText;
        EditText contraseña = contraseñaEditText;

        //Strings
        String errorRegistro= getString(R.string.errorRegistro);
        String errorS=getString(R.string.error);
        String campoVacio=getString(R.string.campoVacio);
        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario(volverlogin,correo,contraseña,errorRegistro,errorS,campoVacio); //Registra el usuario
            }
        });

        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(volverlogin);
            }
        });
    }

    private void registrarUsuario(Intent volverlogin,EditText correo,EditText contraseña,String errorRegistro,String errorS,String campoVacio) {
        // Obtener el correo electrónico y la contraseña ingresados por el usuario
        try{
        // Crear un nuevo usuario en Firebase Authentication
        mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registro exitoso, el usuario está autenticado
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(volverlogin);
                            // Puedes realizar acciones adicionales aquí, como redirigir al usuario a la página principal.
                            error.setVisibility(View.INVISIBLE);
                        } else {
                            // El registro falló, muestra un mensaje de error al usuario.
                            error.setVisibility(View.VISIBLE);
                            correo.setText("");
                            contraseña.setText("");

                            Toast.makeText(Registro.this, errorRegistro, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }catch (Exception e){
            correo.setText(""); // Borra lo escito y salta el mensaje de error
            contraseña.setText("");
            mostrarPopup(errorS,campoVacio);
        }
    }



    private void mostrarPopup(String errorS,String campoVacio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(errorS); // Título del pop-up
        builder.setMessage(campoVacio); // Mensaje del pop-up
        // Mostrar el pop-up
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}