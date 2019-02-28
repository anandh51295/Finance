package com.ingenioustechnologies.finance.adopter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingenioustechnologies.finance.R;
import com.ingenioustechnologies.finance.ReportActivity;
import com.ingenioustechnologies.finance.model.UserVal;

import java.util.ArrayList;

public class UserAdopter extends RecyclerView.Adapter<UserAdopter.ViewHolder> {
    private ArrayList<UserVal> userVals;

    public UserAdopter(ArrayList userVals) {
        this.userVals = userVals;

    }

    @NonNull
    @Override
    public UserAdopter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdopter.ViewHolder viewHolder, int i) {
        viewHolder.user.setText(userVals.get(i).getUsername());
    }

    @Override
    public int getItemCount() {
        return userVals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user=itemView.findViewById(R.id.user_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ReportActivity.class);
                    intent.putExtra("username",userVals.get(getLayoutPosition()).getUsername());
                    intent.putExtra("userid",userVals.get(getLayoutPosition()).getUserid());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
