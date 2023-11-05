package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AjustesUsuario extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustes_usuario);

        //Intents para cambiar de pantalla
        Intent recibirDatos = getIntent(); //Recibe los datos enviados de otro intent
        Intent paginaLogin = new Intent(this, Login.class); //Te lleva al login
        Intent paginaEditarUsuario = new Intent(this, EditarUsuario.class); //Te lleva a cambiar la contraseña
        Intent intentMenu = new Intent(this, MenuPrincipal.class); //Te lleva al menu principal
        String correoRecibido = recibirDatos.getStringExtra("variableCorreo"); //Recoge el correo

        //Botones
        Button botonEliminar= findViewById(R.id.buttonEliminarUsuario);  //Elimina el usuario
        Button botonEditar= findViewById(R.id.buttonEditarUsuario); // Para cambiar la contraseña
        Button botonAtras= findViewById(R.id.buttonAtrasAjustes); // Para volver a la pantalla anterior

        //Strings
        String usuarioElim=getString(R.string.usuarioElim);
        String errorUsuarioElim=getString(R.string.errorUsuarioElim);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMenu.putExtra("variableCorreo", correoRecibido); //Le pasa el correo a la pantalla indicada
                startActivity(intentMenu); //Cambia de pantalla
            }});

            botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) { //Si existe el usuario
                    user.delete() //Se borra
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Usuario eliminado exitosamente
                                        // Puedes realizar alguna acción adicional si es necesario
                                        Toast.makeText(getApplicationContext(), usuarioElim, Toast.LENGTH_SHORT).show();
                                        startActivity(paginaLogin);
                                    } else {
                                        // Error al eliminar el usuario
                                        Toast.makeText(getApplicationContext(), errorUsuarioElim, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paginaEditarUsuario.putExtra("variableCorreo", correoRecibido); //Le pasa el correo a la pantalla indicada
                startActivity(paginaEditarUsuario); //Cambia de pantalla
            }
        });
    }
}