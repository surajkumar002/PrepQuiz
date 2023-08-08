package com.codewithharry.prepquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>{

    private List<Student> studentScoreList;
    private Context mContext;

    public ProgressAdapter() {
        super();
    }

    public ProgressAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.progress_list_item,parent,false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {
        Student student = studentScoreList.get(position);
        holder.studentNameTextView.setText(student.name);
        holder.scoreTextView.setText(student.score);
    }

    @Override
    public int getItemCount() {
        if(studentScoreList == null) return 0;
        else return studentScoreList.size();
    }

    public void setStudentScoreList(List<Student> studentScoreList){
        this.studentScoreList = studentScoreList;
        notifyDataSetChanged();
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        public TextView studentNameTextView;
        private TextView scoreTextView;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);

            studentNameTextView = itemView.findViewById(R.id.student_name_text_view);
            scoreTextView = itemView.findViewById(R.id.score_text_view);
        }
    }
}
