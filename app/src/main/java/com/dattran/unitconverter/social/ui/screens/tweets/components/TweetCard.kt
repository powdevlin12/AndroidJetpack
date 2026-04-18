package com.dattran.unitconverter.social.ui.screens.tweets.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dattran.unitconverter.social.data.model.Tweet
import java.util.Locale

private val PrimaryBlue = Color(0xFF257BF4)
private val TextPrimary = Color(0xFF0D131C)
private val TextSecondary = Color(0xFF64748B)
private val TextMuted = Color(0xFF94A3B8)
private val DividerColor = Color(0xFFF1F5F9)
private val LikeRed = Color(0xFFEF4444)

@Composable
fun TweetCard(
    tweet: Tweet,
    onLikeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // ── Header ──────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                AsyncImage(
                    model = tweet.user.avatarUrl,
                    contentDescription = tweet.user.displayName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                // Name + location
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tweet.user.displayName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        if (tweet.user.isVerified) {
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "✓",
                                    fontSize = 8.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    if (!tweet.location.isNullOrBlank()) {
                        Text(
                            text = tweet.location,
                            fontSize = 11.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            IconButton(
                onClick = { },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More options",
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // ── Media ──────────────────────────────────────────────
        if (!tweet.imageUrl.isNullOrBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(if (tweet.isVideo) 1f else 4f / 5f)
                    .background(Color(0xFFF1F5F9))
            ) {
                AsyncImage(
                    model = tweet.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Video indicator overlay
                if (tweet.isVideo) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Videocam,
                            contentDescription = "Video",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // ── Text-only post (no image) ───────────────────────────
        if (tweet.imageUrl.isNullOrBlank() && tweet.content.isNotBlank()) {
            Text(
                text = buildHashtagText(tweet.content, tweet.hashtags),
                fontSize = 15.sp,
                lineHeight = 22.sp,
                color = TextPrimary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }

        // ── Action Bar ─────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // Like
                val likeColor by animateColorAsState(
                    targetValue = if (tweet.isLiked) LikeRed else TextPrimary,
                    label = "like_color"
                )
                val likeScale by animateFloatAsState(
                    targetValue = if (tweet.isLiked) 1.15f else 1f,
                    animationSpec = spring(stiffness = Spring.StiffnessLow),
                    label = "like_scale"
                )
                IconButton(
                    onClick = onLikeClick,
                    modifier = Modifier.scale(likeScale)
                ) {
                    Icon(
                        imageVector = if (tweet.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                        tint = likeColor,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Comment
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = TextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Retweet
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Repeat,
                        contentDescription = "Retweet",
                        tint = TextPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Share
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Share",
                        tint = TextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Bookmark
            val bookmarkColor by animateColorAsState(
                targetValue = if (tweet.isBookmarked) PrimaryBlue else TextPrimary,
                label = "bookmark_color"
            )
            IconButton(onClick = onBookmarkClick) {
                Icon(
                    imageVector = if (tweet.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = bookmarkColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // ── Caption & Stats ────────────────────────────────────
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Like count
            Text(
                text = "${formatCount(tweet.likeCount)} likes",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            // Caption (for posts with image)
            if (!tweet.imageUrl.isNullOrBlank() && tweet.content.isNotBlank()) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(tweet.user.username)
                        }
                        append("  ")
                        append(tweet.content)
                        if (tweet.hashtags.isNotEmpty()) {
                            append(" ")
                            tweet.hashtags.forEach { tag ->
                                withStyle(
                                    SpanStyle(
                                        color = PrimaryBlue,
                                        fontWeight = FontWeight.Medium
                                    )
                                ) {
                                    append("#$tag")
                                }
                                append(" ")
                            }
                        }
                    },
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = TextPrimary
                )
            }

            // View comments
            if (tweet.commentCount > 0) {
                Text(
                    text = "View all ${tweet.commentCount} comments",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary,
                    modifier = Modifier.clickable { }
                )
            }

            // Stats row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (tweet.retweetCount > 0) {
                    Text(
                        text = "${formatCount(tweet.retweetCount)} retweets",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
                Text(
                    text = tweet.timeAgo,
                    fontSize = 10.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(DividerColor)
        )
    }
}

@Composable
private fun buildHashtagText(content: String, hashtags: List<String>) =
    buildAnnotatedString {
        val words = content.split(" ")
        words.forEachIndexed { index, word ->
            val tag = hashtags.firstOrNull { "#${it.lowercase()}" == word.lowercase().trimEnd() }
            if (tag != null || word.startsWith("#")) {
                withStyle(SpanStyle(color = PrimaryBlue, fontWeight = FontWeight.Medium)) {
                    append(word)
                }
            } else {
                append(word)
            }
            if (index < words.lastIndex) append(" ")
        }
    }

private fun formatCount(count: Int): String = when {
    count >= 1_000_000 -> "${count / 1_000_000}M"
    count >= 1_000 -> String.format(Locale.US, "%.1f", count / 1_000.0).trimEnd('0')
        .trimEnd('.') + "K"

    else -> count.toString()
}

