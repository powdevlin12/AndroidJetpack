# Hướng dẫn sử dụng ảnh trong Drawable với Jetpack Compose

## 1. Sử dụng ảnh PNG/JPG từ drawable

### Cách 1: Sử dụng painterResource

```kotlin
import androidx.compose.ui.res.painterResource
import com.dattran.unitconverter.R

// Trong Composable
Image(
    painter = painterResource(id = R.drawable.avatar),
    contentDescription = "Avatar",
    modifier = Modifier.size(50.dp)
)
```

### Cách 2: Sử dụng với Icon

```kotlin
Icon(
    painter = painterResource(id = R.drawable.shop),
    contentDescription = "Shop Icon",
    tint = Color.Blue, // Có thể thay đổi màu
    modifier = Modifier.size(24.dp)
)
```

### Cách 3: Lưu resource ID vào biến

```kotlin
data class FeatureItem(
    val id: Int,
    val title: String,
    val icon: Int, // Lưu resource ID
    val backgroundColor: Color
)

// Sử dụng
val feature = FeatureItem(
    id = 1,
    title = "Hot DEAL",
    icon = R.drawable.coupon, // Truyền resource ID
    backgroundColor = Color(0xFFFFEBF3)
)

// Hiển thị
Icon(
    painter = painterResource(id = feature.icon),
    contentDescription = feature.title,
    modifier = Modifier.size(24.dp)
)
```

## 2. Sử dụng Vector Drawable (XML)

### Cách 1: Dùng trực tiếp

```kotlin
Icon(
    painter = painterResource(id = R.drawable.ic_home),
    contentDescription = "Home",
    tint = Color.Blue,
    modifier = Modifier.size(24.dp)
)
```

### Cách 2: Với ImageVector

```kotlin
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

Icon(
    imageVector = Icons.Default.Home, // Material Icons built-in
    contentDescription = "Home",
    tint = Color.Blue
)
```

## 3. Ví dụ thực tế trong HomeQTVViewModel

```kotlin
private val _features = MutableStateFlow(
    listOf(
        FeatureItem(
            id = 1,
            title = "Hot DEAL",
            icon = R.drawable.coupon, // ← PNG từ drawable
            backgroundColor = Color(0xFFFFEBF3),
            hasNewBadge = false
        ),
        FeatureItem(
            id = 2,
            title = "Mua sắm",
            icon = R.drawable.shop, // ← PNG từ drawable
            backgroundColor = Color(0xFFE8F5E9),
            hasNewBadge = true
        )
    )
)
```

## 4. Trong Component sử dụng

```kotlin
@Composable
fun FeatureItemCard(
    feature: FeatureItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // ...
    Icon(
        painter = painterResource(id = feature.icon), // ← Lấy từ resource ID
        contentDescription = feature.title,
        tint = Color.Unspecified, // Giữ màu gốc
        modifier = Modifier.size(32.dp)
    )
    // ...
}
```

## 5. Tips và Best Practices

### Thêm ảnh vào drawable:

1. Copy ảnh vào: `app/src/main/res/drawable/`
2. Tên file phải là chữ thường và không có khoảng trắng
    - ✅ Good: `shop.png`, `ic_cart.png`, `user_avatar.jpg`
    - ❌ Bad: `Shop.png`, `ic cart.png`, `User Avatar.jpg`

### Vector vs PNG:

- **Vector (XML)**: Tốt cho icons đơn giản, scale không mất chất lượng
- **PNG/JPG**: Tốt cho ảnh phức tạp, ảnh thật

### ContentScale cho Image:

```kotlin
Image(
    painter = painterResource(id = R.drawable.avatar),
    contentDescription = "Avatar",
    contentScale = ContentScale.Crop, // Cắt ảnh
    modifier = Modifier
        .size(50.dp)
        .clip(CircleShape) // Bo tròn
)
```

### Các ContentScale options:

- `ContentScale.Crop`: Cắt ảnh để fill
- `ContentScale.Fit`: Fit toàn bộ ảnh
- `ContentScale.FillBounds`: Kéo giãn ảnh
- `ContentScale.FillWidth`: Fill theo chiều rộng
- `ContentScale.FillHeight`: Fill theo chiều cao
- `ContentScale.Inside`: Hiển thị toàn bộ trong bounds

## 6. Load ảnh từ URL (với Coil)

```kotlin
// Thêm dependency vào build.gradle.kts
implementation("io.coil-kt:coil-compose:2.5.0")

// Sử dụng
AsyncImage(
    model = "https://example.com/image.jpg",
    contentDescription = "Network Image",
    modifier = Modifier.size(100.dp)
)
```

## 7. Placeholder và Error Image

```kotlin
AsyncImage(
    model = imageUrl,
    contentDescription = "Image",
    placeholder = painterResource(id = R.drawable.placeholder),
    error = painterResource(id = R.drawable.error_image),
    modifier = Modifier.size(100.dp)
)
```

## 8. Tối ưu hóa

### Sử dụng remember để tránh recreate:

```kotlin
@Composable
fun MyIcon() {
    val painter = remember { painterResource(id = R.drawable.icon) }
    Icon(painter = painter, contentDescription = null)
}
```

### Load ảnh lớn:

```kotlin
// Sử dụng Coil với memory cache
AsyncImage(
    model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .build(),
    contentDescription = "Large Image"
)
```

## Tóm tắt

1. **Resource ID**: Dùng `R.drawable.ten_file` để lưu vào biến (Int)
2. **painterResource**: Chuyển resource ID thành Painter để hiển thị
3. **Icon vs Image**: Icon cho icons, Image cho ảnh thật
4. **Tint**: Chỉ hoạt động với Vector, dùng `Color.Unspecified` cho PNG
5. **ContentScale**: Điều chỉnh cách ảnh hiển thị trong bounds

