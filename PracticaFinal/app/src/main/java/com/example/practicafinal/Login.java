package com.example.practicafinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    EditText nombre,clave;
    Button btn,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        instancias();
        acciones();
    }

    private void acciones() {
        btn.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void instancias() {
        nombre = findViewById(R.id.user);
        clave = findViewById(R.id.clave);
        btn = findViewById(R.id.btn);
        btnLogin = findViewById(R.id.btnLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                if (nombre.getText().toString().isEmpty() || clave.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "faltan datos", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(nombre.getText().toString(), clave.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            try {
                                Log.v("funciono", task.getResult().toString());

                            }catch (RuntimeExecutionException e) {
                                //String a  =e.getMessage();
                                //a ="Ya existe esa cuenta";
                                FirebaseAuthUserCollisionException a = null;
                                //throw new IllegalStateException("YA EXISTE:"+a.getMessage());
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

                break;

            case R.id.btnLogin:
                if (nombre.getText().toString().isEmpty() || clave.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "faltan datos", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(nombre.getText().toString(), clave.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Log.v("registro", task.getResult().toString());
                            //Log.v("login", String.valueOf(task.isSuccessful()));

                            firebaseUser = firebaseAuth.getCurrentUser();
                            try {
                                if (task.isSuccessful() == true) {

                                    Intent intent = new Intent(getApplicationContext(), Principal.class);
                                    intent.putExtra("texto", firebaseUser.getUid());
                                    intent.putExtra("correo", firebaseUser.getEmail());
                                    startActivity(intent);
                                }
                            }catch (RuntimeExecutionException e){
                                Toast.makeText(getApplicationContext(),"Constraseña incorrecta"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                break;
            default:
        }
    }


}