package com.programathon.app_programathon.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programathon.app_programathon.R;

import java.util.ArrayList;

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.SimpleItemViewHolder>{

    ArrayList<String> dataSet;

    public SimpleListAdapter(ArrayList<String> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public SimpleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_simpleitem, parent, false);
        return new SimpleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleItemViewHolder holder, int position) {
        holder.textView.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        SimpleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.listeneritem_textview);
        }

    }
}
