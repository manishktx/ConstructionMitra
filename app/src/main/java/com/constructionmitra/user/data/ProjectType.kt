package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class ProjectType(
    @SerializedName("project_type_id")
    val projectTypeId: String,
    @SerializedName("project_type_name")
    val projectTypeName: String
){
    override fun toString(): String {
        return projectTypeName
    }
}