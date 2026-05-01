package com.dattran.unitconverter.social.ui.screens.tweets.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dattran.unitconverter.social.data.model.Post
import java.util.Locale

// ── Design tokens ────────────────────────────────────────────
private val Primary       = Color(0xFF0058BC)
private val OnSurface     = Color(0xFF181C23)
private val Secondary     = Color(0xFF505F76)
private val OutlineVariant = Color(0xFFC1C6D6)
private val SurfaceLowest = Color(0xFFFFFFFF)
private val LikeRed       = Color(0xFFEF4444)
private val RetweetGreen  = Color(0xFF10B981)

@Composable
fun TweetCard(
    post: Post,
    isLiked: Boolean = false,
    likeCount: Int = 0,
    commentCount: Int = 0,
    retweetCount: Int = 0,
    viewCount: Int = 0,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onRetweetClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceLowest)
            .border(
                width = 1.dp,
                color = Color(0xFFF1F5F9),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ── Header ──────────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                AsyncImage(
                    model = post.user.avatar.ifBlank { null },
                    contentDescription = post.user.name,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color(0xFFF1F5F9), CircleShape)
                        .background(Color(0xFFE0E2EC))
                )

                // Name + handle + time
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = post.user.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurface
                        )
                        // Verified badge — show if user has a verified marker
                        Icon(
                            imageVector = Icons.Filled.Verified,
                            contentDescription = "Verified",
                            tint = Primary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "@${post.user.email.substringBefore("@")} • ${post.createdAt.toTimeAgo()}",
                        fontSize = 12.sp,
                        color = Secondary,
                        letterSpacing = 0.sp
                    )
                }
            }

            // More options button
            IconButton(
                onClick = {},
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreHoriz,
                    contentDescription = "More options",
                    tint = Secondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // ── Content ─────────────────────────────────────────────
        Text(
            text = buildTweetText(
                content = post.content,
                hashtags = post.hashTags?.map { it.name } ?: emptyList(),
                mentions = post.mentions?.map { it.name } ?: emptyList()
            ),
            fontSize = 15.sp,
            lineHeight = 24.sp,
            color = OnSurface
        )

        // ── View Count ──────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color(0xFFF1F5F9),
                thickness = 1.dp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = formatCount(viewCount),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = OnSurface
            )
            Text(
                text = "Views",
                fontSize = 13.sp,
                color = Secondary
            )
        }
        HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

        // ── Engagement Bar ──────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Comment
            EngagementButton(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = Secondary,
                        modifier = Modifier.size(20.dp)
                    )
                },
                count = commentCount,
                activeColor = Primary,
                isActive = false,
                onClick = onCommentClick
            )

            // Retweet
            EngagementButton(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Repeat,
                        contentDescription = "Retweet",
                        tint = Secondary,
                        modifier = Modifier.size(20.dp)
                    )
                },
                count = retweetCount,
                activeColor = RetweetGreen,
                isActive = false,
                onClick = onRetweetClick
            )

            // Like
            val likeColor by animateColorAsState(
                targetValue = if (isLiked) LikeRed else Secondary,
                label = "like_color"
            )
            val likeScale by animateFloatAsState(
                targetValue = if (isLiked) 1.2f else 1f,
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                label = "like_scale"
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .clickable { onLikeClick() }
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Like",
                    tint = likeColor,
                    modifier = Modifier
                        .size(20.dp)
                        .scale(likeScale)
                )
                if (likeCount > 0) {
                    Text(
                        text = formatCount(likeCount),
                        fontSize = 12.sp,
                        color = likeColor
                    )
                }
            }

            // Share
            IconButton(
                onClick = onShareClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = "Share",
                    tint = Secondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// ── Helper composable ────────────────────────────────────────

@Composable
private fun EngagementButton(
    icon: @Composable () -> Unit,
    count: Int,
    activeColor: Color,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (isActive) activeColor else Secondary,
        label = "engagement_color"
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        icon()
        if (count > 0) {
            Text(
                text = formatCount(count),
                fontSize = 12.sp,
                color = color
            )
        }
    }
}

// ── Text builder ─────────────────────────────────────────────

private fun buildTweetText(
    content: String,
    hashtags: List<String>,
    mentions: List<String>
) = buildAnnotatedString {
    val words = content.split(" ")
    words.forEachIndexed { index, word ->
        val cleanWord = word.trimEnd(',', '.', '!', '?')
        val isHashtag = hashtags.any { "#${it.lowercase()}" == cleanWord.lowercase() }
            || cleanWord.startsWith("#")
        val isMention = mentions.any { "@${it.lowercase()}" == cleanWord.lowercase() }
            || cleanWord.startsWith("@")

        when {
            isHashtag || isMention -> withStyle(
                SpanStyle(color = Primary, fontWeight = FontWeight.Medium)
            ) { append(word) }
            else -> append(word)
        }
        if (index < words.lastIndex) append(" ")
    }
}

// ── Formatters ───────────────────────────────────────────────

private fun formatCount(count: Int): String = when {
    count >= 1_000_000 -> "${count / 1_000_000}M"
    count >= 1_000 -> String.format(Locale.US, "%.1f", count / 1_000.0)
        .trimEnd('0').trimEnd('.') + "K"
    else -> count.toString()
}

/**
 * Very lightweight relative-time helper.
 * Replace with a proper library (e.g. PrettyTime / Joda) if needed.
 */
private fun String.toTimeAgo(): String {
    // Fallback: just return the raw string if parsing fails
    return try {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.US)
        sdf.timeZone = java.util.TimeZone.getTimeZone("UTC")
        val date = sdf.parse(this) ?: return this
        val diff = System.currentTimeMillis() - date.time
        val minutes = diff / 60_000
        val hours = minutes / 60
        val days = hours / 24
        when {
            minutes < 1 -> "just now"
            minutes < 60 -> "${minutes}m"
            hours < 24 -> "${hours}h"
            days < 7 -> "${days}d"
            else -> "${days / 7}w"
        }
    } catch (e: Exception) {
        this
    }
}
