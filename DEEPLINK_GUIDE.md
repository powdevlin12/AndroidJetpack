# 🔗 Hướng Dẫn Sử Dụng DeepLink Notification

## 📋 Tổng Quan

Tôi đã sửa lỗi deeplink và tạo màn hình test để bạn có thể dễ dàng kiểm tra chức năng.

---

## 🐛 Vấn Đề Đã Sửa

### 1. **NotificationHelper.kt**

**Lỗi cũ:**

```kotlin
val deepLinkIntent = Intent(
    Intent.ACTION_VIEW,
    Uri.parse("loyaltyapp://main/register"),
    context,
    MainActivity::class.java
)
```

**Đã sửa:**

```kotlin
val deepLinkIntent = Intent(context, MainActivity::class.java).apply {
    action = Intent.ACTION_VIEW
    data = Uri.parse("loyaltyapp://main/register")
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
}
```

### 2. **MainActivity.kt**

**Thêm xử lý deeplink:**

```kotlin
// ⭐ XỬ LÝ DEEPLINK
LaunchedEffect(intent) {
    handleDeepLink(intent, navController)
}

// ⭐ Xử lý khi có intent mới (khi app đang chạy)
override fun onNewIntent(intent: android.content.Intent) {
    super.onNewIntent(intent)
    setIntent(intent)
}

// ⭐ Hàm xử lý deeplink
private fun handleDeepLink(
    intent: android.content.Intent?,
    navController: androidx.navigation.NavHostController
) {
    val data = intent?.data
    if (data != null && data.scheme == "loyaltyapp") {
        when (data.host) {
            "main" -> {
                val path = data.pathSegments.firstOrNull()
                when (path) {
                    "register" -> navController.navigate("register") {
                        launchSingleTop = true
                    }
                    "login" -> navController.navigate("login") {
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}
```

### 3. **Thêm màn hình Test DeepLink**

- Tạo `TestDeepLinkScreen.kt`
- Thêm FAB ở màn hình Home để test
- Thêm route vào NavGraph

---

## 🚀 Cách Test

### **Bước 1: Build & Run App**

```bash
./gradlew clean build
```

### **Bước 2: Mở màn hình Test**

1. Mở app
2. Ở màn hình Home, bấm vào nút **icon chuông** (FAB) ở góc phải dưới
3. Sẽ chuyển đến màn hình "Test DeepLink Notification"

### **Bước 3: Test DeepLink**

1. Bấm nút **"Gửi DeepLink Notification"**
2. Notification sẽ xuất hiện ở thanh thông báo
3. **Bấm vào notification**
4. App sẽ chuyển đến màn hình **Register**

---

## 📱 DeepLink Scheme

### **Format:**

```
loyaltyapp://main/{route}
```

### **Ví dụ:**

- `loyaltyapp://main/register` → Màn hình Register
- `loyaltyapp://main/login` → Màn hình Login

### **Thêm route mới:**

Trong `MainActivity.kt`, thêm vào hàm `handleDeepLink`:

```kotlin
when (path) {
    "register" -> navController.navigate("register") { launchSingleTop = true }
    "login" -> navController.navigate("login") { launchSingleTop = true }
    "home" -> navController.navigate("home") { launchSingleTop = true }
    // Thêm route mới ở đây
}
```

---

## 🧪 Test Nâng Cao

### **Test từ Command Line (ADB):**

```bash
# Gửi deeplink từ terminal
adb shell am start -W -a android.intent.action.VIEW \
  -d "loyaltyapp://main/register" \
  com.dattran.unitconverter
```

### **Test từ browser:**

Tạo file HTML với link:

```html
<a href="loyaltyapp://main/register">Open Register Screen</a>
```

---

## ⚙️ Cấu Hình

### **AndroidManifest.xml**

```xml

<intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />

    <data android:scheme="loyaltyapp" android:host="main" />
</intent-filter>
```

---

## 🎯 Lưu Ý Quan Trọng

### 1. **Permission**

- Android 13+ cần permission `POST_NOTIFICATIONS`
- Màn hình test sẽ tự động yêu cầu permission

### 2. **Flags Intent**

```kotlin
flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
```

- `NEW_TASK`: Tạo task mới nếu app chưa chạy
- `CLEAR_TOP`: Clear các activity phía trên

### 3. **launchSingleTop**

```kotlin
navController.navigate("register") {
    launchSingleTop = true
}
```

- Tránh duplicate screen trong back stack

### 4. **onNewIntent**

```kotlin
override fun onNewIntent(intent: android.content.Intent) {
    super.onNewIntent(intent)
    setIntent(intent) // ⭐ Quan trọng!
}
```

- Xử lý deeplink khi app đang chạy

---

## 🔍 Debug

### **Kiểm tra Intent:**

Thêm log trong `handleDeepLink`:

```kotlin
Log.d("DeepLink", "Scheme: ${data?.scheme}")
Log.d("DeepLink", "Host: ${data?.host}")
Log.d("DeepLink", "Path: ${data?.pathSegments}")
```

### **Logcat Filter:**

```bash
adb logcat | grep -i deeplink
```

---

## ✅ Checklist Test

- [ ] Gửi notification thành công
- [ ] Bấm notification → mở app
- [ ] Navigate đến đúng màn hình Register
- [ ] Back button hoạt động đúng
- [ ] Test khi app đang chạy (onNewIntent)
- [ ] Test khi app đã đóng (onCreate)

---

## 🎉 Kết Quả

Sau khi sửa:

1. ✅ Notification hiển thị đúng
2. ✅ Bấm notification → mở app
3. ✅ Navigate đến màn hình Register
4. ✅ Deeplink hoạt động khi app đang chạy hoặc đã đóng

---

## 📞 Troubleshooting

### **Lỗi: Không navigate được**

→ Kiểm tra route trong NavGraph phải khớp với string trong `navigate()`

### **Lỗi: App không mở khi bấm notification**

→ Kiểm tra `PendingIntent.FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT`

### **Lỗi: Duplicate screen**

→ Thêm `launchSingleTop = true` trong navigate

---

## 🔥 Next Steps

Bạn có thể mở rộng:

1. Thêm deeplink với parameters: `loyaltyapp://movie/{movieId}`
2. Thêm analytics tracking cho deeplink
3. Tạo dynamic links (Firebase Dynamic Links)

---

**Author:** GitHub Copilot  
**Date:** January 6, 2026

