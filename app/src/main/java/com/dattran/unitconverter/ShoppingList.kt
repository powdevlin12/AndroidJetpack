package com.dattran.unitconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.collections.plus

@Composable
fun ShopingItemUI(
    item: ShoppingItem,
    onDelete: () -> Unit,
    onSetItemEdit: (item: ShoppingItem) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = item.name)
        Text(text = "Quantity ${item.quantity}", style = TextStyle(fontWeight = FontWeight.Bold))
        Column {
            IconButton(onClick = { onSetItemEdit(item) }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
        Column {
            IconButton(onClick = { onDelete() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}

@Composable
fun renderAlertAddOredit(
    handleChangeIsShowDialog: (status: Boolean) -> Unit,
    handleAddItem: (String, String, Int?) -> Unit,
    itemEdit: ShoppingItem?
) {
    var itemName by remember { mutableStateOf(itemEdit?.let { it.name } ?: run { "" }) }
    var itemQuantity by remember { mutableStateOf(itemEdit?.let { it.quantity } ?: run { "" }) }

    AlertDialog(
        onDismissRequest = { handleChangeIsShowDialog(false) }, modifier = Modifier.padding(16.dp),
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { it ->
                        itemName = it
                    },
                    label = { Text("Name") },
                    placeholder = { Text("Name") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = itemQuantity,
                    onValueChange = { it ->
                        itemQuantity = it
                    },
                    label = { Text("Quantity") },
                )
            }
        },
        title = {
            Text(if (itemEdit != null) "Edit Item" else "Add new item shopping")
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                            handleAddItem(
                                itemName,
                                itemQuantity,
                                itemEdit?.id?.let { it } ?: run { -1 });
                            itemName = ""
                            itemQuantity = ""
                            handleChangeIsShowDialog(false)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(if (itemEdit != null) "Update" else "Add")
                }
                Button(
                    onClick = {
                        handleChangeIsShowDialog(false)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Cancel")
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Shopping(viewModal: ShoppingListViewModel = viewModel()) {
    val defaultItem = ShoppingItem(id = 0, name = "", quantity = "0")
    var isShowDialog by remember { mutableStateOf(false) }
    var isShowDialogEdit by remember { mutableStateOf(false) }
    var itemEdit by remember { mutableStateOf<ShoppingItem>(defaultItem) }

    var search by remember { mutableStateOf("") }
    var listSearch by remember { mutableStateOf<ShoppingItem?>(defaultItem) }

    val shoppingList by viewModal.shoppingList.collectAsState() // Sử dụng collectAsState cho StateFlow

    fun addShopingItem(itemName: String, itemQuantity: String, id: Int?) {
        if (isShowDialog) {
            viewModal.addItem(itemName, itemQuantity)
        } else if (isShowDialogEdit && id != null && id != -1) {
            viewModal.updateItem(itemName, itemQuantity, id)
        }
    }

    fun handleChangeIsShowDialog(status: Boolean) {
        if (!status) {
            itemEdit = defaultItem
        }
        isShowDialog = status
    }

    fun handleChangeIsShowDialogEdit(status: Boolean) {
        if (!status) {
            itemEdit = defaultItem
        }
        isShowDialogEdit = status
    }

    fun handleSetItemEdit(iEdit: ShoppingItem) {
        isShowDialogEdit = true
        isShowDialog = false
        itemEdit = iEdit
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("List Shopping", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
        Spacer(modifier = Modifier
            .height(32.dp)
            .fillMaxWidth())
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { it ->
                    search = it
                    listSearch = viewModal.findItem(search)
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                label = { Text("Search") },
                placeholder = { Text("Search") }
            )
        }
        Spacer(modifier = Modifier
            .height(12.dp)
            .fillMaxWidth())
        Text("${listSearch?.name ?: "No data"} ${if (listSearch?.quantity ?: 0 == 0) "" else listSearch?.quantity}")
        Spacer(modifier = Modifier
            .height(32.dp)
            .fillMaxWidth())
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(shoppingList) { item ->
                ShopingItemUI(item, onDelete = {
                    viewModal.deleteItem(item.id)
                }, onSetItemEdit = ::handleSetItemEdit)
            }

        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                onClick = {
                    isShowDialog = !isShowDialog
                },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    }
    if (isShowDialog) {
        renderAlertAddOredit(
            handleChangeIsShowDialog = ::handleChangeIsShowDialog,
            handleAddItem = ::addShopingItem,
            itemEdit = null
        )
    }

    if (isShowDialogEdit) {
        renderAlertAddOredit(
            handleChangeIsShowDialog = ::handleChangeIsShowDialogEdit,
            handleAddItem = ::addShopingItem,
            itemEdit
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true)
@Composable
fun ShoppingPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Shopping()
    }
}