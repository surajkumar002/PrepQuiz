package com.codewithharry.prepquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionsActivity extends AppCompatActivity {

    private EditText question_edit_text;
    private EditText option_a_edit_text;
    private EditText option_b_edit_text;
    private EditText option_c_edit_text;
    private EditText option_d_edit_text;
    private Spinner correct_answer_spinner;

    private List<Question> questionList;
    ArrayAdapter<CharSequence> adapter;

    private int UPDATE_QUESTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        questionList = HelperClass.questionList;

        question_edit_text = findViewById(R.id.question_edit_text);
        option_a_edit_text = findViewById(R.id.option_a_edit_text);
        option_b_edit_text = findViewById(R.id.option_b_edit_text);
        option_c_edit_text = findViewById(R.id.option_c_edit_text);
        option_d_edit_text = findViewById(R.id.option_d_edit_text);
        correct_answer_spinner = findViewById(R.id.correct_answer_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        correct_answer_spinner.setAdapter(adapter);

        UPDATE_QUESTION = getIntent().getIntExtra("update_question",-10);
        if(UPDATE_QUESTION != -10) {
            updateActivity();
        }

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
                String question = question_edit_text.getText().toString();
                String option_a = option_a_edit_text.getText().toString();
                String option_b = option_b_edit_text.getText().toString();
                String option_c = option_c_edit_text.getText().toString();
                String option_d = option_d_edit_text.getText().toString();
                String correct_answer = correct_answer_spinner.getSelectedItem().toString();

                if(UPDATE_QUESTION != -10){
                    Question q = questionList.get(UPDATE_QUESTION);
                    q.question = question;
                    q.option_a = option_a;
                    q.option_b = option_b;
                    q.option_c = option_c;
                    q.option_d = option_d;
                    q.correct_answer = correct_answer;
                } else {
                    questionList.add(new Question(question,option_a,option_b,option_c,option_d,correct_answer));
                }
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateActivity(){
        Question q = questionList.get(UPDATE_QUESTION);
        question_edit_text.setText(q.getQuestion());
        option_a_edit_text.setText(q.getOption_a());
        option_b_edit_text.setText(q.getOption_b());
        option_c_edit_text.setText(q.getOption_c());
        option_d_edit_text.setText(q.getOption_d());
        correct_answer_spinner.setSelection(adapter.getPosition(q.getCorrect_answer()));
    }
}