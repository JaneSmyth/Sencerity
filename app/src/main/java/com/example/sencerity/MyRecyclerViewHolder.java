package com.example.sencerity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView mSensor, mPatient,mDateTime;
    public MyRecyclerViewHolder(View itemView){
        super(itemView);
        mDateTime=itemView.findViewById(R.id.textViewDateTime);
        mSensor = itemView.findViewById(R.id.textViewSensor);
        mPatient = itemView.findViewById(R.id.textViewPatient);

    }
}
