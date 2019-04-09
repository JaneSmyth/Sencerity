package com.example.sencerity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoggedIn<user> extends AppCompatActivity {

    private TextView data;
    private String names;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);



        data = findViewById(R.id.dataTextView);
        //database initialisation
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("name");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                names = dataSnapshot.getValue(String.class);
                data.setText(names);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//LOGGING OUT, NEED TO COME BACK TO THIS
    public void onClick(View v)
    {
        if(v.getId() == R.id.logOutButton)
            signOut();
           // currentUser();
    }

    private void signOut(){

        FirebaseAuth.getInstance().signOut();
        currentUser();

    }
    private void currentUser(){

        if(user != null)
            Toast.makeText(LoggedIn.this, "currentUser Logged in", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(LoggedIn.this, "No current user", Toast.LENGTH_SHORT).show();

    }
//LOGGING OUT END
}
