package nure.kazantseva.mycar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Optional;

import nure.kazantseva.mycar.model.Repair;

public class DBHelperRepair extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    private static final String DATABASE_NAME = "MyCar.db";
    private static final String TABLE_REPAIR = "repair";
    private static final String COLUMN_REPAIR_ID = "repair_id";
    private static final String COLUMN_AUTO_ID = "auto_id";
    private static final String COLUMN_REPAIR_DATE = "repair_date";
    private static final String COLUMN_REPAIR_RUN = "repair_run";
    private static final String COLUMN_REPAIR_DESCRIPTION = "repair_description";
    private static final String COLUMN_REPAIR_PRICE = "repair_price";

    public DBHelperRepair(Context context) {
        super(context, DATABASE_NAME
                , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REPAIR_TABLE = "CREATE TABLE " + TABLE_REPAIR + "("
                + COLUMN_REPAIR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTO_ID + " INTEGER,"
                + COLUMN_REPAIR_DATE + " TEXT,"
                + COLUMN_REPAIR_RUN + " INTEGER,"
                + COLUMN_REPAIR_DESCRIPTION + " TEXT,"
                + COLUMN_REPAIR_PRICE + " REAL,"
                + " FOREIGN KEY ("+ COLUMN_AUTO_ID + ")  REFERENCES auto (auto_id) "
                + "ON DELETE CASCADE "
                + ")";
        db.execSQL(CREATE_REPAIR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_REPAIR_TABLE = "DROP TABLE IF EXISTS " + TABLE_REPAIR;
        db.execSQL(DROP_REPAIR_TABLE);
        onCreate(db);
    }

    public void addRepair(Repair repair){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_ID, repair.getAuto_id());
        values.put(COLUMN_REPAIR_DATE, repair.getDate().toString());
        values.put(COLUMN_REPAIR_RUN, repair.getRun());
        values.put(COLUMN_REPAIR_DESCRIPTION, repair.getDescription());
        values.put(COLUMN_REPAIR_PRICE, repair.getPrice());

        db.insert(TABLE_REPAIR, null, values);
        db.close();
    }
    public void deleteRepair(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_REPAIR = "DELETE FROM " + TABLE_REPAIR + " WHERE "
                + COLUMN_REPAIR_ID + "=" + id;
        db.execSQL(DELETE_REPAIR);
        db.close();
    }

    public Cursor readAllDateByAutoId(int id){
        String query = "SELECT * FROM " + TABLE_REPAIR + " WHERE("
                + COLUMN_AUTO_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor findById(int id){
        String query = "SELECT * FROM " + TABLE_REPAIR + " WHERE("
                + COLUMN_REPAIR_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateRepair(Repair repair){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_REPAIR = "UPDATE " + TABLE_REPAIR + " SET "
                + COLUMN_REPAIR_DATE + "=" + repair.getDate().toString() + ","
                + COLUMN_REPAIR_RUN + "=" + repair.getRun() + ","
                + COLUMN_REPAIR_DESCRIPTION + "=" + repair.getDescription() + ","
                + COLUMN_REPAIR_PRICE + "=" + repair.getPrice()
                + " WHERE " + COLUMN_REPAIR_ID + "=" + repair.getId();
        db.execSQL(UPDATE_REPAIR);
        db.close();
    }
}
