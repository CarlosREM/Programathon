package com.programathon.app_programathon.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programathon.app_programathon.R;

import java.util.ArrayList;

public class SimpleListenerListAdapter extends RecyclerView.Adapter<SimpleListenerListAdapter.SimpleListenerItemViewHolder>{

    ArrayList<String> names;
    ArrayList<String> descriptions;

    public SimpleListenerListAdapter(ArrayList<String> names, ArrayList<String> descriptions) {
        this.names = names;
        this.descriptions = descriptions;
    }

    @NonNull
    @Override
    public SimpleListenerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_simpleitem, parent, false);
        return new SimpleListenerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleListenerItemViewHolder holder, int position) {
        holder.textView.setText(names.get(position));
        holder.txtDescripcion.setText(descriptions.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class SimpleListenerItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView txtDescripcion;

        SimpleListenerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.listeneritem_textview);
            txtDescripcion = itemView.findViewById(R.id.listeneritem_description);
        }

    }
}
