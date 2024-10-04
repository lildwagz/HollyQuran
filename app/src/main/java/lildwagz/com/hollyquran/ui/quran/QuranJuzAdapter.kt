package lildwagz.com.hollyquran.ui.quran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.databinding.ItemJuzBinding

import lildwagz.com.hollyquran.model.Juz

class QuranJuzAdapter(val listJuz: List<Juz>, val itemClickListener:((juz: Juz)->Unit)) :
    RecyclerView.Adapter<QuranJuzAdapter.QuranJuzViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranJuzViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_juz, parent, false)
        return QuranJuzViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuranJuzViewHolder, position: Int) {
        val juz : Juz = listJuz[position]
//        Log.d("TAG", "message" + listJuz[1])
        holder.bindView(juz)

        holder.itemView.setOnClickListener{
            itemClickListener.invoke(juz)
        }
    }

    override fun getItemCount(): Int {
        return listJuz.size
    }

    class QuranJuzViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding : ItemJuzBinding by viewBinding()

        fun bindView(juz: Juz) {
            binding.textJuzName.text = "Juz ${juz.juzNumber}"
            binding.textJuzNumber.text = juz.juzNumber.toString()
            binding.textQuranAyat.text = juz.textQuran
            binding.surahNameAndNumber.text = "${juz.surahName}: ${juz.ayahNumber}"
        }

    }
}

