package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class SettingsActivity : AppCompatActivity() {

    private lateinit var reload: Button
    private lateinit var save: Button
    private lateinit var modelNamesSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        reload = findViewById(R.id.reload)
        save = findViewById(R.id.save)
        modelNamesSpinner = findViewById(R.id.modelNamesSpinner)

        val switchButton2 = findViewById<Button>(R.id.switchButton2)
        switchButton2.setOnClickListener {
            // Switch back to MainActivity
            val intent = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish the current activity
        }

        // Get Info from backend
        reload.setOnClickListener {
            getModels()
        }

        // Forward new options
        save.setOnClickListener {
            val selectedModel = modelNamesSpinner.selectedItem.toString()
            changeModel(selectedModel)
        }
    }

    private fun getModels() {
        GlobalScope.launch {
            val response = ApiService.sendGetRequest()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    try {
                        val jsonArray = JSONArray(response)
                        val modelNames = ArrayList<String>()

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                            val modelName: String = jsonObject.getString("model_name")
                            modelNames.add(modelName)
                        }

                        val adapter = ArrayAdapter(
                            this@SettingsActivity,
                            android.R.layout.simple_spinner_item,
                            modelNames
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        modelNamesSpinner.adapter = adapter

                    } catch (e: JSONException) {
                        Log.e("JSONParsing", "Error parsing JSON response: ${e.message}")
                    }
                }
            }
        }
    }

    private fun changeModel(model: String) {
        GlobalScope.launch{
            ApiService.sendPostRequestOptions(model)
        }
    }
}