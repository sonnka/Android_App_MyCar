package nure.kazantseva.mycar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nure.kazantseva.mycar.model.Washer;

public class DBHelperWasher extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    private static final String DATABASE_NAME = "MyCar.db";
    private static final String TABLE_WASHER = "washer";
    private static final String COLUMN_WASHER_ID = "washer_id";
    private static final String COLUMN_AUTO_ID = "auto_id";
    private static final String COLUMN_WASHER_DATE = "washer_date";
    private static final String COLUMN_WASHER_PRICE = "washer_price";

    public DBHelperWasher(Context context) {
        super(context, DATABASE_NAME
                , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WASHER_TABLE = "CREATE TABLE " + TABLE_WASHER + "("
                + COLUMN_WASHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTO_ID + " INTEGER,"
                + COLUMN_WASHER_DATE + " TEXT,"
                + COLUMN_WASHER_PRICE + " REAL,"
                + " FOREIGN KEY ("+ COLUMN_AUTO_ID + ")  REFERENCES auto (auto_id) "
                + "ON DELETE CASCADE "
                + ")";
        db.execSQL(CREATE_WASHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_WASHER_TABLE = "DROP TABLE IF EXISTS " + TABLE_WASHER;
        db.execSQL(DROP_WASHER_TABLE);
        onCreate(db);
    }

    public void addWasher(Washer washer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_ID, washer.getAuto_id());
        values.put(COLUMN_WASHER_DATE, washer.getDate().toString());
        values.put(COLUMN_WASHER_PRICE, washer.getPrice());

        db.insert(TABLE_WASHER, null, values);
        db.close();
    }

    public void deleteWasher(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_WASHER = "DELETE FROM " + TABLE_WASHER + " WHERE "
                + COLUMN_WASHER_ID + "=" + id;
        db.execSQL(DELETE_WASHER);
        db.close();
    }

    public Cursor readAllDateByAutoId(int id){
        String query = "SELECT * FROM " + TABLE_WASHER + " WHERE("
                + COLUMN_AUTO_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor findById(int id){
        String query = "SELECT * FROM " + TABLE_WASHER + " WHERE("
                + COLUMN_WASHER_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateWasher(Washer washer){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_WASHER = "UPDATE " + TABLE_WASHER + " SET "
                + COLUMN_WASHER_DATE + " = '" + washer.getDate().toString() + "',"
                + COLUMN_WASHER_PRICE + " = '" + washer.getPrice() + "'"
                + " WHERE(" + COLUMN_WASHER_ID + "=" + washer.getId() + ")";
        db.execSQL(UPDATE_WASHER);
        db.close();
    }
}
