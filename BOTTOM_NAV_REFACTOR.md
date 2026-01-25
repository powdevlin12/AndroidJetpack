# Hướng dẫn: Di chuyển BottomNavigationBar ra ngoài Navigation

## Tổng quan

Đã di chuyển `BottomNavigationBar` ra khỏi màn hình `HomeQTV` để tất cả các màn hình trong
navigation đều có thanh điều hướng dưới cùng.

## Các thay đổi đã thực hiện

### 1. Tạo MainScreen Container (MainScreen.kt)

**File mới:** `/app/src/main/java/com/dattran/unitconverter/social/ui/screens/MainScreen.kt`

```kotlin
@Composable
fun MainScreen(
    navController: NavController,
    currentRoute: String?,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Nội dung màn hình con
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)  // Tạo khoảng trống cho BottomNavBar
        ) {
            content()
        }

        // Bottom Navigation Bar - luôn hiển thị
        BottomNavigationBar(
            selectedRoute = currentRoute ?: "home",
            onItemSelected = { route ->
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

**Chức năng:**

- Wrapper component bọc nội dung màn hình con
- Hiển thị `BottomNavigationBar` ở dưới cùng
- Xử lý navigation giữa các tab
- Tự động highlight tab đang active dựa trên `currentRoute`

### 2. Tạo các màn hình tab mới

#### CategoryScreen.kt

**File mới:**
`/app/src/main/java/com/dattran/unitconverter/social/ui/screens/category/CategoryScreen.kt`

- Màn hình "Mua sắm" (tab 2)

#### CartScreen.kt

**File mới:** `/app/src/main/java/com/dattran/unitconverter/social/ui/screens/cart/CartScreen.kt`

- Màn hình "Thông báo" (tab 3)

#### ProfileScreen.kt

**File mới:**
`/app/src/main/java/com/dattran/unitconverter/social/ui/screens/profile/ProfileScreen.kt`

- Màn hình "Tài khoản" (tab 4)

### 3. Cập nhật Navigation Graph (NavGrap.kt)

#### a. Thêm Screen objects mới:

```kotlin
sealed class Screen(val route: String) {
    object Register : Screen("register")
    object Login : Screen("login")
    object Home : Screen("home")
    object Category : Screen("category")     // ⭐ Mới
    object Cart : Screen("cart")             // ⭐ Mới
    object Profile : Screen("profile")       // ⭐ Mới
    // ...existing screens
}
```

#### b. Cập nhật NavHost để bọc các màn hình bằng MainScreen:

```kotlin
@Composable
fun NavGraph(
    navController: NavHostController,
    userPreferences: UserPreferences,
    loginViewModel: LoginViewModel
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Màn hình không có BottomNav (Login, Register)
        composable(Screen.Login.route) {
            LoginScreen(navController, viewModel = loginViewModel)
        }

        composable(Screen.Register.route) {
            Register()
        }

        // ⭐ Các màn hình CÓ BottomNavigationBar
        composable(Screen.Home.route) {
            MainScreen(navController, currentRoute) {
                HomeQTV(navController)
            }
        }

        composable(Screen.Category.route) {
            MainScreen(navController, currentRoute) {
                CategoryScreen(navController)
            }
        }

        composable(Screen.Cart.route) {
            MainScreen(navController, currentRoute) {
                CartScreen(navController)
            }
        }

        composable(Screen.Profile.route) {
            MainScreen(navController, currentRoute) {
                ProfileScreen(navController)
            }
        }

        // ...other screens
    }
}
```

### 4. Cập nhật HomeQTV.kt

#### Loại bỏ:

- ❌ Box wrapper chứa BottomNavigationBar
- ❌ `selectedRoute` state
- ❌ Import `BottomNavigationBar`
- ❌ Padding bottom 80.dp (đã move lên MainScreen)

#### Giữ lại:

- ✅ Nội dung chính của màn hình
- ✅ ViewModel logic
- ✅ UI components

## Cấu trúc Navigation mới

```
MainActivity
└── NavGraph
    ├── Login (không có BottomNav)
    ├── Register (không có BottomNav)
    └── MainScreen (có BottomNav)
        ├── Home (HomeQTV)
        ├── Category (CategoryScreen)
        ├── Cart (CartScreen)
        └── Profile (ProfileScreen)
```

## Lợi ích

1. **Code reusability**: BottomNavigationBar chỉ được khai báo 1 lần
2. **Consistent UI**: Tất cả màn hình tab đều có cùng thanh điều hướng
3. **Easy to maintain**: Thay đổi BottomNav ở 1 nơi, áp dụng cho tất cả
4. **Flexible**: Dễ dàng thêm/bớt tab mới
5. **State preservation**: Navigation giữ state khi chuyển tab nhờ `saveState = true`

## Cách thêm màn hình mới có BottomNav

```kotlin
// 1. Tạo Screen object
object NewScreen : Screen("new_screen")

// 2. Thêm vào NavHost
composable(Screen.NewScreen.route) {
    MainScreen(navController, currentRoute) {
        NewScreenContent(navController)
    }
}

// 3. Cập nhật BottomNavigationBar items nếu cần
```

## Testing

✅ Build thành công
✅ Không có compile errors
✅ Bottom Navigation Bar hiển thị trên tất cả màn hình tab
✅ Navigation hoạt động đúng giữa các tab

## Next Steps (Tùy chọn)

1. Implement nội dung cho CategoryScreen, CartScreen, ProfileScreen
2. Thêm animations cho tab transitions
3. Implement badge logic cho Cart/Notifications
4. Thêm deep linking cho các tab
5. Thêm splash screen animation

---
**Ngày tạo:** 25/01/2026  
**Trạng thái:** ✅ Hoàn thành

