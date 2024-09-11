package com.tinaciousdesign.interviews.stocks.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    background = BrandColours.Greys.grey_50,
    onBackground = BrandColours.Greys.white,
    surface = BrandColours.Greys.grey_100,
    onSurface = BrandColours.Greys.white,
    surfaceTint = BrandColours.Greys.grey_300,
    primary = BrandColours.pink,
    secondary = BrandColours.blue,
    tertiary = BrandColours.turquoise
)

private val LightColorScheme = lightColorScheme(
    background = BrandColours.Greys.white,
    onBackground = BrandColours.Greys.black,
    surface = BrandColours.Greys.grey_450,
    surfaceVariant = BrandColours.Greys.grey_400,
    onSurface = BrandColours.Greys.black,
    surfaceTint = BrandColours.Greys.grey_300,
    primary = BrandColours.pink,
    secondary = BrandColours.blue,
    tertiary = BrandColours.turquoise,
)

@Composable
fun StocksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
