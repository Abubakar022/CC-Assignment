package com.example.compilerconstruction.classes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compilerconstruction.ui.theme.GreenColor
import com.example.compilerconstruction.ui.theme.RedColor

@Composable
public fun DFAVisualizer() {
    var input by remember { mutableStateOf(TextFieldValue("")) }
    var currentState by remember { mutableStateOf(State.S00) }
    var result by remember { mutableStateOf("") }
    var highlightState by remember { mutableStateOf(State.S00) }

    // DFA transition logic
    fun transition(state: State, char: Char): State {
        return when (state) {
            State.S00 -> if (char == 'a') State.S10 else State.S01
            State.S01 -> if (char == 'a') State.S11 else State.S00
            State.S10 -> if (char == 'a') State.S00 else State.S11
            State.S11 -> if (char == 'a') State.S01 else State.S10
        }
    }

    fun simulateDFA(input: String) {
        var state = State.S00
        for (char in input) {
            state = transition(state, char)
            highlightState = state
            Thread.sleep(500)  // Simulate DFA step-by-step with a delay
        }
        currentState = state
        result = if (state == State.S00) "Accepted" else "Rejected"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter input string") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { simulateDFA(input.text) }) {
            Text("Simulate DFA")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Result: $result", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun DFAStateView(state: State, highlight: Boolean) {
    val backgroundColor = if (highlight) GreenColor else RedColor

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(color = backgroundColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Proper usage of Text with TextStyle
            Text(
                text = state.name,                // state.name is a String (enum's name)
                style = androidx.compose.ui.text.TextStyle(

                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Proper usage of Text with TextStyle for the Accept/Reject message
        Text(
            text = if (state == State.S00) "Accept" else "Reject",
            style = androidx.compose.ui.text.TextStyle(

                fontSize = 14.sp
            )
        )
    }
}

@Composable
fun Box(modifier: Any, contentAlignment: Any, content: @Composable () -> Unit) {

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DFAVisualizer()
}