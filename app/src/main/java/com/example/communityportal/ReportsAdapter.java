package com.example.communityportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {

    private final ArrayList<Message> messages;
    Context context;
    static OnMessageClicked onMessageClicked;


    public ReportsAdapter(ArrayList<Message> messages, Context context, OnMessageClicked onMessageClicked) {
        this.messages = messages;
        this.context = context;
        ReportsAdapter.onMessageClicked = onMessageClicked;
    }

    interface OnMessageClicked {
        void onMessageClicked(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cases_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.textView1.setText(message.getSender());
        holder.textView3.setText(message.getContent());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMessageClicked.onMessageClicked(getAdapterPosition());

                }
            });
            textView1 = itemView.findViewById(R.id.sender);
            textView2 = itemView.findViewById(R.id.reportCount);
            textView3 = itemView.findViewById(R.id.text);
        }
    }

}




