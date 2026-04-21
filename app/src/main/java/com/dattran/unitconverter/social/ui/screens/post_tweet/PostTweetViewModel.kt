package com.dattran.unitconverter.social.ui.screens.post_tweet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dattran.unitconverter.social.data.local.entity.UserEntity
import com.dattran.unitconverter.social.data.model.TweetCreateBody
import com.dattran.unitconverter.social.data.repository.TweetRepository
import com.dattran.unitconverter.social.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PostTweetData(
    val loading: Boolean,
)

class PostTweetViewModel(
    private val repository: TweetRepository
) : ViewModel() {
    private val _postTweetUi = MutableStateFlow<PostTweetData>(PostTweetData(loading = false))
    val postTweetUi: StateFlow<PostTweetData> = _postTweetUi.asStateFlow()

    fun handleAddTweet(
        content: String
    ) {
        viewModelScope.launch {
            repository.addTweet(
                newTweet = TweetCreateBody(content = content)
            ).fold(
                onSuccess = { response ->
                    Log.d("DatTest", "Thanh Cong")
                },
                onFailure = { error ->
                    Log.d("DatTest", "That Bai")
                },
            )
        }
    }
}