package com.constructionmitra.user.utilities

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import com.constructionmitra.user.R
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
    private const val  FILE_EXT = ".jpeg"
    const val DEFAULT_BUFFER_SIZE = 8192

    fun getMediaDir(context: Context): File? {
        log("external Dir : ${context.externalMediaDirs.firstOrNull().toString()}")
        val dirs = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
            context.externalMediaDirs
        }
        else
            context.getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS)

        return dirs.firstOrNull()?.let {
            File(it, context.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
    }


    fun getImageFileWithTimeStamp(context: Context): File{
        return  File(
            getMediaDir(context),
            SimpleDateFormat(FILENAME, Locale.US)
                .format(System.currentTimeMillis()) + FILE_EXT
        )
    }

    fun saveStream(inputStream: InputStream?, outputFile: File): Long?{
        try {
            val outputStream = FileOutputStream(
                outputFile,
                true
            )
            return inputStream?.copyTo(outputStream, kotlin.io.DEFAULT_BUFFER_SIZE)
        }
        catch (exp: IOException){
            log(exp.toString())
        }
        return null
    }

    fun saveBitmapToFile(bitmap: Bitmap, context: Context): File{
        val outPutFile = getImageFileWithTimeStamp(context)
        val outputStream = FileOutputStream(outPutFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.apply {
            flush()
            close()
        }
        return outPutFile
    }

    private fun log(message: String){
        Timber.d("FileUtils $message")
    }

}