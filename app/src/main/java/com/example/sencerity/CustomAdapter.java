package com.example.sencerity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class CustomAdapter extends FirestoreRecyclerAdapter<DataModel, CustomAdapter.DataHolder> {

    public CustomAdapter(@NonNull FirestoreRecyclerOptions<DataModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DataHolder holder, int position, @NonNull DataModel model) {

        holder.textViewDate.setText(model.getDateAndTime());
        holder.textViewSensor.setText(model.getSensor());
        holder.textViewPressure.setText(String.valueOf(model.getSensorPressure()));
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new DataHolder(v);
    }

    class DataHolder extends RecyclerView.ViewHolder {

        TextView textViewDate;
        TextView textViewSensor;
        TextView textViewPressure;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate=itemView.findViewById(R.id.textViewDateTime);
            textViewSensor=itemView.findViewById(R.id.textViewSensor);
            textViewPressure=itemView.findViewById(R.id.textViewSensorPres);


        }
    }

}