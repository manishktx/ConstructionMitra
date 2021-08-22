package com.constructionmitra.user.utilities

import com.constructionmitra.user.api.FEMALE
import com.constructionmitra.user.api.MALE
import com.constructionmitra.user.api.OTHER

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
}