package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    var input by remember { mutableStateOf("") }
    val names = remember { mutableStateListOf("Tanu", "Tina", "Tono") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Spacer(Modifier.height(12.dp))
                PrimaryTextButton(
                    text = stringResource(id = R.string.button_click),
                    onClick = {
                        val name = input.trim()
                        if (name.isNotEmpty()) {
                            names.add(name)
                            input = ""
                        }
                    }
                )
                Spacer(Modifier.height(12.dp))
            }
        }

        items(names) { name ->
            OnBackgroundItemText(
                text = name,
                // jarak antar item
                // (pakai Spacer juga bisa; di sini cukup padding saja)
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Preview(showBackground = true, name = "PreviewHome")
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Home()
        }
    }
}
