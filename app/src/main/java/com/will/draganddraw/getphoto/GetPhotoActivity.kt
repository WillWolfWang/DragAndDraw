package com.will.draganddraw.getphoto

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.will.draganddraw.databinding.ActivityGetPhotoBinding
import java.io.File

class GetPhotoActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityGetPhotoBinding
    lateinit var imageUri: Uri
    lateinit var outputImage: File

    private val takePhotoLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {result->
        if (result) {
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
            viewBinding.ivShow.setImageBitmap(rotateIfRequired(bitmap))
        }
    }

    private val pickPhotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {uri->
        uri?.let {
            viewBinding.ivShow.setImageURI(uri)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityGetPhotoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnGetPhoto.setOnClickListener {
            // 创建 file 对象，用于存储拍照后的照片
            outputImage = File(externalCacheDir, "outputImage.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(this, "com.will.draganddraw.fileprovider", outputImage)
            } else {
                imageUri = Uri.fromFile(outputImage)
            }

            // 启动相机程序
            takePhotoLauncher.launch(imageUri)
        }

        viewBinding.btnAlbum.setOnClickListener {
            // 启动相册
            pickPhotoLauncher.launch("image/*")
        }
    }

    // 旋转图片
    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when(orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()// 将不需要的 bitmap 回收
        return rotatedBitmap
    }
}