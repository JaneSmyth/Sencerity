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

import models.FoodHeaderRow;
import models.FoodNormalRow;
import models.RecyclerViewItem;

public class FoodAdapter extends RecyclerView.Adapter {

    List<RecyclerViewItem> foodDataList;

    private static final int HEADER_ITEM=0;
    private static final int NORMAL_ITEM=1;
    Context mContext;

   public FoodAdapter(Context mContext,List<RecyclerViewItem> foodDataList){
       this.mContext=mContext;
       this.foodDataList=foodDataList;
   }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View row;
        if(viewType == HEADER_ITEM)
        {
            row = inflater.inflate(R.layout.food_header, viewGroup,false);
            return new HeaderViewHolder(row);
        }
        else if(viewType == NORMAL_ITEM){
            row = inflater.inflate(R.layout.food_item, viewGroup,false);
            return new NormalViewHolder(row);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewItem recyclerItem= foodDataList.get(position);

        if(holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder=(HeaderViewHolder)holder;
            FoodHeaderRow header=(FoodHeaderRow)recyclerItem;
            //set data
            headerHolder.headerView.setText(header.getMealType());
        }
         else if(holder instanceof NormalViewHolder){
             NormalViewHolder normalHolder = (NormalViewHolder)holder;
             FoodNormalRow normalRow = (FoodNormalRow)recyclerItem;

             normalHolder.mProduct.append(normalRow.getmProduct());
             normalHolder.mServing.append(String.valueOf(normalRow.getmServing()));
             normalHolder.mCalories.append(String.valueOf(normalRow.getmCalories()));
             normalHolder.mGrade.append(normalRow.getmGrade());


        }

    }

    @Override
    public int getItemCount() {
        return foodDataList.size();
    }

    @Override
    public int getItemViewType(int position){
        //set view type
        RecyclerViewItem recyclerViewItem = foodDataList.get(position);
        if(recyclerViewItem instanceof FoodHeaderRow){
            return HEADER_ITEM;
        }
        else if(recyclerViewItem instanceof FoodNormalRow){
            return NORMAL_ITEM;
        }
        else{
            return super.getItemViewType(position);
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public TextView headerView;
        public HeaderViewHolder(View itemView){
            super(itemView);
             headerView=itemView.findViewById(R.id.mealTypeView);
        }
    }
    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView mProduct, mServing, mCalories,mGrade;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mProduct = itemView.findViewById(R.id.productView);
            mServing = itemView.findViewById(R.id.servingView);
            mCalories = itemView.findViewById(R.id.caloriesView);
            mGrade =itemView.findViewById(R.id.gradeView);

        }
    }

}
