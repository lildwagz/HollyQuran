package lildwagz.com.hollyquran.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lildwagz.com.hollyquran.model.*

@Dao
interface QuranDao {

    @Query("SELECT * FROM quran")
    fun getQuran() : Flow<List<Quran>>

    @Query("SELECT id,sora, sora_name_en, sora_name_ar, sora_name_id,type, jozz, page, aya_no, aya_text, aya_text_emlaey, translation, footnotes from quran WHERE sora = :surahNumber")
    fun getQuranBySurahNumber(surahNumber: Int) : Flow<List<Quran>>

    @Query("SELECT id,sora, sora_name_en, sora_name_ar,sora_name_id,type, jozz, page, aya_no, aya_text, aya_text_emlaey, translation, footnotes from quran WHERE jozz = :juzNumber")
    fun getQuranByJuzNumber(juzNumber: Int) : Flow<List<Quran>>

    @Query("SELECT id,sora, sora_name_en, sora_name_ar,sora_name_id, type,jozz, page, aya_no, aya_text, aya_text_emlaey, translation, footnotes from quran WHERE page = :pageNumber")
    fun getQuranByPageNumber(pageNumber: Int) : Flow<List<Quran>>

    @Query("SELECT * FROM surah")
    fun getSurah() : Flow<List<Surah>>

    @Query("SELECT * FROM juz")
    fun getJuz() : Flow<List<Juz>>

    @Query("SELECT * FROM page")
    fun getPage() : Flow<List<Page>>


    @Insert
    suspend fun setLastRead(LastRead: LastRead)

    @Query ("SELECT * FROM last_read ORDER BY id DESC LIMIT 1")
    fun getLastRead() : Flow<LastRead>


    @Query("SELECT * FROM quran WHERE translation LIKE :search OR aya_text_emlaey LIKE :search")
    fun searchAyah(search: String): Flow<List<Quran>>


    @Insert
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query ("SELECT * FROM bookmark ORDER BY id DESC")
    fun getBookmarks() : Flow<List<Bookmark>>
}