package com.beapps.assessment_task_trycar.post_feature.presentation.post_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beapps.assessment_task_trycar.post_feature.domain.models.Post
import com.beapps.assessment_task_trycar.ui.theme.poppinsFontFamily

@Composable
fun PostItem(modifier: Modifier = Modifier, post: Post, onPostClicked: (Post) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(modifier = modifier
            .clickable {
                onPostClicked(post)
            }
            .padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularUserProfile()
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = post.userId.toString(), fontFamily = poppinsFontFamily , color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = post.title, fontWeight = FontWeight.Bold, fontFamily = poppinsFontFamily)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.body,
                fontSize = 22.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
            InteractWithPostBottomBar()
        }
    }
}

@Composable
fun CircularUserProfile(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = "User",
        modifier = Modifier.size(50.dp),
        tint = Color.Gray
    )
}

@Composable
fun InteractWithPostBottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        postBottomBarItems.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = item.icon, contentDescription = item.title, tint = item.tint)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.title, fontFamily = poppinsFontFamily)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostItemPreview() {
    PostItem(
        post = Post(
            1,
            1,
            "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
        )
    ) {

    }
}


data class PostBottomBarItem(
    val title: String,
    val icon: ImageVector,
    val tint: Color = Color.Black
)

val postBottomBarItems = listOf(
    PostBottomBarItem("Like", Icons.Outlined.ThumbUp, Color.Blue),
    PostBottomBarItem("Comment", Icons.AutoMirrored.Outlined.Comment),
    PostBottomBarItem("Share", Icons.Outlined.Share)
)