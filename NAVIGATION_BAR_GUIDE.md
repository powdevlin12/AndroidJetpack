# NavigationBar Implementation Guide

## Tổng quan

NavigationBar đã được tạo thành công với các tính năng:

- ✅ 4 tabs: Trang chủ, Danh mục, Giỏ hàng, Cá nhân
- ✅ Badge hiển thị số lượng items trong giỏ hàng
- ✅ Selected state với màu xanh (#1E88E5)
- ✅ Bo tròn góc trên cùng
- ✅ Shadow đẹp mắt
- ✅ Smooth animations

## Cấu trúc Files

### 1. Component chính

**File**:
`app/src/main/java/com/dattran/unitconverter/movie_project/ui/components/BottomNavigationBar.kt`

### 2. Icons

```
app/src/main/res/drawable/
├── ic_home.xml      # Icon trang chủ
├── ic_category.xml  # Icon danh mục
├── ic_cart.xml      # Icon giỏ hàng
└── ic_person.xml    # Icon cá nhân
```

### 3. Tích hợp trong HomeQTV

**File**: `app/src/main/java/com/dattran/unitconverter/movie_project/ui/screens/home_qtv/HomeQTV.kt`

## Cách sử dụng

### Cơ bản

```kotlin
@Composable
fun MyScreen() {
    var selectedRoute by remember { mutableStateOf("home") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Your main content here

        BottomNavigationBar(
            selectedRoute = selectedRoute,
            onItemSelected = { route ->
                selectedRoute = route
                // Handle navigation
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
```

### Với Navigation Component

```kotlin
@Composable
fun MyScreen(navController: NavController) {
    var selectedRoute by remember { mutableStateOf("home") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Your main content here

        BottomNavigationBar(
            selectedRoute = selectedRoute,
            onItemSelected = { route ->
                selectedRoute = route
                navController.navigate(route)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
```

## Customization

### Thay đổi màu sắc

```kotlin
// Trong BottomNavigationBar.kt
val selectedColor = Color(0xFF1E88E5) // Màu khi selected
val unselectedColor = Color(0xFF9E9E9E) // Màu khi unselected
val badgeColor = Color(0xFFFF3B30) // Màu badge
```

### Thêm/Xóa tabs

```kotlin
val items = remember {
    listOf(
        NavigationItem(
            title = "Trang chủ",
            icon = R.drawable.ic_home,
            route = "home"
        ),
        NavigationItem(
            title = "Yêu thích",
            icon = R.drawable.ic_favorite, // Thêm icon mới
            route = "favorite"
        ),
        // ... thêm items khác
    )
}
```

### Thay đổi badge count

```kotlin
// Trong HomeQTV.kt hoặc ViewModel
val cartItemCount by viewModel.cartItemCount.collectAsState()

BottomNavigationBar(
    selectedRoute = selectedRoute,
    onItemSelected = { route -> selectedRoute = route },
    cartBadgeCount = cartItemCount, // Pass badge count
    modifier = Modifier.align(Alignment.BottomCenter)
)
```

### Custom badge count trong NavigationItem

```kotlin
NavigationItem(
    title = "Giỏ hàng",
    icon = R.drawable.ic_cart,
    route = "cart",
    hasBadge = cartItemCount > 0, // Hiển thị khi có items
    badgeCount = cartItemCount
)
```

## Styling

### Bo tròn góc

```kotlin
shape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp
)
```

### Shadow

```kotlin
.shadow(
    elevation = 8.dp,
    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    clip = false
)
```

### Background cho selected item

```kotlin
if (isSelected) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFF1E88E5).copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
    )
}
```

## Integration với HomeQTV

### Structure

```
HomeQTV
├── Box (fillMaxSize)
│   ├── Column (Main Content)
│   │   ├── Header với user name
│   │   ├── BarcodeCard
│   │   ├── Box (white background)
│   │   │   ├── PointAndGift
│   │   │   ├── FeatureList
│   │   │   └── PromotionSection
│   │   └── padding(bottom = 80.dp) ← Space cho NavigationBar
│   │
│   └── BottomNavigationBar ← Positioned at bottom
```

### Key Points

1. Main content có `padding(bottom = 80.dp)` để không bị che bởi NavigationBar
2. NavigationBar được align ở `BottomCenter` của Box
3. NavigationBar có height 80dp với shadow

## Icons trong Drawable

### Cách tạo icon mới

1. Tạo file XML trong `res/drawable/`
2. Format:

```xml

<vector xmlns:android="http://schemas.android.com/apk/res/android" android:width="24dp"
    android:height="24dp" android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#000000" android:pathData="M10,20v-6h4v6h5v-8h3L12,3 2,12h3v8z" />
</vector>
```

### Sử dụng Material Icons

```kotlin
// Thêm dependency
implementation("androidx.compose.material:material-icons-extended:1.5.4")

// Sử dụng
Icon(
    imageVector = Icons.Default.Home,
    contentDescription = "Home"
)
```

### Convert SVG to Vector Drawable

1. Right-click `res/drawable` → New → Vector Asset
2. Chọn "Local file (SVG, PSD)"
3. Browse và select SVG file
4. Adjust size và color nếu cần

## Troubleshooting

### Icon không hiển thị

- Kiểm tra file XML có đúng format không
- Build lại project: `./gradlew clean build`
- Sync Gradle: File → Sync Project with Gradle Files

### Badge không hiển thị

- Kiểm tra `hasBadge = true` và `badgeCount > 0`
- Kiểm tra badge count có được pass đúng không

### NavigationBar bị che content

- Thêm `padding(bottom = 80.dp)` vào main content
- Hoặc dùng `Scaffold` với `bottomBar`

### Selected state không work

- Kiểm tra `selectedRoute` có match với `item.route` không
- Đảm bảo state được update trong `onItemSelected`

## Best Practices

1. **State Management**: Sử dụng ViewModel để quản lý selected route
2. **Badge**: Update badge count từ data layer (Room DB, Repository)
3. **Navigation**: Tích hợp với Jetpack Navigation Component
4. **Accessibility**: Luôn provide `contentDescription` cho icons
5. **Performance**: Sử dụng `remember` để cache painter resources

## Example với ViewModel

```kotlin
@HiltViewModel
class MainViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val cartItemCount: StateFlow<Int> = cartRepository
        .getCartItemCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
}

@Composable
fun HomeQTV(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val cartItemCount by viewModel.cartItemCount.collectAsState()
    var selectedRoute by remember { mutableStateOf("home") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content...

        BottomNavigationBar(
            selectedRoute = selectedRoute,
            onItemSelected = { route ->
                selectedRoute = route
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
```

## Screenshots

### Default State

- Home tab selected (blue)
- Other tabs gray
- Cart badge shows "3"

### Features

- Smooth transition khi click
- Background highlight cho selected item
- Badge với bo tròn và số count
- Shadow tạo depth

## Next Steps

1. Tích hợp Navigation Component
2. Thêm animations khi switch tabs
3. Implement deep linking cho các routes
4. Thêm haptic feedback khi click
5. Support landscape mode

## Resources

- [Material Design - Bottom Navigation](https://m3.material.io/components/navigation-bar/overview)
- [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- [Material Icons](https://fonts.google.com/icons)

