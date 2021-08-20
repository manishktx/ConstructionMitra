package com.constructionmitra.user.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
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

}