package com.beapps.assessment_task_trycar.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = lightBlue100,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = darkGray,
    onBackground = Color.White,
    surfaceVariant = mainComponentColor35Alpha,
    surfaceTint = mainComponentColor35Alpha,
    onSurfaceVariant = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = lightBlue100,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = darkGray,
    onBackground = Color.White,
    surfaceVariant = mainComponentColor35Alpha,
    surfaceTint = mainComponentColor35Alpha,
    onSurfaceVariant = Color.White



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Assessment_Task_tryCarTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}