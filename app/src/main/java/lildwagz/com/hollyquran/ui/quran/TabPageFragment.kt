package lildwagz.com.hollyquran.ui.quran

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.QuranDatabase
import lildwagz.com.hollyquran.databinding.FragmentTabPageBinding

class TabPageFragment: Fragment(R.layout.fragment_tab_page) {

    private val binding: FragmentTabPageBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            val database: QuranDatabase = QuranDatabase.getInstance(it)
            val quranDao = database.quranDao()
            quranDao.getPage().asLiveData().observe(viewLifecycleOwner, {pageList ->
                val adapter: QuranPageAdapter = QuranPageAdapter(pageList) {page ->
                    val bundle = bundleOf(ReadQuranFragment.KEY_PAGE_NUMBER to page.pageNumber,
                            ReadQuranFragment.KEY_SURAH_NAME to page.surahName,
                        ReadQuranFragment.KEY_SURAH_TYPE to page.surahType
                    )

                    // Callback clickListener from adapter
                    findNavController().navigate(R.id.action_nav_quran_to_nav_read_quran, bundle)
                }
                binding.recyclerview.adapter = adapter
            })

        }

    }

}