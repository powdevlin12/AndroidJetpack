package com.dattran.unitconverter.social.ui.screens.tweets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dattran.unitconverter.social.navigation.Screen
import com.dattran.unitconverter.social.ui.screens.tweets.components.StoriesRail
import com.dattran.unitconverter.social.ui.screens.tweets.components.TweetCard
import com.dattran.unitconverter.social.ui.screens.tweets.components.TweetCardOffline
import kotlinx.coroutines.launch

private val PrimaryBlue = Color(0xFF257BF4)
private val BgLight = Color(0xFFF5F7F8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TweetsScreen(
    navigateController: NavController,
    viewModel: TweetsViewModel
) {
    // handle pull refresh
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val stories by viewModel.stories.collectAsState()
    val postData by viewModel.postData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleGetPosts()
    }

    Scaffold(
        topBar = {
            TweetsTopBar() {
                navigateController.navigate(Screen.PostTweet.route)
            }
        },
        containerColor = BgLight
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            onRefresh = {
                scope.launch {
                    isRefreshing = true
                    viewModel.handleGetPosts()
                    isRefreshing = false
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // ── Stories Rail ─────────────────────────────────
                item(key = "stories") {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        shadowElevation = 1.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        ) {
                            StoriesRail(stories = stories)
                        }
                    }
                }

                // ── Tweet Cards ───────────────────────────────────
                items(postData.posts.orEmpty(), key = { it.id }) { post ->
                    TweetCard(
                        post = post,
                        onLikeClick = { },
                        onCommentClick = { },
                        onRetweetClick = { },
                        onShareClick = { }
                    )
                }

                if (postData.isUseOfflinePost) {
                    items(postData.postsOffline.orEmpty(), key = { it.id }) { post ->
                        TweetCardOffline(
                            post = post,
                            onLikeClick = { },
                            onCommentClick = { },
                            onRetweetClick = { },
                            onShareClick = { }
                        )
                    }
                }

                // ── Footer spacer ─────────────────────────────────
                item(key = "spacer") {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TweetsTopBar(
    onClickIcon: () -> Unit
) {
    Surface(
        color = Color.White.copy(alpha = 0.95f),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "D-Connect",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D131C)
            )

            // DM / Send button with notification dot
            Box {
                IconButton(
                    onClick = {
                        onClickIcon()
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Messages",
                        tint = Color(0xFF0D131C),
                        modifier = Modifier.size(24.dp)
                    )
                }
                // Unread dot
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            PrimaryBlue,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                        .align(Alignment.TopEnd)
                        .offset(x = (-6).dp, y = 6.dp)
                )
            }
        }
    }
}
//
//@Preview(showBackground = true, widthDp = 460)
//@Composable
//fun PreviewTweetsScreen() {
//    TweetsScreen()
//}
//
