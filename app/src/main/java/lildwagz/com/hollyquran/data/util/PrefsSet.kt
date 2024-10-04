package lildwagz.com.hollyquran.data.util


object PrefsSet : PrefManager("global_location") {

    @JvmStatic
    var city by StringPref()




}

object  PrefsReciter : PrefManager("reciter_name"){
    @JvmStatic
    var reciter by StringPref()
}

object  PrefsTranslation : PrefManager("translation"){
    @JvmStatic
    var notranslation by BooleanPref()
}



