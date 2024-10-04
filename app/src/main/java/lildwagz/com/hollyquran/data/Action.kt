package lildwagz.com.hollyquran.data

sealed class Action

data class LoadScheduleSalatDaily(val city : String) : Action()
data class LoadScheduleSalatDailyBydate(val city : String, val date : String) : Action()

//data class Loadjadwal(val cityId: String) : Action()