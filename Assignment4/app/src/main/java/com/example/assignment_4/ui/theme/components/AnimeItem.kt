package com.example.assignment_4.ui.theme.components

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignment_4.model.Anime
import com.example.assignment_4.ui.theme.Darker
import com.example.assignment_4.ui.theme.LightGray
import com.example.assignment_4.ui.theme.NetflixRed
import com.example.assignment_4.ui.theme.White

@Composable
fun AnimeItem(
    anime: Anime,
    navController: NavController,
    onFavoriteClick: (Anime) -> Unit,
    isFavorite: Boolean
) {
    var favoriteState by remember { mutableStateOf(isFavorite) }

    val iconTint by animateColorAsState(
        targetValue = if (favoriteState) NetflixRed else LightGray, label = ""
    )

    val iconScale by animateFloatAsState(
        targetValue = if (favoriteState) 1.2f else 1f, label = ""
    )

    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .clickable(
                onClick = {
                    Log.v("Nav message", "Anime ID: ${anime.mal_id}")
                    navController.navigate("animeDetail/${anime.mal_id}")
                },
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true, color = NetflixRed)
            ), colors = CardDefaults.cardColors(
            containerColor = Darker,
            contentColor = White,
        ), shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = rememberAsyncImagePainter(anime.images.jpg.image_url),
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = anime.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold, color = White
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        favoriteState = !favoriteState
                        onFavoriteClick(anime)
                    }, modifier = Modifier
                        .size(24.dp)
                        .scale(iconScale)
                ) {
                    Icon(
                        imageVector = if (favoriteState) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (favoriteState) "Remove from Favorites" else "Add to Favorites",
                        tint = iconTint
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}