package com.codewithharry.prepquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GiveQuestionAdapter extends RecyclerView.Adapter<GiveQuestionAdapter.GiveQuestionViewHolder>{

    private Context mContext;
    private List<Question> questionList;

    public GiveQuestionAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public GiveQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.give_question_list_item,parent,false);
        return new GiveQuestionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GiveQuestionViewHolder holder, int position) {
        Question q = questionList.get(position);

        holder.question_text_view.setText("Q. " + q.question);
        holder.option_a_radio_button.setText(q.option_a);
        holder.option_b_radio_button.setText(q.option_b);
        holder.option_c_radio_button.setText(q.option_c);
        holder.option_d_radio_button.setText(q.option_d);
    }

    @Override
    public int getItemCount() {
        if(questionList == null) return 0;
        else return questionList.size();
    }

    public void setQuestionList(List<Question> qL){
        questionList = qL;
        notifyDataSetChanged();
    }

    public List<Question> getQuestionList(){
        return questionList;
    }

    public class GiveQuestionViewHolder extends RecyclerView.ViewHolder{

        public TextView question_text_view;
        public RadioButton option_a_radio_button;
        public RadioButton option_b_radio_button;
        public RadioButton option_c_radio_button;
        public RadioButton option_d_radio_button;

        public GiveQuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            question_text_view = itemView.findViewById(R.id.give_question_text_view);
            option_a_radio_button = itemView.findViewById(R.id.option_a_radio_button);
            option_b_radio_button = itemView.findViewById(R.id.option_b_radio_button);
            option_c_radio_button = itemView.findViewById(R.id.option_c_radio_button);
            option_d_radio_button = itemView.findViewById(R.id.option_d_radio_button);
        }
    }
}
