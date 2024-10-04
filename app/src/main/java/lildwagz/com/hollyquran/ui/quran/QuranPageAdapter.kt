package lildwagz.com.hollyquran.ui.quran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.databinding.ItemPageBinding

import lildwagz.com.hollyquran.model.Page

class QuranPageAdapter (val listPage: List<Page>, val itemClickListener: ((page: Page)->Unit)) :
    RecyclerView.Adapter<QuranPageAdapter.QuranPageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        return QuranPageViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuranPageViewHolder, position: Int) {
        val page : Page = listPage[position]
        holder.bindView(page)
        holder.itemView.setOnClickListener{
            itemClickListener.invoke(page)
        }
    }

    override fun getItemCount(): Int {
        return listPage.size
    }

    class QuranPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding : ItemPageBinding by viewBinding()

        fun bindView(page: Page) {
            binding.textPageName.text = "Halaman ${page.pageNumber}"
            binding.textPageNumber.text = page.pageNumber.toString()
            binding.textQuranAyah.text = page.textQuran
            binding.surahNameAndNumber.text = "${page.surahName}: ${page.ayahNumber}"
        }

    }
}