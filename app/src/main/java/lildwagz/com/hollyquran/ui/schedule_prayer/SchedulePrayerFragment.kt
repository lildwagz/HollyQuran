package lildwagz.com.hollyquran.ui.schedule_prayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider

import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.vivekkaushik.datepicker.OnDateSelectedListener
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.api.MuslimSholatAPI
import lildwagz.com.hollyquran.data.util.PrefsSet

import lildwagz.com.hollyquran.databinding.FragmentSchedulePrayerBinding
import java.text.SimpleDateFormat
import java.util.*

class SchedulePrayerFragment : Fragment(R.layout.fragment_schedule_prayer)  {


    val TAG = "SchedulePrayerFragment"

    private var city: String = ""
    private lateinit var viewModel : SchedulePrayerViewModel



    private val binding : FragmentSchedulePrayerBinding by viewBinding()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(SchedulePrayerViewModel::class.java)

        if (PrefsSet.city.isNotEmpty()) {
            city = PrefsSet.city
        }

        binding.tvScheduleLocation.text = city

        setUpDateTimePicker()


//        viewModel.loading.observe(viewLifecycleOwner,  { displayLoading(it ) })
//        viewModel.message.observe(viewLifecycleOwner,  { displayLoading(it ) })
        getDailySchedule(city)



    }

    private fun getDailySchedule(city: String) {
        viewModel.LoadScheduleSalat(city)

        viewModel.jadwal.observe(viewLifecycleOwner,  { it?.let { it1 -> displaySchedule(it1) } })



    }

    private fun displaySchedule(schdl: MuslimSholatAPI.MuslimSalatDailyResponse) {
        binding.tvPrayTimeFajr.text = schdl.items[0].fajr
        binding.tvPrayTimeShurooq.text = schdl.items[0].shurooq
        binding.tvPrayTimeDhuhr.text = schdl.items[0].dhuhr
        binding.tvPrayTimeAsr.text = schdl.items[0].asr
        binding.tvPrayTimeMaghrib.text = schdl.items[0].maghrib
        binding.tvPrayTimeIsha.text = schdl.items[0].isha

    }



//    private fun displayLoading(loading: String) {
////        if (!loading) {
//        Toast.makeText(requireContext(), loading,Toast.LENGTH_LONG).show()
//
////        } else if (loading){
////            Toast.makeText(requireContext(),"tidak oke",Toast.LENGTH_LONG).show()
////        }
//    }


    private fun setUpDateTimePicker() {
        val calendar = Calendar.getInstance()
        val ar: Locale = Locale("ar")
        var cal: Calendar = UmmalquraCalendar(ar)
        Log.d(
            TAG,
            "setUpDateTimePicker: ${calendar[Calendar.YEAR]}, ${calendar[Calendar.MONTH]}, ${calendar[Calendar.DAY_OF_MONTH]}"
        )
        binding.dtpSchedulePrayer.apply {
            setInitialDate(
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
//                calendar[cal.get(Calendar.YEAR)],                                      // 1436
//                calendar[cal.getDisplayName(Calendar.MONTH, Calendar.LONG, ar)],       // جمادى الأولى
//                calendar[cal.get(Calendar.DAY_OF_MONTH)]

            )
//            setOnDateSelectedListener(onDtpListener)
        }
    }

    private val onDtpListener = object : OnDateSelectedListener {
        @SuppressLint("SetTextI18n")
        override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
            val calendar = Calendar.getInstance().apply {
                set(year, month, day, 0, 0)
            }

            binding.tvScheduleDay.text = "${getDayFormat(calendar.time)} "
            binding.tvScheduleDate.text = "${getDateNormalFormat(calendar.time)} "

            getDailyScheduleBydate(city, getDateFormat(calendar.time))
//            Toast.makeText(requireContext(),getDateFormat(calendar.time),Toast.LENGTH_LONG).show()

        }

        override fun onDisabledDateSelected(
            year: Int,
            month: Int,
            day: Int,
            dayOfWeek: Int,
            isDisabled: Boolean
        ) {
        }
    }

    private fun getDailyScheduleBydate(city: String, dateFormat: String) {
        viewModel.LoadScheduleSalatByDate(city,dateFormat)

    }


    @SuppressLint("SimpleDateFormat")
    fun getDayFormat(date: Date): String = SimpleDateFormat("EEEE").format(date)

    @SuppressLint("SimpleDateFormat")
    fun getDateNormalFormat(date: Date): String = SimpleDateFormat("dd MMMM yyyy").format(date)

    @SuppressLint("SimpleDateFormat")
    fun getDateFormat(date: Date): String = SimpleDateFormat("yyyy-MM-dd").format(date)


}