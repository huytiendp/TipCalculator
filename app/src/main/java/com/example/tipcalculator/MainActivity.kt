package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                TipCalculatorScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorScreen() {
    var costInput by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableStateOf(20) }
    var roundUp by remember { mutableStateOf(false) }
    var tipAmount by remember { mutableStateOf("") }

    fun calculateTip() {
        val cost = costInput.toDoubleOrNull() ?: 0.0
        var tip = cost * tipPercentage / 100
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        tipAmount = "$%.2f".format(tip)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tip Time") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            TextField(
                value = costInput,
                onValueChange = { costInput = it },
                label = { Text("Cost of Service") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Text("How was the service?")
            RadioGroup(
                selectedOption = tipPercentage,
                onOptionSelected = { tipPercentage = it }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Round up tip?")
                Switch(
                    checked = roundUp,
                    onCheckedChange = { roundUp = it }
                )
            }
            Button(
                onClick = { calculateTip() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CALCULATE")
            }
            Text(
                text = "Tip Amount: $tipAmount",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun RadioGroup(selectedOption: Int, onOptionSelected: (Int) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        RadioButtonWithLabel("Amazing (20%)", 20, selectedOption, onOptionSelected)
        RadioButtonWithLabel("Good (18%)", 18, selectedOption, onOptionSelected)
        RadioButtonWithLabel("Okay (15%)", 15, selectedOption, onOptionSelected)
    }
}

@Composable
fun RadioButtonWithLabel(
    text: String,
    value: Int,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(
            selected = value == selectedOption,
            onClick = { onOptionSelected(value) }
        )
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}
