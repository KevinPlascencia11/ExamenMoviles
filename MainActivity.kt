package com.example.dm_pract3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.dm_pract3.Screens.MainScreen
import com.example.dm_pract3.data.DataProvider
import com.example.dm_pract3.data.Plant
import com.example.dm_pract3.ui.theme.DM_pract3Theme

class MainActivity : ComponentActivity() {
    private val dataProvider = DataProvider()
    private val state: MutableState<List<Plant>> = mutableStateOf(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DM_pract3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    dataProvider.loadData(this, "plants.json")
                    state.value = dataProvider.plants
                    MainScreen(plants = state.value, onPlantClick = ::goToDetailScreen)
                }
            }
        }
    }

    //Iniciar DetailActivity y pasar el objeto Plant a través de un Intent
    private fun goToDetailScreen(plant: Plant) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("PLANT", plant)
        startActivity(intent)
        Log.d("DetailActivity", "Plant passed to DetailActivity: $plant")
    }
}

