package com.example.reto1;

import static com.example.reto1.R.id;
import static com.example.reto1.R.layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    ImageView imageViewGif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Shared Preferences: Idioma e email
        SharedPreferences prefs = getSharedPreferences ("MisPreferencias",Context.MODE_PRIVATE); //Inicia shared preferences
        String correoSug = prefs.getString("email", ""); //Recoge las variables
        String idiomaUtil = prefs.getString("idiomaUtil", "");
        String idiomaNoUtil = prefs.getString("idiomaNoUtil", "en");
        String[] sugerencias= {correoSug}; //Coge el ultimo correo usado y lo mete en el autocomplete
        cambiarIdioma(idiomaUtil); //Cambia el idioma al ultimo utilizado

        // Se visualiza
        setContentView(layout.login);
        View layout = findViewById(R.id.layoutLogin);

        //Se crea una instancia de firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Intents
        Intent Index = new Intent(this, MenuPrincipal.class); // Te manda al menu principal al iniciar sesion
        Intent registro = new Intent(this, Registro.class); // Te manda a la pantalla de registrarse

        // TextViews
        AutoCompleteTextView correo = findViewById(R.id.editTextCorreo);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        if(!(sugerencias[0].equals(""))) // Si el ultimo email no es ""( es decir el predeterminado, porque no se ha introducido uno), se introduce el email
             adapter.add(sugerencias[0]);
        correo.setAdapter(adapter);

        TextView error=findViewById(id.textViewError);
        EditText contra=findViewById(id.editTextContrasena);

        // Botones
        Button registroButton = findViewById(R.id.botonRegistroLog); // Te manda a la pantalla registrar
        Button botonLogin=findViewById(id.buttonLogin); // Revisa si se inicio sesion correctamente y te manda al menu principal
        Button botonAnonimo=findViewById(id.botonAnonimo); //Activa el modo anonimo y te manda al menu principal
        Button botonIdioma=findViewById(id.buttonIdioma); //Cambia el idioma

        // Gif
        imageViewGif = findViewById(R.id.cargando);
        imageViewGif.setVisibility(View.INVISIBLE);

        // Strings
        String inicioCorrecto=getString(R.string.inicioCorrecto);
        String campoVacio=getString(R.string.campoVacio);
        String errorInicio=getString(R.string.errorInicio);
        String elecAnonimo=getString(R.string.elecAnonimo);
        String errorS=getString(R.string.error);
        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registro); // Manda a la pantalla registro

            }
        });
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                // Obtén el correo electrónico y la contraseña ingresados por el usuario
                String email = correo.getText().toString();
                String password = contra.getText().toString();

                // Intenta autenticar al usuario utilizando Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mostrarCargando(); //Muestra el GIF
                                    Index.putExtra("variableCorreo", correo.getText().toString()); // Envia el correo al menu principal
                                    error.setText(inicioCorrecto);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            SharedPreferences.Editor editor = prefs.edit(); // Crea el editor
                                            editor.putString("email", email); // Introduce el email
                                            editor.commit(); // Guarda los cambios

                                            try {
                                                // Pausa el hilo durante 3 segundos (3000 milisegundos)
                                                Thread.sleep(3000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            startActivity(Index);
                                            // Después de la pausa, puedes realizar más operaciones en este hilo
                                        }
                                    }).start();
                                        //Si alguno de los campos esta vacio
                                } else if (TextUtils.isEmpty(correo.toString()) || TextUtils.isEmpty(contra.toString())) {
                                    error.setText(campoVacio);
                                } else {
                                    correo.setText("");
                                    contra.setText("");
                                    error.setText(errorInicio);
                                }
                            }
                        });
            }catch (Exception e){
                    mostrarPopup(campoVacio,errorS); //Muestra en PopUp señalando el error
                }
            }
        });

        botonAnonimo.setOnClickListener(new View.OnClickListener() { // Crea una instancia anonima
            @Override
            public void onClick(View v) {
                correo.setText(""); // Resetea los campos de correo y contraseña
                contra.setText("");
                signInAnonymously(mAuth,campoVacio,error); // Inicia sesion de forma anonima
                error.setText(elecAnonimo); // Mensaje de error
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000); // Pausa el hilo durante 3 segundos (3000 milisegundos)
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Index.putExtra("variableCorreo", correo.getText().toString()); // Redirecciona al menu principal con el nombre de correo anonimo
                        startActivity(Index);
                        // Después de la pausa, puedes realizar más operaciones en este hilo
                    }
                }).start();
            }
        });

        botonIdioma.setOnClickListener(new View.OnClickListener() { // Cambia el idioma
            @Override
            public void onClick(View v) {
                cambiarIdioma(idiomaNoUtil);
                recreate(); // Reinicia la pantalla
                SharedPreferences.Editor editor = prefs.edit(); // Crea el editor
                editor.putString("idiomaUtil", idiomaNoUtil); // Añade el idioma que se ha cambiado en la variable idiomaNoUtil
                editor.putString("idiomaNoUtil", idiomaUtil);// Añade el idioma al que se ha cambiado en la variable idiomaUtil
                editor.commit(); // Guarda los cambios
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
    private void mostrarCargando(){
        imageViewGif.setVisibility(View.VISIBLE);//Visualiza el GIF
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .into(imageViewGif);
    }
    private void signInAnonymously(FirebaseAuth mAuth,String campoVacio,TextView error){
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // La autenticación anónima fue exitosa
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                mostrarCargando();
                                // El usuario anónimo se ha autenticado correctamente
                            }
                        } else {
                            // La autenticación anónima falló
                            error.setText(campoVacio);
                        }
                    }
                });
    }
    private void cambiarIdioma(String idioma){
        Configuration configuration = new Configuration(); // Crea la configuracion
        configuration.setLocale(new java.util.Locale(idioma)); // Cambia los recursos
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics()); // Actualiza los recursos
    }

}
