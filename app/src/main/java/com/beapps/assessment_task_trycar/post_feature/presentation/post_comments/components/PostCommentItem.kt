package com.beapps.assessment_task_trycar.post_feature.presentation.post_comments.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beapps.assessment_task_trycar.post_feature.domain.models.PostComment
import com.beapps.assessment_task_trycar.post_feature.presentation.components.CircularUserProfile
import com.beapps.assessment_task_trycar.ui.theme.poppinsFontFamily

@Composable
fun PostCommentItem(modifier: Modifier = Modifier , postComment: PostComment) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularUserProfile()
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = postComment.email, fontFamily = poppinsFontFamily)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = postComment.name, fontWeight = FontWeight.Bold, fontFamily = poppinsFontFamily)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = postComment.body,
                fontSize = 22.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray
            )
        }
    }
}