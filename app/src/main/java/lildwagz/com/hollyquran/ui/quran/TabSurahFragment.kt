package lildwagz.com.hollyquran.ui.quran

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.appquran.ui.quran.QuranViewModel
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.QuranDatabase
import lildwagz.com.hollyquran.databinding.FragmentTabSurahBinding

class TabSurahFragment: Fragment(R.layout.fragment_tab_surah) {

    private val binding: FragmentTabSurahBinding by viewBinding()
    private val viewModel: QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            val database: QuranDatabase = QuranDatabase.getInstance(it)
            val quranDao = database.quranDao()
            quranDao.getSurah().asLiveData().observe(viewLifecycleOwner, {surahList ->
                viewModel.setTotalAyahList(surahList)

                val adapter: QuranSurahAdapter = QuranSurahAdapter(surahList) { surah ->
                    val bundle = bundleOf(
                        ReadQuranFragment.KEY_SURAH_NUMBER to surah.surahNumber,
                        ReadQuranFragment.KEY_SURAH_NAME to surah.surahName,
                        ReadQuranFragment.KEY_SURAH_TYPE to surah.surahType
                    )

                    // Callback clickListener from adapter
                    findNavController().navigate(R.id.action_nav_quran_to_nav_read_quran, bundle)
                }
                binding.recyclerview.adapter = adapter
            })
        }


    }

}