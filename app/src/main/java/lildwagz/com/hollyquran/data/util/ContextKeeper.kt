package lildwagz.com.hollyquran.data.util

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.net.Uri

class ContextKeeper : ContentProvider() {

    companion object {
        @JvmStatic
        lateinit var appContext: Context
    }

    override fun onCreate(): Boolean {
        context?.let {
            appContext = it
        }
        return false
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?) = null
    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ) = null

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ) = 0

}