package com.example.myapplication

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

object ApiService {
    private const val BASE_URL = "http://192.168.1.55:7861/sdapi/v1/txt2img"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    fun sendPostRequest(prompt: String, steps: Int, width: Int, height: Int): ByteArray? {
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
            .url(BASE_URL)
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