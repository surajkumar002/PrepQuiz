package com.codewithharry.prepquiz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private List<Quiz> quizList;
    private final Context mContext;

    public QuizAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public QuizAdapter.QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item,parent,false);

        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.QuizViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);

        holder.quizNameTextView.setText(quiz.getName());
        holder.timeTextView.setText("Time : " + quiz.getTime() + " hours");
        if(!HelperClass.TEACHER){
            holder.resultButton.setText("Give Quiz");
        }
    }

    @Override
    public int getItemCount() {
        if(quizList == null) return 0;
        else return quizList.size();
    }

    public List<Quiz> getQuizes() { return quizList; }

    public void setQuizList(List<Quiz> quizList){
        this.quizList = quizList;
        notifyDataSetChanged();
    }

    class QuizViewHolder extends RecyclerView.ViewHolder{
        TextView quizNameTextView;
        TextView timeTextView;
        Button resultButton;

        public QuizViewHolder(View itemView){
            super(itemView);

            quizNameTextView = itemView.findViewById(R.id.student_name_text_view);
            timeTextView = itemView.findViewById(R.id.time_text_view);
            resultButton = itemView.findViewById(R.id.result_button);

            resultButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(HelperClass.TEACHER){
                        Intent intent = new Intent(mContext,ProgressActivity.class);
                        intent.putExtra("quiz_name",quizNameTextView.getText().toString());
                        mContext.startActivity(intent);

                    } else {
                        Intent intent = new Intent(mContext, GiveQuizActivity.class);
                        intent.putExtra("quiz_name",quizNameTextView.getText().toString());
                        intent.putExtra("time",timeTextView.getText().toString().replace("Time : ","").replace(" hours",""));
                        mContext.startActivity(intent);
                    }
                }
            });

        }
    }

}
