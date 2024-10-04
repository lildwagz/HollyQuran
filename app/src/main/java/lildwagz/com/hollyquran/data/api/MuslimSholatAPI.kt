package lildwagz.com.hollyquran.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.converter.moshi.MoshiConverterFactory


object MuslimSholatAPI {

    const val URL = "http://muslimsalat.com"

    class MuslimSalatDailyResponse(
        val address: String,
        val city: String,
        val country: String,
        val country_code: String,
        @SerializedName("items")
        val items: List<ItemsItem>,
        val postal_code: String,
        val state: String,
        val status_code: Int,
        @SerializedName("today_weather")
        val todayWeather: TodayWeather
    )
    class ItemsItem(
        val asr: String ,
        val date_for: String,
        val dhuhr: String,
        val fajr: String,
        val isha: String,
        val maghrib: String,
        val shurooq: String
    )

    class TodayWeather(
        val pressure: Int,
        val temperature: String
    )


    interface Service{
        @GET("{city}/daily.json")
        fun getScheduleSalatDaily(
            @Path("city") city: String
        ): Call<MuslimSalatDailyResponse>

        @GET("{city}/daily/{date}.json")
        fun getScheduleSalatDailyByDate(
            @Path("city") city: String,
            @Path("date") date: String
        ): Call<MuslimSalatDailyResponse>
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(MoshiConverterFactory.create()).build()

    val service = retrofit.create(Service::class.java)!!


}