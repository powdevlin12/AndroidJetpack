package com.dattran.unitconverter.social.ui.screens.tweets

import androidx.lifecycle.ViewModel
import com.dattran.unitconverter.social.data.model.StoryItem
import com.dattran.unitconverter.social.data.model.Tweet
import com.dattran.unitconverter.social.data.model.TweetUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TweetsViewModel : ViewModel() {

    private val _stories = MutableStateFlow(
        listOf(
            StoryItem(
                id = "own",
                user = TweetUser(
                    username = "me",
                    displayName = "My Story",
                    avatarUrl = "https://i.pravatar.cc/150?img=1"
                ),
                isOwn = true
            ),
            StoryItem(
                id = "1",
                user = TweetUser(
                    username = "sarah_j",
                    displayName = "Sarah",
                    avatarUrl = "https://i.pravatar.cc/150?img=5"
                )
            ),
            StoryItem(
                id = "2",
                user = TweetUser(
                    username = "mike_travels",
                    displayName = "Mike",
                    avatarUrl = "https://i.pravatar.cc/150?img=8"
                )
            ),
            StoryItem(
                id = "3",
                user = TweetUser(
                    username = "jules",
                    displayName = "Jules",
                    avatarUrl = "https://i.pravatar.cc/150?img=12"
                )
            ),
            StoryItem(
                id = "4",
                user = TweetUser(
                    username = "alex",
                    displayName = "Alex",
                    avatarUrl = "https://i.pravatar.cc/150?img=15"
                ),
                isViewed = true
            )
        )
    )
    val stories: StateFlow<List<StoryItem>> = _stories.asStateFlow()

    private val _tweets = MutableStateFlow(
        listOf(
            Tweet(
                id = "1",
                user = TweetUser(
                    username = "sarah_j",
                    displayName = "sarah_j",
                    avatarUrl = "https://i.pravatar.cc/150?img=5",
                    isVerified = true
                ),
                content = "Best night ever! 🎸",
                imageUrl = "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=800",
                likeCount = 1240,
                commentCount = 45,
                retweetCount = 88,
                isLiked = false,
                timeAgo = "2 hours ago",
                location = "Los Angeles, CA",
                hashtags = listOf("SummerVibes")
            ),
            Tweet(
                id = "2",
                user = TweetUser(
                    username = "mike_travels",
                    displayName = "mike_travels",
                    avatarUrl = "https://i.pravatar.cc/150?img=8"
                ),
                content = "Take me back to Tokyo 🇯🇵",
                imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=800",
                isVideo = true,
                likeCount = 850,
                commentCount = 12,
                retweetCount = 34,
                isLiked = true,
                timeAgo = "5 hours ago",
                location = "Tokyo, Japan"
            ),
            Tweet(
                id = "3",
                user = TweetUser(
                    username = "jules_art",
                    displayName = "Jules ✨",
                    avatarUrl = "https://i.pravatar.cc/150?img=12",
                    isVerified = true
                ),
                content = "New artwork just dropped 🎨 Spent 3 months on this piece. What do you think?",
                imageUrl = "https://images.unsplash.com/photo-1579783902614-a3fb3927b6a5?w=800",
                likeCount = 3200,
                commentCount = 204,
                retweetCount = 512,
                isLiked = false,
                isBookmarked = true,
                timeAgo = "Yesterday",
                hashtags = listOf("Art", "Digital", "Creative")
            ),
            Tweet(
                id = "4",
                user = TweetUser(
                    username = "alex_dev",
                    displayName = "Alex 💻",
                    avatarUrl = "https://i.pravatar.cc/150?img=15"
                ),
                content = "Just shipped a new feature at work. Jetpack Compose is honestly amazing once you get the hang of it 🚀 #AndroidDev #JetpackCompose",
                imageUrl = null,
                likeCount = 421,
                commentCount = 37,
                retweetCount = 61,
                isLiked = false,
                timeAgo = "1 day ago",
                hashtags = listOf("AndroidDev", "JetpackCompose")
            )
        )
    )
    val tweets: StateFlow<List<Tweet>> = _tweets.asStateFlow()

    fun toggleLike(tweetId: String) {
        _tweets.value = _tweets.value.map { tweet ->
            if (tweet.id == tweetId) {
                tweet.copy(
                    isLiked = !tweet.isLiked,
                    likeCount = if (tweet.isLiked) tweet.likeCount - 1 else tweet.likeCount + 1
                )
            } else tweet
        }
    }

    fun toggleBookmark(tweetId: String) {
        _tweets.value = _tweets.value.map { tweet ->
            if (tweet.id == tweetId) tweet.copy(isBookmarked = !tweet.isBookmarked)
            else tweet
        }
    }
}

