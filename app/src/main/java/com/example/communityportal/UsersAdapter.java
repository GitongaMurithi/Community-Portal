package com.example.communityportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private final ArrayList<UserData> names;
    Context context;
    static OnUserClicked onUserClicked;


    public UsersAdapter(ArrayList<UserData> names, Context context, OnUserClicked onUserClicked) {
        this.names = names;
        this.context = context;
        UsersAdapter.onUserClicked = onUserClicked;
    }

    interface OnUserClicked {
        void onUserClicked(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserData userData = names.get(position);
        holder.textView1.setText(userData.getUserName());

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUserClicked.onUserClicked(getAdapterPosition());

                }
            });
            textView1 = itemView.findViewById(R.id.text1);
        }
    }

}




