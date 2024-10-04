package lildwagz.com.hollyquran.ui.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.appquran.ui.quran.QuranViewModel
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.QuranDatabase
import lildwagz.com.hollyquran.databinding.FragmentSearchQuranBinding
import lildwagz.com.hollyquran.ui.quran.ReadQuranAdapter

class SearchQuranFragment : Fragment(R.layout.fragment_search_quran) {

    private val binding : FragmentSearchQuranBinding by viewBinding()
    private val viewMode : QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showOrHideKeyboard(true)

        binding.inputTextSearch.setOnEditorActionListener{v,actionId,event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val query = binding.inputTextSearch.text.toString()
                setQuranAdapter(query)
                Toast.makeText(requireContext(),"$query",Toast.LENGTH_SHORT).show()
                true
            }
            false
        }
        binding.toolbar.setNavigationOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun setQuranAdapter(query : String){

        val QuraDao = QuranDatabase.getInstance(requireContext()).quranDao()
        viewMode.getTotalAyahList().observe(viewLifecycleOwner,{totalAyah->
            QuraDao.searchAyah("%$query%").asLiveData().observe(viewLifecycleOwner,{listQuran->
                val adapter = ReadQuranAdapter(listQuran,totalAyah,"")
                Log.d("Cek",listQuran.size.toString())
                binding.recyclerview.adapter =adapter
            })
        })
    }

    private fun showOrHideKeyboard(show : Boolean){
        val imn : InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
        as InputMethodManager
        if (show){
            binding.inputTextSearch.requestFocus()
            imn.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
        }else{
            binding.inputTextSearch.clearFocus()
            imn.hideSoftInputFromWindow(binding.inputTextSearch.windowToken,0)

        }
    }

}