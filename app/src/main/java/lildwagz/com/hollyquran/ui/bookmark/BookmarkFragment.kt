package lildwagz.com.hollyquran.ui.bookmark

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import lildwagz.com.appquran.ui.quran.QuranViewModel
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.QuranDatabase
import lildwagz.com.hollyquran.databinding.FragmentBookmarkBinding
import lildwagz.com.hollyquran.ui.quran.ReadQuranFragment

class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {
    private  val binding : FragmentBookmarkBinding by viewBinding()
    private val viewModel : QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quranDatabase = QuranDatabase.getInstance(requireContext())
        val quranDao = quranDatabase.quranDao()

        quranDao.getBookmarks().asLiveData().observe(viewLifecycleOwner,{
            val adapter = BookmarkAdapter(it){
//
            }
            binding.recyclerview.adapter = adapter
            adapter.deleteClickListener = {bookmark ->
                lifecycleScope.launch {
                    quranDao.deleteBookmark(bookmark)
                    Toast.makeText(requireContext(),"Bookmark ${bookmark.namaSurah} : ${bookmark.numberayah} berhasil di apus",
                    Toast.LENGTH_SHORT).show()

                }

            }
            adapter.intentClickListener = {bookmark->
                val bundle = bundleOf(ReadQuranFragment.KEY_SURAH_NUMBER to bookmark.surahNumber
                    ,ReadQuranFragment.KEY_SURAH_NAME to bookmark.namaSurah
                    ,ReadQuranFragment.KEY_SCROLL_TO_POSITION to bookmark.numberayah-1)
                findNavController().navigate(R.id.action_nav_bookmark_to_nav_read_quran,bundle)
                viewModel.setTablePosition(ReadQuranFragment.TAB_SURAH)
            }

//            adapter.deleteClickListener = {bookmark ->
//                lifecycleScope.launch {
//                    quranDao.
//                }
//
//            }
        })


    }


}