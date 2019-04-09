package com.example.sencerity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder>{

    private SleepActivity sleepActivity;
    private ArrayList<SleepDataModel> sleepDataArrayList;

    public RecyclerViewAdapter(SleepActivity sleepActivity, ArrayList<SleepDataModel> sleepDataArrayList) {
        this.sleepActivity = sleepActivity;
        this.sleepDataArrayList = sleepDataArrayList;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater layoutInflater = LayoutInflater.from(sleepActivity.getBaseContext());
        View view= layoutInflater.inflate(R.layout.single_row, viewGroup, false);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int i) {


        holder.mDateTime.setText(sleepDataArrayList.get(i).getDateTimeFormatted());
        holder.mSensor.setText(sleepDataArrayList.get(i).getSensor());
        holder.mPatient.setText(sleepDataArrayList.get(i).getPatientId());

    }

    @Override
    public int getItemCount() {
        return sleepDataArrayList.size();
    }
}
