package com.dattran.unitconverter.social.ui.screens.tweets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dattran.unitconverter.social.data.local.entity.PostsEntity
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
    val loading: Boolean,
    val isUseOfflinePost: Boolean,
    val postsOffline: List<PostsEntity>?
)

class TweetsViewModel(
    private val repository: TweetRepository,
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

    val _postData = MutableStateFlow(
        TweetsViewData(
            posts = emptyList(),
            loading = false,
            postsOffline = emptyList(),
            isUseOfflinePost = false
        )
    )
    val postData: StateFlow<TweetsViewData> = _postData.asStateFlow()

    fun handleGetPostOffline() {
        viewModelScope.launch {
            repository.getTweetsLocal().fold(
                onSuccess = { res ->
                    _postData.update {
                        it.copy(
                            loading = false,
                            postsOffline = res,
                            isUseOfflinePost = true
                        )
                    }
                },
                onFailure = { e ->
                    Log.e("DatTest", "That Bai: ${e.message}")
                    _postData.update { it.copy(loading = false, isUseOfflinePost = false) }
                }
            )
        }
    }

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
                    handleGetPostOffline()
                }
            )
        }
    }
}

