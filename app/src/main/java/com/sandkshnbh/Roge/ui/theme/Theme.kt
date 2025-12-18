package com.sandkshnbh.Roge.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryExpressive,
    onPrimary = OnPrimaryExpressive,
    primaryContainer = PrimaryContainerExpressive,
    onPrimaryContainer = OnPrimaryContainerExpressive,
    secondary = SecondaryExpressive,
    onSecondary = OnSecondaryExpressive,
    secondaryContainer = SecondaryContainerExpressive,
    onSecondaryContainer = OnSecondaryContainerExpressive,
    tertiary = TertiaryExpressive,
    onTertiary = OnTertiaryExpressive,
    tertiaryContainer = TertiaryContainerExpressive,
    onTertiaryContainer = OnTertiaryContainerExpressive,
    background = BackgroundExpressive,
    onBackground = OnBackgroundExpressive,
    surface = SurfaceExpressive,
    onSurface = OnSurfaceExpressive,
    error = ErrorExpressive,
    onError = OnErrorExpressive,
    surfaceVariant = SurfaceVariantExpressive,
    onSurfaceVariant = OnSurfaceVariantExpressive
)

// Expressive Typography 2025
val ExpressiveTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

// Expressive Shapes 2025
val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(32.dp),
    extraLarge = RoundedCornerShape(40.dp)
)

@Composable
fun RogeTheme(
    darkTheme: Boolean = false, // Force light theme as per user request
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ExpressiveTypography,
        shapes = ExpressiveShapes,
        content = content
    )
}