package com.example.lab_week_09

import android.os.Bundle
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab_week_09.ui.theme.*

// Moshi
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

// ==== DATA ====
data class Student(var name: String)

// ==== ACTIVITY ====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Pakai theme project-mu (sudah dark di LAB_WEEK_09Theme)
            LAB_WEEK_09Theme {
                // >>> Penting: cat background root biar tidak putih
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val navController = rememberNavController()
                    App(navController = navController)
                }
            }
        }
    }
}

// ==== NAV HOST ====
@Composable
fun App(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Home { jsonString ->
                val safe = Uri.encode(jsonString) // aman di route
                navController.navigate("resultContent/?listData=$safe")
            }
        }
        composable(
            "resultContent/?listData={listData}",
            arguments = listOf(navArgument("listData") { type = NavType.StringType })
        ) { backStackEntry ->
            val listJson = backStackEntry.arguments?.getString("listData").orEmpty()
            ResultContent(listJson)
        }
    }
}

// ==== HOME ====
@Composable
fun Home(
    navigateFromHomeToResult: (String) -> Unit
) {
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"), Student("Tina"), Student("Tono")
        )
    }
    var inputField by remember { mutableStateOf(Student("")) }

    // Moshi adapter untuk List<Student>
    val moshi = remember { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
    val listType = remember { Types.newParameterizedType(List::class.java, Student::class.java) }
    val adapter = remember { moshi.adapter<List<Student>>(listType) }

    HomeContent(
        listData = listData,
        inputField = inputField,
        onInputValueChange = { newText -> inputField = inputField.copy(name = newText) },
        onButtonClick = {
            val trimmed = inputField.name.trim()
            if (trimmed.isNotEmpty()) {
                listData.add(Student(trimmed))
                inputField = Student("")
            }
        },
        navigateFromHomeToResult = {
            // >>> Kirim sebagai JSON (BONUS)
            val json = adapter.toJson(listData.toList())
            navigateFromHomeToResult(json)
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    navigateFromHomeToResult: () -> Unit
) {
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
                    value = inputField.name,
                    onValueChange = { onInputValueChange(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = TextFieldDefaults.colors(
                        // kotak input gelap (UI tetap)
                        focusedContainerColor   = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor  = MaterialTheme.colorScheme.surface,

                        // teks putih
                        focusedTextColor   = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledTextColor  = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

                        // kursor & underline
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        focusedIndicatorColor   = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color(0xFF3C3C3C)
                    )
                )

                Spacer(Modifier.height(12.dp))

                Row {
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_click),
                        onClick = onButtonClick
                    )
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_navigate),
                        onClick = navigateFromHomeToResult
                    )
                }

                Spacer(Modifier.height(8.dp))
            }
        }

        items(listData) { item ->
            OnBackgroundItemText(text = item.name)
            Spacer(Modifier.height(6.dp))
        }
    }
}

// ==== RESULT ====
// Menerima JSON, parse ke List<Student>, dan tampilkan dengan LazyColumn.
// UI tetap dark + tidak nabrak status bar.
@Composable
fun ResultContent(listDataJson: String) {
    // Moshi adapter
    val moshi = remember { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
    val listType = remember { Types.newParameterizedType(List::class.java, Student::class.java) }
    val adapter = remember { moshi.adapter<List<Student>>(listType) }

    val list: List<Student> = remember(listDataJson) {
        runCatching { adapter.fromJson(listDataJson) }.getOrNull().orEmpty()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding() // biar tidak nabrak jam/status bar
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(list) { item ->
            OnBackgroundItemText(text = item.name)
            Spacer(Modifier.height(6.dp))
        }
    }
}

// ==== PREVIEW ====
@Preview(showBackground = false)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val dummyNav = rememberNavController()
            App(navController = dummyNav)
        }
    }
}
