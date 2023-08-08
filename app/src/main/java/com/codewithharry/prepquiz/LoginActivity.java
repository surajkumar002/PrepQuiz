package com.codewithharry.prepquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_edit_text;
    private EditText password_edit_text;
    private Button login_button;
    private TextView forgot_password_text_view;
    private TextView sign_up_text_view;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference rootDataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        rootDataReference = FirebaseDatabase.getInstance().getReference();

        email_edit_text = findViewById(R.id.email_pr_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        login_button = findViewById(R.id.reset_password_button);
        forgot_password_text_view = findViewById(R.id.forgot_password_text_view);
        sign_up_text_view = findViewById(R.id.sign_up_text_view);
        progressBar = findViewById(R.id.progressBar3);

        sign_up_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(this);

        forgot_password_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();

        if(email.isEmpty()){
            email_edit_text.setError("Email is required");
            email_edit_text.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_edit_text.setError("Please enter a valid email");
            email_edit_text.requestFocus();
            return;
        }

        if(password.isEmpty()){
            password_edit_text.setError("Password is required");
            password_edit_text.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    rootDataReference.child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if(task.isSuccessful()){
                                User user = task.getResult().getValue(User.class);
                                assert user != null;
                                HelperClass.NAME = user.getName();
                                HelperClass.TEACHER = user.getTeacher();
                                HelperClass.CODE = user.getCode();
                                if(HelperClass.TEACHER){
                                    Intent intent = new Intent(LoginActivity.this,AllQuizActivity.class);
                                    startActivity(intent);
                                } else{
                                    Intent intent = new Intent(LoginActivity.this,AddSubjectActivity.class);
                                    startActivity(intent);
                                }
                            } else{
                                Toast.makeText(LoginActivity.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}