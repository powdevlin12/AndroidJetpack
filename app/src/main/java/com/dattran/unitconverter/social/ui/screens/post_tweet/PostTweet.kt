package com.dattran.unitconverter.social.ui.screens.post_tweet

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dattran.unitconverter.social.ui.screens.login.FormField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dattran.unitconverter.R

private val BgLight = Color(0xFFF5F7F8)
private val PrimaryBlue = Color(0xFF257BF4)

data class FeatureNewPostItem(
    val img: Int,
    val name: String,
    val onPress: () -> Unit?
)

private val featureNewPost: List<FeatureNewPostItem> = listOf(
    FeatureNewPostItem(R.drawable.tag, name = "Tag people", onPress = {}),
    FeatureNewPostItem(R.drawable.location, name = "Add Location", onPress = {}),
    FeatureNewPostItem(R.drawable.reply, name = "Who can reply?", onPress = {})
)

@Composable
fun PostTweetScreen(
    viewModel: PostTweetViewModel,
    navController: NavController
) {
    var post by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            PostTweetsTopBar(onPressCancel = {
                Log.d("DatTest", "Go Back")
                navController.popBackStack()
            }) {
                Log.d("DatTest", "on Post")
                viewModel.handleAddTweet(post);
            }
        },
        containerColor = BgLight
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = post,
                    onValueChange = { post = it },
                    placeholder = { Text("What do you feel? ...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color(0xFF94A3B8)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(size = 24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF257BF4),
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        cursorColor = Color(0xFF257BF4)
                    ),
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFE2E8F0))
                )
            }
            items(featureNewPost, key = { it.name }) { feature ->
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(feature.img),
                        contentDescription = feature.name,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        feature.name,
                        style = TextStyle(fontSize = 14.sp, color = Color(0xFF0F172A))
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostTweetsTopBar(
    onPressCancel: () -> Unit,
    onPost: () -> Unit
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
            Box(modifier = Modifier.width(60.dp)) {
                Text(
                    "Cancel",
                    color = Color(0xFF475569),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.clickable {
                        onPressCancel()
                    }
                )
            }
            Text(
                text = "New Post",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D131C)
            )

            // DM / Send button with notification dot
            Box {
                IconButton(
                    onClick = {
                        onPost()
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Messages",
                        tint = Color(0xFF257BF4),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
//
//@SuppressLint("ViewModelConstructorInComposable")
//@Preview
//@Composable
//fun Preview() {
//    PostTweetScreen(
//        viewModel = PostTweetViewModel(),
//        navController = rememberNavController()
//    )
//}