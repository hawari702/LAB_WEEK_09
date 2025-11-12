package com.example.lab_week_09.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OnBackgroundTitleText(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground)
}

@Composable
fun OnBackgroundItemText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground, modifier = modifier)
}

@Composable
fun PrimaryTextButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3A3A3A),   // tombol abu gelap seperti contoh
            contentColor   = Color.White
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.labelMedium)
    }
}
