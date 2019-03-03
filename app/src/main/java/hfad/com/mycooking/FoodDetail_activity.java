package hfad.com.mycooking;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class FoodDetail_activity extends AppCompatActivity {

    public static final String EXTRA_FOOD_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fooddetail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        int position = (Integer) getIntent().getExtras().get(EXTRA_FOOD_ID);
        String where = getIntent().getStringExtra("where");
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("where", where);
        FoodDetail_fragment foodDetail_fragment = new FoodDetail_fragment();
        foodDetail_fragment.setArguments(bundle);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.recentfood_holder, foodDetail_fragment);
        ft.addToBackStack(null);
        ft.commit();


    }
}
