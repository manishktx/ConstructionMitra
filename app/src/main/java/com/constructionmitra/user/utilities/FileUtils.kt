package com.constructionmitra.user.utilities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.constructionmitra.user.R
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URLConnection
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

    fun shareImageFile(context: Context, file: File){
        val shareUri = getFileUri(context, file)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, shareUri)
        }
        context.startActivity(shareIntent)
    }

    private fun getFileUri(context: Context, file: File): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider", file
            )
        } else {
            Uri.fromFile(file)
        }
    }

//    fun shareImageFile(context: Context, file: File){
//        val intentShareFile = ShareCompat.IntentBuilder(context)
//            .setType(URLConnection.guessContentTypeFromName(file.name))
//            .setStream(
////                        FileProvider.getUriForFile(
////                            requireActivity(),
////                            requireActivity().applicationContext.packageName + ".provider",
////                            uri.toFile()
////                        )
//                Uri.fromFile(file),
//            )
//            .createChooserIntent()
//            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            .addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//        context.startActivity(intentShareFile)
//    }

    private fun log(message: String){
        Timber.d("FileUtils $message")
    }

}