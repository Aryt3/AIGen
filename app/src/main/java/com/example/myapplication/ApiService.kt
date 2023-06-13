package com.example.myapplication

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

object ApiService {

    // Declare Base URL to connect to
    private const val BASE_URL = "http://192.168.1.55:7861/sdapi/v1/"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

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
            .url(BASE_URL + "txt2img")
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