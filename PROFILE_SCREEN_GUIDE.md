# ProfileScreen UI Implementation Guide

## Tổng quan

Đã implement ProfileScreen với UI giống Instagram dựa trên HTML template được cung cấp.

## Các thành phần UI đã triển khai

### 1. **ProfileTopBar** - Top App Bar

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopBar()
```

**Đặc điểm:**

- Username với verified badge (@arivera_design ✓)
- Settings button ở góc phải
- Background color: `#F5F7F8`
- Centered title với icon verified màu xanh `#257BF4`

---

### 2. **ProfileHeader** - Phần header profile

#### a. Avatar với gradient border & Add Story button

- **Avatar size**: 96dp
- **Gradient border**: Linear gradient từ `#257BF4` → `#60A5FA`
- **Add button**: CircleShape 28dp, background `#257BF4`
- **Border**: 3dp padding cho gradient effect

#### b. Stats Row (145 Posts, 12.4K Followers, 890 Following)

```kotlin
@Composable
private fun StatItem(value: String, label: String)
```

- **Value**: 18sp, Bold, color `#0D131C`
- **Label**: 12sp, Medium, color `#496C9C`
- Layout: Row với spacing equal

#### c. Bio Section

- **Name**: "Alex Rivera" - 20sp, Bold
- **Bio text**: 14sp, line height 20sp
- **Link**: Icon + Text màu `#257BF4`
- **Followed by**: Avatar stack + text (12sp, color `#496C9C`)

#### d. Action Buttons

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
```

- **Edit Profile**: Primary button - `#257BF4`, rounded 20dp
- **Share Profile**: Secondary button - `#E2E8F0`, text color `#0D131C`
- **Add Person**: IconButton - 40dp circle

---

### 3. **ProfileTabs** - Content Tabs

```kotlin
@Composable
private fun ProfileTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
)
```

**3 tabs:**

- Grid View (Icons.Default.GridView)
- Videos (Icons.Default.PlayArrow)
- Tagged (Icons.Default.Person)

**Tab indicator:**

- Selected color: `#257BF4`
- Inactive color: `#496C9C`
- Bottom border: 2dp height, full width

---

### 4. **PhotoGrid** - Grid ảnh 3 cột

```kotlin
@Composable
private fun PhotoGrid()
```

**Đặc điểm:**

- Grid 3 columns với spacing 1dp
- AspectRatio 1:1 cho mỗi ô
- Background placeholder: Gradient từ `#CBD5E1` → `#94A3B8`
- Icons cho multiple images (Collections) hoặc video (PlayCircle)

**Data structure:**

```kotlin
private data class PhotoItem(
    val hasMultiple: Boolean = false,
    val hasVideo: Boolean = false
)
```

---

## Color Palette

```kotlin
val Primary = Color(0xFF257BF4)              // Blue primary
val BackgroundLight = Color(0xFFF5F7F8)       // Light gray background
val TextMainLight = Color(0xFF0D131C)         // Dark text
val TextSecondaryLight = Color(0xFF496C9C)    // Secondary gray text
val SurfaceLight = Color(0xFFE2E8F0)          // Surface/card background
val GradientStart = Color(0xFF257BF4)         // Gradient start
val GradientEnd = Color(0xFF60A5FA)           // Gradient end
```

---

## Layout Structure

```
ProfileScreen
├── Column (fillMaxSize, background: #F5F7F8)
│   ├── ProfileTopBar
│   └── Column (verticalScroll)
│       ├── ProfileHeader
│       │   ├── Row (Avatar + Stats)
│       │   ├── Bio Section
│       │   └── Action Buttons
│       ├── ProfileTabs
│       └── PhotoGrid
│           └── 4 Rows × 3 Columns
```

---

## Responsive Features

1. **Spacing cho BottomNav**: `Spacer(height = 16.dp)` ở cuối
2. **Scrollable content**: Toàn bộ nội dung scroll được trừ TopBar và Tabs
3. **Sticky Tabs**: Có thể làm sticky bằng cách tách Column

---

## Interactive Elements

### Click Handlers (Ready for implementation)

```kotlin
// Settings button
onClick = { /* Navigate to settings */ }

// Edit Profile button
onClick = { /* Navigate to edit profile */ }

// Share Profile button
onClick = { /* Open share sheet */ }

// Add Person button
onClick = { /* Show add person dialog */ }

// Tab selection
onTabSelected = { selectedTab = it }

// Photo grid item
onClick = { /* Open photo detail */ }
```

---

## Customization Options

### 1. Thêm hình ảnh thật

Replace placeholder Icon với AsyncImage:

```kotlin
AsyncImage(
    model = profileImageUrl,
    contentDescription = "Profile",
    modifier = Modifier.fillMaxSize().clip(CircleShape)
)
```

### 2. Thêm hình ảnh vào Grid

```kotlin
AsyncImage(
    model = photoItem.imageUrl,
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier.fillMaxSize()
)
```

### 3. Làm Tabs sticky

Tách ProfileTabs ra khỏi scrollable Column và đặt trong Box với Modifier.stickyHeader()

### 4. Thêm animations

```kotlin
// Animate tab indicator
AnimatedVisibility(visible = isSelected) {
    Box(modifier = Modifier.background(Color(0xFF257BF4)))
}

// Animate photo hover
scale(animateFloatAsState(if (isPressed) 0.95f else 1f))
```

---

## Dependencies Required

```kotlin
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.material:material-icons-extended")
implementation("io.coil-kt:coil-compose:2.5.0") // For AsyncImage
```

---

## Next Steps

1. ✅ **Đã hoàn thành**: UI structure và layout
2. 🔲 **Tích hợp ViewModel**: Lấy data thật từ API
3. 🔲 **Add images**: Sử dụng Coil/Glide cho load ảnh
4. 🔲 **Navigation**: Implement navigation clicks
5. 🔲 **Dark mode**: Thêm hỗ trợ dark theme
6. 🔲 **Animations**: Thêm transitions và animations
7. 🔲 **Pull to refresh**: Thêm SwipeRefresh
8. 🔲 **Lazy loading**: Chuyển PhotoGrid sang LazyVerticalGrid cho performance

---

## Testing Checklist

- ✅ Compile thành công
- ✅ Không có errors
- ✅ UI hiển thị đúng layout
- 🔲 Test navigation
- 🔲 Test tab switching
- 🔲 Test scroll behavior
- 🔲 Test với different screen sizes
- 🔲 Test dark mode
- 🔲 Test loading states

---

**Created**: January 25, 2026  
**Status**: ✅ Complete - Ready for integration  
**Based on**: D-Connect HTML Template

