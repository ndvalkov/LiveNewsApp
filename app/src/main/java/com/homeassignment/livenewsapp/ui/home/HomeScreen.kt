package com.homeassignment.livenewsapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.fillMaxSize().background(color = Color.Red)) {
        Text(text = "Home")
    }
}