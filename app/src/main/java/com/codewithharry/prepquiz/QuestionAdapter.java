package com.codewithharry.prepquiz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;
    private Context mContext;

    public QuestionAdapter(Context mcontext){
        mContext = mcontext;
    }

    @NonNull
    @Override
    public QuestionAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.question_list_item,parent,false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);

        String q = question.getQuestion();
        String option_a = question.getOption_a();
        String option_b = question.getOption_b();
        String option_c = question.getOption_c();
        String option_d = question.getOption_d();
        String correct_answer = question.getCorrect_answer();

        holder.question_text_view.setText(q);
        holder.option_a_text_view.setText(option_a);
        holder.option_b_text_view.setText(option_b);
        holder.option_c_text_view.setText(option_c);
        holder.option_d_text_view.setText(option_d);
        holder.correct_answer_text_view.setText(correct_answer);
    }

    @Override
    public int getItemCount() {
        if(questionList == null) return 0;
        else return questionList.size();
    }

//    public List<Question> getQuestionList() { return questionList; }

    public void setQuestionList(List<Question> qL){
        questionList = qL;
        notifyDataSetChanged();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView question_text_view;
        private TextView option_a_text_view;
        private TextView option_b_text_view;
        private TextView option_c_text_view;
        private TextView option_d_text_view;
        private TextView correct_answer_text_view;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            question_text_view = itemView.findViewById(R.id.question_text_view);
            option_a_text_view = itemView.findViewById(R.id.option_a_text_view);
            option_b_text_view = itemView.findViewById(R.id.option_b_text_view);
            option_c_text_view = itemView.findViewById(R.id.option_c_text_view);
            option_d_text_view = itemView.findViewById(R.id.option_d_text_view);
            correct_answer_text_view = itemView.findViewById(R.id.correct_answer_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext,AddQuestionsActivity.class);
            intent.putExtra("update_question",this.getAdapterPosition());
            mContext.startActivity(intent);
        }
    }
}
