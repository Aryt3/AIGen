package com.example.myapplication

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Base64
import android.util.DisplayMetrics
import android.widget.*
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var imageView: ImageView
    private lateinit var responseTextView: TextView
    private lateinit var requestButton: Button
    private lateinit var promptEditText: EditText
    private lateinit var downloadButton: Button
    private lateinit var valueSeekBar: SeekBar
    private lateinit var valueWidth: EditText
    private lateinit var valueHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        imageView = findViewById(R.id.imageView)
        responseTextView = findViewById(R.id.responseTextView)
        requestButton = findViewById(R.id.requestButton)
        promptEditText = findViewById(R.id.promptEditText)
        downloadButton = findViewById(R.id.downloadButton)
        valueSeekBar = findViewById(R.id.valueSeekBar)
        valueWidth = findViewById(R.id.width)
        valueHeight = findViewById(R.id.height)


        requestButton.setOnClickListener {
            sendRequest()
        }

        downloadButton.setOnClickListener {
            saveImage()
        }
    }

    private fun sendRequest() {
        val prompt = promptEditText.text.toString()
        val seekBarValue = valueSeekBar.progress + 1
        val steps = seekBarValue * 6
        val width: Int = valueWidth.text.toString().toIntOrNull() ?: 0
        val height: Int = valueHeight.text.toString().toIntOrNull() ?: 0

        val promptTextView: TextView = findViewById(R.id.promptTextView)
        promptTextView.text = "Prompt:\n$prompt"
        promptTextView.setTypeface(null, Typeface.BOLD)

        GlobalScope.launch {
            val imageBytes = ApiService.sendPostRequest(prompt, steps, width, height)
            if (imageBytes != null) {
                try {
                    val jsonResponse = String(imageBytes, Charsets.UTF_8)
                    val jsonObject = JSONObject(jsonResponse)
                    val imageArray = jsonObject.getJSONArray("images")
                    val base64Image = imageArray.getString(0)
                    val decodedImageBytes = Base64.decode(base64Image, Base64.DEFAULT)

                    withContext(Dispatchers.Main) {
                        val imageBitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.size)
                        imageView.setImageBitmap(imageBitmap)
                        responseTextView.text = "Image received"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        responseTextView.text = "Failed to decode image bytes"
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    responseTextView.text = "Failed to retrieve image bytes"
                }
            }
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "img_$timeStamp.png"

        // Get the directory for storing images
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(imagesDir, fileName)

        // Save the bitmap to the image file
        FileOutputStream(imageFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }

        // Add the image to the Media Store
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
        }

        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val mediaScannerConnectionClient = object : MediaScannerConnection.MediaScannerConnectionClient {
            override fun onMediaScannerConnected() {
                // Do nothing
            }

            override fun onScanCompleted(path: String?, uri: Uri?) {
                // Display a toast message indicating the image has been saved
                Toast.makeText(applicationContext, "Image saved to gallery", Toast.LENGTH_SHORT).show()
            }
        }
        MediaScannerConnection.scanFile(
            applicationContext,
            arrayOf(imageFile.absolutePath),
            null,
            mediaScannerConnectionClient
        )
    }


    private fun saveImage() {
        val imageDrawable = imageView.drawable
        if (imageDrawable is BitmapDrawable) {
            val imageBitmap = imageDrawable.bitmap
            // Save the image to the gallery
            saveImageToGallery(imageBitmap)
        }
    }

}