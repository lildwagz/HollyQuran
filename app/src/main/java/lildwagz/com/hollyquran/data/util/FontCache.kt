package lildwagz.com.hollyquran.data.util

import android.content.Context
import android.graphics.Typeface

import android.text.TextUtils
import android.util.ArrayMap


class FontCache {
    private var sInstance: FontCache? = null
    private val fontMap: ArrayMap<String, Typeface> = ArrayMap()

    fun get(): FontCache? {
        if (sInstance == null) {
            sInstance = FontCache()
        }
        return sInstance
    }

    fun getFont(context: Context, fontName: String?): Typeface? {
        if (TextUtils.isEmpty(fontName)) {
            return null
        }
        if (!fontMap.containsKey(fontName)) {
            fontMap.put(fontName, Typeface.createFromAsset(context.getAssets(), fontName))
        }
        return fontMap.get(fontName)
    }
}