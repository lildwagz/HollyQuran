package lildwagz.com.hollyquran.data.repository

import lildwagz.com.hollyquran.data.api.MuslimSholatAPI
import retrofit2.await


object MuslimSholatRepository{


    suspend fun getScheduleSalatDaily(city: String) :MuslimSholatAPI.MuslimSalatDailyResponse
            = MuslimSholatAPI.service.getScheduleSalatDaily(city).await()

    suspend fun getScheduleSalatDailyByDate(city: String,date : String)
            = MuslimSholatAPI.service.getScheduleSalatDailyByDate(city,date).await()

}