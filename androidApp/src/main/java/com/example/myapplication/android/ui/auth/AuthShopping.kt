package com.example.myapplication.android.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    var testKey by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.getTestKey { key ->
                testKey = key
            }
        }) {
            Text("Получить тестовый ключ")
        }

        Text(text = "Ключ: $testKey", modifier = Modifier.padding(top = 16.dp))

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (testKey.isNotBlank()) {
                    navController.navigate("shopping")
                }
            },
            enabled = testKey.isNotBlank()
        ) {
            Text("Перейти к покупкам")
        }
    }
}
