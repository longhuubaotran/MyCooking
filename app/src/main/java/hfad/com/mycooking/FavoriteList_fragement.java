package hfad.com.mycooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FavoriteList_fragement extends Fragment {
    CookingDatabase db;
    List<Food> favoriteFoodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = new CookingDatabase(getActivity());
        favoriteFoodList = db.getFavoriteFoodList();
        View view = inflater.inflate(R.layout.favoritelist_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_recycler);

        CookingAdapter adapter = new CookingAdapter(favoriteFoodList);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.setListener(new CookingAdapter.Listener() {
            @Override
            public void onClick(int position) {
                MainActivity.mPagePosition = MainActivity.mPager.getCurrentItem();
                Intent intent = new Intent(getActivity(), FoodDetail_activity.class);
                intent.putExtra(FoodDetail_activity.EXTRA_FOOD_ID, position);
                intent.putExtra("where", "favorite_fragment");
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
