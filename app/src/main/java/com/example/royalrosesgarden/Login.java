package com.example.royalrosesgarden;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private Button btnSignInLogin, btnSignUpLogin;
    private EditText editEmailLogin, editPasswordLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnSignInLogin = (Button) findViewById(R.id.btnSignInLogin);
        btnSignUpLogin = (Button) findViewById(R.id.btnSignUpLogin);
        editEmailLogin = (EditText) findViewById(R.id.editEmailLogin);
        editPasswordLogin = (EditText) findViewById(R.id.editPasswordLogin);
        mAuth = FirebaseAuth.getInstance();

        btnSignInLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmailLogin.getText().toString().trim();
                String watchWord = editPasswordLogin.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email, watchWord)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), Products.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed sign in", Toast.LENGTH_SHORT).show();
                             }
                        });
            }
        });

        btnSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }
}