package com.lukitor.projectandroidfundamentalkotlin2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class FavoriteHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = DatabaseContract.FavoriteColumns.TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: FavoriteHelper? = null
        private lateinit var database: SQLiteDatabase
        fun getInstance(context: Context): FavoriteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteHelper(context)
            }
    }
    init {dataBaseHelper = DatabaseHelper(context)}
    @Throws(SQLException::class)
    fun open() {database = dataBaseHelper.writableDatabase}
    fun cek(username: String): Int {
        val c: Cursor = database.query(DATABASE_TABLE,null,"${DatabaseContract.FavoriteColumns.USERNAME} = ?",arrayOf(username),null,null,null,null)
        val sum = c.count
        return sum
    }
    fun queryAll(): Cursor {return database.query(DATABASE_TABLE,null,null,null,null,null,"${DatabaseContract.FavoriteColumns.ID} ASC")}
    fun queryById(id: String): Cursor {return database.query(DATABASE_TABLE,null,"${DatabaseContract.FavoriteColumns.ID} = ?",arrayOf(id),null,null,null,null) }
    fun insert(values: ContentValues?): Long {return database.insert(DATABASE_TABLE, null, values)}
    fun deleteById(id: String): Int {return database.delete(DATABASE_TABLE, "${DatabaseContract.FavoriteColumns.ID} = '$id'", null)}
}