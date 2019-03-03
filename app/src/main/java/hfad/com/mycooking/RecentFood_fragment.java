package hfad.com.mycooking;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentTransaction;

import java.util.Collections;
import java.util.List;

public class RecentFood_fragment extends Fragment {
    List<Food> recentList;
    List<Food>subRecentList;
    CookingDatabase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db= new CookingDatabase(getActivity());
        recentList=db.getData();

        View fragment_layout= inflater.inflate(R.layout.recentfood_fragment_layout,container,false);
        RecyclerView recyclerView= (RecyclerView)fragment_layout.findViewById(R.id.id_recycler);

        try{
            subRecentList= recentList.subList(0,4);
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        CookingAdapter adapter = new CookingAdapter(subRecentList);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        // set listener for this fragment
        adapter.setListener(new CookingAdapter.Listener() {
            @Override
            public void onClick(int position) {
                MainActivity.mPagePosition=MainActivity.mPager.getCurrentItem();
                Intent intent = new Intent(getActivity(),FoodDetail_activity.class);
                intent.putExtra(FoodDetail_activity.EXTRA_FOOD_ID,position);
                intent.putExtra("where","recent_fragment");
                getActivity().startActivity(intent);
            }
        });
        return fragment_layout;
    }


}
