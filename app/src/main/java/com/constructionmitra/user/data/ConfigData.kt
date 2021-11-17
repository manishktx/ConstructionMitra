package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class ConfigData(
    @SerializedName("active_locations")
    val activeLocations: List<ActiveLocation>,
    @SerializedName("experiences")
    val experiences: List<Experience>,
    @SerializedName("project_type")
    val projectType: List<ProjectType>,
    @SerializedName("qualifications")
    val qualifications: List<Qualification>
){
    fun getProjectAt(position: Int) = projectType[position]
}