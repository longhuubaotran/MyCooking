package hfad.com.mycooking;

import android.provider.BaseColumns;

public class DBContract  {
    private DBContract() {
    }
    public static class CookingDB implements BaseColumns{
        public static final String TABLE_NAME="cooking";
        public static final String COLUMN_ID="_id";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_IMAGEID= "image_id";
        public static final String COLUMN_FAVORITE="favorite";

        public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("
                + COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME +" TEXT,"
                + COLUMN_DESCRIPTION+" TEXT,"+COLUMN_IMAGEID +" INTEGER,"+COLUMN_FAVORITE +" INTEGER"+" )";


        public static final String DROP_TABLE= "drop table" + TABLE_NAME;
    }
}
