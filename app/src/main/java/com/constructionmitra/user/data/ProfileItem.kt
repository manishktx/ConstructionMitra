package com.constructionmitra.user.data

import android.os.Parcelable
import com.constructionmitra.user.adapters.Profile
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProfileItem(
    val icon: Int,
    val title: String,
    val ctaText: String,
    val profile: Profile
): Parcelable {

}