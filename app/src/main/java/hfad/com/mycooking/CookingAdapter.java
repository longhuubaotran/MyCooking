package hfad.com.mycooking;

import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CookingAdapter extends RecyclerView.Adapter<CookingAdapter.Viewholder> {
    List<Food> foodList;
    private String name;
    private int imageID;
    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    public CookingAdapter(List<Food> foods) {
        foodList = foods;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        CardView cardView;

        public Viewholder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.layout_cardview, viewGroup, false);
        return new Viewholder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, final int position) {
        CardView cv = viewholder.cardView;
        ImageView imageView = (ImageView) cv.findViewById(R.id.info_image);
        Drawable drawable = ContextCompat.getDrawable(cv.getContext(),
                foodList.get(position).getImageId());
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(foodList.get(position).getName());
        TextView textView = (TextView) cv.findViewById(R.id.info_text);
        textView.setText(foodList.get(position).getName());
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
