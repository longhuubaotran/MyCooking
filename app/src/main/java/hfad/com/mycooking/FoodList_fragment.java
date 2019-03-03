package hfad.com.mycooking;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FoodList_fragment extends Fragment {

    List<Food>foodList;
    CookingDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db= new CookingDatabase(getActivity());
        foodList=db.getData();

        View view = inflater.inflate(R.layout.foodlist_fragment,container,false);
        RecyclerView recyclerView= (RecyclerView)view.findViewById(R.id.id_recycler);

        CookingAdapter cookingAdapter = new CookingAdapter(foodList);
        recyclerView.setAdapter(cookingAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        cookingAdapter.setListener(new CookingAdapter.Listener() {
            @Override
            public void onClick(int position) {
                MainActivity.mPagePosition=MainActivity.mPager.getCurrentItem();
                Intent intent = new Intent(getActivity(),FoodDetail_activity.class);
                intent.putExtra(FoodDetail_activity.EXTRA_FOOD_ID,position);
                intent.putExtra("where","foodlist_fragment");
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
