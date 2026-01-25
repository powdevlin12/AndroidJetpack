package com.dattran.unitconverter.social.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    var selectedTab by remember { mutableStateOf(0) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleGetUserDataLocal()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7F8))
    ) {
        // Top App Bar
        ProfileTopBar(username = uiState.profileDataLocal?.email ?: "username")

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header Section
            ProfileHeader(name = uiState.profileDataLocal?.name ?: "Name")

            // Content Tabs
            ProfileTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            // Grid Content
            PhotoGrid()

            // Spacing for bottom nav
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopBar(
    username: String
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = username,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D131C)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Verified",
                    tint = Color(0xFF257BF4),
                    modifier = Modifier.size(18.dp)
                )
            }
        },
        navigationIcon = {
            // Placeholder for spacing
            Spacer(modifier = Modifier.width(48.dp))
        },
        actions = {
            IconButton(onClick = { /* Settings */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color(0xFF0D131C)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF5F7F8)
        )
    )
}

@Composable
private fun ProfileHeader(
    name: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Avatar & Stats Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with gradient border
            Box(
                modifier = Modifier.size(96.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF257BF4),
                                    Color(0xFF60A5FA)
                                )
                            ),
                            shape = CircleShape
                        )
                        .padding(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE2E8F0), CircleShape)
                            .clip(CircleShape)
                    ) {
                        // Placeholder for profile image
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            tint = Color(0xFF64748B)
                        )
                    }
                }

                // Add story button
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color(0xFF257BF4), CircleShape)
                        .border(2.dp, Color(0xFFF5F7F8), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Story",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Stats
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("145", "Posts")
                StatItem("12.4K", "Followers")
                StatItem("890", "Following")
            }
        }

        // Name & Bio
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D131C)
            )

            Text(
                text = "Digital Creator 🎨 | NYC 📍 | creating moments that matter.",
                fontSize = 14.sp,
                color = Color(0xFF0D131C),
                lineHeight = 20.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color(0xFF257BF4)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "d-connect.com/alex",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF257BF4)
                )
            }

            // Followed by
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                // Avatar stack
                Box(modifier = Modifier.width(40.dp)) {
                    Box(
                        modifier = Modifier
                            .offset(x = 0.dp)
                            .size(24.dp)
                            .background(Color(0xFFE2E8F0), CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .offset(x = 16.dp)
                            .size(24.dp)
                            .background(Color(0xFFE2E8F0), CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Followed by design.co and 14 others",
                    fontSize = 12.sp,
                    color = Color(0xFF496C9C)
                )
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { /* Edit Profile */ },
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF257BF4)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Edit Profile",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { /* Share Profile */ },
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE2E8F0)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Share Profile",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D131C)
                )
            }

            IconButton(
                onClick = { /* Add Person */ },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE2E8F0), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = "Add Person",
                    tint = Color(0xFF0D131C),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D131C)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF496C9C)
        )
    }
}

@Composable
private fun ProfileTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F7F8))
            .padding(top = 8.dp)
    ) {
        TabItem(
            icon = Icons.Default.GridView,
            isSelected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            modifier = Modifier.weight(1f)
        )
        TabItem(
            icon = Icons.Default.PlayArrow,
            isSelected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            modifier = Modifier.weight(1f)
        )
        TabItem(
            icon = Icons.Default.Person,
            isSelected = selectedTab == 2,
            onClick = { onTabSelected(2) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TabItem(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color(0xFF257BF4) else Color(0xFF496C9C),
                modifier = Modifier.size(24.dp)
            )
            if (isSelected) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color(0xFF257BF4))
                )
            }
        }
    }
}

@Composable
private fun PhotoGrid() {
    // Sample data for grid
    val photos = remember {
        listOf(
            PhotoItem(hasMultiple = true),
            PhotoItem(hasMultiple = false),
            PhotoItem(hasMultiple = false),
            PhotoItem(hasMultiple = false),
            PhotoItem(hasVideo = true),
            PhotoItem(hasMultiple = false),
            PhotoItem(hasMultiple = false),
            PhotoItem(hasMultiple = false),
            PhotoItem(hasMultiple = false),
            PhotoItem(hasMultiple = false),
            PhotoItem(hasMultiple = false),
            PhotoItem(hasMultiple = false)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F7F8))
    ) {
        HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)

        // Grid
        photos.chunked(3).forEach { rowPhotos ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                rowPhotos.forEach { photo ->
                    PhotoGridItem(
                        photo = photo,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}

@Composable
private fun PhotoGridItem(
    photo: PhotoItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(Color(0xFFE2E8F0))
            .clickable { /* Open photo */ }
    ) {
        // Placeholder background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFCBD5E1),
                            Color(0xFF94A3B8)
                        )
                    )
                )
        )

        // Top right icon
        if (photo.hasMultiple || photo.hasVideo) {
            Icon(
                imageVector = if (photo.hasMultiple) Icons.Default.Collections else Icons.Default.PlayCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(20.dp)
            )
        }
    }
}

private data class PhotoItem(
    val hasMultiple: Boolean = false,
    val hasVideo: Boolean = false
)

