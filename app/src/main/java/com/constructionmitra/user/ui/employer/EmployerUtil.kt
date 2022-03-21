package com.constructionmitra.user.ui.employer

import com.constructionmitra.user.utilities.constants.Role

object EmployerUtil {

    fun findRole(categoryName: String) = when(true) {
        categoryName.contains(Role.ENGINEER_SUPERVISOR.role, ignoreCase = true) -> {
            Role.ENGINEER_SUPERVISOR
        }
        categoryName.contains(Role.SPECIALISED_AGENCY.role, ignoreCase = true) -> {
            Role.SPECIALISED_AGENCY
        }
        else -> {
            Role.PETTY_CONTRACTOR
        }
    }
}