package com.example.myapplication

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiService {

    // Declare Base URL to connect to
    private const val DEFAULT_BASE_URL = "http://192.168.1.55:7861"

    private var BASE_URL = DEFAULT_BASE_URL

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    // Function to change the Base URL dynamically
    fun setBaseUrl(newBaseUrl: String) {
        BASE_URL = newBaseUrl
    }

    // Function for Image Generation
    fun sendPostRequestImg(prompt: String, steps: Int, width: Int, height: Int): ByteArray? {
        val mediaType = "application/json".toMediaType()
        val requestBody = """
            {
                "prompt": "$prompt",
                "steps": $steps,
                "width": $width,
                "height": $height
            }
        """.trimIndent().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$BASE_URL/sdapi/v1/txt2img")
            .post(requestBody)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body
                    val bytes = responseBody?.bytes()
                    return bytes
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    // Function for getting available Models
    fun sendGetRequest(): String? {
        val getRequest = Request.Builder()
            .url("$BASE_URL/sdapi/v1/sd-models")
            .build()

        try {
            client.newCall(getRequest).execute().use { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body
                    val result = responseBody?.string()
                    return result
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    // Request Function to change options like model used
    fun sendPostRequestOptions(model: String): ByteArray? {
        val mediaType = "application/json".toMediaType()
        val requestBody = """
            {
                "sd_model_checkpoint": "$model"
            }
        """.trimIndent().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$BASE_URL/sdapi/v1/options")
            .post(requestBody)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body
                    val bytes = responseBody?.bytes()
                    return bytes
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}