package com.example.hackaton2025.wishlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.hackaton2025.R

@Composable
fun AddItemDialog(onAdd: (WishlistItem) -> Unit, onCancel: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            Button(
                onClick = {
                    val parsedPrice = price.toFloatOrNull() ?: 0f
                    if (name.isNotBlank() && parsedPrice > 0f) {
                        onAdd(WishlistItem(name, parsedPrice, R.drawable.ic_launcher_foreground,prioriry = 999))
                    }
                }
            ) {
                Text("Adaugă")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("Renunță")
            }
        },
        title = { Text("Adaugă un obiect") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nume obiect") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Preț (RON)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}
