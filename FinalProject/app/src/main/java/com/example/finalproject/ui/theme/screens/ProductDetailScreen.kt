package com.example.finalproject.ui.theme.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.finalproject.model.Review
import com.example.finalproject.ui.theme.navigation.BottomNavigationBar
import com.example.finalproject.ui.theme.navigation.Screen
import com.example.finalproject.viewmodel.CartViewModel
import com.example.finalproject.viewmodel.ReviewViewModel
import com.example.finalproject.viewmodel.ShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int,
    shopViewModel: ShopViewModel,
    cartViewModel: CartViewModel,
    reviewViewModel: ReviewViewModel
) {
    val context = LocalContext.current
    val products by shopViewModel.products.collectAsState()
    val product = products.find { it.productId == productId }

    val reviews by reviewViewModel.reviews.collectAsState()
    val scale by animateFloatAsState(1.2f, label = "Scale")

    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }
    var showReviewForm by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    LaunchedEffect(productId) {
        reviewViewModel.fetchReviews(productId)
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(product?.name ?: "Product Detail") })
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }, floatingActionButton = {
        FloatingActionButton(onClick = { showReviewForm = true }) {
            Icon(Icons.Filled.Star, contentDescription = "Add Review")
        }
    }, content = { padding ->
        if (product != null) {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                item {
                    Image(
                        painter = rememberAsyncImagePainter(product.imageUrl),
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = product.name, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$${"%.2f".format(product.price)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.description, style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            cartViewModel.addToCart(product.productId)
                            navController.navigate(Screen.Cart.route)
                            showSnackbar = true
                        }, modifier = Modifier.scale(scale)
                    ) {
                        Text("Add to Cart")
                    }
                    if (showSnackbar) {
                        Snackbar(
                            action = {
                                TextButton(onClick = { showSnackbar = false }) {
                                    Text("Dismiss")
                                }
                            }
                        ) { Text("${product.name} added to cart") }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Reviews", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(reviews) { review ->
                    ReviewItem(review = review)
                }
            }

            if (showReviewForm) {
                ReviewForm(onSubmit = { r, c ->
                    if (r < 1 || r > 5) {
                        errorMessage = "Rating must be between 1 and 5."
                    } else if (c.isBlank()) {
                        errorMessage = "Comment cannot be empty."
                    } else {
                        val newReview = Review(
                            productId = product.productId, userId = 1, rating = r, comment = c
                        )
                        reviewViewModel.addReview(newReview, onSuccess = {
                            showReviewForm = false
                            errorMessage = ""
                        }, onError = { error ->
                            errorMessage = error
                        })
                    }
                }, onDismiss = { showReviewForm = false })
            }

            if (errorMessage.isNotEmpty()) {
                AlertDialog(onDismissRequest = { errorMessage = "" },
                    title = { Text("Error") },
                    text = { Text(errorMessage) },
                    confirmButton = {
                        TextButton(onClick = { errorMessage = "" }) {
                            Text("OK")
                        }
                    })
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Product not found")
            }
        }
    })
}

@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) { index ->
                    if (index < review.rating) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            Icons.Filled.StarBorder,
                            contentDescription = "Star Border",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.comment, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ReviewForm(onSubmit: (Int, String) -> Unit, onDismiss: () -> Unit) {
    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = { onDismiss() }, title = { Text("Add Review") }, text = {
        Column {
            Text("Rating:")
            Row {
                for (i in 1..5) {
                    IconButton(onClick = { rating = i }) {
                        if (i <= rating) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = "$i Stars",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Icon(
                                Icons.Filled.StarBorder,
                                contentDescription = "$i Stars",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comment") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }, confirmButton = {
        TextButton(onClick = { onSubmit(rating, comment) }) {
            Text("Submit")
        }
    }, dismissButton = {
        TextButton(onClick = { onDismiss() }) {
            Text("Cancel")
        }
    })
}