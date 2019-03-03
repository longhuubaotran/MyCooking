package hfad.com.mycooking;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hfad.com.mycooking.CookingDatabase;
import hfad.com.mycooking.Food;
import hfad.com.mycooking.FoodDetail_activity;
import hfad.com.mycooking.R;

public class FoodDetail_fragment extends Fragment {
    CookingDatabase db;
    List<Food> foodList; // used when this fragment get called recent_fragment or foodList_fragment
    List<Food> favoriteList; // used when this fragment get called by FavoriteList_fragment
    int position;
    String where; // this determines which ArrayList should be used based on which fragment called to this
    String newRecipe;// this is for update recipe
    Food food = null;
    boolean isFavorite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = new CookingDatabase(getActivity());

        foodList = db.getData(); // assign foodList by the list in Database
        favoriteList = db.getFavoriteFoodList();

        position = getArguments().getInt("position");// get position from Fooddetail_Activity
        where = getArguments().getString("where");// determine this fragment get called by which fragment

        if (where.equals("favorite_fragment")) {
            food = favoriteList.get(position);
        } else {
            food = foodList.get(position);// recent_frag or foodList_frag called
        }

        isFavorite = (food.getFavorite() == 1);

        View view = inflater.inflate(R.layout.fooddetail_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.food_name);
        textView.setText(food.getName());

        ImageView imageView = (ImageView) view.findViewById(R.id.foodDetail_image);
        imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), food.getImageId()));
        imageView.setContentDescription(food.getRecipe());

        final TextView text_recipe = (TextView) view.findViewById(R.id.foodDetail_text);
        text_recipe.setText(food.getRecipe());

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.favorite);
        checkBox.setChecked(isFavorite);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.setFavorite(food)) {
                    Toast toast = Toast.makeText(getActivity()
                            , getResources().getString(R.string.editSuccessfully)
                            , Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getActivity()
                            , getResources().getString(R.string.editFail)
                            , Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        final EditText edit = (EditText) view.findViewById(R.id.editRecipe);
        Button button = (Button) view.findViewById(R.id.bEdit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    newRecipe = edit.getText().toString(); // get the text from editText
                    if (db.editDB(position, newRecipe, food)) {
                        //edit successfully so display a message
                        text_recipe.setText(newRecipe);
                        edit.getText().clear();
                        Toast toast = Toast.makeText(getActivity()
                                , getResources().getString(R.string.editSuccessfully)
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getActivity()
                                , getResources().getString(R.string.editFail)
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
