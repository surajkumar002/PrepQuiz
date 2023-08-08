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

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private Context context;
    public List<String> subjectList;

    public SubjectAdapter() {
        super();
    }

    public SubjectAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.subject_list_item,parent,false);
        return new SubjectViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        String subject = subjectList.get(position);

        holder.subjectTextView.setText(subject);
    }

    @Override
    public int getItemCount() {
        if(subjectList == null) return 0;
        else return subjectList.size();
    }

    public void setSubjectList(List<String> subjectList) {
        this.subjectList = subjectList;
        notifyDataSetChanged();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView subjectTextView;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectTextView = itemView.findViewById(R.id.subject_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String subjectCode = this.subjectTextView.getText().toString().trim();
            Intent intent = new Intent(context,AllQuizActivity.class);
            intent.putExtra("subjectCode",subjectCode);
            context.startActivity(intent);
        }
    }
}
