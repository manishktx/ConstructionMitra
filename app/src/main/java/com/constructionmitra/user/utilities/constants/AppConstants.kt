package com.constructionmitra.user.utilities.constants

object AppConstants {
    val ages = arrayOf(
        "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
        "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
        "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
        "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65",
    )

    const val  USER_TYPE_CONTRACTOR = "user_type_contractor"
    const val  USER_TYPE_PETTY_CONTRACTOR = "user_type_petty_contractor"

}

enum class Role(val role: String){
    ENGINEER_SUPERVISOR("engineer"), PETTY_CONTRACTOR("petty"), SPECIALISED_AGENCY("specialised")
}
