package com.example.finalproject.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.ui.theme.navigation.BottomNavigationBar
import com.example.finalproject.ui.theme.navigation.Screen
import com.example.finalproject.viewmodel.CartViewModel
import com.example.finalproject.viewmodel.CheckoutViewModel
import com.example.finalproject.viewmodel.ShopViewModel

@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    checkoutViewModel: CheckoutViewModel,
    cartViewModel: CartViewModel,
    shopViewModel: ShopViewModel
) {
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("Credit Card") }
    var errorMessage by remember { mutableStateOf("") }

    val cartItems by cartViewModel.cartItems.collectAsState()
    val products by shopViewModel.products.collectAsState()

    val totalAmount by derivedStateOf {
        cartItems.sumOf { cartItem ->
            val product = products.find { it.productId == cartItem.productId }
            product?.price?.times(cartItem.quantity) ?: 0.0
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Checkout") })
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Shipping Address", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = street,
                onValueChange = { street = it },
                label = { Text("Street") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = state,
                onValueChange = { state = it },
                label = { Text("State") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = zipCode,
                onValueChange = { zipCode = it },
                label = { Text("Zip Code") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Payment Method", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenuBox(selectedItem = paymentMethod, onItemSelected = { paymentMethod = it })
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (street.isBlank() || city.isBlank() || state.isBlank() || zipCode.isBlank()) {
                        errorMessage = "Please fill all address fields."
                    } else {
                        checkoutViewModel.processCheckout(userId = 1,
                            totalAmount = totalAmount,
                            onSuccess = {
                                cartViewModel.clearCart()
                                navController.navigate(Screen.Profile.route) {
                                    popUpTo(Screen.Checkout.route) { inclusive = true }
                                }
                            },
                            onError = { error ->
                                errorMessage = error
                            })
                    }
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pay Now - $${"%.2f".format(totalAmount)}")
            }
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    })
}

@Composable
fun DropdownMenuBox(selectedItem: String, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Credit Card", "Debit Card", "PayPal", "Cash on Delivery")

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(value = selectedItem,
            onValueChange = {},
            label = { Text("Select Payment Method") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.Add, contentDescription = "Dropdown")
                }
            })
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { label ->
                DropdownMenuItem(text = { Text(label) }, onClick = {
                    onItemSelected(label)
                    expanded = false
                })
            }
        }
    }
}