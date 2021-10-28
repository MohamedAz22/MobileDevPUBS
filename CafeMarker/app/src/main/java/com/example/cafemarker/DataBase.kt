package com.example.cafemarker

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

class DataBase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_PUBS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_TABLE_PUBS)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun allPubs(): ArrayList<String> {
        val pubsArrayList = ArrayList<String>()
        var name: String
        val db = this.readableDatabase

        val projection = arrayOf(KEY_ID, NAME)
        val selection = null
        val selectionArgs = null
        val sortOrder = "${NAME} DESC"

        val cursor = db.query(
            TABLE_PUBS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(NAME))
                pubsArrayList.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return pubsArrayList
    }

    fun addPubs(pub: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, pub)
        values.put(LAT, pub)
        values.put(LON, pub)

        return db.insert(TABLE_PUBS, null, values)
    }

    companion object {

        var DATABASE_NAME = "pubs_database"
        private val DATABASE_VERSION = 7
        private val TABLE_PUBS = "pubs"
        private val KEY_ID = "id"
        private val NAME = "name"
        private val LAT = "lat"
        private val LON = "lon"

        private val CREATE_TABLE_PUBS = ("CREATE TABLE "
                + TABLE_PUBS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT );"
                + LAT + " DECIMAL );"
                + LON + " DECIMAL );")




        private val DELETE_TABLE_PUBS = "DROP TABLE IF EXISTS $TABLE_PUBS"


    }
}


