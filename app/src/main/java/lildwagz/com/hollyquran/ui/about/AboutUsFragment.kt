package lildwagz.com.hollyquran.ui.about

import android.os.Bundle
import android.view.View
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.databinding.FragmentAboutUsBinding


class AboutUsFragment : Fragment(R.layout.fragment_about_us) {

    private val binding : FragmentAboutUsBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvFeedback.setOnClickListener {
            ShareCompat.IntentBuilder.from(requireActivity())
                .setType("message/rfc822")
                .addEmailTo("123azzam.4everr@gmail.com")
                .setSubject("bug error report")
                .setText("reports : .......")
                .setChooserTitle("tittle")
                .startChooser()
        }

    }


}