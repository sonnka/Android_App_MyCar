package nure.kazantseva.mycar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nure.kazantseva.mycar.model.Auto;

public class DBHelperAuto extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    private static final String DATABASE_NAME = "MyCar.db";
    private static final String TABLE_AUTO = "auto";
    private static final String COLUMN_AUTO_ID = "auto_id";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_AUTO_BRAND = "auto_brand";
    private static final String COLUMN_AUTO_MODEL = "auto_model";
    private static final String COLUMN_AUTO_YEAR = "auto_year";
    private static final String COLUMN_AUTO_FUEL = "auto_fuel";
    private static final String COLUMN_AUTO_RUN = "auto_run";

    public DBHelperAuto(Context context) {
        super(context, DATABASE_NAME
                , null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AUTO_TABLE = "CREATE TABLE " + TABLE_AUTO + "("
                + COLUMN_AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_AUTO_BRAND + " TEXT," + COLUMN_AUTO_MODEL + " TEXT,"
                + COLUMN_AUTO_YEAR + " INTEGER," + COLUMN_AUTO_FUEL + " TEXT NOT NULL,"
                + COLUMN_AUTO_RUN + " INTEGER,"
                + " FOREIGN KEY ("+ COLUMN_USER_EMAIL + ")  REFERENCES user (user_email) "
                + "ON DELETE CASCADE "
                + ")";
        db.execSQL(CREATE_AUTO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_AUTO_TABLE = "DROP TABLE IF EXISTS " + TABLE_AUTO;
        db.execSQL(DROP_AUTO_TABLE);
        onCreate(db);
    }

    public void addAuto(Auto auto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, auto.getUser_email());
        values.put(COLUMN_AUTO_BRAND, auto.getBrand());
        values.put(COLUMN_AUTO_MODEL, auto.getModel());
        values.put(COLUMN_AUTO_YEAR, auto.getYear());
        values.put(COLUMN_AUTO_FUEL, auto.getFuel());
        values.put(COLUMN_AUTO_RUN, auto.getRun());

        db.insert(TABLE_AUTO, null, values);
        db.close();
    }

    public void deleteAuto(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_AUTO = "DELETE FROM " + TABLE_AUTO + " WHERE "
                + COLUMN_AUTO_ID + "=" + id;
        db.execSQL(DELETE_AUTO);
        db.close();
    }

    public int checkByEmail(String email){
        String[] columns = {
                COLUMN_AUTO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_AUTO, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount;
    }

    public int findByEmail(String email){
        String[] columns = {
                COLUMN_AUTO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_AUTO, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        if(cursor.getCount() > 0 && cursor.moveToFirst()){
            int id = cursor.getInt(0);
            db.close();
            return id;
        }else {
            db.close();
            return 0;
        }

    }
}
