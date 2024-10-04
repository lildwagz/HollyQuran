package lildwagz.com.hollyquran.ui.quran


import android.content.*
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.control.RepeatMode
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import lildwagz.com.appquran.ui.quran.QuranViewModel
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.QuranDatabase
import lildwagz.com.hollyquran.databinding.FragmentReadQuranBinding
import lildwagz.com.hollyquran.model.Quran
import android.app.Activity
import android.widget.*
import lildwagz.com.hollyquran.data.util.PrefsReciter


class ReadQuranFragment : Fragment(R.layout.fragment_read_quran) {

    val binding : FragmentReadQuranBinding by viewBinding()
    private val viewModel : QuranViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAudioConfig()
        setHasOptionsMenu(true)
        val scrollPosition = arguments?.getInt(KEY_SCROLL_TO_POSITION)?:0

        val numberSurah = arguments?.getInt(KEY_SURAH_NUMBER) ?: 1
        val nameSurah = arguments?.getString(KEY_SURAH_NAME) ?: ""
        val numberJuz = arguments?.getInt(KEY_JUZ_NUMBER) ?:1
        val numberPage = arguments?.getInt(KEY_PAGE_NUMBER) ?:1
        val SurahType = arguments?.getString(KEY_SURAH_TYPE) ?: ""

//        binding.include.buttonStop.setOnClickListener {
//            StarrySky.with().stopMusic()
//        }
//        binding.include.buttonPlayPause.setOnClickListener {
//            if(StarrySky.with().isPaused()){
//                StarrySky.with().skipToNext()
//            }else{
//                StarrySky.with().pauseMusic()
//
//            }
//        }
//        binding.include.buttonPrevious.setOnClickListener {
//            StarrySky.with().skipToPrevious()
//        }
//        binding.include.buttonNext.setOnClickListener {
//                    StarrySky.with().skipToNext()
//        }

        viewModel.getTablePosition().observe(viewLifecycleOwner,{tabPosition ->

            viewModel.getTotalAyahList().observe(viewLifecycleOwner,{totalAyahList ->
                val database = QuranDatabase.getInstance(requireContext())
                val quranDao = database.quranDao()
                var tittleToolbar : String = ""
                when(tabPosition){
                    TAB_SURAH ->{ // TabSurah
                        quranDao.getQuranBySurahNumber(numberSurah).asLiveData().observe(viewLifecycleOwner, {quranList ->
                            setQuranAdapter(quranList,totalAyahList,SurahType,scrollPosition)



                        })
                        tittleToolbar = "$nameSurah"

                    }
                    TAB_JUZ ->{
                        quranDao.getQuranByJuzNumber(numberJuz).asLiveData().observe(viewLifecycleOwner, {quranList ->
                            setQuranAdapter(quranList,totalAyahList,SurahType,scrollPosition)

                        })
                        tittleToolbar = "Juz $numberJuz"

                    }
                    TAB_PAGE ->{
                        quranDao.getQuranByPageNumber(numberPage).asLiveData().observe(viewLifecycleOwner, {quranList ->
                            setQuranAdapter(quranList,totalAyahList,SurahType,scrollPosition)

                        })
                        tittleToolbar = "Page $numberPage"


                    }

                }
                val toolbarActivity = requireActivity().findViewById<Toolbar>(R.id.toolbar)
                toolbarActivity.title = tittleToolbar
//                Toast.makeText(requireContext(), "$tittleToolbar", Toast.LENGTH_SHORT).show()

            })


        })


    }

    private fun setupAudioConfig()  = StarrySky.with().apply{
        StarrySky.openNotification()

        setRepeatMode(RepeatMode.REPEAT_MODE_NONE, false)
    }

    private fun setQuranAdapter(quranList: List<Quran>, ayahTotal: List<Int>, SurahType: String,scrollPosition :Int) {
//        Toast.makeText(requireContext(), getAyahFontSize(), Toast.LENGTH_SHORT).show()

        val adapter: ReadQuranAdapter = ReadQuranAdapter(quranList, ayahTotal,SurahType)
        binding.recyclerview.adapter = adapter

        adapter.playClickListener = {quran ->

            val surahNumberFormat = String.format("%03d",quran.surahNumber)
            val ayahNumberFormat = String.format("%03d",quran.ayahNumber)
            val reciter = PrefsReciter.reciter
            val urlAudio = "https://archive.org/download/quran-every-ayah/$reciter.zip/" +
                    "$surahNumberFormat$ayahNumberFormat.mp3"

            val audio = SongInfo()
            audio.songName = "${quran.surahName}: ${quran.surahNumber}"
            audio.songUrl = urlAudio
            audio.songId = quran.id.toString()
            audio.artist = reciter
            StarrySky.with().playMusicByInfo(audio)
            binding.ivplay.visibility = View.VISIBLE
            binding.flPlay.visibility = View.VISIBLE
            binding.flPlay.setImageResource(R.drawable.ic_pause_white_24dp)


            binding.flPlay.setOnClickListener {
                if(StarrySky.with().isPlaying()){
                    binding.flPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp)
                    StarrySky.with().pauseMusic()
                }else if(StarrySky.with().isPaused()){
                    binding.flPlay.setImageResource(R.drawable.ic_pause_white_24dp)

                    StarrySky.with().playMusicByInfo(audio)


                }
            }

            binding.skipPrevious.setOnClickListener {
                if(StarrySky.with().isSkipToPreviousEnabled()){
                    StarrySky.with().skipToPrevious()
                }
            }
            binding.skipNext.setOnClickListener {
                if(StarrySky.with().isSkipToNextEnabled()){
                    StarrySky.with().skipToNext()
                }
            }

            binding.ivStop.setOnClickListener {
                StarrySky.with().stopMusic()
                binding.ivplay.visibility = View.INVISIBLE
                binding.flPlay.visibility = View.INVISIBLE
            }

            binding.repeatmode.setOnClickListener {
                StarrySky.with().setRepeatMode(1,true)
                StarrySky.with().getRepeatMode()


            }




        }
        adapter.shareClickListener = {quran ->
            val shareContent = "${quran.textQuran}\n\n${quran.translation}\n\n(QS. ${quran.surahName}: (${quran.ayahNumber}))"
            ShareCompat.IntentBuilder(requireContext())
                .setText(shareContent)
                .setType("text/plain")
                .startChooser()
        }
        adapter.copyClickListener = {quran ->
            val copyContent = "${quran.textQuran}\n${quran.translation}\n${quran.surahName}\n${quran.ayahNumber}"
            val clipboard: ClipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("copy ayah", copyContent)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
        }

        adapter.moreclickListener = {quran,totalAyah ->

            val dialog = BottomSheetDialog(requireContext())

            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)


            val btnPlaySurah = view.findViewById<LinearLayout>(R.id.layoutPlay)

            btnPlaySurah.setOnClickListener {
                startAudioSurah(quran,totalAyah)
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.setContentView(view)


            dialog.show()

        }

        adapter.footnotesOnClickListener = {quran ->
            val bundle = bundleOf(QuranFootnotesFragment.KEY_FOOTNOTES to quran.footnotes)
            findNavController().navigate(R.id.action_nav_read_quran_to_nav_quran_footnotes,bundle)

        }

        adapter.lastReadClickListener = {lastRead ->
            val database = QuranDatabase.getInstance(requireContext())
            val quranDao = database.quranDao()
            lifecycleScope.launch {
                quranDao.setLastRead(lastRead)
                Toast.makeText(
                    requireContext(),
                    "Posisi terakhir baca: ${lastRead.namaSurah}: ${lastRead.numberayah}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        adapter.bookmarkClickListener ={ bookmark ->
            val database = QuranDatabase.getInstance(requireContext())
            val quranDao = database.quranDao()
            lifecycleScope.launch {
                quranDao.insertBookmark(bookmark)
                Toast.makeText(requireContext(),"Berhasil Menambahkan bookmark QS ${bookmark.namaSurah} ayah ${bookmark.numberayah}",Toast.LENGTH_SHORT).show()
            }
        }


        binding.recyclerview.adapter = adapter
        binding.recyclerview.smoothScrollToPosition(scrollPosition)
    }

    private fun startAudioSurah(quran: Quran, totalAyah: Int) {

            val reciter = PrefsReciter.reciter

            val audioList = mutableListOf<SongInfo>()
            val surahNumberFormat = String.format("%03d",quran.surahNumber)

            for (i in 1..totalAyah){
                val ayahNumberFormat = String.format("%03d",i)
                val urlAudio = "https://archive.org/download/quran-every-ayah/$reciter.zip/" +
                        "$surahNumberFormat$ayahNumberFormat.mp3"
                val audio = SongInfo()
                audio.songName = "${quran.surahName}: ${i}"
                audio.songUrl = urlAudio
                audio.songCover = "https://myislamicworld.com/wp-content/uploads/2020/06/20200611_053702.jpg"
                audio.songId = surahNumberFormat + ayahNumberFormat
                audio.artist = "Abdurrahman as-Sudais"
                audioList.add(audio)
            }
            val startAudioPlay = quran.ayahNumber!! -1
//            StarrySky.with().setRepeatMode(RepeatMode.REPEAT_MODE_ONE,false)
//            StarrySky.with().playMusicById(quran.id.toString())
            StarrySky.with().playMusic(audioList, startAudioPlay)



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_search ->{
                findNavController().navigate(R.id.action_nav_read_quran_to_nav_search_quran_Fragment)
                true
            }else ->{
                false
            }

        }
    }

    companion object{
        const val KEY_SURAH_NUMBER = "NUMBER_SURAH"
        const val KEY_JUZ_NUMBER =  "JUZ_NUMBER"
        const val KEY_PAGE_NUMBER = "PAGE_NUMBER"
        const val KEY_AYAH_TOTAL = "AYAH_TOTAL"
        const val KEY_SURAH_TYPE = "SURAH_TYPE"
        const val KEY_SURAH_NAME = "SURAH_NAME"
        const val KEY_SCROLL_TO_POSITION = "SCROLL_POSITION"
        const val TAB_SURAH = 0
        const val TAB_JUZ = 1
        const val TAB_PAGE = 2
    }

}