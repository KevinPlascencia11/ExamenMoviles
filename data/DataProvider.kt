package com.example.dm_pract3.data

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.FileNotFoundException

//Clase que ayuda a importar los datos
class DataProvider {
    var plants: List<Plant> = emptyList()

    fun loadData(context: Context, fileName: String) {
        val contactsJsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Array<Plant>::class.java)
        plants = adapter.fromJson(contactsJsonString)?.toList() ?: emptyList()
    }

    fun saveGarden(context: Context, garden: List<Plant>) {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Array<Plant>::class.java)
        val gardenJson = adapter.toJson(garden.toTypedArray())
        context.openFileOutput("garden.json", Context.MODE_PRIVATE).use {
            it.write(gardenJson.toByteArray())
        }
    }

    fun loadGarden(context: Context): List<Plant> {
        return try {
            val gardenJson = context.openFileInput("garden.json").bufferedReader().use {
                it.readText()
            }
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter = moshi.adapter(Array<Plant>::class.java)
            adapter.fromJson(gardenJson)?.toList() ?: emptyList()
        } catch (e: FileNotFoundException) {
            emptyList()
        }
    }

    fun clearGarden(context: Context) {
        val emptyGarden = emptyList<Plant>()
        saveGarden(context, emptyGarden)
    }
}
