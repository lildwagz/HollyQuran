package lildwagz.com.hollyquran.data

import kotlinx.coroutines.flow.Flow
import lildwagz.com.hollyquran.model.Quran

class QuranRepository(val quranDatabase: QuranDatabase) {

    fun getQuran(): Flow<List<Quran>> {
        return quranDatabase.quranDao().getQuran()
    }

}