package com.codewithharry.prepquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddSubjectActivity extends AppCompatActivity {

    private EditText subject_code_edit_text;
    private Button add_subject_button;
    private RecyclerView subject_recycler_view;

    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    private List<String> subjectList;
    private SubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        subjectList = new ArrayList<>();

        subject_code_edit_text = findViewById(R.id.subject_code_edit_text);
        add_subject_button = findViewById(R.id.add_subject_button);
        subject_recycler_view = findViewById(R.id.subject_recycler_view);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(HelperClass.CODE);


        add_subject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectCode = subject_code_edit_text.getText().toString().trim();
                if(subjectCode.isEmpty()){
                    subject_code_edit_text.setError("Subject code is required!");
                    subject_code_edit_text.requestFocus();
                    return;
                }
                databaseReference.push().setValue(subjectCode);
            }
        });

        subjectAdapter = new SubjectAdapter(this);
        subject_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        subject_recycler_view.setAdapter(subjectAdapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                subjectList.add(snapshot.getValue(String.class));
                subjectAdapter.setSubjectList(subjectList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addChildEventListener(childEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(childEventListener);
    }
}