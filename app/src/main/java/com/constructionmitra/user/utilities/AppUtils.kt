package com.constructionmitra.user.utilities

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.constructionmitra.user.api.FEMALE
import com.constructionmitra.user.api.MALE
import com.constructionmitra.user.api.OTHER
import com.constructionmitra.user.data.JobRole
import com.constructionmitra.user.data.SelectWorkData
import java.util.regex.Matcher
import java.util.regex.Pattern

object AppUtils {
     fun genderType(gender: String) = when (gender) {
        MALE().hindiName, MALE().englishName-> {
            MALE().englishName
        }

        FEMALE().hindiName, FEMALE().englishName -> {
            FEMALE().englishName
        }
        else -> OTHER().englishName
    }

    fun genderTypeValue(gender: String) = when (gender) {
        MALE().hindiName, MALE().englishName-> {
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

    fun getSelectedWorkString(selectedWorkData: List<SelectWorkData>): String{
            val idList = selectedWorkData.map {
                    jobRole ->  jobRole.work
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

}