package com.example.mad;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DB_Employee extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Emp_man.db";
    public static final String TABLE_NAME = "employee_details";
    public static final String COL_1 = "EmployeeID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Address";
    public static final String COL_4 = "TelephoneNo";
    public static final String COL_5 = "EmailAddress";
    public static final String COL_6 = "WorkingPosition";
    public static final String COL_7 = "Salary";

    public DB_Employee( Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteOpenHelper=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(EmployeeID text primary key,Name text, Address text  ,TelephoneNo integer, EmailAddress text  ,WorkingPosition text, Salary integer)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }//insert Employee Data
    public boolean insertData(String EmployeeID, String Name, String Address, String TelephoneNo,String EmailAddress, String WorkingPosition, String Salary) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,EmployeeID);
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Address);
        contentValues.put(COL_4, TelephoneNo);
        contentValues.put(COL_5, EmailAddress);
        contentValues.put(COL_6, WorkingPosition);
        contentValues.put(COL_7, Salary);

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
    }//update Employee Data
    public  boolean updateDetail(String EmployeeID, String Name, String Address, String TelephoneNo,String EmailAddress, String WorkingPosition, String Salary){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Address);
        contentValues.put(COL_4, TelephoneNo);
        contentValues.put(COL_5, EmailAddress);
        contentValues.put(COL_6, WorkingPosition);
        contentValues.put(COL_7, Salary);

        sqLiteDatabase.update(TABLE_NAME,contentValues,"EmployeeID = ?",new String[] {EmployeeID});
        return true;
    }//Delete Employee Data
    public Integer deleteDetail (String EmployeeID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"EmployeeID = ?",new String[] {EmployeeID});
    }//Search Employee Data
    public Cursor searchData(String EmployeeID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "= '" + EmployeeID + "'",null);
        return data;
    };


}
