package com.example.dm_pract3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dm_pract3.Screens.DetailScreen
import com.example.dm_pract3.data.Plant
import com.example.dm_pract3.ui.theme.DM_pract3Theme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DM_pract3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val plant = intent.getParcelableExtra<Plant>("PLANT")
                    Log.d("DetailActivity", "Plant received in DetailActivity: $plant")
                    plant?.let {
                        DetailScreen(plant = it, onBackClick = { finish() }, onShareClick = { sharePlant(it.imageUrl) })
                    }
                }
            }
        }
    }

    //Compartir URL
    private fun sharePlant(imageUrl: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, imageUrl)
        }
        startActivity(Intent.createChooser(intent, "Compartir con"))
    }
}
