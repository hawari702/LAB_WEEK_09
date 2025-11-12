package com.example.lab_week_09.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Title di atas TextField
@Composable
fun OnBackgroundTitleText(text: String) {
    TitleText(text = text, color = MaterialTheme.colorScheme.onBackground)
}

@Composable
fun TitleText(text: String, color: Color) {
    Text(text = text, style = MaterialTheme.typography.titleLarge, color = color)
}

// Item list (nama-nama)
@Composable
fun OnBackgroundItemText(text: String, modifier: Modifier = Modifier) {
    ItemText(text = text, color = MaterialTheme.colorScheme.onBackground, modifier = modifier)
}

@Composable
fun ItemText(text: String, color: Color, modifier: Modifier = Modifier) {
    Text(text = text, style = MaterialTheme.typography.bodySmall, color = color, modifier = modifier)
}

// Tombol utama (pil) dengan teks
@Composable
fun PrimaryTextButton(text: String, onClick: () -> Unit) {
    AppButton(text = text, textColor = Color.White, onClick = onClick)
}

@Composable
fun AppButton(text: String, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3D5A98), // biru gelap
            contentColor = textColor
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.labelMedium)
    }
}
