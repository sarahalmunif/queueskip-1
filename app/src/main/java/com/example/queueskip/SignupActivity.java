package com.example.queueskip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private Button registerBtn;
    EditText editName,editEmail,editPass,editConfPass;
    DatabaseReference reff;
    private User user;
    private FirebaseAuth firebaseAuth;
    boolean register = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //set toolbar
        Toolbar toolbar=findViewById(R.id.registerId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("hello");



        editName=findViewById(R.id.signUpNameET);
        editEmail=findViewById(R.id.signUpEmailET);
        editPass=findViewById(R.id.signUpPasswordET);
        editConfPass=findViewById(R.id.confPasswordET);
        registerBtn=findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(this);
        reff= FirebaseDatabase.getInstance().getReference().child("User");

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void onClick(View view) {

        String nameInput = editName.getText().toString();
        String emailInput = editEmail.getText().toString();
        String passInput = editPass.getText().toString();
        String confPassInput = editConfPass.getText().toString();

        if(validate()) {
            user = new User();

            user.setUsername(nameInput);
            user.setEmail(emailInput);
            user.setPassword(passInput);
            reff.push().setValue(user);


            firebaseAuth.createUserWithEmailAndPassword(emailInput,passInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),
                            "inside onComplete",
                            Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this,MainActivity.class));

                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "inside onComplete[failed]",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }//end of onClick


    private boolean validate() {
      return true;

    }


    /*
    public boolean checkEmailExistsOrNot(){
        firebaseAuth.fetchSignInMethodsForEmail(editEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
               // Log.d(TAG,""+task.getResult().getSignInMethods().size());
                if (task.getResult().getSignInMethods().size() == 0){
                    // email not existed
                    Toast.makeText(getApplicationContext(), "email not existed", Toast.LENGTH_SHORT).show();
                    register=true;

                }else {
                    // email existed
                    Toast.makeText(getApplicationContext(), "email existed", Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
        return register;
    }



     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}//end of SignUp Activity


