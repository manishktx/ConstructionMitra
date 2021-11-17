package com.constructionmitra.user.utilities

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.view.PixelCopy
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

interface BitmapConfig {
    val outHeight: Int
    val outWidth: Int
}

class CMBitmapConfig : BitmapConfig {
    override val outHeight: Int
        get() = 500
    override val outWidth: Int
        get() = 500
}

object BitmapUtils {


    fun saveBitmapToGallery(bitmap: Bitmap, context: Context): File {
        val outPutFile = FileUtils.getImageFileWithTimeStamp(context)
        val outputStream = FileOutputStream(outPutFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.apply {
            flush()
            close()
        }
        return outPutFile
    }

    fun decodeStreamToBitmap(context: Context, imageUri: Uri, bitmapConfig: BitmapConfig) =
        BitmapFactory.Options().run {
            inJustDecodeBounds = true

            BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(imageUri),
                null,
                this
            )

            inSampleSize = calculateInSampleSize(this, bitmapConfig.outWidth, bitmapConfig.outHeight)
            inJustDecodeBounds = false

            val bitmap = BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(imageUri),
                null,
                this
            )

            rotateImage(bitmap!!, imageUri, context)
        }

    private fun rotateImage(bitmap: Bitmap, imageUri: Uri, context: Context): Bitmap? {
        var rotate = 0f
        val exif = ExifInterface(context.contentResolver.openInputStream(imageUri)!!)
        val orientation: Int = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        Timber.d("BitmapUtils: Orientation: $orientation")
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270f
            ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180f
            ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90f
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> {
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSVERSE -> {
//                matrix.postRotate(-90f)
                rotate = 90f
                matrix.postScale(-1f, 1f)
            }
        }
//        matrix.preScale(-1.0f, 0f);
        matrix.postRotate(rotate)
        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, matrix, true
        )
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        Timber.d("BitmapUtils: inSampleSize = $inSampleSize")
        return inSampleSize
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun takeScreenShot(view: View, activity: AppCompatActivity, callback:(bitmap: Bitmap) -> Unit){
        activity.window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(window, Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
                    if (copyResult == PixelCopy.SUCCESS) {
                        callback(bitmap)
                    }
                    // possible to handle other result codes ...
                }, Handler(activity.mainLooper))
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
                e.printStackTrace()
            }
        }
    }

    fun takeScreenShot(view: View): Bitmap{
        val b = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        c.translate((-view.scrollX).toFloat(), (-view.scrollY).toFloat())
        view.draw(c)
        return b
    }

}