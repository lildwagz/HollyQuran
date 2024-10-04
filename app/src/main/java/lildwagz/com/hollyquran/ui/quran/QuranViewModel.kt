package lildwagz.com.appquran.ui.quran

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import lildwagz.com.hollyquran.model.Surah





class QuranViewModel  (): ViewModel() {
    private val tabPosition : MutableLiveData<Int> = MutableLiveData()
    private val totalAyahList : MutableLiveData<List<Int>> = MutableLiveData()

    fun setTablePosition(position: Int){
        tabPosition.value = position
    }

    fun getTablePosition() = tabPosition

    fun setTotalAyahList(surahList: List<Surah>){
        val totallist = mutableListOf<Int>()
        surahList.forEach{surah ->
            val ayahTotal = surah.ayahTotal ?: 1
            totallist.add(ayahTotal)

        }
        totalAyahList.value = totallist
    }

    fun getTotalAyahList() = totalAyahList



}