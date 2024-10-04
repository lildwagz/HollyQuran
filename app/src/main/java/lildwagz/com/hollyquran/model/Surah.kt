package lildwagz.com.hollyquran.model

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@DatabaseView("SELECT id,sora, sora_name_en, sora_name_ar, sora_name_id,type, count(id) as aya_total from quran GROUP by sora")
data class Surah(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "sora") val surahNumber : Int,
    @ColumnInfo(name = "sora_name_en") val surahName : String,
    @ColumnInfo(name = "sora_name_ar") val surahNameArabic : String,
    @ColumnInfo(name = "sora_name_id") val surahNameIndo : String,
    @ColumnInfo(name = "type") val surahType : String,
    @ColumnInfo(name = "aya_total") val ayahTotal : Int
)
