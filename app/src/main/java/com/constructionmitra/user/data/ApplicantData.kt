package com.constructionmitra.user.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApplicantData(
    @SerializedName("applicant_id")
    val applicantId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("mapping_id")
    val mappingId: String,
    @SerializedName("phone_number")
    val phoneNumber: String
): Parcelable {
    var showContactDetails: Boolean = false
}