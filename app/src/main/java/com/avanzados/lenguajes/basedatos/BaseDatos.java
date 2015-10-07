package com.avanzados.lenguajes.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by estudiante on 29/09/2015.
 */
public class BaseDatos extends SQLiteOpenHelper{

    public BaseDatos(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(context, nombre, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table persona (nombre text, apellidos text, edad integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
