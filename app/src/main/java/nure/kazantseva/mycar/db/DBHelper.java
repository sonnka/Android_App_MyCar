package nure.kazantseva.mycar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;

import nure.kazantseva.mycar.model.Auto;
import nure.kazantseva.mycar.model.Other;
import nure.kazantseva.mycar.model.Refill;
import nure.kazantseva.mycar.model.Repair;
import nure.kazantseva.mycar.model.User;
import nure.kazantseva.mycar.model.UserAuto;
import nure.kazantseva.mycar.model.Washer;
import nure.kazantseva.mycar.utils.InputValidator;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;

    private static final String DATABASE_NAME = "MyCar.db";

    private static final String TABLE_AUTO = "auto";
    private static final String TABLE_USER = "user";
    private static final String TABLE_USER_AUTO = "userAuto";
    private static final String TABLE_REPAIR = "repair";
    private static final String TABLE_REFILL = "refill";
    private static final String TABLE_WASHER = "washer";
    private static final String TABLE_OTHER = "other";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_AUTO_ID = "user_auto_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_IMAGE = "image";

    private static final String COLUMN_AUTO_ID = "auto_id";
    private static final String COLUMN_UNIQUE_CODE = "unique_code";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_MODEL = "model";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_FUEL = "fuel";
    private static final String COLUMN_RUN = "run";

    private static final String COLUMN_COLOR = "color";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";

    private static final String COLUMN_BEFORE_REFILL = "before_refill";
    private static final String COLUMN_ADD_FUEL = "add_fuel";
    private static final String COLUMN_STATION = "station";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME
                , null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";

        String CREATE_AUTO_TABLE = "CREATE TABLE " + TABLE_AUTO + "("
                + COLUMN_AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_UNIQUE_CODE + " TEXT,"
                + COLUMN_BRAND + " TEXT,"
                + COLUMN_MODEL + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_FUEL + " TEXT NOT NULL,"
                + COLUMN_RUN + " INTEGER"
                + ")";

        String CREATE_USER_AUTO_TABLE = "CREATE TABLE " + TABLE_USER_AUTO + "("
                + COLUMN_USER_AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_AUTO_ID + " INTEGER,"
                + " FOREIGN KEY ("+ COLUMN_EMAIL + ")  REFERENCES user (email) "
                + "ON DELETE CASCADE ON UPDATE CASCADE ,"
                + " FOREIGN KEY ("+ COLUMN_AUTO_ID + ")  REFERENCES auto (auto_id) "
                + "ON DELETE CASCADE "
                + ")";

        String CREATE_REPAIR_TABLE = "CREATE TABLE " + TABLE_REPAIR + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTO_ID + " INTEGER,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_COLOR + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_RUN + " INTEGER,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + " FOREIGN KEY ("+ COLUMN_AUTO_ID + ")  REFERENCES auto (auto_id) "
                + "ON DELETE CASCADE "
                + ")";

        String CREATE_REFILL_TABLE = "CREATE TABLE " + TABLE_REFILL + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTO_ID + " INTEGER,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_COLOR + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_RUN + " INTEGER,"
                + COLUMN_BEFORE_REFILL + " REAL,"
                + COLUMN_ADD_FUEL + " REAL,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_STATION + " TEXT,"
                + " FOREIGN KEY ("+ COLUMN_AUTO_ID + ")  REFERENCES auto (auto_id) "
                + "ON DELETE CASCADE "
                + ")";

        String CREATE_WASHER_TABLE = "CREATE TABLE " + TABLE_WASHER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTO_ID + " INTEGER,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_COLOR + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + " FOREIGN KEY ("+ COLUMN_AUTO_ID + ")  REFERENCES auto (auto_id) "
                + "ON DELETE CASCADE "
                + ")";

        String CREATE_OTHER_TABLE = "CREATE TABLE " + TABLE_OTHER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTO_ID + " INTEGER,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_COLOR + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + " FOREIGN KEY ("+ COLUMN_AUTO_ID + ")  REFERENCES auto (auto_id) "
                + "ON DELETE CASCADE "
                + ")";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_AUTO_TABLE);
        db.execSQL(CREATE_USER_AUTO_TABLE);
        db.execSQL(CREATE_REPAIR_TABLE);
        db.execSQL(CREATE_REFILL_TABLE);
        db.execSQL(CREATE_WASHER_TABLE);
        db.execSQL(CREATE_OTHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

        String DROP_AUTO_TABLE = "DROP TABLE IF EXISTS " + TABLE_AUTO;

        String DROP_USER_AUTO_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER_AUTO;

        String DROP_REPAIR_TABLE = "DROP TABLE IF EXISTS " + TABLE_REPAIR;

        String DROP_REFILL_TABLE = "DROP TABLE IF EXISTS " + TABLE_REFILL;

        String DROP_WASHER_TABLE = "DROP TABLE IF EXISTS " + TABLE_WASHER;

        String DROP_OTHER_TABLE = "DROP TABLE IF EXISTS " + TABLE_OTHER;

        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_AUTO_TABLE);
        db.execSQL(DROP_USER_AUTO_TABLE);
        db.execSQL(DROP_REPAIR_TABLE);
        db.execSQL(DROP_REFILL_TABLE);
        db.execSQL(DROP_WASHER_TABLE);
        db.execSQL(DROP_OTHER_TABLE);

        onCreate(db);
    }


    public Cursor executeQuery(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            try{
                cursor = db.rawQuery(query, null);
            }catch (SQLiteException ignored){

            }
        }
        return cursor;
    }

    public int searchByCode(String code){
        String[] columns = {
                COLUMN_AUTO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_UNIQUE_CODE + " = ?";

        String[] selectionArgs = {code};

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

    public Cursor readAllDataByAutoId(int id){
        String query = " SELECT "+ COLUMN_ID + " , " + COLUMN_COLOR + " , " + COLUMN_NAME + " , "
                + COLUMN_DATE + " , " + COLUMN_PRICE +  " FROM "
                + TABLE_REPAIR  + " WHERE " +  COLUMN_AUTO_ID + "=" + id
                + " UNION "
                + "SELECT "+ COLUMN_ID + " , " + COLUMN_COLOR + " , " + COLUMN_NAME + " , "
                + COLUMN_DATE + " , " + COLUMN_PRICE + " FROM "
                + TABLE_REFILL  + " WHERE " +  COLUMN_AUTO_ID + "=" + id +
                " UNION "
                + "SELECT "+ COLUMN_ID + " , " + COLUMN_COLOR + " , " + COLUMN_NAME + " , "
                + COLUMN_DATE + " , " + COLUMN_PRICE + " FROM "
                + TABLE_WASHER  + " WHERE " +  COLUMN_AUTO_ID + "=" + id +
                " UNION "
                + "SELECT "+ COLUMN_ID + " , " + COLUMN_COLOR + " , " + COLUMN_NAME + " , "
                + COLUMN_DATE + " , " + COLUMN_PRICE + " FROM "
                + TABLE_OTHER  + " WHERE " +  COLUMN_AUTO_ID + "=" + id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readCertainDataByAutoId(int id, String table_name){
        String query = " SELECT "+ COLUMN_ID + " , " + COLUMN_COLOR + " , " + COLUMN_NAME + " , "
                + COLUMN_DATE + " , " + COLUMN_PRICE +  " FROM "
                + table_name  + " WHERE " +  COLUMN_AUTO_ID + "=" + id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor searchByDate(int id, String date){
        String query = " SELECT "+ COLUMN_ID + " , " + COLUMN_COLOR + " , " + COLUMN_NAME + " , "
                + COLUMN_DATE + " , " + COLUMN_PRICE +  " FROM "
                + TABLE_REPAIR  + " WHERE " +  COLUMN_AUTO_ID + "=" + id
                + " AND " + COLUMN_DATE + " LIKE '%" + date + "%'"
                + " UNION "
                + "SELECT "+ COLUMN_ID + " , " + COLUMN_COLOR + " , " + COLUMN_NAME + " , "
                + COLUMN_DATE + " , " + COLUMN_PRICE + " FROM "
                + TABLE_REFILL  + " WHERE " +  COLUMN_AUTO_ID + "=" + id
                + " AND " + COLUMN_DATE + " LIKE '%" + date + "%'"
                + " UNION "
                + "SELECT "+ COLUMN_ID + " , " + COLUMN_COLOR + " , " + COLUMN_NAME + " , "
                + COLUMN_DATE + " , " + COLUMN_PRICE + " FROM "
                + TABLE_WASHER  + " WHERE " +  COLUMN_AUTO_ID + "=" + id
                + " AND " + COLUMN_DATE + " LIKE '%" + date + "%'"
                + " UNION "
                + "SELECT "+ COLUMN_ID + " , " + COLUMN_COLOR + " , " + COLUMN_NAME + " , "
                + COLUMN_DATE + " , " + COLUMN_PRICE + " FROM "
                + TABLE_OTHER  + " WHERE " +  COLUMN_AUTO_ID + "=" + id
                + " AND " + COLUMN_DATE + " LIKE '%" + date + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    public void addAuto(Auto auto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UNIQUE_CODE, auto.getUniqueCode());
        values.put(COLUMN_BRAND, auto.getBrand());
        values.put(COLUMN_MODEL, auto.getModel());
        values.put(COLUMN_YEAR, auto.getYear());
        values.put(COLUMN_FUEL, auto.getFuel());
        values.put(COLUMN_RUN, auto.getRun());

        db.insert(TABLE_AUTO, null, values);
        db.close();
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addUserAuto(UserAuto userAuto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, userAuto.getUserEmail());
        values.put(COLUMN_AUTO_ID, userAuto.getAutoId());

        db.insert(TABLE_USER_AUTO, null, values);
        db.close();
    }

    public void addRepair(Repair repair){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_ID, repair.getAuto_id());
        values.put(COLUMN_NAME, "Ремонт");
        values.put(COLUMN_COLOR, "#F5D4F8");
        values.put(COLUMN_DATE, repair.getDate().toString());
        values.put(COLUMN_RUN, repair.getRun());
        values.put(COLUMN_DESCRIPTION, repair.getDescription());
        values.put(COLUMN_PRICE, repair.getPrice());

        db.insert(TABLE_REPAIR, null, values);
        db.close();
    }

    public void addRefill(Refill refill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_ID, refill.getAuto_id());
        values.put(COLUMN_NAME, "Заправка");
        values.put(COLUMN_COLOR, "#D5F6D3");
        values.put(COLUMN_DATE, refill.getDate().toString());
        values.put(COLUMN_RUN, refill.getRun());
        values.put(COLUMN_BEFORE_REFILL, refill.getBeforeRefill());
        values.put(COLUMN_ADD_FUEL, refill.getAddFuel());
        values.put(COLUMN_PRICE, refill.getPrice());
        values.put(COLUMN_STATION, refill.getStation());

        db.insert(TABLE_REFILL, null, values);
        db.close();
    }

    public void addWasher(Washer washer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_ID, washer.getAuto_id());
        values.put(COLUMN_NAME, "Автомийка");
        values.put(COLUMN_COLOR, "#BBF1F6");
        values.put(COLUMN_DATE, washer.getDate().toString());
        values.put(COLUMN_PRICE, washer.getPrice());

        db.insert(TABLE_WASHER, null, values);
        db.close();
    }

    public void addOther(Other other){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_ID, other.getAuto_id());
        values.put(COLUMN_NAME, "Інше");
        values.put(COLUMN_COLOR, "#FDFDD4");
        values.put(COLUMN_DATE, other.getDate().toString());
        values.put(COLUMN_DESCRIPTION, other.getDescription());
        values.put(COLUMN_PRICE, other.getPrice());

        db.insert(TABLE_OTHER, null, values);
        db.close();
    }


    public void deleteAuto(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_AUTO = "DELETE FROM " + TABLE_AUTO + " WHERE "
                + COLUMN_AUTO_ID + "=" + id;
        db.execSQL(DELETE_AUTO);
        db.close();
    }

    public void deleteUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_USER = "DELETE FROM " + TABLE_USER + " WHERE "
                + COLUMN_EMAIL + "=" + email;
        db.execSQL(DELETE_USER);
        db.close();
    }

    public void deleteUserAuto (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_USER_AUTO = "DELETE FROM " + TABLE_USER_AUTO + " WHERE "
                + COLUMN_USER_AUTO_ID + "=" + id;
        db.execSQL(DELETE_USER_AUTO);
        db.close();
    }

    public void deleteRepair(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_REPAIR = "DELETE FROM " + TABLE_REPAIR + " WHERE "
                + COLUMN_ID + "=" + id;
        db.execSQL(DELETE_REPAIR);
        db.close();
    }

    public void deleteRefill(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_REFILL = "DELETE FROM " + TABLE_REFILL + " WHERE "
                + COLUMN_ID + "=" + id;
        db.execSQL(DELETE_REFILL);
        db.close();
    }

    public void deleteWasher(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_WASHER = "DELETE FROM " + TABLE_WASHER + " WHERE "
                + COLUMN_ID + "=" + id;
        db.execSQL(DELETE_WASHER);
        db.close();
    }

    public void deleteOther(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_OTHER = "DELETE FROM " + TABLE_OTHER + " WHERE "
                + COLUMN_ID + "=" + id;
        db.execSQL(DELETE_OTHER);
        db.close();
    }


    public void updateUser(User user, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_USER = "UPDATE " + TABLE_USER + " SET "
                + COLUMN_NAME + " = '" + user.getName() + "', "
                + COLUMN_PASSWORD + " = '" + user.getPassword() + "', "
                + COLUMN_EMAIL + " = '" + user.getEmail() + "'"
                + " WHERE(" + COLUMN_EMAIL + "='" + email + "')";
        db.execSQL(UPDATE_USER);
        db.close();
    }

    public void updateAuto(Auto auto){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_AUTO = "UPDATE " + TABLE_AUTO + " SET "
                + COLUMN_MODEL + " = '" + auto.getModel() + "',"
                + COLUMN_BRAND + " = '" + auto.getBrand() + "',"
                + COLUMN_YEAR + " = " + auto.getYear() + ", "
                + COLUMN_RUN + " = " + auto.getRun()
                + " WHERE(" + COLUMN_AUTO_ID + "=" + auto.getId() + ")";
        db.execSQL(UPDATE_AUTO);
        db.close();
    }

    public void updateRepair(Repair repair){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_REPAIR = "UPDATE " + TABLE_REPAIR + " SET "
                + COLUMN_DATE + " = '" + repair.getDate().toString() + "', "
                + COLUMN_RUN + " = '" + repair.getRun() + "',"
                + COLUMN_DESCRIPTION + " = '" + repair.getDescription() + "', "
                + COLUMN_PRICE + " = '" + repair.getPrice() + "'"
                + " WHERE(" + COLUMN_ID + "=" + repair.getId() + ")";
        db.execSQL(UPDATE_REPAIR);
        db.close();
    }

    public void updateRefill(Refill refill){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_REFILL = "UPDATE " + TABLE_REFILL + " SET "
                + COLUMN_DATE + " = '" + refill.getDate().toString() + "',"
                + COLUMN_RUN + " = '" + refill.getRun() + "',"
                + COLUMN_BEFORE_REFILL + " = '" + refill.getBeforeRefill() + "',"
                + COLUMN_ADD_FUEL + " = '" + refill.getAddFuel() + "',"
                + COLUMN_PRICE + " = '" + refill.getPrice() + "',"
                + COLUMN_STATION + " = '" + refill.getStation() + "'"
                + " WHERE(" + COLUMN_ID + "=" + refill.getId() +")";
        db.execSQL(UPDATE_REFILL);
        db.close();
    }

    public void updateWasher(Washer washer){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_WASHER = "UPDATE " + TABLE_WASHER + " SET "
                + COLUMN_DATE + " = '" + washer.getDate().toString() + "',"
                + COLUMN_PRICE + " = '" + washer.getPrice() + "'"
                + " WHERE(" + COLUMN_ID + "=" + washer.getId() + ")";
        db.execSQL(UPDATE_WASHER);
        db.close();
    }

    public void updateOther(Other other){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_OTHER = "UPDATE " + TABLE_OTHER + " SET "
                + COLUMN_DATE + " = '" + other.getDate().toString() + "',"
                + COLUMN_DESCRIPTION + " = '" + other.getDescription() + "',"
                + COLUMN_PRICE + " = '" + other.getPrice() +"'"
                + " WHERE(" + COLUMN_ID + "=" + other.getId() + ")";
        db.execSQL(UPDATE_OTHER);
        db.close();
    }


    public Cursor findRepairById(int id){
        String query = "SELECT * FROM " + TABLE_REPAIR + " WHERE("
                + COLUMN_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor findRefillById(int id){
        String query = "SELECT * FROM " + TABLE_REFILL + " WHERE("
                + COLUMN_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor findWasherById(int id){
        String query = "SELECT * FROM " + TABLE_WASHER + " WHERE("
                + COLUMN_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor findOtherById(int id){
        String query = "SELECT * FROM " + TABLE_OTHER + " WHERE("
                + COLUMN_ID + "=" + id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    public boolean checkUser(String email) {
        String query = "SELECT * FROM " + TABLE_USER + " WHERE("
                + COLUMN_EMAIL + "='" + email +"')";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        if(cursor == null){
            return false;
        }
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {
                COLUMN_EMAIL
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public boolean checkUniqueCode(String uniqueCode) {
        String[] columns = {
                COLUMN_AUTO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_UNIQUE_CODE + " = ?";

        String[] selectionArgs = {uniqueCode};

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
        return cursorCount > 0;
    }

    public int checkByEmail(String email){
        String[] columns = {
                COLUMN_USER_AUTO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER_AUTO, //Table to query
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
        String query = "SELECT * FROM " + TABLE_USER_AUTO + " WHERE("
                + COLUMN_EMAIL + "='" + email +"')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
            if(cursor.getCount() > 0 && cursor.moveToFirst()) {
                int id = cursor.getInt(2);
                db.close();
                return id;
            } else {
                db.close();
                return 0;
            }
        }else {
            db.close();
            return -1;
        }

    }

    public Cursor findUserByEmail(String email){
        String query = "SELECT * FROM " + TABLE_USER + " WHERE("
                + COLUMN_EMAIL + "='" + email +"')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor findAutoById(int auto_id){
        String query = "SELECT * FROM " + TABLE_AUTO + " WHERE("
                + COLUMN_AUTO_ID + "=" + auto_id +")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor findMaxRun(int auto_id){
        String query = "SELECT MAX(" + COLUMN_RUN + ") AS max FROM ("
                + "SELECT MAX(" + COLUMN_RUN+ ") AS run FROM "  + TABLE_AUTO
                + " WHERE(" + COLUMN_AUTO_ID + "='" + auto_id +"')"
                + "UNION "
                + "SELECT MAX(" + COLUMN_RUN+ ") AS run FROM "  + TABLE_REPAIR
                + " WHERE(" + COLUMN_AUTO_ID + "='" + auto_id +"')"
                + "UNION "
                + "SELECT MAX(" + COLUMN_RUN+ ") AS run FROM "  + TABLE_REFILL
                + " WHERE(" + COLUMN_AUTO_ID + "='" + auto_id +"')"
                + ") AS TEMP" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor getFuelConsumption(int auto_id){
        String query = "SELECT " + COLUMN_RUN + ", "
                + COLUMN_BEFORE_REFILL + ", " + COLUMN_ADD_FUEL
                + " FROM " + TABLE_REFILL
                + " WHERE(" + COLUMN_AUTO_ID + "=" + auto_id + " )"
                + " ORDER BY " + COLUMN_RUN + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public int countAutos(String email){
        String query = "SELECT * FROM " + TABLE_USER_AUTO
                + " WHERE(" + COLUMN_EMAIL + "='" + email + "')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor.getCount();
    }

    public Cursor getAutosOfUser(String email){
        String query = "SELECT " + COLUMN_AUTO_ID + ", " + COLUMN_BRAND + ", " + COLUMN_MODEL
                + " FROM " + TABLE_AUTO
                + " WHERE(" + COLUMN_AUTO_ID + " IN ("
                + "SELECT " + COLUMN_AUTO_ID + " FROM " + TABLE_USER_AUTO
                + " WHERE(" + COLUMN_EMAIL + "='" + email + "'))"
                + ")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    public void addImageColumnUser(){
        String query = "ALTER TABLE " + TABLE_USER + " ADD COLUMN " + COLUMN_IMAGE + " text";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void addImageColumnAuto(){
        String query = "ALTER TABLE " + TABLE_AUTO + " ADD COLUMN " + COLUMN_IMAGE + " text";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }


    public void insertImageUser(String image, String email){
        String query = "UPDATE " + TABLE_USER + " SET " + COLUMN_IMAGE
                + "='" + image + "'" + " WHERE(" + COLUMN_EMAIL + "='" + email + "')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void insertImageAuto(String image, int auto_id){
        String query = "UPDATE " + TABLE_AUTO + " SET " + COLUMN_IMAGE
                + "='" + image + "'" + " WHERE(" + COLUMN_AUTO_ID + "=" + auto_id + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public Cursor getImageUser(String email){
        String query = "SELECT " + COLUMN_IMAGE + " FROM " + TABLE_USER
                + " WHERE(" + COLUMN_EMAIL + "='" + email + "')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor getImageAuto(int auto_id){
        String query = "SELECT " + COLUMN_IMAGE + " FROM " + TABLE_AUTO
                + " WHERE(" + COLUMN_AUTO_ID + "=" + auto_id + ")";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


}
