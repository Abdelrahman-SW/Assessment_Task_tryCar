package com.beapps.assessment_task_trycar.post.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItems(
    val label: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    var badgeCount: Int? = null,
    val tint : Color = Color.Black
)
