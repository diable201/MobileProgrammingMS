package com.example.assignment_4.ui.theme.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignment_4.ui.theme.LightGray
import com.example.assignment_4.ui.theme.NetflixRed
import com.example.assignment_4.ui.theme.White
import com.example.assignment_4.ui.theme.components.FavoriteAnimeItem
import com.example.assignment_4.viewmodel.AnimeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController, animeViewModel: AnimeViewModel
) {
    val username = animeViewModel.getCurrentUsername()
    val favoriteAnimeList by animeViewModel.favoriteAnimeList.collectAsState()
    val profileImageUri by animeViewModel.profileImageUri.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        animeViewModel.updateProfileImage(uri.toString())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!profileImageUri.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(120.dp)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Default Profile Image",
                    tint = LightGray,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }

            Text(
                text = username,
                style = MaterialTheme.typography.headlineSmall,
                color = White,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = NetflixRed, contentColor = White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Upload Profile Image")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Your Favorites", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            if (favoriteAnimeList.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxHeight(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(favoriteAnimeList.size) { index ->
                        FavoriteAnimeItem(favoriteAnimeList[index],
                            navController = navController,
                            onRemoveClick = { animeViewModel.removeFavorite(it) })
                    }
                }
            } else {
                Text("You have no favorite anime yet.", color = LightGray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    animeViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = NetflixRed, contentColor = White
                ), modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)
            ) {
                Text("Logout")
            }
        }
    }
}
