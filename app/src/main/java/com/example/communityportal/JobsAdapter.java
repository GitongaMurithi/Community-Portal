package com.example.communityportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    private final ArrayList<JobsModel> jobs;
    Context context;
    static OnJobClicked onJobClicked;


    public JobsAdapter(ArrayList<JobsModel> jobs, Context context, OnJobClicked onMessageClicked) {
        this.jobs = jobs;
        this.context = context;
        JobsAdapter.onJobClicked = onMessageClicked;
    }

    interface OnJobClicked {
        void onJobClicked(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobsModel model = jobs.get(position);
        holder.textView1.setText(model.getCompanyName());
        holder.textView2.setText(model.getCompanyLocation());
        holder.textView3.setText(model.getJobTitle());

    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onJobClicked.onJobClicked(getAdapterPosition());

                }
            });
            textView1 = itemView.findViewById(R.id.jobTitle);
            textView2 = itemView.findViewById(R.id.employer);
            textView3 = itemView.findViewById(R.id.location);
        }
    }

}




