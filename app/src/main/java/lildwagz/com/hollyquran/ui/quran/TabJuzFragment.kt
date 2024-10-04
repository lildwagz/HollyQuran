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
import lildwagz.com.hollyquran.databinding.FragmentTabJuzBinding


class TabJuzFragment: Fragment(R.layout.fragment_tab_juz) {

    private val binding: FragmentTabJuzBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            val database: QuranDatabase = QuranDatabase.getInstance(it)
            val quranDao = database.quranDao()
            quranDao.getJuz().asLiveData().observe(viewLifecycleOwner, { juzList ->
                val adapter: QuranJuzAdapter = QuranJuzAdapter(juzList) { juz ->
                    val bundle = bundleOf(ReadQuranFragment.KEY_JUZ_NUMBER to juz.juzNumber,
                        ReadQuranFragment.KEY_SURAH_NAME to juz.surahName,
                        ReadQuranFragment.KEY_SURAH_TYPE to juz.surahType
                    )


                    findNavController().navigate(R.id.action_nav_quran_to_nav_read_quran, bundle)
                }
                binding.recyclerview.adapter = adapter
            })


        }
    }
}