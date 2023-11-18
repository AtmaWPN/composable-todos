package com.example.testapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapplication.ui.theme.TestApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Greeting("Atma", modifier = Modifier.padding(24.dp))
                    TodoList(label = "Todos For This App")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(color = Color.Blue) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}

@Composable
fun TodoList(label: String, modifier: Modifier = Modifier) {
    val checkedState = remember { mutableStateOf(false) }
    Column {
        Text (
            text = label,
            modifier = modifier
        )
        TodoItem(name = "Populate from local database")
        TodoItem(name = "Center Todo text vertically", done = true)
        TodoItem(name = "Sketch UI layout")

    }
}

@Composable
fun TodoItem(name: String, modifier: Modifier = Modifier, done: Boolean = false) {
    val checkedState = remember { mutableStateOf(done) }
    Row (verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Red,
                checkedColor = Color.Green,
                checkmarkColor = Color.Yellow
            ))
        Text (
            text = name,
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestApplicationTheme {
        Column {
            Greeting("Android")
            TodoList(label = "Todos For This App")
        }
    }
}