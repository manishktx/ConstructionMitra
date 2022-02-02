package com.constructionmitra.user.utilities.constants

import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.Profile
import com.constructionmitra.user.data.*

object AppConstants {
    val ages = arrayOf(
        "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
        "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
        "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
        "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65",
    )

    const val  USER_TYPE_CONTRACTOR = "user_type_contractor"
    const val  USER_TYPE_PETTY_CONTRACTOR = "user_type_petty_contractor"

    val defaultAppConfig = ConfigData(
        activeLocations =  listOf(
            ActiveLocation(
                activeLocationId = "5",
                city = "Noida",
                image = "http://creativemint.in/cmitra/public/location/1629798719_noida.png"
            ),
            ActiveLocation(
                activeLocationId = "4",
                city = "Gurgaon",
                image = "http://creativemint.in/cmitra/public/location/1629798737_download.jpg"
            ),
            ActiveLocation(
                activeLocationId = "3",
                city = "Faridabad",
                image = "http://creativemint.in/cmitra/public/location/1629798767_faridabaad.jpg"
            )
        ),
        experiences = listOf(
            Experience(
                experienceId = "1",
                experience = "0 - 3 years",
                experienceHn = "0 - 3 years",
            ),
            Experience(
                experienceId = "2",
                experience = "3 - 5 years",
                experienceHn = "3 - 5 years",
            ),
            Experience(
                experienceId = "3",
                experience = "5 -10 years",
                experienceHn = "5 -10 years",
            ),
            Experience(
                experienceId = "4",
                experience = "above 15 years",
                experienceHn = "above 15 years",
            ),
        ),
        qualifications = listOf(
            Qualification(
                qualificationId = "15",
                qualification = "B.A pass",
                qualificationHn = "बी.ए पास",
            ),
            Qualification(
                qualificationId = "14",
                qualification = "12th pass",
                qualificationHn = "12 वीं पास",
            ),

            Qualification(
                qualificationId = "13",
                qualification = "M.com",
                qualificationHn = "एम.कॉम",
            ),
        ),
        projectType = listOf(
            ProjectType(
                projectTypeId = "12",
                projectTypeName = "Residential/Real Estate Project",
            ),
            ProjectType(
                projectTypeId = "11",
                projectTypeName = "Commercial Project",
            ),
            ProjectType(
                projectTypeId = "10",
                projectTypeName = "Industrial Project",
            ),
            ProjectType(
                projectTypeId = "9",
                projectTypeName = "Infra Project (Road, bridges etc.)",
            ),
            ProjectType(
                projectTypeId = "7",
                projectTypeName = "Others",
            )
        ),
        workPreferences = listOf(
            WorkPreference(
                workPreferenceId = "1",
                workPreference = "construction site",
                workPreferenceHn = "निर्माण स्थल",
            ),
            WorkPreference(
                workPreferenceId = "2",
                workPreference = "private builder",
                workPreferenceHn = "निजी बिल्डर/ बिल्डर",
            ),
            WorkPreference(
                workPreferenceId = "3",
                workPreference = "wherever you work",
                workPreferenceHn = "जहाँ  भी काम हो",
            )
        )
    )

    val  salaryRanges = listOf<Salary>(
        Salary("0-240000", "Upto ₹ 2.4 Lacs P.A."),
        Salary("120000-320000", "₹ 1.2 – 3.2 Lacs P.A."),
        Salary("320000-500000", "₹ 3.2 – 5.0 Lacs P.A."),
        Salary("500000-750000","₹ 5.0 – 7.5 Lacs P.A."),
        Salary("750000-1000000","₹ 7.5 – 10.0 Lacs P.A."),
        Salary("1000000-10000000","More than ₹ 10 Lacs P.A."),
    )
}

enum class Role(val role: String){
    ENGINEER_SUPERVISOR("engineer"), PETTY_CONTRACTOR("petty"), SPECIALISED_AGENCY("specialized")
}

enum class UserType(val category: String, val id: Int){
    WORKER("Construction worker", 1),
    PETTY_CONTRACTOR("Petty Contractors", 2),
    SPECIALISED_AGENCY("Specialized agency", 3),
    ENGINEER_SUPERVISOR("Engineer", 4),
}

val  PROFILE_CARDS_WORKER = listOf(
    ProfileItem(R.drawable.ic_person,
        "अपने बारें में बताएं",
        "जानकारी जोड़े",
        Profile.ABOUT),
    ProfileItem(
        R.drawable.ic_bag,
        "अपना अनुभव साझा करें",
        "जानकारी जोड़े" ,
        Profile.EXPERIENCE,
    ),

    ProfileItem(
        R.drawable.ic_location,
        "अपने काम करने की जगह",
        "जानकारी जोड़े",
        Profile.WORK_LOCATION,
    ),

    ProfileItem(
        R.drawable.ic_location_city,
        "अपने कार्य की प्रार्थमिकता",
        "जानकारी जोड़े +",
        Profile.WORK_PREFERENCE,
    ),

    ProfileItem(R.drawable.ic_camera_alt,
        "फोटो और आईडी",
        "जानकारी जोड़े",
        Profile.PHOTO_AND_ID_BOTH
    ),
)
