package com.beapps.assessment_task_trycar.post_feature.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beapps.assessment_task_trycar.ui.theme.lightBlue10035Alpha

@Composable
fun CircularUserProfile(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = "User",
        modifier = modifier.size(50.dp),
        tint = lightBlue10035Alpha
    )
}