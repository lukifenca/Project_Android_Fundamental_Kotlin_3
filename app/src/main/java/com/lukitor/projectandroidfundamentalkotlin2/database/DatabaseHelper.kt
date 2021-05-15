package com.lukitor.projectandroidfundamentalkotlin2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbFavorite"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_Favorite = "CREATE TABLE ${DatabaseContract.FavoriteColumns.TABLE_NAME}" + " (${DatabaseContract.FavoriteColumns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," + " ${DatabaseContract.FavoriteColumns.USERNAME} TEXT NOT NULL," + " ${DatabaseContract.FavoriteColumns.NAME} TEXT NOT NULL," + " ${DatabaseContract.FavoriteColumns.IMAGE} TEXT NOT NULL)"
    }
    override fun onCreate(db: SQLiteDatabase) {db.execSQL(SQL_CREATE_TABLE_Favorite)}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.FavoriteColumns.TABLE_NAME}")
        onCreate(db)
    }
}