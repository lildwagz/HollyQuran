package lildwagz.com.hollyquran.model

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@DatabaseView("SELECT MIN(id) AS id, jozz, aya_no, aya_text, sora_name_en,sora_name_id,type from quran GROUP by jozz ORDER BY id")
data class Juz(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "jozz") val juzNumber : Int,
    @ColumnInfo(name = "aya_no") val ayahNumber : Int,
    @ColumnInfo(name = "aya_text") val textQuran : String,
    @ColumnInfo(name = "sora_name_en") val surahName : String,
    @ColumnInfo(name = "sora_name_id") val surahNameIndo : String,
    @ColumnInfo(name = "type") val surahType: String


)
