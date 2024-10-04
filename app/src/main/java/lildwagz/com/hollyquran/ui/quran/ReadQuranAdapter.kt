package lildwagz.com.hollyquran.ui.quran

import android.content.Context
import android.content.SharedPreferences
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.color.MaterialColors
import kotlinx.coroutines.NonDisposableHandle.parent
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.util.FontCache
import lildwagz.com.hollyquran.data.util.PrefsTranslation
import lildwagz.com.hollyquran.data.util.QuranUtils
import lildwagz.com.hollyquran.databinding.ItemAyahBinding
import lildwagz.com.hollyquran.model.Bookmark
import lildwagz.com.hollyquran.model.LastRead
import lildwagz.com.hollyquran.model.Quran
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class ReadQuranAdapter(val listQuran: List<Quran>, val ayahTotal: List<Int>, val type: String ) :
    RecyclerView.Adapter<ReadQuranAdapter.ReadQuranViewHolder>() {

    var copyClickListener : ((Quran) -> Unit)?= null
    var shareClickListener : ((Quran) -> Unit)?= null
    var playClickListener : ((Quran) -> Unit)?=null
    var moreclickListener : ((Quran, Int) -> Unit)?=null
    var footnotesOnClickListener : ((Quran) -> Unit)?=null
    var lastReadClickListener : ((LastRead) -> Unit)? = null
    var bookmarkClickListener: ((Bookmark) -> Unit)? = null

    var nextJuzNumber:Int = 0
    var nextPageNumber:Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadQuranViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ayah, parent, false)

        return ReadQuranViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReadQuranViewHolder, position: Int) {
        val quran: Quran = listQuran[position]
        val isLastIndex = listQuran.lastIndex == position
        val index = if (isLastIndex) {
            position
        } else {
            position + 1
        }
        val nextData = listQuran[index]
        val surahNumber = quran.surahNumber ?: 1
        val totalAyah = ayahTotal[surahNumber - 1]


        holder.binding.textJuzOrPageNumber.isVisible = quran.pageNumber != nextData.pageNumber
        holder.bindView(quran)
        holder.binding.textNumberOfAyah.text = "$type - $totalAyah ayat"
        holder.getFontSize()

        holder.binding.imageCopy.setOnClickListener {
            copyClickListener?.invoke(quran)
        }
        holder.binding.imageShare.setOnClickListener {
            shareClickListener?.invoke(quran)
        }
        holder.binding.imagePlayaudio.setOnClickListener {
            playClickListener?.invoke(quran)
        }

        holder.binding.menuMore.setOnClickListener {
            moreclickListener?.invoke(quran, totalAyah)
        }


        holder.binding.bookmark.setOnClickListener {
            val bookmark = Bookmark(id = null,
                positionScroll = position,
                surahNumber = quran.surahNumber!!,
                namaSurah = quran.surahName!!,
                numberayah = quran.ayahNumber!!,
                timestamp = Date().time
            )
            bookmarkClickListener?.invoke(bookmark)
        }
        holder.createSpannableFootnotes(holder.itemView,quran,footnotesOnClickListener)
        holder.createQuranTajweed(holder.itemView,quran)
        nextJuzNumber = nextData.juzNumber ?:0
        nextPageNumber = nextData.pageNumber ?:0

        holder.binding.lastRead.setOnClickListener{
            val lastRead = LastRead (
                id = null ,
                positionScroll = position,
                namaSurah = quran.surahName!!,
                surahNumber = quran.surahNumber!!,
                numberayah = quran.ayahNumber!!
            )
            lastReadClickListener?.invoke(lastRead)

        }



    }


    override fun getItemCount(): Int {
        return listQuran.size
    }

    class ReadQuranViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding : ItemAyahBinding by viewBinding()
        private lateinit var prefs: SharedPreferences

        val firstindexSurahNumber : Int = 0

        fun bindView(quran: Quran) {



            binding.textSurahName.text = "${quran.surahName} (${quran.surahNameIndo})"
            binding.textSurahNameArabic.text = quran.surahNameArabic
//            binding.textAyah.text = quran.textQuran
            binding.textJuzOrPageNumber.text = "• Juz ${quran.juzNumber} • Hal ${quran.pageNumber} •"
            binding.textNumberOfAyah.text = ""
            binding.textAyahNumber.text = quran.ayahNumber.toString()

            if(PrefsTranslation.notranslation){
                binding.textTranslation.visibility = View.GONE

            }else{
                binding.textTranslation.visibility = View.VISIBLE

                binding.textTranslation.text = quran.translation

            }

            binding.textSurahNumber.text = quran.surahNumber.toString()
//            fontSize?.let {
//                binding.textAyah.textSize = it.toFloat()
//            }
            if(position == firstindexSurahNumber ) {
                binding.containerSurahLayout.visibility = View.VISIBLE
                binding.textBismillah.visibility =  View.VISIBLE

            } else {
                binding.containerSurahLayout.visibility = View.GONE
                binding.textBismillah.visibility = View.GONE

            }

            if ( quran.surahNumber.toString() =="1" || quran.surahNumber.toString() =="9" ){
                binding.textBismillah.visibility =  View.GONE
            }



        }

        fun createSpannableFootnotes(view : View,quran: Quran,
                                     footnotesOnClickListener: ((Quran) -> Unit )?){
            val colorPrimary = MaterialColors.getColor(view, R.attr.colorPrimary)
            val translation = quran.translation
            val sb = SpannableStringBuilder(translation)
            val p : Pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE)
            val m : Matcher = p.matcher(translation)
            while (m.find()){
                val clickableSpan = object : ClickableSpan(){
                    override fun updateDrawState(textPaint: TextPaint) {
                        textPaint.color = colorPrimary
                        textPaint.isUnderlineText = true
                    }

                    override fun onClick(view: View) {
                        footnotesOnClickListener?.invoke(quran)
                    }
                }
                sb.setSpan(clickableSpan, m.start(), m.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
            binding.textTranslation.movementMethod = LinkMovementMethod.getInstance()
            binding.textTranslation.setText(sb, TextView.BufferType.SPANNABLE)


        }

        fun getFontSize() {
            val context = itemView.context
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val font = prefs.getString("ayah_text_preference", null)
            font?.let {
                binding.textAyah.textSize = it.toFloat()
            }
        }

        fun createQuranTajweed(itemView: View, quran: Quran) {

            val quranFinal : String? =quran.textQuran?.let { reverseAyahfromNumber(it) }
            binding.textAyah.text = quranFinal?.let {
                QuranUtils().getTajweed(itemView.context,
                    it
                )
            }

        }

        fun reverseAyahfromNumber(textQuran: String): String{

            val digits = mutableListOf<Char>()
            textQuran.forEach { char->
                if (char.isDigit()){
                    digits.add(char)
                }
            }
            val ayahNumberArabic = digits.joinToString("")
            val textWithoutNumber = textQuran.replace(ayahNumberArabic, "")
            return  textWithoutNumber + digits.reversed().joinToString("")
        }



    }
}