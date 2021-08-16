package com.constructionmitra.user.utilities

import com.constructionmitra.user.data.JobRole
import com.constructionmitra.user.data.Location


object StringUtils {

    fun stringPresentationOfLocations(list: List<Location>): String{
        if(list.isEmpty())
            return ""
        var locations = ""
        for (location in list){
            locations = locations.plus(location.id).plus(",")
        }
        return locations.subSequence(0, locations.length - 3).toString()
    }

    fun stringLocations(list: List<Location>): String{
        if(list.isEmpty())
            return ""
        var locations = ""
        for (location in list){
            locations = locations.plus(location.name).plus(", ")
        }
        return locations.subSequence(0, locations.length - 4).toString()
    }

    fun jobRolesInString(list: List<JobRole>): String{
        if(list.isEmpty())
            return ""
        var locations = ""
        for (location in list){
            locations = locations.plus(location.jobRole).plus(",")
        }
        return locations.subSequence(0, locations.length - 3).toString()
    }
}