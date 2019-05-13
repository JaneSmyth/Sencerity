package adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.sencerity.R;

import java.util.List;

import models.SleepHeader;
import models.NormalRow;
import models.RecyclerViewItem;

public class SleepAdapter extends RecyclerView.Adapter{

    //Declare list of recyclerview item
    List<RecyclerViewItem> sleepDataList;

    //header item type, normal item type (anything thats not a header)
    private static final int HEADER_ITEM=0;
    private static final int NORMAL_ITEM=1;

    Context mContext;

    public SleepAdapter(Context mContext,List<RecyclerViewItem> sleepDataList){
        this.mContext=mContext;
        this.sleepDataList= sleepDataList;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View row;
        //check view type and inflate accordingly
        if(viewType == HEADER_ITEM)
        {
            row = inflater.inflate(R.layout.single_header, viewGroup,false);
            return new HeaderViewHolder(row);
        }
        else if(viewType == NORMAL_ITEM){
            row = inflater.inflate(R.layout.single_row, viewGroup,false);
            return new NormalViewHolder(row);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder , int index) {

        RecyclerViewItem recyclerItem= sleepDataList.get(index);

        if(holder instanceof HeaderViewHolder){
          //  HeaderViewHolder headerHolder=(HeaderViewHolder)holder;
          //  SleepHeader header=(SleepHeader)recyclerItem;
            //set data
           // headerHolder.textViewHeader.setText(header.getHeaderText());

        }
        else if(holder instanceof NormalViewHolder){
            NormalViewHolder normalHolder=(NormalViewHolder)holder;
            NormalRow normalRow = (NormalRow)recyclerItem;

            //normalHolder.mDateTime.setText(normalRow.getLocalDateTime());
            normalHolder.mDate.setText(normalRow.getDate());
            normalHolder.mSleepToWakeHrs.setText(normalRow.getSleepingHours());
            normalHolder.mDuration.setText(normalRow.getTimeAsleep());
                    /*
            normalHolder.mDateTime.setText(normalRow.calculateDuration());
            normalHolder.mSensor.setText(normalRow.getSensor());
            normalHolder.mPatient.setText(normalRow.getPatientId());
            */
        }

    }

    @Override
    public int getItemViewType(int position){
        //set view type
        RecyclerViewItem recyclerViewItem = sleepDataList.get(position);
        if(recyclerViewItem instanceof SleepHeader){
            return HEADER_ITEM;
        }
        else if(recyclerViewItem instanceof NormalRow){
            return NORMAL_ITEM;
        }
        else{
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return sleepDataList.size();
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHeader;

        public HeaderViewHolder(View itemView){
            super(itemView);
           // textViewHeader=itemView.findViewById(R.id.headerTextView);
        }
    }
    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView mSleepToWakeHrs, mDuration, mDate;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.textViewDate);
            mDuration = itemView.findViewById(R.id.textViewDuration);
            mSleepToWakeHrs = itemView.findViewById(R.id.textViewTime);

            /*
            mDateTime = itemView.findViewById(R.id.textViewDateTime);
            mSensor = itemView.findViewById(R.id.textViewSensor);
            mPatient = itemView.findViewById(R.id.textViewPatient);
            */
        }
    }


}
