package nure.kazantseva.mycar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nure.kazantseva.mycar.model.Other;
import nure.kazantseva.mycar.model.Repair;

public class DBHelperOther extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    private static final String DATABASE_NAME = "MyCar.db";
    private static final String TABLE_OTHER = "other";
    private static final String COLUMN_OTHER_ID = "other_id";
    private static final String COLUMN_AUTO_ID = "auto_id";
    private static final String COLUMN_OTHER_DATE = "other_date";
    private static final String COLUMN_OTHER_DESCRIPTION = "other_description";
    private static final String COLUMN_OTHER_PRICE = "other_price";

    public DBHelperOther(Context context) {
        super(context, DATABASE_NAME
                , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_OTHER_TABLE = "CREATE TABLE " + TABLE_OTHER + "("
                + COLUMN_OTHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTO_ID + " INTEGER,"
                + COLUMN_OTHER_DATE + " TEXT,"
                + COLUMN_OTHER_DESCRIPTION + " TEXT,"
                + COLUMN_OTHER_PRICE + " REAL,"
                + " FOREIGN KEY ("+ COLUMN_AUTO_ID + ")  REFERENCES auto (auto_id) "
                + "ON DELETE CASCADE "
                + ")";
        db.execSQL(CREATE_OTHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_OTHER_TABLE = "DROP TABLE IF EXISTS " + TABLE_OTHER;
        db.execSQL(DROP_OTHER_TABLE);
        onCreate(db);
    }

    public void addOther(Other other){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_ID, other.getAuto_id());
        values.put(COLUMN_OTHER_DATE, other.getDate().toString());
        values.put(COLUMN_OTHER_DESCRIPTION, other.getDescription());
        values.put(COLUMN_OTHER_PRICE, other.getPrice());

        db.insert(TABLE_OTHER, null, values);
        db.close();
    }
    public void deleteOther(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_OTHER = "DELETE FROM " + TABLE_OTHER + " WHERE "
                + COLUMN_OTHER_ID + "=" + id;
        db.execSQL(DELETE_OTHER);
        db.close();
    }

    public Cursor readAllDateByAutoId(int id){
        String query = "SELECT * FROM " + TABLE_OTHER + " WHERE("
                + COLUMN_AUTO_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor findById(int id){
        String query = "SELECT * FROM " + TABLE_OTHER + " WHERE("
                + COLUMN_OTHER_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateOther(Other other){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_OTHER = "UPDATE " + TABLE_OTHER + " SET "
                + COLUMN_OTHER_DATE + " = '" + other.getDate().toString() + "',"
                + COLUMN_OTHER_DESCRIPTION + " = '" + other.getDescription() + "',"
                + COLUMN_OTHER_PRICE + " = '" + other.getPrice() +"'"
                + " WHERE(" + COLUMN_OTHER_ID + "=" + other.getId() + ")";
        db.execSQL(UPDATE_OTHER);
        db.close();
    }
}
