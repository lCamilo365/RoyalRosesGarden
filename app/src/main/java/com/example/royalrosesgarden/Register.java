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

public class Register extends AppCompatActivity {
    private Button btnSignUpRegister;
    private EditText editEmailRegister, editPasswordRegister, editConfirmRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btnSignUpRegister = (Button) findViewById(R.id.btnSignUpRegister);
        editEmailRegister = (EditText) findViewById(R.id.editEmailRegister);
        editPasswordRegister = (EditText) findViewById(R.id.editPasswordRegister);
        editConfirmRegister = (EditText) findViewById(R.id.editConfirmRegister);
        mAuth = FirebaseAuth.getInstance();

        btnSignUpRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmailRegister.getText().toString().trim();
                String watchWord = editPasswordRegister.getText().toString().trim();
                String confirm = editConfirmRegister.getText().toString().trim();

                if(watchWord.compareTo(confirm) == 0){
                    mAuth.createUserWithEmailAndPassword(email, watchWord)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Sign up error", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}