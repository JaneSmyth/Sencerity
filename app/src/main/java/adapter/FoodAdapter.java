package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sencerity.FoodActivity;
import com.example.sencerity.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import models.RecyclerViewItem;

public class FoodAdapter extends RecyclerView.Adapter {

    List<RecyclerViewItem> foodDataList;

   Context mContext;

   public FoodAdapter(Context mContext,List<RecyclerViewItem> foodDataList){
       this.mContext=mContext;
       this.foodDataList=foodDataList;
   }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View row=inflater.inflate(R.layout.activity_nutrional_info,viewGroup,false);
        //return new ItemViewHolder(row);

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
