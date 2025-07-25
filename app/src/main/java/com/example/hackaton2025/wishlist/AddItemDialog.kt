package com.example.hackaton2025.wishlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.hackaton2025.Constants
import com.example.hackaton2025.R
import com.example.hackaton2025.data.fakeData.WishlistData
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(onAdd: (WishlistData) -> Unit, onCancel: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    var isNameFieldFocused by remember { mutableStateOf(false) }
    val suggestions = wishilistFakeItems
    var filteredSuggestions by remember { mutableStateOf(suggestions) }
    var expandedDropdown by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    LaunchedEffect(isNameFieldFocused, filteredSuggestions.isNotEmpty()) {
        if (!isNameFieldFocused) {
            delay(200L)
            expandedDropdown = false
        } else {
            expandedDropdown = filteredSuggestions.isNotEmpty()
        }
    }

    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            Button(
                onClick = {
                    val parsedPrice = price.toFloatOrNull() ?: 0f
                    if (name.isNotBlank() && parsedPrice > 0f) {
                        onAdd(WishlistData(name, parsedPrice, R.drawable.ic_launcher_foreground,priority = 999))
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        },
        title = { Text("Add wishlist item") },
        text = {
            val focusRequester = remember { FocusRequester() }

            Column {
                ExposedDropdownMenuBox(
                    expanded = expandedDropdown && isNameFieldFocused && filteredSuggestions.isNotEmpty(),
                    onExpandedChange = {
                        // We control expansion based on focus and suggestions
                        // expandedDropdown = !expandedDropdown // Default behavior, but we want more control
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            filteredSuggestions = suggestions.filter { suggestion ->
                                suggestion.contains(it, ignoreCase = true)
                            }
                            // Show dropdown if there are suggestions and field has focus
                            expandedDropdown = filteredSuggestions.isNotEmpty() && isNameFieldFocused
                        },
                        label = { Text("Wishlist item name") },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            }
                            .onFocusChanged { focusState ->
                                isNameFieldFocused = focusState.isFocused
                                if (focusState.isFocused) {
                                    expandedDropdown = filteredSuggestions.isNotEmpty()
                                } else {
                                    // Consider a small delay before collapsing if needed
                                    // to allow clicking on dropdown items
                                    expandedDropdown = false
                                }
                            },
                        trailingIcon = {
                            val closeIcon = @Composable {
                                IconButton(onClick = {
                                    name = ""
                                }) {
                                    Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                                }
                            }

                            if (isNameFieldFocused) {
                                if (filteredSuggestions.isNotEmpty()) {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown)
                                }
                                else
                                    closeIcon()
                            }
                            else if (name.isNotEmpty()) {
                                closeIcon()
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences)
                    )

                    ExposedDropdownMenu(
                        expanded = expandedDropdown && isNameFieldFocused && filteredSuggestions.isNotEmpty(),
                        onDismissRequest = {
                            expandedDropdown = false
                        },
                        modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                    ) {
                        filteredSuggestions.forEach { suggestion ->
                            DropdownMenuItem(
                                text = { Text(suggestion) },
                                onClick = {
                                    name = suggestion
                                    expandedDropdown = false
                                    isNameFieldFocused = false // Optionally remove focus
                                    // You might want to move focus to the price field here
                                }
                            )
                        }
                        if (filteredSuggestions.isEmpty() && name.isNotEmpty()) {
                            DropdownMenuItem(
                                text = { Text("No suggestions found") },
                                onClick = { expandedDropdown = false },
                                enabled = false
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Amount (${Constants.CURRENCY_NAME})") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}
