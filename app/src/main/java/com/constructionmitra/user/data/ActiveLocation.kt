package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class ActiveLocation(
    @SerializedName("active_location_id")
    val activeLocationId: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("image")
    val image: String
)