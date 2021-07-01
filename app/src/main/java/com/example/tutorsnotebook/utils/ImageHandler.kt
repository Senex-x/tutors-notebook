package com.example.tutorsnotebook.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import java.io.InputStream


class ImageHandler {
    companion object {
        private const val MAX_HEIGHT = 1024
        private const val MAX_WIDTH = 1024

        fun handleSamplingAndRotationBitmap(context: Context, selectedImage: Uri?): Bitmap? {
            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var imageStream: InputStream =
                context.contentResolver.openInputStream(selectedImage!!)!!
            BitmapFactory.decodeStream(imageStream, null, options)
            imageStream.close()

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            imageStream = context.contentResolver.openInputStream(selectedImage)!!
            var img = BitmapFactory.decodeStream(imageStream, null, options)
            img = rotateImageIfRequired(context, img!!, selectedImage)
            return img
        }

        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int, reqHeight: Int
        ): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {

                // Calculate ratios of height and width to requested height and width
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

                // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
                // with both dimensions larger than or equal to the requested height and width.
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio

                // This offers some additional logic in case the image has a strange
                // aspect ratio. For example, a panorama may have a much larger
                // width than height. In these cases the total pixels might still
                // end up being too large to fit comfortably in memory, so we should
                // be more aggressive with sample down the image (=larger inSampleSize).
                val totalPixels = (width * height).toFloat()

                // Anything more than 2x the requested pixels we'll sample down further
                val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
                while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                    inSampleSize++
                }
            }
            return inSampleSize
        }

        private fun rotateImageIfRequired(context: Context, img: Bitmap, selectedImage: Uri): Bitmap? {
            val imageStream: InputStream? =
                context.contentResolver.openInputStream(selectedImage)
            val ei = ExifInterface(imageStream!!)
            return when (ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
                else -> img
            }
        }

        private fun rotateImage(img: Bitmap, degree: Int): Bitmap? {
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
            img.recycle()
            return rotatedImg
        }

        private fun scaleBitmapToFitScreenWidth(context: Context, originalBitmap: Bitmap): Bitmap {
            val width = context.resources.displayMetrics.widthPixels
            val height: Int = width * originalBitmap.height / originalBitmap.width
            return Bitmap.createScaledBitmap(originalBitmap, width, height, true)
        }
    }
}