package utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceBetweenViews extends RecyclerView.ItemDecoration {
    int space;

    public SpaceBetweenViews(int space) {
        this.space=space;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        super.getItemOffsets(outRect,view,parent,state);
        //Check if header or not, no space needed for header
        if(!(parent.getChildLayoutPosition(view)==0)){
//unfinished ..not sure i need to use this
        }
    }
}
