package com.codewithharry.prepquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllQuizActivity extends AppCompatActivity {

    private RecyclerView quiz_recycler_view;
    private ExtendedFloatingActionButton add_quiz_fab;

    private DatabaseReference quizNamesDatabaseReference;
    private ValueEventListener quiz_name_vel;
    private String subjectCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quiz);

        if(!HelperClass.TEACHER){
            subjectCode = getIntent().getStringExtra("subjectCode");
            if(subjectCode != null) HelperClass.CODE = subjectCode;
        }

        add_quiz_fab = findViewById(R.id.add_quiz_fab);
        quiz_recycler_view = findViewById(R.id.quiz_recycler_view);
        QuizAdapter quizAdapter = new QuizAdapter(this);
        quiz_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        quiz_recycler_view.setAdapter(quizAdapter);

        if(HelperClass.TEACHER){
            add_quiz_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AllQuizActivity.this,NewQuizActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            add_quiz_fab.setVisibility(View.INVISIBLE);
        }

        DatabaseReference root_database_reference = FirebaseDatabase.getInstance().getReference();
        quizNamesDatabaseReference = root_database_reference.child("Subjects").child(HelperClass.CODE).child("quiz_names");

        quiz_name_vel = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Quiz> quizList = new ArrayList<Quiz>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    quizList.add(ds.getValue(Quiz.class));
                }
                if(quizList.size() == 0) {
                    Toast.makeText(AllQuizActivity.this, "No quizzes available right now!", Toast.LENGTH_SHORT).show();
                } else {
                    quizAdapter.setQuizList(quizList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        quizNamesDatabaseReference.addValueEventListener(quiz_name_vel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        quizNamesDatabaseReference.removeEventListener(quiz_name_vel);
    }
}