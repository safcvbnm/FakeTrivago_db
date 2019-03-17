package com.example.faketrivago_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class MyDB {

    public SQLiteDatabase db = null;
    private final static String DATABASE_NAME = "db1.db";
    private final static String TABLE_NAME = "table01";
    private final static String _ID = "_id";
    private final static String HOTEL_NAME = "hotel_name";
    private final static String HOTEL_ADDRESS = "hotel_address";
    private final static String HOTEL_PHONE = "hotel_phone";
    private final static String NUM_ROOM = "num_room";
    private final static String PRICE = "price";
    private final static String RANK = "rank";
    private final static String IMAGE = "image";
    private final static String LATITUBE = "latitude";
    private final static String LONGITUBE = "longitube";
    private final static String DISCOUNT = "discount";

    private final static String CREAK_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+_ID+" INTEGER PRIMARY KEY, "+HOTEL_NAME+" TEXT, "+
            HOTEL_ADDRESS+" TEXT, "+HOTEL_PHONE+" TEXT, "+NUM_ROOM+" INTEGER, "+PRICE+" INTEGER, "+RANK+" INTEGER, "+IMAGE+" INTEGER, "+LATITUBE+" FLOAT, "+LONGITUBE+" FLOAT, "+DISCOUNT+" TEXT)";
    private Context mCtx = null;

    //建構子
    public MyDB(Context ctx){
        //傳入 建立物件的Activity
        this.mCtx = ctx;
    }
    //打開資料庫
    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        try{
            db.execSQL(CREAK_TABLE);
        }catch (Exception e){

        }
    }

    //關閉資料庫
    public void close(){
        db.close();
    }

    //刪除Table
    public void drop(){
        db.execSQL("DROP TABLE "+TABLE_NAME);
    }

    //查詢所有資料 取出所有欄位
    //public Cursor getAll(){
    //    return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    //}

    //查詢所有資料 取出10個欄位
    public Cursor getAll(){
        return db.query(TABLE_NAME,new String[] {_ID,HOTEL_NAME,HOTEL_ADDRESS,HOTEL_PHONE,NUM_ROOM,PRICE,RANK,IMAGE,LATITUBE,LONGITUBE,DISCOUNT},null,null,null,null,null,null);
    }

    //查詢指定ID的資料 取出10個欄位
    public Cursor get(long rowID) throws SQLException{
        Cursor mCursor = db.query(TABLE_NAME,new String[] {_ID,HOTEL_NAME,HOTEL_ADDRESS,HOTEL_PHONE,NUM_ROOM,PRICE,RANK,IMAGE,LATITUBE,LONGITUBE,DISCOUNT},_ID+"="+rowID,null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return  mCursor;
    }
    //查詢指定name的資料 使用like模糊語法找出類似詞彙 取出10個欄位
    public Cursor get(String name) throws SQLException{
        Cursor mCursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+HOTEL_NAME+" LIKE '"+name+"%'",null);
        return  mCursor;
    }
    //查詢指定price範圍內的資料 取出10個欄位
    public Cursor get(String upPrice,String downPrice) throws SQLException{
        Cursor mCursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+PRICE+">="+downPrice+" AND "+PRICE+"<="+upPrice,null);
        return  mCursor;
    }
    //新增資料
    public long append(String name,String address,String phone,int num,int price,int rank,int image,float latitube,float longitube,String discount){
        ContentValues args = new ContentValues();
        args.put(HOTEL_NAME,name);
        args.put(HOTEL_ADDRESS,address);
        args.put(HOTEL_PHONE,phone);
        args.put(NUM_ROOM,num);
        args.put(PRICE,price);
        args.put(RANK,rank);
        args.put(IMAGE,image);
        args.put(LATITUBE,latitube);
        args.put(LONGITUBE,longitube);
        args.put(DISCOUNT,discount);

        return db.insert(TABLE_NAME,null,args);
    }


}