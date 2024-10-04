package lildwagz.com.hollyquran.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.*
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.data.util.PrefsReciter
import lildwagz.com.hollyquran.data.util.PrefsTranslation

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        setAyahFontSizeSummary()
        setDarkMode()
        setReciters()
        setTranslation()
        }

    private fun setTranslation() {
        findPreference<SwitchPreference>("switch_ayah_translate_preference")?.apply {
//            summary = "${sharedPreferences.getBoolean("switch_dark_mode_preference",false)}"
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                if (newValue == false){
                    PrefsTranslation.notranslation = false
                    Toast.makeText(requireContext(),"no ayah translation",Toast.LENGTH_SHORT).show()


                }else {
                    PrefsTranslation.notranslation = true

                    Toast.makeText(requireContext()," ayah translation",Toast.LENGTH_SHORT).show()
                }
                true
            }
        }
    }

    private fun setReciters() {
        findPreference<ListPreference>("setting_reciter")?.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->

                PrefsReciter.reciter = newValue.toString()
                Toast.makeText(requireContext(),newValue.toString(),Toast.LENGTH_SHORT).show()
//                if (preference is ListPreference) {
//                    val index = preference.findIndexOfValue(newValue.toString())
//                    val entry = preference.entries.get(index)
//                    val entryvalue = preference.entryValues.get(index)
//                    Log.i("selected val", " position - $index value - $entry, entryvalue - $entryvalue ")
//                }
                true
            }
        }
    }

    private fun setDarkMode() {
        findPreference<SwitchPreference>("switch_dark_mode_preference")?.apply {
//            summary = "${sharedPreferences.getBoolean("switch_dark_mode_preference",false)}"
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                if (newValue == false){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    Toast.makeText(requireContext(),"Dark Mode Disabled ",Toast.LENGTH_SHORT).show()


                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                    Toast.makeText(requireContext(),"Dark mode enabled",Toast.LENGTH_SHORT).show()
                }
                true
            }
        }
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title="Settings"
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle=""
    }


    private fun setAyahFontSizeSummary() {
        findPreference<EditTextPreference>("ayah_text_preference")?.apply {
            summary =
                "${sharedPreferences.getString("ayah_text_preference", "18")} Font Size"

            onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, o ->
                    summary = "${o.toString()} Font size"
                    true
                }
        }
    }


}
