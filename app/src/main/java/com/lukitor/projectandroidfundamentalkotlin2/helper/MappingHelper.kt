package com.lukitor.projectandroidfundamentalkotlin2.helper

import android.database.Cursor
import com.lukitor.projectandroidfundamentalkotlin2.database.DatabaseContract
import com.lukitor.projectandroidfundamentalkotlin2.entity.Favorite

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favorite> {
        val FavoriteList = ArrayList<Favorite>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val nama = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
                val imgUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.IMAGE))
                FavoriteList.add(Favorite(id, username, nama, imgUrl))
            }
        }
        return FavoriteList
    }
}
