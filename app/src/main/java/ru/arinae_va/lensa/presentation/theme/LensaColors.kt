package ru.arinae_va.lensa.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val black = Color(0xFF131513)
val white = Color(0xFFEEEEEC)
val gray = Color(0xFF929091)
val purple = Color(0xFFA94FFF)

data class LensaColors(
    val logoColor: Color,
    val dividerColor: Color,
    val backgroundColor: Color,
    val defaultButtonBg: Color,
    val textColor: Color,
    val textColorSecondary: Color,
    val textColorAccent: Color,
)

@Composable
fun lensaLightColors() = LensaColors(
    logoColor = black,
    dividerColor = black,
    backgroundColor = white,
    textColor = black,
    textColorSecondary = gray,
    textColorAccent = purple,
    defaultButtonBg = purple,
)

@Composable
fun lensaDarkColors() = LensaColors(
    logoColor = white,
    dividerColor = white,
    backgroundColor = black,
    textColor = white,
    textColorAccent = purple,
    textColorSecondary = gray,
    defaultButtonBg = purple,
)