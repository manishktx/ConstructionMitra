package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class JobCategory(
    @SerializedName("category")
    val category: String,
    @SerializedName("category_hn")
    val categoryHn: String,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("category_image")
    val categoryImage: String,
    @SerializedName("desc")
    val workDesc: String
){
    override fun toString(): String {
        return category
    }
}