package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sencerity.MainMenuActivity;
import com.example.sencerity.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import models.PatientInfo;



public class PatientAdapter extends RecyclerView.Adapter{

    Context mContext;
    List<PatientInfo> patientList;
    CharSequence patientName;


    public PatientAdapter(Context mContext, List<PatientInfo> patientList) {
        this.mContext=mContext;
        this.patientList=patientList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.patient_item, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder , int position){
        PatientInfo patientInfo = patientList.get(position);
        MyViewHolder mHolder =(MyViewHolder)holder;

        mHolder.patientNameV.setText(patientInfo.getPatientName());

    }


    @Override
    public int getItemCount() {
        return patientList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        Button patientNameV;


        public MyViewHolder(View itemView){
            super(itemView);
            patientNameV=itemView.findViewById(R.id.patientNameBtn);

            patientNameV.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                   Intent intent = new Intent(patientNameV.getContext(), MainMenuActivity.class);
                   intent.putExtra("pName",patientNameV.getText());


                   patientNameV.getContext().startActivity(intent);
                }
            });



        }

  }
}
