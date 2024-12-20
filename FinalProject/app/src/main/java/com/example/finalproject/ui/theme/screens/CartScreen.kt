package com.example.finalproject.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.finalproject.model.CartItem
import com.example.finalproject.model.Product
import com.example.finalproject.ui.theme.navigation.BottomNavigationBar
import com.example.finalproject.ui.theme.navigation.Screen
import com.example.finalproject.utils.toCurrency
import com.example.finalproject.viewmodel.CartViewModel
import com.example.finalproject.viewmodel.ShopViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController, cartViewModel: CartViewModel, shopViewModel: ShopViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val products by shopViewModel.products.collectAsState()

    val totalAmount = cartItems.sumOf { cartItem ->
        val product = products.find { it.productId == cartItem.productId }
        product?.price?.times(cartItem.quantity) ?: 0.0
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Your Cart") })
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text("Your cart is empty")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { cartItem ->
                        val product = products.find { it.productId == cartItem.productId }
                        product?.let {
                            CartItemRow(cartItem = cartItem,
                                product = it,
                                onRemove = { cartViewModel.removeFromCart(cartItem) },
                                onQuantityChange = { newQty ->
                                    cartViewModel.updateQuantity(cartItem, newQty)
                                })
                        } ?: run {
                            Text("Product not found")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total: ${totalAmount.toCurrency()}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { navController.navigate(Screen.Checkout.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Proceed to Checkout")
                }
            }
        }
    })
}

@Composable
fun CartItemRow(
    cartItem: CartItem, product: Product, onRemove: () -> Unit, onQuantityChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${"%.2f".format(product.price)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        if (cartItem.quantity > 1) onQuantityChange(cartItem.quantity - 1)
                    }) {
                        Icon(Icons.Filled.Remove, contentDescription = "Decrease Quantity")
                    }
                    Text(
                        text = cartItem.quantity.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IconButton(onClick = { onQuantityChange(cartItem.quantity + 1) }) {
                        Icon(Icons.Filled.Add, contentDescription = "Increase Quantity")
                    }
                }
                IconButton(onClick = { onRemove() }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Remove Item")
                }
            }
        }
    }
}