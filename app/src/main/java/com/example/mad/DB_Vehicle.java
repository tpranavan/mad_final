package com.example.mad;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB_Vehicle extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Vehicle_maneger.db";
    public static final String TABLE_NAME = "Vehicle";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "VehicleType";
    public static final String COL_3 = "NoOfVehicle";
    public static final String COL_4 = "VehicleCost";
    public static final String COL_5 = "VehicleFacilities";

    public DB_Vehicle(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER primary key ,VehicleType text,NoOfVehicle interger,VehicleCost interger,VehicleFacilities text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public boolean insertData(String ID, String VehicleType, String NoOfVehicle, String VehicleCost, String VehicleFacilities) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, ID);
        contentValues.put(COL_2, VehicleType);
        contentValues.put(COL_3, NoOfVehicle);
        contentValues.put(COL_4, VehicleCost);
        contentValues.put(COL_5, VehicleFacilities);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public  boolean updateData(String ID,String VehicleType, String NoOfVehicle, String VehicleCost, String VehicleFacilities){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(COL_2, VehicleType);
        contentValues.put(COL_3, NoOfVehicle);
        contentValues.put(COL_4, VehicleCost);
        contentValues.put(COL_5, VehicleFacilities);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"ID = ?",new String[] {ID});
        return true;
    }
    public Integer deleteData (String ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"ID = ?",new String[]{ID});
    }
    public Cursor searchData(String ID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "= '" + ID + "'",null);
        return data;
    };

}
