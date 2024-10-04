package lildwagz.com.hollyquran.ui.quran

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.mobiledevidn.quranku.ui.quran.QuranViewPagerAdapter
import lildwagz.com.appquran.ui.quran.QuranViewModel
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.QuranDatabase
import lildwagz.com.hollyquran.data.util.PrefsSet
import lildwagz.com.hollyquran.databinding.FragmentQuranBinding
import java.util.*
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar


class QuranViewPagerFragment : Fragment(R.layout.fragment_quran) {

    private val binding: FragmentQuranBinding by viewBinding()
    private val titles = arrayOf("Surah", "Juz", "Page")
    private val fragmentList = listOf(TabSurahFragment(), TabJuzFragment(), TabPageFragment())
    private val viewModel: QuranViewModel by activityViewModels()


    var hariIni: String? = null
    var tanggal: String? = null

//    private var locationCallback: LocationCallback? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = QuranViewPagerAdapter(this, fragmentList)
        val quranDao = QuranDatabase.getInstance(requireContext()).quranDao()

        quranDao.getLastRead().asLiveData().observe(viewLifecycleOwner, {
            it?.let { lastRead ->
                binding.textSurahName.text = "${lastRead.namaSurah}"
                binding.textAyahNumber.text = ": ${lastRead.numberayah}"

                binding.lastread.setOnClickListener { view ->
                    val bundle = bundleOf(
                        ReadQuranFragment.KEY_SURAH_NUMBER to lastRead.surahNumber,
                        ReadQuranFragment.KEY_SURAH_NAME to lastRead.namaSurah,
                        ReadQuranFragment.KEY_SCROLL_TO_POSITION to lastRead.numberayah - 1
                    )
                    findNavController().navigate(R.id.action_nav_quran_to_nav_read_quran, bundle)
                    viewModel.setTablePosition(ReadQuranFragment.TAB_SURAH)
                }
            }
        })

        binding.viewPager.adapter = adapter



        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                Toast.makeText(requireContext(),"$position",Toast.LENGTH_SHORT).show()
                viewModel.setTablePosition(position)

            }
        })
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]

        }.attach()


//        val dateNow = Calendar.getInstance().time
//        hariIni = android.text.format.DateFormat.format("EEEE", dateNow) as String
//        tanggal = android.text.format.DateFormat.format("d MMMM yyyy", dateNow) as String

//        binding.tvToday.setText("$hariIni,")
//        binding.tvDate.setText(tanggal)
        binding.txtLocation.setText(PrefsSet.city)

        getCurrentDate()


    }

    private fun getCurrentDate() {
        val ar = Locale("ar")
        val cal: Calendar = UmmalquraCalendar(ar)


        val finalDate = "${cal[Calendar.DAY_OF_MONTH]} ,${cal.getDisplayName(Calendar.MONTH, Calendar.LONG, ar)} " +
                "${cal[Calendar.YEAR]}"



        binding.tvDate.text =finalDate
                //                cal.get(Calendar.YEAR) ,        // 1436
//                cal.get(Calendar.MONTH),        // 5 <=> Jumada al-Akhirah
//                cal.get(Calendar.DAY_OF_MONTH) // 14


    }
}