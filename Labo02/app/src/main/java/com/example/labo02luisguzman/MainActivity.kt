package com.example.labo02luisguzman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.labo02luisguzman.ui.theme.Labo02LuisGuzmanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labo02LuisGuzmanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NameListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NameListScreen(modifier: Modifier = Modifier) {
    //
    var nombre by remember { mutableStateOf("") }

    // Almacenamiento en la lista
    val listaNombres = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // TextBox para el ingreso del nombre
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Coloca un nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // btn para almacenar
        Button(
            onClick = {
                // Codigo para agregar con VALIDACIONES ahora xd :)
                if (nombre.isNotBlank()) {
                    listaNombres.add(nombre)
                    nombre = ""
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // titulo de la lista y btn limpiar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Nombres y \nposicion en la lista",
                modifier = Modifier.weight(1f)
            )

            // Borrador de nombres Funcion para BORRAR
            Button(
                onClick = {
                    listaNombres.clear()
                }
            ) {
                Text("Borrar todoo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Lista y bordesito de color
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 4.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            // LazyColumn para que se puedo hacer scrool

            LazyColumn {
                itemsIndexed(listaNombres) { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item)
                        Text(text = (index + 1).toString()) // sumamos 1 para que no empiece en 0
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Labo02LuisGuzmanTheme {
        NameListScreen()
    }
}