package com.dattran.unitconverter.social.ui.screens.post_tweet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PostTweetData (
    val loading: Boolean,
)

class PostTweetViewModel : ViewModel() {
    private val _postTweetUi = MutableStateFlow<PostTweetData>(PostTweetData(loading = false))
    val postTweetUi: StateFlow<PostTweetData> = _postTweetUi.asStateFlow()
}