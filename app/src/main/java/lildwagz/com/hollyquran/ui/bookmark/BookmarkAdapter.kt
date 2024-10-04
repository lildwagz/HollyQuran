package lildwagz.com.hollyquran.ui.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.databinding.ItemBookmarkBinding
import lildwagz.com.hollyquran.model.Bookmark

import java.text.SimpleDateFormat
import java.util.*

class BookmarkAdapter(
    val bookmarklist: List<Bookmark>,
    val clickListener: (Bookmark) -> Unit) :
    RecyclerView.Adapter<BookmarkAdapter.BookmarkAdapterViewHolder>()    {


    var deleteClickListener: ((Bookmark) -> Unit)? = null
    var intentClickListener: ((Bookmark) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
        return BookmarkAdapter.BookmarkAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.BookmarkAdapterViewHolder, position: Int) {
        val bookmark : Bookmark = bookmarklist[position]
//        holder.bindView(bookmark)
//        holder.binding.root.setOnClickListener{
//            clickListener.invoke(bookmark)
//        }
//        val bookmark = listBookmark[position]
        holder.bindView(bookmark)

        holder.itemView.setOnClickListener{
            clickListener.invoke(bookmark)
        }
        holder.binding.imageBookmark.setOnClickListener {
            intentClickListener?.invoke(bookmark)
        }
        holder.binding.imgDeleteBookmark.setOnClickListener {
            deleteClickListener?.invoke(bookmark)
        }
    }

    class BookmarkAdapterViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val binding : ItemBookmarkBinding by viewBinding()
        fun bindView(bookmark: Bookmark){
            binding.textSurahName.text = bookmark.namaSurah
            binding.textNumberOfAyah.text = "ayat ke ${bookmark.numberayah.toString()}"
            binding.textDateTime.text = getDateTime(bookmark.timestamp)
        }
        fun getDateTime(timestamp: Long):String?{
            return try {
                val sdf = SimpleDateFormat("dd/mmm/yyyy hh:mm", Locale.getDefault())
                val netDate = Date().apply {
                    time = timestamp
                }
                sdf.format(netDate)
            }catch (e: Exception){
                e.toString()
            }
        }

    }

    override fun getItemCount(): Int {
        return bookmarklist.size
    }

//    override fun getSectionText(position: Int): CharSequence {
//        val bookmark = bookmarklist[position]
//        val popupText = "${bookmark.namaSurah}"
//        return popupText
//    }
}