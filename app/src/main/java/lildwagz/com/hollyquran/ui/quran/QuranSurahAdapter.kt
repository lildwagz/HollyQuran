package lildwagz.com.hollyquran.ui.quran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.databinding.ItemSurahBinding
import lildwagz.com.hollyquran.model.Surah

class QuranSurahAdapter(val listSurah: List<Surah>, val itemClickListener: ((surah: Surah)->Unit)) :
    RecyclerView.Adapter<QuranSurahAdapter.QuranSurahViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranSurahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surah, parent, false)
        return QuranSurahViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuranSurahViewHolder, position: Int) {
        val surah : Surah = listSurah[position]
        holder.bindView(surah)
        holder.itemView.setOnClickListener{
            itemClickListener.invoke(surah)
        }
    }

    override fun getItemCount(): Int {
        return listSurah.size
    }

    class QuranSurahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding : ItemSurahBinding by viewBinding()

        fun bindView(surah: Surah) {
            binding.textSurahNumber.text = surah.surahNumber.toString()
            binding.textSurahName.text = surah.surahName
            binding.textNumberOfAyah.text = "${surah.surahType} - ${surah.ayahTotal} Ayat"
            binding.textSurahNameArabic.text = surah.surahNameArabic
        }

    }

}