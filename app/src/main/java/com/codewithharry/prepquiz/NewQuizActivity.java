package com.codewithharry.prepquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewQuizActivity extends AppCompatActivity {

    private List<Question> questionList;

    private EditText quiz_name_edit_text;
    private TextView no_of_questions_text_view;
    private EditText time_edit_text;
    private RecyclerView questions_recycler_view;
    private ExtendedFloatingActionButton add_questions_fab;

    private QuestionAdapter questionAdapter;

    private DatabaseReference databaseReference;
    private DatabaseReference quiz_database_reference;
    private DatabaseReference quiz_names_database_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);

        questionList = new ArrayList<Question>();
        HelperClass.questionList = questionList;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Subjects").child(HelperClass.CODE);
        quiz_database_reference = databaseReference.child("quizzes");
        quiz_names_database_reference = databaseReference.child("quiz_names");

        quiz_name_edit_text = findViewById(R.id.quiz_name_edit_text);
        no_of_questions_text_view = findViewById(R.id.no_of_questions_text_view);
        time_edit_text = findViewById(R.id.time_edit_text);
        questions_recycler_view = findViewById(R.id.questions_recycler_view);
        add_questions_fab = findViewById(R.id.add_question_fab);

        add_questions_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewQuizActivity.this,AddQuestionsActivity.class);
                startActivity(i);
            }
        });

        questionAdapter = new QuestionAdapter(this);
        questions_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        questions_recycler_view.setAdapter(questionAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        questions_recycler_view.addItemDecoration(decoration);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                int position = viewHolder.getAdapterPosition();
                questionList.remove(position);
                questionAdapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(questions_recycler_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        no_of_questions_text_view.setText(String.valueOf(questionList.size()));
        questionAdapter.setQuestionList(questionList);
        int s = questionList.size();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.done_button:
                String quizName = quiz_name_edit_text.getText().toString();
                String time = time_edit_text.getText().toString();
                quiz_database_reference.child(quizName).child("Questions").setValue(questionList);
                DatabaseReference dr = quiz_names_database_reference.push();
                dr.child("name").setValue(quizName);
                dr.child("time").setValue(time);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}