package com.example.ajanda;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Veritabani extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "yapilacaklarListesi";
    public static final int dbVersion = 1;

    public Veritabani(@Nullable Context context) {
        super(context, DATABASE_NAME, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sorgu = "CREATE TABLE liste (id INTEGER PRIMARY KEY AUTOINCREMENT, listeNotu TEXT NOT NULL, durum INTEGER NOT NULL)";
        db.execSQL(sorgu);
    }
    public long ekle(String not){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("listeNotu",not);
        contentValues.put("durum",0);
        long durum = db.insert("liste",null,contentValues);
        db.close();
        return durum;
    }
    public int duzenle(int id, String not){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("listeNotu",not);
        int durum = db.update("liste",contentValues,"id=?", new String[]{String.valueOf(id)});
        db.close();
        return durum;
    }
    public int sil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int durum = db.delete("liste","id=?",new String[]{String.valueOf(id)});
        db.close();
        return durum;
    }
    public int durumDuzenle(int id, int durum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("durum",durum);
        int donecekDurum = db.update("liste",contentValues,"id=?",new String[]{String.valueOf(id)});
        db.close();
        return donecekDurum;
    }
    public List<Liste> liste(){
        List<Liste> donecekListemiz = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM liste";
        Cursor c = db.rawQuery(sql,null);
        int id = c.getColumnIndex("id");
        int listeNotu = c.getColumnIndex("listeNotu");
        int durum = c.getColumnIndex("durum");
        c.moveToFirst();
        while (c.moveToNext()) {
            Liste liste = new Liste(c.getInt(id),c.getInt(durum),c.getString(listeNotu));
            donecekListemiz.add(liste);
        }
        c.close();
        db.close();
        return donecekListemiz;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS liste");
        onCreate(db);
    }
}
