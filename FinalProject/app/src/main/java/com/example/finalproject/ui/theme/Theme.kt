package com.example.finalproject.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

val lightColors = lightColorScheme(
    primary = Purple80,
    onPrimary = White,
    secondary = PurpleGrey80,
    onSecondary = White,
    error = Red80,
    onError = White,
    background = Background,
    onBackground = DarkGray,
    surface = White,
    onSurface = DarkGray,
)

val darkColors = darkColorScheme(
    primary = Purple40,
    onPrimary = DarkGray,
    secondary = PurpleGrey40,
    onSecondary = DarkGray,
    error = Red40,
    onError = DarkGray,
    background = DarkGray,
    onBackground = White,
    surface = SurfaceDark,
    onSurface = White,
)

@SuppressLint("ObsoleteSdkInt")
@Composable
fun PCShoppingAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColors
        else -> lightColors
    }
    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}
