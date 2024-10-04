package lildwagz.com.hollyquran.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_read")
data class LastRead(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?,
    @ColumnInfo(name = "sora_name_en") val namaSurah: String,
    @ColumnInfo(name = "aya_no")val numberayah :Int,
    @ColumnInfo(name = "position") val positionScroll : Int,
    @ColumnInfo(name = "sora_no") val surahNumber : Int


)