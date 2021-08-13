package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("name_hn")
    val nameHn: String
){
    var isChecked = false
}