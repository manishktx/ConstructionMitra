package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("active_location_id")
    val id: String,
    @SerializedName("name")
    val name: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("name_hn")
    val nameHn: String
){
    var isChecked = false

    override fun toString(): String {
        return city
    }
}