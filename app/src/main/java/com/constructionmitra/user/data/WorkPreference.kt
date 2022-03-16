package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class WorkPreference(
    @SerializedName("work_preference")
    val workPreference: String,
    @SerializedName("work_preference_hn")
    val workPreferenceHn: String,
    @SerializedName("work_preference_id", alternate = ["id"])
    val workPreferenceId: String
) {
    override fun toString(): String {
        return workPreferenceHn
    }
}