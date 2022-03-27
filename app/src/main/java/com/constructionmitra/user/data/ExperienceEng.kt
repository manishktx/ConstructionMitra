package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class ExperienceEng(
    @SerializedName("current_resposibility ")
    val current_resposibility : String,
    @SerializedName("working_from")
    val working_from: String,
    @SerializedName("working_till")
    val working_till: String,
    @SerializedName("present_employer ")
    val present_employer : String,
    @SerializedName("present_designation  ")
    val present_designation  : String,
    @SerializedName("salary_range   ")
    val salary_range   : String,
    @SerializedName("previous_company   ")
    val previous_company   : String,
    @SerializedName("previous_time_period   ")
    val previous_time_period   : String,
    @SerializedName("previous_designation   ")
    val previous_designation   : String,

)