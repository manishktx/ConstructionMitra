package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName("address")
    val address: String,
    @SerializedName("age")
    val age: String,
    @SerializedName("current_residence")
    val currentResidence: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("experience")
    val experience: String,
    @SerializedName("fcm_id")
    val fcmId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("firm_name")
    val firmName: String,
    @SerializedName("no_of_workers")
    val noOfWorker: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("id")
    val id: String,
    @SerializedName("other_phone_number")
    val otherPhoneNumber: String,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("preffered_location")
    val prefferedLocations: List<Location>,

    @SerializedName("job_role_ids")
    val jobRoles: List<JobRole>,

    @SerializedName("profile_pic")
    val profilePic: String,
    @SerializedName("user_doc")
    val userDoc: String
)

data class AboutData(
    var name: String = "",
    var phoneNum: String = "",
    var otherMobileNum: String = "",
    var gender: String = "",
    var age: String = "",
    var homeAddress: String = "",
    var currentAddress: String = "",
)

data class ContractorAboutData(
    var employeeName: String = "",
    var designation: String = "",
    var mobileNumber: String = "",
    var emailId: String = "",
    var companyName: String = "",
    var companyAddress: String = "",
    var city: String = "",
)