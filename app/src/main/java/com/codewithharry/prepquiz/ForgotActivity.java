package com.codewithharry.prepquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    private EditText email_edit_text;
    private Button reset_password_button;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        
        email_edit_text = findViewById(R.id.email_pr_edit_text);
        reset_password_button = findViewById(R.id.reset_password_button);
        progressBar = findViewById(R.id.progressBar3);
        
        mAuth = FirebaseAuth.getInstance();
        
        reset_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }
    
    private void resetPassword(){
        String email = email_edit_text.getText().toString().trim();
        
        if(email.isEmpty()){
            email_edit_text.setError("Email is required");
            email_edit_text.requestFocus();
            return;
        }
        
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_edit_text.setError("Please provide valid email");
            email_edit_text.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    Toast.makeText(ForgotActivity.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgotActivity.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}