package com.codewithharry.prepquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GiveQuizActivity extends AppCompatActivity {

    private TextView timer_text_view;
    private RecyclerView give_question_recycler_view;
    private CountDownTimer countDownTimer;

    private int time_left_in_milliseconds;

    DatabaseReference databaseReferenceForQuestions;
    DatabaseReference databaseReferenceForStudents;
    ValueEventListener valueEventListener;

    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_quiz);

        timer_text_view = findViewById(R.id.timer_text_view);
        give_question_recycler_view = findViewById(R.id.give_question_recycler_view);

        GiveQuestionAdapter giveQuestionAdapter = new GiveQuestionAdapter(this);
        give_question_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        give_question_recycler_view.setAdapter(giveQuestionAdapter);

        Intent i = getIntent();
        String quiz_name = i.getStringExtra("quiz_name");
        time_left_in_milliseconds = (int) (Double.parseDouble(i.getStringExtra("time").toString()) * 3600000);

        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Subjects").child(HelperClass.CODE).child("quizzes").child(quiz_name);
        databaseReferenceForQuestions = dr.child("Questions");
        databaseReferenceForStudents = dr.child("Students");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Question> questions = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    questions.add(ds.getValue(Question.class));
                }
                questionList = questions;
                giveQuestionAdapter.setQuestionList(questions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReferenceForQuestions.addValueEventListener(valueEventListener);
        startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReferenceForQuestions.removeEventListener(valueEventListener);
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(time_left_in_milliseconds,1000) {
            @Override
            public void onTick(long l) {
                time_left_in_milliseconds = (int) l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                stopTimer();
                int marks = calculateMarks();
                uploadMarks(marks);
            }
        }.start();
    }

    public void stopTimer(){
        countDownTimer.cancel();
    }

    public void updateTimer(){
        int hours = (int) time_left_in_milliseconds / 3600000;
        int minutes = (int) (time_left_in_milliseconds % 3600000)/60000;
        int seconds = (int) ((time_left_in_milliseconds % 3600000)%60000) / 1000;

        String timeLeftText;
        timeLeftText = "";
        if(hours < 10 ) timeLeftText += "0";
        timeLeftText += hours + ":";
        if(minutes < 10) timeLeftText += "0";
        timeLeftText += minutes + ":";
        if(seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        timer_text_view.setText(timeLeftText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.done_button){
            stopTimer();
            int marks = calculateMarks();
            uploadMarks(marks);
        }
        return super.onOptionsItemSelected(item);
    }

    public int calculateMarks(){
        int count = 0;
        int x = give_question_recycler_view.getChildCount();
        GiveQuestionAdapter.GiveQuestionViewHolder holder;
        for(int i=0; i<x; i++){
            holder = (GiveQuestionAdapter.GiveQuestionViewHolder) give_question_recycler_view.getChildViewHolder(give_question_recycler_view.
                    getChildAt(i));
            String ticked = holder.option_a_radio_button.isChecked() ? "Option A" : holder.option_b_radio_button.isChecked() ? "Option B"
                                        : holder.option_c_radio_button.isChecked() ? "Option C" : holder.option_d_radio_button.isChecked() ? "Option D" : "no answer";
            String correct_answer = questionList.get(i).correct_answer;
            if(ticked.equals(correct_answer)) count++;
        }
        return count;
    }

    public void uploadMarks(int marks){
        Toast.makeText(this, "" + marks+ '/' +questionList.size(), Toast.LENGTH_SHORT).show();
        databaseReferenceForStudents.child(HelperClass.NAME).setValue("" + marks+ '/' +questionList.size());
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't leave the quiz until you submit it!", Toast.LENGTH_SHORT).show();
    }
}