package com.programathon.app_programathon.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programathon.app_programathon.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EstudianteExamenListAdapter extends RecyclerView.Adapter<EstudianteExamenListAdapter.ItemViewHolder> {

    ArrayList<String> dataSetColumna1,
                      dataSetColumna2;

    public EstudianteExamenListAdapter(ArrayList<String> dataSetColumna1, ArrayList<String> dataSetColumna2) {
        this.dataSetColumna1 = dataSetColumna1;
        this.dataSetColumna2 = dataSetColumna2;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.columna1.setText(dataSetColumna1.get(position));
        holder.columna2.setText(dataSetColumna2.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView columna1;
        private TextView columna2;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.columna1 = itemView.findViewById(R.id.Row_Columna1);
            this.columna2 = itemView.findViewById(R.id.Row_Columna2);
        }
    }
}
