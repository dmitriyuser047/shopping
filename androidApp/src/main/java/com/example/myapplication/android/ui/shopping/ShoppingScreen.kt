package com.example.myapplication.android.ui.shopping

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.entity.ShoppingList
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListsScreen(viewModel: ShoppingListsViewModel = hiltViewModel()) {
    val shopLists by viewModel.shopLists.collectAsState(initial = emptyList())
    val showListDialog = remember { mutableStateOf(false) }
    val showItemDialog = remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Lists") },
                actions = {
                    IconButton(onClick = { viewModel.loadShoppingLists() }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showListDialog.value = true }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Shopping List")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (shopLists.isEmpty()) {
                Text("No shopping lists available.", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(shopLists) { list ->
                        ShoppingListItem(list = list, viewModel = viewModel, onAddItem = { listId ->
                            showItemDialog.value = listId
                        })
                    }
                }
            }
        }
    }

    if (showListDialog.value) {
        NewShoppingListDialog(
            onDismiss = { showListDialog.value = false },
            onCreate = { name ->
                if (name.isNotEmpty()) {
                    viewModel.createShoppingList(name)
                }
                showListDialog.value = false
            }
        )
    }

    showItemDialog.value?.let { listId ->
        NewItemDialog(
            onDismiss = { showItemDialog.value = null },
            onAdd = { name, quantity ->
                viewModel.addToShoppingList(listId, name, quantity)
                showItemDialog.value = null
            }
        )
    }
}

@Composable
fun ShoppingListItem(list: ShoppingList, viewModel: ShoppingListsViewModel, onAddItem: (Int) -> Unit) {
    val shoppingItems by viewModel.shoppingItems.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { viewModel.loadShoppingListItems(list.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = list.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Created: ${list.created}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(6.dp))

            val items = shoppingItems[list.id] ?: emptyList()

            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(0.dp, 300.dp)
            ) {
                items(items) { item ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                textDecoration = if (item.isCrossed) TextDecoration.LineThrough else TextDecoration.None
                            ),
                            modifier = Modifier.weight(3f)
                        )
                        IconButton(onClick = { viewModel.crossOffItem(item.id) }) {
                            Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "Cross off")
                        }
                        IconButton(onClick = { viewModel.removeFromShoppingList(list.id, item.id) }) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete item")
                        }
                    }
                }
            }

            Button(onClick = { onAddItem(list.id) }) {
                Text("Добавить продукт")
            }

            Button(onClick = { viewModel.removeShoppingList(list.id) }) {
                Text("Удалить список")
            }
        }
    }
}

@Composable
fun NewShoppingListDialog(onDismiss: () -> Unit, onCreate: (String) -> Unit) {
    val newListName = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Shopping List") },
        text = {
            OutlinedTextField(
                value = newListName.value,
                onValueChange = { newListName.value = it },
                label = { Text("Enter list name") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onCreate(newListName.value)
                }
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun NewItemDialog(onDismiss: () -> Unit, onAdd: (String, Int) -> Unit) {
    val itemName = remember { mutableStateOf("") }
    val itemQuantity = remember { mutableStateOf("1") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = itemName.value,
                    onValueChange = { itemName.value = it },
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = itemQuantity.value,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            itemQuantity.value = it
                        }
                    },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val quantity = itemQuantity.value.toIntOrNull() ?: 1
                    if (itemName.value.isNotEmpty()) {
                        onAdd(itemName.value, quantity)
                        onDismiss()
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}