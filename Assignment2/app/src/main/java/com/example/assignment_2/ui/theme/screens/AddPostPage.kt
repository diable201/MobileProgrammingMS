package com.example.assignment_2.ui.theme.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignment_2.data.MockData.currentUser
import com.example.assignment_2.navigation.Screen
import com.example.assignment_2.viewmodel.MainViewModel

@Composable
fun AddPostPage(navController: NavController, mainViewModel: MainViewModel) {
    val context = LocalContext.current
    var caption by remember { mutableStateOf("") }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Image Picker Launcher
    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                Log.d("AddPostPage", "Image URI: $uri")
                val bitmap = loadBitmapFromUri(context, it)
                if (bitmap != null) {
                    imageBitmap = resizeBitmap(bitmap, maxSize = 1024)
                    Log.d("AddPostPage", "Bitmap loaded successfully")
                } else {
                    Log.d("AddPostPage", "Failed to load bitmap")
                }
            } ?: Log.d("AddPostPage", "No image selected")
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Posting as ${currentUser.username}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = rememberAsyncImagePainter(currentUser.profileImageUrl),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.Gray)
                .clickable {
                    pickImageLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap!!.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Select Image",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = "Tap to select an image",
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        TextField(
            value = caption,
            onValueChange = { caption = it },
            placeholder = { Text("Write a caption...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                imageBitmap?.let { bitmap ->
                    mainViewModel.addPost(bitmap, caption)
                    imageBitmap = null
                    caption = ""
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            },
            enabled = imageBitmap != null && caption.isNotBlank(),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Post")
        }
    }
}

fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        Log.d("AddPostPage", "Source: $source. Uri: $uri")
        ImageDecoder.decodeBitmap(source)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun resizeBitmap(bitmap: Bitmap, maxSize: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val aspectRatio = width.toFloat() / height.toFloat()
    return if (width > height) {
        val scaledHeight = (maxSize / aspectRatio).toInt()
        Bitmap.createScaledBitmap(bitmap, maxSize, scaledHeight, true)
    } else {
        val scaledWidth = (maxSize * aspectRatio).toInt()
        Bitmap.createScaledBitmap(bitmap, scaledWidth, maxSize, true)
    }
}
