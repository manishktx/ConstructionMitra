package com.constructionmitra.user.utilities.constants

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
        )
    )
}

enum class Role(val role: String){
    ENGINEER_SUPERVISOR("engineer"), PETTY_CONTRACTOR("petty"), SPECIALISED_AGENCY("specialized")
}
