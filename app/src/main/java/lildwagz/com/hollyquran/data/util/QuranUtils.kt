package lildwagz.com.hollyquran.data.util

import android.content.Context
import java.util.regex.Matcher

import android.text.style.ForegroundColorSpan

import android.text.SpannableString

import android.text.Spannable

import android.os.SystemClock

import androidx.core.content.ContextCompat
import lildwagz.com.hollyquran.R
import java.util.regex.Pattern


class QuranUtils() {
    val quranmatcher: Matcher =
        Pattern.compile("[01]?\\d?\\d:\\d?\\d?\\d").matcher("") //withtout brackets


    //vowels
    val fathah = '\u064e'
    val dhamma = '\u064f'
    val kasra = '\u0650'

    val ya_hamza = '\u0626'
    val joining_hamza = '\u0654'

    val kasratain = '\u064d'
    val fathatain = '\u064b'
    val dammatain = '\u064c'
    val shadda = '\u0651'
    val space = ' '

    //arabic letters
    val nun = '\u0646'
    val mim = '\u0645'
    val qaf = '\u0642'
    val toa = '\u0637'
    val ba = '\u0628'
    val zim = '\u062c'
    val dal = '\u062f'
    val soad = '\u0635'
    val zaal = '\u0630'
    val tha = '\u062b'
    val kaf = '\u0643'
    val wow = '\u0648'
    val shin = '\u0634'
    val seen = '\u0633'
    val zha = '\u0632'
    val fa = '\u0641'
    val ta = '\u062a'
    val doad = '\u0636'
    val zoa = '\u0638'
    val ra = '\u0631'
    val lam = '\u0644'
    val indopak_kaf = '\u06a9'

    //stop signs uthmani
    val stop_sign_zim = '\u06da'
    val stop_sign_lam = '\u06d9'
    val stop_sign_high_seen = '\u06dc'
    val stop_sign_mim = '\u06d8'
    val stop_sign_three_dots = '\u06db'
    val stop_sign_qaf_lam = '\u06d7'
    val stop_sign_soad_lam = '\u06d6'

    val low_seen = '\u06e3'

    //sukuns
    val sukun = '\u0652'
    val curvy_sukun = '\u06e1'
    val small_rounded_zero = '\u06df'

    //others
    val low_meem = '\u06ed'
    val high_meem = '\u06e2'
    val alif_hamza = '\u0627'
    val empty_ya = alif_hamza
    val empty_alif = '\u0649'
    val another_ya = '\u064a'
    val ta_marbuta = '\u0647'
    val supercript_alif_khara_fatha = '\u0670'
    val small_waw_elongation = "\u06E5"
    val small_ya_elongation = "\u06E6"
    val tatweel_empty_letter = "\u0640"
    val high_small_ya = "\u06E7"

    val uthmani_stop_signs =
        "" + stop_sign_three_dots + stop_sign_zim + stop_sign_qaf_lam + stop_sign_soad_lam + stop_sign_lam + stop_sign_mim

    private val hadithmatcher: Matcher =
        Pattern.compile("(bukhari|Bukhari|Muslim|dawud|tirmizi)..\\d?\\d\\/\\d\\d?\\d?\\d?")
            .matcher("")
    private val HADITHINTENT = "app_hadith.intent.action.HADITH"
    private val CURSOR_VIEWTYPE = "cursortype"
    private val QUERY = "query"
    private val FETCHSEARCH_HADITH = 3

    private val gunnahmatcher: Matcher = Pattern.compile("[$nun|$mim]$shadda").matcher("")
    private val iqlabmmatcher: Matcher =
        Pattern.compile("[$high_meem$low_meem][$sukun$curvy_sukun$empty_ya$empty_alif]?[$uthmani_stop_signs]?$space?$ba")
            .matcher("")
    private val qalqalamatcher: Matcher =
        Pattern.compile("[$qaf$toa$ba$zim$dal]($sukun|$curvy_sukun|[^$ta_marbuta]?[^$ta_marbuta$empty_alif$empty_ya]?[^$ta_marbuta$empty_alif$alif_hamza]$)")
            .matcher("")
    private val idhghammatcher: Matcher =
        Pattern.compile("([$nun$fathatain$dammatain$kasratain][$sukun$curvy_sukun$empty_ya$empty_alif]?[$uthmani_stop_signs]?$space[$nun$mim$another_ya$wow]$shadda?)|$mim[$uthmani_stop_signs$sukun$curvy_sukun]?$space$mim")
            .matcher("")
    private val idhghammatcherwihtoutgunnah: Matcher =
        Pattern.compile("[$nun$kasratain$fathatain$dammatain][$sukun$curvy_sukun$empty_ya$empty_alif]?[$uthmani_stop_signs]?$space[$ra$lam]")
            .matcher("")
    private val ikhfamatcher: Matcher =
        Pattern.compile("([$nun$kasratain$fathatain$dammatain][$sukun$curvy_sukun$empty_ya$empty_alif]?[$uthmani_stop_signs]?$space?[$soad$zaal$tha$kaf$zim$shin$qaf$seen$dal$toa$zha$fa$ta$doad$zoa$indopak_kaf])|$mim[$sukun$curvy_sukun]?$space?$ba")
            .matcher("")

    private val hamzawaslmatcher: Matcher = Pattern.compile("[$nun|$mim]$shadda").matcher("")
    private val lastelongationmatcher: Matcher = Pattern.compile("[$nun|$mim]$").matcher("")
    private val onemaadmatcher: Matcher = Pattern.compile("[$nun|$mim]$shadda").matcher("")
    private val threemaadmatcher: Matcher = Pattern.compile("[$nun|$mim]$shadda").matcher("")

    //thick letters such as qaf, lam, soad, kha
    private val thicklettermatcher: Matcher = Pattern.compile("[$nun|$mim]$shadda").matcher("")


    /**
     * As we are using space to split the words to be clickable, we cannot have extra spaces in a word.
     * But in indopak mushaf, if we want to show the stop signs correctly , we need to have spaces
     * before and after them.
     * So we are using different type of space to make a distinction.
     *
     * @see "http://jkorpela.fi/chars/spaces.html"
     */
    private val ZERO_WIDTH_NO_BR_SPACE = "\uFEFF" // doesn't separate

    private val WORD_JOINER = "\u2060" // doesn't separate

    private val NO_BREAK_SPACE = "\u00A0" // doesn't separate

    private val FIGURE_SPACE = "\u2007" // too big space

    private val ZERO_WIDTH_JOINER = "\u200d" // too big space

    private val THIN_SPACE = "\u2009" // currently most appropriate


    private val arabicmatcher: Matcher =
        Pattern.compile("[\u0600-\u065F\u066A-\u06EF\u06FA-\u06FF]+").matcher("")
    private var colors: IntArray? = null


    fun getTajweed(context: Context?, s: String): Spannable? {
        val t2: Long = SystemClock.uptimeMillis()
        if (colors == null) {
            colors = IntArray(6)
            colors!![0] = ContextCompat.getColor(context!!, R.color.color_ghunna)
            colors!![1] = ContextCompat.getColor(context, R.color.color_qalqala)
            colors!![2] = ContextCompat.getColor(context, R.color.color_iqlab)
            colors!![3] = ContextCompat.getColor(context, R.color.color_idgham)
            colors!![4] = ContextCompat.getColor(context, R.color.color_idghamwo)
            colors!![5] = ContextCompat.getColor(context, R.color.color_ikhfa)
        }
        val text: Spannable = SpannableString(s)
        gunnahmatcher.reset(s)
        while (gunnahmatcher.find()) {
//            Timber.d("gunnah Found " + gunnahmatcher.group(), "starting at " + gunnahmatcher.start() + " and ending at " + gunnahmatcher.end());
            text.setSpan(
                ForegroundColorSpan(colors!![0]),
                gunnahmatcher.start(),
                getEnd(s, gunnahmatcher.end()),
                0
            )
        }
        qalqalamatcher.reset(s)
        while (qalqalamatcher.find()) {
//            Timber.d("qalqala Found " + qalqalamatcher.group(), "starting at " + qalqalamatcher.start() + " and ending at " + qalqalamatcher.end());
            text.setSpan(
                ForegroundColorSpan(colors!![1]),
                qalqalamatcher.start(),
                qalqalamatcher.end(),
                0
            )
        }
        //
        iqlabmmatcher.reset(s)
        while (iqlabmmatcher.find()) {
//            Timber.d("iqlab Found text " + iqlabmmatcher.group()+ "starting at " + iqlabmmatcher.start() + " " + "and ending at " + iqlabmmatcher.end());
            text.setSpan(
                ForegroundColorSpan(colors!![2]),
                getIqlabStart(s, iqlabmmatcher.start()),
                iqlabmmatcher.end() + 1,
                0
            )
        }
        //
        idhghammatcher.reset(s)
        while (idhghammatcher.find()) {
//            Timber.d("idhgam Found " + idhghammatcher.group(), idhghammatcher.group() + " starting at " + idhghammatcher.start() + " and ending at " + idhghammatcher.end());
            text.setSpan(
                ForegroundColorSpan(colors!![3]),
                getStart(s, idhghammatcher.start()),
                getEnd(s, idhghammatcher.end()),
                0
            )
        }
        idhghammatcherwihtoutgunnah.reset(s)
        while (idhghammatcherwihtoutgunnah.find()) {
//            Timber.d("idhgamwo Found " + idhghammatcherwihtoutgunnah.group(), "starting at " + idhghammatcherwihtoutgunnah.start() + " and ending at " + idhghammatcherwihtoutgunnah.end());
            text.setSpan(
                ForegroundColorSpan(colors!![4]),
                getStart(s, idhghammatcherwihtoutgunnah.start()),
                idhghammatcherwihtoutgunnah.end() - 1,
                0
            )
        }
        ikhfamatcher.reset(s)
        while (ikhfamatcher.find()) {
//            Timber.d("ikhfa Found text " + ikhfamatcher.group()+ "starting at " + ikhfamatcher.start() + " " + "and ending at " + ikhfamatcher.end() + " " + "actual " + getEnd(s, ikhfamatcher.end()) + " " + s.charAt(ikhfamatcher.end() + 1));
            text.setSpan(
                ForegroundColorSpan(colors!![5]),
                getStart(s, ikhfamatcher.start()),
                getEnd(s, ikhfamatcher.end()),
                0
            )
        }

//        onemaadmatcher.reset(s);
//        while (onemaadmatcher.find()) {
//            Timber.d("ikhfa Found text " + onemaadmatcher.group(), "starting at " + onemaadmatcher.start() + " and ending at " + onemaadmatcher.end() + " actual " + getEnd(s, onemaadmatcher.end()) + " " + s.charAt(ikhfamatcher.end() + 1));
//            text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_ikhfa)), getStart(s, onemaadmatcher.start()), getEnd(s, onemaadmatcher.end()), 0);
//        }
//
//        hamzawaslmatcher.reset(s);
//        while (hamzawaslmatcher.find()) {
//            Timber.d("ikhfa Found text " + hamzawaslmatcher.group(), "starting at " + hamzawaslmatcher.start() + " and ending at " + hamzawaslmatcher.end() + " actual " + getEnd(s, hamzawaslmatcher.end()) + " " + s.charAt(ikhfamatcher.end() + 1));
//            text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_ikhfa)), getStart(s, hamzawaslmatcher.start()), getEnd(s, hamzawaslmatcher.end()), 0);
//        }
//
//        lastelongationmatcher.reset(s);
//        while (lastelongationmatcher.find()) {
//            Timber.d("ikhfa Found text " + lastelongationmatcher.group(), "starting at " + lastelongationmatcher.start() + " and ending at " + lastelongationmatcher.end() + " actual " + getEnd(s, lastelongationmatcher.end()) + " " + s.charAt(ikhfamatcher.end() + 1));
//            text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_ikhfa)), getStart(s, lastelongationmatcher.start()), getEnd(s, lastelongationmatcher.end()), 0);
//        }
//
//        threemaadmatcher.reset(s);
//        while (threemaadmatcher.find()) {
//            Timber.d("ikhfa Found text " + threemaadmatcher.group(), "starting at " + threemaadmatcher.start() + " and ending at " + threemaadmatcher.end() + " actual " + getEnd(s, threemaadmatcher.end()) + " " + s.charAt(ikhfamatcher.end() + 1));
//            text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_ikhfa)), getStart(s, threemaadmatcher.start()), getEnd(s, threemaadmatcher.end()), 0);
//        }
//        Timber.d("%sms", SystemClock.uptimeMillis() - t2);
        return text
    }

    private fun getIqlabStart(m: String, start: Int): Int {
        val ch = m[start - 1]
        return if ((ch == fathatain) or (ch == dammatain) or (ch == kasratain)) {
            if (m[start - 2] == shadda) start - 3 else start - 2
        } else start - 1
    }

    private fun getEnd(m: String, end: Int): Int {
        if (m[end] == shadda) {
            return if (m[end + 2] == supercript_alif_khara_fatha) { // standing fathah
                end + 3
            } else end + 2
        }
        return if (m[end + 1] == supercript_alif_khara_fatha || m[end + 1] == shadda) { // standing fathah
            end + 2
        } else end + 1
    }

    private fun getStart(m: String, start: Int): Int {
        val ch = m[start]
        return if ((ch == fathatain) or (ch == dammatain) or (ch == kasratain)) {
            if (m[start - 1] == shadda) {
                start - 2
            } else start - 1
        } else start
    }

}