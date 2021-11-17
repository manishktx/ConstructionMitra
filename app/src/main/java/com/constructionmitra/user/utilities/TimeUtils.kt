package com.constructionmitra.user.utilities

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

// 2021-08-09 02:08:36
object TimeUtils {

    /**
     * Method converting 2021-01-15 21:32:23 09:32 PM
     */
    @SuppressLint("SimpleDateFormat")
    fun getFormattedTimeForChatScreen(time: String?): String {
        if (time != null && time.isNotEmpty()) {
            val inputDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val outputDate = SimpleDateFormat(AppUtils.TIME_STAMP_FORMAT)
            val date = inputDate.parse(time)
            return outputDate.format(date)
        }
        throw IllegalArgumentException("Time can not be empty!")
    }

    /**
     * Method converting yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    @SuppressLint("SimpleDateFormat")
    fun getFormattedTime(time: String?): String {
        if (time != null && time.isNotEmpty()) {
            val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val outputDate = SimpleDateFormat(AppUtils.TIME_STAMP_FORMAT)
            val date = inputDate.parse(time)
            return outputDate.format(date)
        }
        throw IllegalArgumentException("Time can not be empty!")
    }

    @SuppressLint("SimpleDateFormat")
    fun getDayOnlyFromTimeStamp(time: String?): Int {
        return if (time != null && time.isNotEmpty()) {
            val inputDate = SimpleDateFormat( AppUtils.APP_TIME_STAMP_FORMAT)
            val outputDate = SimpleDateFormat(AppUtils.TIME_STAMP_FORMAT)
            val date = inputDate.parse(time)
            outputDate.format(date).split(" ", limit = 2).first().toInt()
        } else 0
    }

    fun getDayOnlyFromCurrentDate(): Int {
        return SimpleDateFormat("d hh:mm a", Locale.ENGLISH)
            .format(Date().time).split(" ", limit = 2).first().toInt()
    }

    fun getFormattedTimeFromMillis(time: Long): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .format(time)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeOnlyFromTimeStamp(time: String?): String {
        return if (time != null && time.isNotEmpty()) {
            val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val outputDate = SimpleDateFormat(AppUtils.ONLY_TIME_STAMP_FORMAT)
            val date = inputDate.parse(time)
            outputDate.format(date)
        } else ""
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateMonthTimeStamp(time: String?): String {
        return if (time != null && time.isNotEmpty()) {
            val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val outputDate = SimpleDateFormat(AppUtils.ONLY_DATE_MONTH_FORMAT)
            val date = inputDate.parse(time)
            val format = outputDate.format(date)
            val split = format.split("-")
            return if (split.size > 1) {
                split[0] + " " + split[1]
            } else {
                ""
            }
        } else ""
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateMonthYearTimeStamp(time: String?): String {
        return if (time != null && time.isNotEmpty()) {
            val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val outputDate = SimpleDateFormat(AppUtils.ONLY_DATE_MONTH_NO_FORMAT)
            val date = inputDate.parse(time)
            return outputDate.format(date)

        } else ""
    }

    val formattedTime = SimpleDateFormat("d hh:mm a", Locale.ENGLISH)
        .format(Date().time).toString()

}