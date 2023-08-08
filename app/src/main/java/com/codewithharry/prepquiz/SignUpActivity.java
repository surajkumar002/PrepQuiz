package com.codewithharry.prepquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText full_name_edit_text;
    private EditText email_edit_text;
    private EditText password_edit_text;
    private RadioButton student_radio_button;
    private RadioButton teacher_radio_button;
    private EditText code_edit_text;
    private Button sign_up_button;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootDatabaseReference = firebaseDatabase.getReference();

        full_name_edit_text = findViewById(R.id.full_name_edit_text);
        email_edit_text = findViewById(R.id.email_address_edit_text_sign_up);
        password_edit_text = findViewById(R.id.password_edit_text_sign_up);
        student_radio_button = findViewById(R.id.student_radio_button);
        teacher_radio_button = findViewById(R.id.teacher_radio_buttton);
        code_edit_text = findViewById(R.id.code_edit_text);
        sign_up_button = findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.progressBar);

        student_radio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code_edit_text.setVisibility(View.VISIBLE);
                code_edit_text.setHint("Enter roll no.");
            }
        });
        teacher_radio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code_edit_text.setVisibility(View.VISIBLE);
                code_edit_text.setHint("Enter subject code");
            }
        });

        sign_up_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();
        String fullName = full_name_edit_text.getText().toString().trim();

        if(fullName.isEmpty()){
            full_name_edit_text.setError("Full name is required");
            full_name_edit_text.requestFocus();
            return;
        }

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

        if(password.isEmpty()){
            password_edit_text.setError("Password is required");
            password_edit_text.requestFocus();
            return;
        }

        if( !(student_radio_button.isChecked() || teacher_radio_button.isChecked()) ){
            Toast.makeText(this, "Please select Student or Teacher", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean teacher = teacher_radio_button.isChecked();

        String code = code_edit_text.getText().toString().trim();
        if(code.isEmpty()){
            code_edit_text.setError("This field is required!");
            code_edit_text.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        signUp(fullName,email,password,teacher,code);
    }

    private void signUp(String name,String email,String password,boolean teacher,String code){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        rootDatabaseReference.child(user.getUid()).setValue(new User(name, teacher, code)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(SignUpActivity.this, "Account crea   ted successfully! Please Login", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(SignUpActivity.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignUpActivity.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}