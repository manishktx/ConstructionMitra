package com.constructionmitra.user.utilities

object ServerConstants {
    const val STATUS_SUCCESS = "200"
}

enum class UpdateJobPost(val param: String) {
    DELETE("deleted"), PUBLISHED("published")
}