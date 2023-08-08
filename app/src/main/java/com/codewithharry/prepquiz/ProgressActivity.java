package com.codewithharry.prepquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProgressActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        recyclerView = findViewById(R.id.student_score_recycler_view);

        String quizName = getIntent().getStringExtra("quiz_name");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Subjects").child(HelperClass.CODE).child("quizzes").child(quizName).child("Students");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProgressAdapter progressAdapter = new ProgressAdapter(this);
        recyclerView.setAdapter(progressAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Student> studentScoreList = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    String name = ds.getKey();
                    String score = ds.getValue(String.class);

                    Student s = new Student(name,score);
                    studentScoreList.add(s);
                }

                if(studentScoreList.size() != 0){
                    progressAdapter.setStudentScoreList(studentScoreList);
                } else Toast.makeText(ProgressActivity.this, "No data available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}