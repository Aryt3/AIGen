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
import android.util.Log
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

        // importing objects from UI
        imageView = findViewById(R.id.imageView)
        responseTextView = findViewById(R.id.responseTextView)
        requestButton = findViewById(R.id.requestButton)
        promptEditText = findViewById(R.id.promptEditText)
        downloadButton = findViewById(R.id.downloadButton)
        valueSeekBar = findViewById(R.id.valueSeekBar)
        valueWidth = findViewById(R.id.width)
        valueHeight = findViewById(R.id.height)


        // Button for Image Generation Post Request
        requestButton.setOnClickListener {
            sendRequest()
        }

        // Button to download a displayed Picture
        downloadButton.setOnClickListener {
            saveImage()
        }

        // sendGetRequest()
        // changeOptions()
    }

    private fun sendRequest() {
        val prompt = promptEditText.text.toString()
        val seekBarValue = valueSeekBar.progress + 1
        val steps = seekBarValue * 6
        var width: Int = valueWidth.text.toString().toIntOrNull() ?: 0
        var height: Int = valueHeight.text.toString().toIntOrNull() ?: 0

        // Checking if inputs are usable otherwise reassigning them
        if (width <= 0) {
            width = 512
        }
        if (height <= 0) {
            height = 512
        }

        // Displayed Prompt from Request
        val promptTextView: TextView = findViewById(R.id.promptTextView)
        promptTextView.text = "Prompt:\n$prompt"
        promptTextView.setTypeface(null, Typeface.BOLD)

        // Launch Globscope to launch a request
        GlobalScope.launch {
            val imageBytes = ApiService.sendPostRequestImg(prompt, steps, width, height)
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

    // Function to save picture to Local File System
    private fun saveImageToGallery(bitmap: Bitmap) {
        // Generating unique name with time
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

    // Function to save image to external Storage (Phones Gallery to be viewed instantly)
    private fun saveImage() {
        val imageDrawable = imageView.drawable
        if (imageDrawable is BitmapDrawable) {
            val imageBitmap = imageDrawable.bitmap
            // Save the image to the gallery
            saveImageToGallery(imageBitmap)
        }
    }

    // Function to get all available models
    private fun sendGetRequest() {
        GlobalScope.launch {
            val response = ApiService.sendGetRequest()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    // Handle the response here
                    var models = response
                }
                if (response != null) {
                    Log.d("main",response)
                }
            }
        }
    }

    // Function to change used model (permanent)
    private fun changeOptions() {
        GlobalScope.launch {
            ApiService.sendPostRequestOptions("dreamer")
        }
    }
}