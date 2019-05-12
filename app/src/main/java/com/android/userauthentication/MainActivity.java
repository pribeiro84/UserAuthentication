package com.android.userauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference;

    EditText questionField;
    EditText answerField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    userReference = database.getReference(user.getUid());
                } else {
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
            }
        };

        questionField = (EditText) findViewById(R.id.question_field);
        answerField = (EditText) findViewById(R.id.answer_field);

        userReference = database.getReference("HardCodedUser");
    }

    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }


    public void sendToFirebase(View v) {
        String q = questionField.getText().toString();
        String a = answerField.getText().toString();
        TestItem testItem = new TestItem(q, a);
        userReference.push().setValue(testItem);
    }

    public void logOut(View view) {
        mAuth.signOut();
    }
}
