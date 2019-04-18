package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sencerity.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(View itemView){
        super(itemView);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

       public HeaderViewHolder(View itemView){
            super(itemView);
            textView=itemView.findViewById(R.id.headerTextView);
        }
    }
    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView mSensor, mPatient, mDateTime;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mDateTime = itemView.findViewById(R.id.textViewDateTime);
            mSensor = itemView.findViewById(R.id.textViewSensor);
            mPatient = itemView.findViewById(R.id.textViewPatient);

        }
    }
}
