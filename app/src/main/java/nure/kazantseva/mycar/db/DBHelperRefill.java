package nure.kazantseva.mycar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nure.kazantseva.mycar.model.Refill;

public class DBHelperRefill extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    private static final String DATABASE_NAME = "MyCar.db";
    private static final String TABLE_REFILL = "refill";
    private static final String COLUMN_REFILL_ID = "refill_id";
    private static final String COLUMN_AUTO_ID = "auto_id";
    private static final String COLUMN_REFILL_DATE = "refill_date";
    private static final String COLUMN_REFILL_RUN = "refill_run";
    private static final String COLUMN_REFILL_BEFORE_REFILL = "refill_beforeRefill";
    private static final String COLUMN_REFILL_ADD_FUEL = "refill_addFuel";
    private static final String COLUMN_REFILL_PRICE = "refill_price";
    private static final String COLUMN_REFILL_STATION = "refill_station";

    public DBHelperRefill(Context context) {
        super(context, DATABASE_NAME
                , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REFILL_TABLE = "CREATE TABLE " + TABLE_REFILL + "("
                + COLUMN_REFILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTO_ID + " INTEGER,"
                + COLUMN_REFILL_DATE + " TEXT,"
                + COLUMN_REFILL_RUN + " INTEGER,"
                + COLUMN_REFILL_BEFORE_REFILL + " REAL,"
                + COLUMN_REFILL_ADD_FUEL + " REAL,"
                + COLUMN_REFILL_PRICE + " REAL,"
                + COLUMN_REFILL_STATION + " TEXT,"
                + " FOREIGN KEY ("+ COLUMN_AUTO_ID + ")  REFERENCES auto (auto_id) "
                + "ON DELETE CASCADE "
                + ")";
        db.execSQL(CREATE_REFILL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_REFILL_TABLE = "DROP TABLE IF EXISTS " + TABLE_REFILL;
        db.execSQL(DROP_REFILL_TABLE);
        onCreate(db);
    }

    public void addRefill(Refill refill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_ID, refill.getAuto_id());
        values.put(COLUMN_REFILL_DATE, refill.getDate().toString());
        values.put(COLUMN_REFILL_RUN, refill.getRun());
        values.put(COLUMN_REFILL_BEFORE_REFILL, refill.getBeforeRefill());
        values.put(COLUMN_REFILL_ADD_FUEL, refill.getAddFuel());
        values.put(COLUMN_REFILL_PRICE, refill.getPrice());
        values.put(COLUMN_REFILL_STATION, refill.getStation());

        db.insert(TABLE_REFILL, null, values);
        db.close();
    }
    public void deleteRefill(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_REFILL = "DELETE FROM " + TABLE_REFILL + " WHERE "
                + COLUMN_REFILL_ID + "=" + id;
        db.execSQL(DELETE_REFILL);
        db.close();
    }

    public Cursor readAllDateByAutoId(int id){
        String query = "SELECT * FROM " + TABLE_REFILL + " WHERE("
                + COLUMN_AUTO_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor findById(int id){
        String query = "SELECT * FROM " + TABLE_REFILL + " WHERE("
                + COLUMN_REFILL_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateRefill(Refill refill){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_REFILL = "UPDATE " + TABLE_REFILL + " SET "
                + COLUMN_REFILL_DATE + "=" + refill.getDate().toString() + ","
                + COLUMN_REFILL_RUN + "=" + refill.getRun() + ","
                + COLUMN_REFILL_BEFORE_REFILL + "=" + refill.getBeforeRefill() + ","
                + COLUMN_REFILL_ADD_FUEL + "=" + refill.getAddFuel() + ","
                + COLUMN_REFILL_PRICE + "=" + refill.getPrice() + ","
                + COLUMN_REFILL_STATION + "=" + refill.getStation() + ","
                + " WHERE " + COLUMN_REFILL_ID + "=" + refill.getId();
        db.execSQL(UPDATE_REFILL);
        db.close();
    }
}
