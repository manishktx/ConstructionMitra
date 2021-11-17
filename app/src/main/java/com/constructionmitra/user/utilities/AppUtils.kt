package com.constructionmitra.user.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.constructionmitra.user.api.FEMALE
import com.constructionmitra.user.api.MALE
import com.constructionmitra.user.api.OTHER
import com.constructionmitra.user.data.SelectWorkData
import java.util.regex.Matcher
import java.util.regex.Pattern

object AppUtils {

    const val TIME_STAMP_FORMAT = "d MMM\nh:mm aaa"
    const val APP_TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val ONLY_TIME_STAMP_FORMAT = "hh:mm a"
    const val ONLY_DATE_MONTH_FORMAT = "dd-MMM-yyyy"
    const val ONLY_DATE_MONTH_NO_FORMAT = "dd-MM-yyyy"

    fun genderType(gender: String) = when (gender) {
        MALE().hindiName, MALE().englishName -> {
            MALE().englishName
        }

        FEMALE().hindiName, FEMALE().englishName -> {
            FEMALE().englishName
        }
        else -> OTHER().englishName
    }

    fun genderTypeValue(gender: String) = when (gender) {
        MALE().hindiName, MALE().englishName -> {
            MALE()
        }

        FEMALE().hindiName, FEMALE().englishName -> {
            FEMALE()
        }
        else -> OTHER()
    }

    fun isValidMobile(s: String): Boolean {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        val p = Pattern.compile("[6-9][0-9]{9}")

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        val m = p.matcher(s)
        return m.find() && m.group() == s
    }

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email!!)
        return matcher.matches()
    }

    fun getSelectedWorkString(selectedWorkData: List<SelectWorkData>): String {
        val idList = selectedWorkData.map { jobRole ->
            jobRole.work
        }
        return idList.joinToString(separator = ",")
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        }
    }

    fun preventTwoClick(view: View?) {
        view?.isEnabled = false
        view?.postDelayed({ view.isEnabled = true }, 500)
    }

    fun openDial(activity: Activity, number: String) {
        val num = "+91$number"
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$num")
        activity.startActivity(intent)
    }

}