package lildwagz.com.hollyquran.ui.schedule_prayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import lildwagz.com.hollyquran.data.Action
import lildwagz.com.hollyquran.data.api.MuslimSholatAPI
import lildwagz.com.hollyquran.data.repository.MuslimSholatRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import lildwagz.com.hollyquran.data.LoadScheduleSalatDaily
import lildwagz.com.hollyquran.data.LoadScheduleSalatDailyBydate


class SchedulePrayerViewModel : ViewModel() {

    private val mutableJadwal = MutableLiveData<MuslimSholatAPI.MuslimSalatDailyResponse>().apply { value = null }
    private val mutableMessage = MutableLiveData<String>()
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val byDate : Boolean = false




    val jadwal: LiveData<MuslimSholatAPI.MuslimSalatDailyResponse> = mutableJadwal
    val message: LiveData<String> = mutableMessage
    val loading: LiveData<Boolean> = mutableLoading


    private val scope = CoroutineScope(Dispatchers.Default)



    private val actor = scope.actor<Action>(Dispatchers.Main, Channel.CONFLATED) {
        for (action in this) when (action) {
            is LoadScheduleSalatDaily -> {
                mutableLoading.value = true
                try {
                    mutableJadwal.value = getJadwal(action.city)
                    mutableMessage.value = "mantap"


                } catch (e: Exception) {
                    mutableMessage.value = e.message.toString()
                }
                mutableLoading.value = false
            }
            is LoadScheduleSalatDailyBydate ->{
                mutableLoading.value = true
                try {
                    mutableJadwal.value = getJadwalByDate(action.city,action.date)
                    mutableMessage.value = "mantap"


                } catch (e: Exception) {
                    mutableMessage.value = e.message.toString()
                }
                mutableLoading.value = false
            }
        }
    }

    private suspend fun getJadwal(city: String): MuslimSholatAPI.MuslimSalatDailyResponse {
        val jadwalsolat : MuslimSholatAPI.MuslimSalatDailyResponse = MuslimSholatRepository.getScheduleSalatDaily(city)
        return jadwalsolat
    }
    private suspend fun getJadwalByDate(city: String, date : String): MuslimSholatAPI.MuslimSalatDailyResponse {
        val jadwalsolat : MuslimSholatAPI.MuslimSalatDailyResponse = MuslimSholatRepository.getScheduleSalatDailyByDate(city,date)
        return jadwalsolat
    }


    fun LoadScheduleSalat(city: String){
        action(LoadScheduleSalatDaily(city))

    }

    fun LoadScheduleSalatByDate(city: String,date: String){
        action(LoadScheduleSalatDailyBydate(city, date))
    }

    private fun action(action: Action) = actor.trySend(action).isSuccess

//    @ObsoleteCoroutinesApi
//    override fun onCleared() = actor.

}
