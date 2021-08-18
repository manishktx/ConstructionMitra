package com.constructionmitra.user.utilities

import com.constructionmitra.user.api.FEMALE
import com.constructionmitra.user.api.MALE
import com.constructionmitra.user.api.OTHER

object AppUtils {
     fun genderInEnglish(genderInHindi: String) = when (genderInHindi) {
        MALE().hindiName -> {
            MALE().englishName
        }

        FEMALE().hindiName -> {
            FEMALE().englishName
        }
        else -> OTHER().englishName
    }
}