package hfad.com.mycooking;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class CookingDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "Cooking";
    private static final int DB_VERSION = 1;
    private ArrayList<Food> foods = new ArrayList<>();
    private ArrayList<Food> favoriteFoodList = new ArrayList<>();

    // Database Constructor
    public CookingDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private void insert(SQLiteDatabase db, String name, String recipe, int imageID, int favortie) {
        ContentValues cookingValue = new ContentValues();
        cookingValue.put(DBContract.CookingDB.COLUMN_NAME, name);
        cookingValue.put(DBContract.CookingDB.COLUMN_DESCRIPTION, recipe);
        cookingValue.put(DBContract.CookingDB.COLUMN_IMAGEID, imageID);
        cookingValue.put(DBContract.CookingDB.COLUMN_FAVORITE, favortie);
        db.insert(DBContract.CookingDB.TABLE_NAME, null, cookingValue);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVer, int newVer) {

        if (oldVer < 1) {
            try {
                db.execSQL(DBContract.CookingDB.CREATE_TABLE);
                insert(db, "Pizza", "Bake in 10 minutes", R.drawable.pizza, 0);
                insert(db, "Steak", "Salt and pepper", R.drawable.steak, 1);
                insert(db, "Sushi", "Fresh Salmon", R.drawable.sushi, 0);
                insert(db, "Coconut Shrimp", "Coconut and shrimp", R.drawable.coconutshrimp, 0);
                insert(db, "Grilled Pork Rice", "Pork and rice", R.drawable.grilledporkrice, 1);
                insert(db, "Dumpling", "Chinese food", R.drawable.dumpling, 1);
                insert(db, "Stir fried meat", "Meat and fish sauce", R.drawable.stirfiredmeat, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // add record in database into ArrayList
    public ArrayList<Food> getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String row = "select * from " + DBContract.CookingDB.TABLE_NAME + " ORDER BY " + DBContract.CookingDB.COLUMN_ID + " DESC";
        Cursor cursor = db.rawQuery(row, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    foods.add(new Food(cursor.getString(cursor.getColumnIndex(DBContract.CookingDB.COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(DBContract.CookingDB.COLUMN_DESCRIPTION)),
                            cursor.getInt(cursor.getColumnIndex(DBContract.CookingDB.COLUMN_IMAGEID)),
                            cursor.getInt(cursor.getColumnIndex(DBContract.CookingDB.COLUMN_FAVORITE))));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return new ArrayList<>(foods);
    }

    public boolean editDB(int position, String newRecipe, Food foodEdit) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (isRecordExist(foodEdit)) {
            // foodEdit exists so perform editing on it
            String selectedRow = DBContract.CookingDB.COLUMN_NAME + " =?";
            ContentValues content = new ContentValues();
            content.put(DBContract.CookingDB.COLUMN_DESCRIPTION, newRecipe);
            db.update(DBContract.CookingDB.TABLE_NAME, content, selectedRow, new String[]{foodEdit.getName()});
            db.close();
            return true;
        } else {
            return false;
        }
    }

    // check if the food which need to be edited  exists in database or no
    private boolean isRecordExist(Food foodEdit) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean exist;
        String name = foodEdit.getName();
        String query = "select * from " + DBContract.CookingDB.TABLE_NAME + " where " + DBContract.CookingDB.COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});
        exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }

    // add all favorite food into ArrayList
    public ArrayList<Food> getFavoriteFoodList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + DBContract.CookingDB.TABLE_NAME + " where " + DBContract.CookingDB.COLUMN_FAVORITE + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{Integer.toString(1)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    favoriteFoodList.add(new Food(cursor.getString(cursor.getColumnIndex(DBContract.CookingDB.COLUMN_NAME))
                            , cursor.getString(cursor.getColumnIndex(DBContract.CookingDB.COLUMN_DESCRIPTION))
                            , cursor.getInt(cursor.getColumnIndex(DBContract.CookingDB.COLUMN_IMAGEID))
                            , cursor.getInt(cursor.getColumnIndex(DBContract.CookingDB.COLUMN_FAVORITE))));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return new ArrayList<>(favoriteFoodList);
    }

    public boolean setFavorite(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        int setFavorite;
        if (food.getFavorite() == 0) {
            setFavorite = 1;
        } else {
            setFavorite = 0;
        }
        ContentValues content = new ContentValues();
        content.put(DBContract.CookingDB.COLUMN_FAVORITE, setFavorite);
        if (isRecordExist(food)) {
            db.update(DBContract.CookingDB.TABLE_NAME, content
                    , DBContract.CookingDB.COLUMN_NAME + " =? "
                    , new String[]{food.getName()});
            db.close();
            return true;
        }
        db.close();
        return false;
    }
}
