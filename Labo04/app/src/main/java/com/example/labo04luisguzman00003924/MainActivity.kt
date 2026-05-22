package com.example.labo04luisguzman00003924

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.labo04luisguzman00003924.ui.theme.Labo04LuisGuzman00003924Theme
import com.example.labo04luisguzman00003924.Navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labo04LuisGuzman00003924Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    AppNavigation(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}