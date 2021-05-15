package com.lukitor.projectandroidfundamentalkotlin2.database

import android.net.Uri
import android.provider.BaseColumns

object  DatabaseContract {
    const val AUTHORITY = "com.lukitor.projectandroidfundamentalkotlin2"
    const val SCHEME = "content"
    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "Favorite"
            const val ID = "id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val IMAGE = "imageURL"
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_NAME).build()
        }
    }
}
