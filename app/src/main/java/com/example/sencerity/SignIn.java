package com.example.sencerity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;


public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private TextView username;
    private TextView password;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        // authentication instance



        username = findViewById(R.id.usernameTextView);
        password = findViewById(R.id.passwordTextView);

        findViewById(R.id.loginButton).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //check to see if user is already signed in, update UI
       // FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //sign in success
                            Log.d(TAG, "signIn:success");

                            Intent activityChangeIntent = new Intent(SignIn.this, MainMenu.class);

                            // currentContext.startActivity(activityChangeIntent);

                            SignIn.this.startActivity(activityChangeIntent);
                   //         FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.d(TAG,"sign in failed", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }


    @Override
    public void onClick(View v){
        int i= v.getId();
        if(i == R.id.loginButton)
        {
            signInWithEmailAndPassword(username.getText().toString(), password.getText().toString());
        }
    }

}





