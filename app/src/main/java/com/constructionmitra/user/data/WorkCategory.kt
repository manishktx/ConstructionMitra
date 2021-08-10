package com.constructionmitra.user.data

data class WorkCategory(
    val name: String,
    val backgroundColor: String,
    val desc: String
) {
}

val dummyList = listOf<WorkCategory>(
    WorkCategory("निर्माण श्रमिक", "", "स्वयं के लिए काम ढूंढ रहे हो तो यह विकल्प चुने"),
    WorkCategory("पेट्टी कांट्रेक्टर", "", "अगर आप के पास काम करने वालों की टीम है तो यहाँ विकल्प चुने"),
    WorkCategory("Specialised Agency", "", "वाटर प्रूफिंग, फायर फाइटिंग इत्यादि काम करते हो तो यह विकल्प चुनें"),
    WorkCategory("Engineer/Supervisor", "", "इंजीनियर / कंस्ट्रक्शन साइट सुपरवाइजर करते हो तो यह विकल्प चुनें"),

)


val dummyListSubCategories = listOf<WorkCategory>(
    WorkCategory("नचुनाई मिस्त्री / राज मिस्त्री", "#FFE4EA", ""),
    WorkCategory("प्लास्टरिंग", "#F3DCF4", ""),
    WorkCategory("लेबर सप्लायर", "#D5F3FF", ""),
    WorkCategory("बार बाइंडर/सरिया मिस्त्री", "#B1DF8D", ""),
    WorkCategory("शटरिंग", "#FFD89C", ""),
    WorkCategory("फर्नीचर", "#A5D7FE", ""),
    WorkCategory("वेल्डिंग वर्क", "#CAB4E7", ""),
    WorkCategory("इलेक्ट्रिक वर्क", "#FFFAB0", ""),
    WorkCategory("P.O.P, वॉल पुट्टी", "#FFBDC7", ""),
    WorkCategory("पेंटिंग", "#A5D7FE", ""),

    )