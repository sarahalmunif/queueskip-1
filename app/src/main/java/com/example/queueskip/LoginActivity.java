package com.example.queueskip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private EditText Name;
    private EditText Pass;
    private TextView attempts;
    private Button login;
    private int counter=5;//to count the incorrect attempts
    private TextView forgot;//not yet
    private TextView register;
    private FirebaseAuth firebaseAuth;
    //private ProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name =  findViewById(R.id.username);
        Pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        attempts=findViewById(R.id.attempts);
        forgot=findViewById(R.id.forgot);
        register=findViewById(R.id.register);


        attempts.setText("Number of attempts remaining:5");// wrote down number 5 so it doesn't appear empty at first.

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser(); //checks if user already  logged in to direct him to the next activity

        if(user!=null){ //not very much
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(),Pass.getText().toString());

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class) );//to razan's page(Registration)

            }
        });


    }//end on create.
    private void validate(String name,String pass){
        firebaseAuth.signInWithEmailAndPassword(name,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class) );
                }
                else{
                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    counter--;
                    attempts.setText("Number of attempts remaining: "+ counter);
                    if(counter==0){
                        login.setEnabled(false);
                    }
                }
            }
        });


    }
}
