package com.dattran.unitconverter.social.ui.screens.tweets.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dattran.unitconverter.social.data.model.StoryItem

private val PrimaryBlue = Color(0xFF257BF4)
private val StoryGradientStart = Color(0xFF257BF4)
private val StoryGradientEnd = Color(0xFF22D3EE)

@Composable
fun StoriesRail(
    stories: List<StoryItem>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(stories) { story ->
            StoryAvatar(story = story)
        }
    }
}

@Composable
fun StoryAvatar(
    story: StoryItem,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .width(72.dp)
            .clickable { }
    ) {
        Box(
            modifier = Modifier.size(72.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            // Ring gradient or viewed gray
            val ringModifier = if (!story.isOwn && !story.isViewed) {
                Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(StoryGradientStart, StoryGradientEnd)
                        )
                    )
                    .padding(2.5.dp)
            } else if (story.isViewed) {
                Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFCBD5E1))
                    .padding(2.5.dp)
            } else {
                Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFFE2E8F0), CircleShape)
                    .padding(2.5.dp)
            }

            Box(modifier = ringModifier) {
                AsyncImage(
                    model = story.user.avatarUrl,
                    contentDescription = story.user.displayName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            }

            // Add button for own story
            if (story.isOwn) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue)
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add story",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        Text(
            text = story.user.displayName,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = if (story.isViewed) Color(0xFF94A3B8) else Color(0xFF0D131C),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

