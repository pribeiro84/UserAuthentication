package com.android.userauthentication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText)findViewById(R.id.email);
        passwordEditText = (EditText)findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
    }

    public void SignIn(View v)
    {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (!task.isSuccessful())
                        {
                            Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Login.this, task.getResult().getUser().getEmail() + " logged in successful",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

    }

    public void Register(View v)
    {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, task.getResult().getUser().getEmail() + "signed up successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
