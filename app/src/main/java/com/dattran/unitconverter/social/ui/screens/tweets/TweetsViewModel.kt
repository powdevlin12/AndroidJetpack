package com.dattran.unitconverter.social.ui.screens.tweets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dattran.unitconverter.social.data.model.Post
import com.dattran.unitconverter.social.data.model.PostAudience
import com.dattran.unitconverter.social.data.model.PostType
import com.dattran.unitconverter.social.data.model.StoryItem
import com.dattran.unitconverter.social.data.model.Tweet
import com.dattran.unitconverter.social.data.model.TweetUser
import com.dattran.unitconverter.social.data.repository.TweetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TweetsViewData(
    val posts: List<Post>?,
    val loading: Boolean
)

class TweetsViewModel(
    private val repository: TweetRepository
) : ViewModel() {

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

    val _postData = MutableStateFlow(TweetsViewData(posts = null, loading = false))
    val postData: StateFlow<TweetsViewData> = _postData.asStateFlow()

    fun handleGetPosts() {
        viewModelScope.launch {
            repository.getTweets().fold(
                onSuccess = { response ->
                    val listPosts = response.data.map { post ->
                        Post(
                            id = post._id ?: "",
                            content = post.content ?: "",
                            type = post.type ?: PostType.Tweet,
                            userViews = post.user_views ?: 0,
                            guestViews = post.guest_views ?: 0,
                            user = post.user,
                            parent = post.parent,
                            audience = post.audience ?: PostAudience.None,
                            hashTags = post.hashTags ?: emptyList(),
                            mentions = post.mentions ?: emptyList(),
                            createdAt = post.created_at ?: "",
                            updatedAt = post.updated_at ?: "",
                        )
                    }

                    _postData.update { it.copy(loading = false, posts = listPosts) }
                },
                onFailure = { error ->
                    Log.e("DatTest", "That Bai: ${error.message}")
                    _postData.update { it.copy(loading = false) }
                }
            )
        }
    }
}

