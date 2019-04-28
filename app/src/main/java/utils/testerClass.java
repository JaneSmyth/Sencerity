package utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class testerClass {

    public testerClass(){

    }

    public void getStuff(){
        List list = new ArrayList<>();
        list.add("Banana");
        list.add("cat");
        list.add("asd");
        list.add("hat");
        list.add("gat");
        list.add("Banaaefasdna");

        for(Object i: list){
            System.out.println(i + "   ");
        }


    }
}

