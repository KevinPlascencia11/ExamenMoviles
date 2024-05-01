package com.example.dm_pract3.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.dm_pract3.data.Plant
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.dm_pract3.data.DataProvider

//Composable que forma la estructura del intent principal para mostrar plantas
@Composable
fun MainScreen(plants: List<Plant>, onPlantClick: (Plant) -> Unit = {}) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var userGarden by remember { mutableStateOf(listOf<Plant>()) }
    val dataProvider = DataProvider()

    //Se verifica si el jardín del usuario está vacío
    val gardenIsEmpty = userGarden.isEmpty()

    Column {
        //Encabezado fijo
        Text(
            text = "Sunflower",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

        //Navegación entre la lista de plantas y mi jardín
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color.Black
                )
            }
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 }
            ) {
                Text(
                    "Plant List",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (selectedTabIndex == 0) Color.Black else Color.Gray
                    )
                )
            }
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 }
            ) {
                Text(
                    "My Garden",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (selectedTabIndex == 1) Color.Black else Color.Gray
                    )
                )
            }
        }

        //Contenido dinámico dependiendo de la pestaña seleccionada
        when (selectedTabIndex) {
            0 -> {
                //Contenido de la sección "Plant List"
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        items(plants) { plant ->
                            PlantItem(plant = plant, onPlantClick = {
                                onPlantClick(plant)
                            })
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            1 -> {
                //Contenido de la sección "My Garden"
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val context = LocalContext.current
                    val garden = dataProvider.loadGarden(context)

                    if (garden.isEmpty()) {
                        Text(
                            text = "Your garden is empty",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 100.dp)
                        )
                        Button(
                            onClick = {
                                selectedTabIndex = 0
                            }
                        ) {
                            Text(text = "Add Plant")
                        }
                    }
                    else {
                        //Mostrar las plantas en el jardín del usuario
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            verticalItemSpacing = 4.dp,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            content = {
                                items(garden) { plant ->
                                    PlantItemGarden(plant = plant, onPlantClick = { onPlantClick(plant) })
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                        //Botón para limpiar el jardín
                        FloatingActionButton(
                            onClick = {
                                dataProvider.clearGarden(context)
                                selectedTabIndex = 0
                            },
                            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Clear Garden"
                            )
                        }
                    }
                }
            }
        }
    }
}

//Composable para las plantas de Plant List
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantItem(plant: Plant, onPlantClick: (Plant) -> Unit = {}) {
    Card(
        onClick = { onPlantClick(plant) },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD3D3D3),
        ),
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            //Imagen de la planta en la parte superior
            val painter = rememberImagePainter(data = plant.imageUrl)
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            )

            //Espacio entre la imagen y el nombre
            Spacer(modifier = Modifier.height(12.dp))

            //Nombre de la planta en la parte inferior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}

//Composable para las plantas de My Garden
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantItemGarden(plant: Plant, onPlantClick: (Plant) -> Unit = {}) {
    Card(
        onClick = { onPlantClick(plant) },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD3D3D3),
        ),
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.7f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            //Imagen de la planta
            val painter = rememberImagePainter(data = plant.imageUrl)
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            //Nombre de la planta
            Text(
                text = plant.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            //Intervalo de riego
            Text(
                text = "Watering Interval: ${plant.wateringInterval}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}