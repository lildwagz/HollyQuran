package lildwagz.com.hollyquran.model

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@DatabaseView("SELECT MIN(id) as id ,page, aya_no, aya_text, sora_name_en,sora_name_id,type from quran GROUP by page ORDER by id")
data class Page(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "page")
    val pageNumber : Int,
    @ColumnInfo(name = "aya_no")
    val ayahNumber : Int,
    @ColumnInfo(name = "aya_text")
    val textQuran : String,
    @ColumnInfo(name = "sora_name_en")
    val surahName : String,
    @ColumnInfo(name = "sora_name_id")
    val surahNameIndo : String,
    @ColumnInfo(name = "type")
    val surahType : String

)
