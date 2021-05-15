package com.lukitor.projectandroidfundamentalkotlin2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.lukitor.projectandroidfundamentalkotlin2.database.DatabaseContract
import com.lukitor.projectandroidfundamentalkotlin2.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.lukitor.projectandroidfundamentalkotlin2.database.FavoriteHelper

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val FAV_USER = 1
        private const val FAV_USER_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var Favoritehelper: FavoriteHelper
        init {
            sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.FavoriteColumns.TABLE_NAME, FAV_USER)
            sUriMatcher.addURI(DatabaseContract.AUTHORITY, "${DatabaseContract.FavoriteColumns.TABLE_NAME}/#", FAV_USER_ID)
        }
    }

    override fun onCreate(): Boolean {
        Favoritehelper = FavoriteHelper.getInstance(context as Context)
        Favoritehelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAV_USER -> Favoritehelper.queryAll()
            FAV_USER_ID -> Favoritehelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {return null}

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (FAV_USER) {
            sUriMatcher.match(uri) -> Favoritehelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,selectionArgs: Array<String>?): Int {return 0}

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var deleted: Int = 0
        if(sUriMatcher.match(uri)== FAV_USER) deleted = Favoritehelper.deleteById(uri.lastPathSegment.toString())
        else if(sUriMatcher.match(uri)== FAV_USER_ID) deleted = Favoritehelper.deleteById(uri.lastPathSegment.toString())
        else deleted = 0
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}