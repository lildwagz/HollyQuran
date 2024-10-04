package lildwagz.com.hollyquran.ui.schedule_prayer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.api.MuslimSholatAPI
import lildwagz.com.hollyquran.databinding.ItemPrayerTimeBinding


class PrayerTimeRecyclerAdapter(val list: List<MuslimSholatAPI.MuslimSalatDailyResponse>) :
    RecyclerView.Adapter<PrayerTimeRecyclerAdapter.PrayerTimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerTimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prayer_time, parent, false)
        return PrayerTimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrayerTimeViewHolder, position: Int) {
        val schedule : MuslimSholatAPI.MuslimSalatDailyResponse = list[position]
        Log.d("TAG", "message" + schedule.todayWeather.pressure)
        holder.bindView(schedule)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PrayerTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding : ItemPrayerTimeBinding by viewBinding()

        fun bindView(schedule: MuslimSholatAPI.MuslimSalatDailyResponse) {
            binding.tvPrayTimeFajr.text = schedule.items[0].fajr
            binding.tvPrayTimeShurooq.text = schedule.items[0].shurooq
            binding.tvPrayTimeDhuhr.text = schedule.items[0].dhuhr
            binding.tvPrayTimeAsr.text = schedule.items[0].asr
            binding.tvPrayTimeMaghrib.text = schedule.items[0].maghrib
            binding.tvPrayTimeIsha.text = schedule.items[0].isha
        }

    }
}

