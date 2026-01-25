# ProfileScreen - UserData Integration Fix

## Vấn đề

ProfileScreen đã có UI hoàn chỉnh nhưng chưa hiển thị dữ liệu user thật từ database. Cần tích hợp
ProfileViewModel để lấy email và thông tin user.

## Giải pháp đã triển khai

### 1. **ProfileViewModel đã sẵn sàng** ✅

```kotlin
// ProfileViewModel.kt
class ProfileViewModel(
    private val userDao: UserDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

    fun handleGetUserDataLocal(): Unit {
        viewModelScope.launch {
            val userEntity = userDao.getUserLocal()
            handleSetProfileDataLocal(user = userEntity)
        }
    }
}

data class ProfileState(
    val isLoading: Boolean = false,
    val errorMsg: String = "",
    val profileDataLocal: UserEntity? = null
)
```

### 2. **MainActivity - ViewModel initialization** ✅

```kotlin
class MainActivity : ComponentActivity() {
    private lateinit var userPreferences: UserPreferences
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // ...
        val database = AppDatabase.getDatabase(applicationContext)
        val userDao = database.userDao()

        loginViewModel = LoginViewModel(userPreferences, userDao)
        profileViewModel = ProfileViewModel(userDao = userDao) // ✅ Initialized

        setContent {
            // ...
            NavGraph(
                navController = navController,
                userPreferences = userPreferences,
                loginViewModel = loginViewModel,
                profileViewModel = profileViewModel // ✅ Passed
            )
        }
    }
}
```

### 3. **NavGraph - ViewModel passing** ✅

```kotlin
@Composable
fun NavGraph(
    navController: NavHostController,
    userPreferences: UserPreferences,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel // ✅ Received
) {
    // ...
    composable(Screen.Profile.route) {
        MainScreen(navController, currentRoute) {
            ProfileScreen(
                navController,
                viewModel = profileViewModel // ✅ Passed to screen
            )
        }
    }
}
```

### 4. **ProfileScreen - Data integration** ✅

```kotlin
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel, // ✅ Receives ViewModel
) {
    var selectedTab by remember { mutableStateOf(0) }
    val uiState by viewModel.uiState.collectAsState() // ✅ Observe state

    // ✅ Load user data on screen launch
    LaunchedEffect(Unit) {
        viewModel.handleGetUserDataLocal()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7F8))
    ) {
        // ✅ Pass email to TopBar
        ProfileTopBar(username = uiState.profileDataLocal?.email ?: "username")

        // ...rest of UI
    }
}
```

### 5. **ProfileTopBar - Display user data** ✅

```kotlin
@Composable
private fun ProfileTopBar(
    username: String // ✅ Receives email from state
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = username, // ✅ Displays actual email
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
        // ...
    )
}
```

---

## Data Flow

```
MainActivity
    ↓
  Initialize ProfileViewModel(userDao)
    ↓
  Pass to NavGraph
    ↓
  NavGraph passes to ProfileScreen
    ↓
  ProfileScreen.LaunchedEffect
    ↓
  viewModel.handleGetUserDataLocal()
    ↓
  userDao.getUserLocal() → UserEntity
    ↓
  Update uiState.profileDataLocal
    ↓
  ProfileTopBar receives email
    ↓
  Display in UI ✅
```

---

## UserEntity Structure

```kotlin
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val email: String,
    val name: String?,
    val avatar: String?,
    val createdAt: String?
)
```

---

## Các thay đổi đã thực hiện

### ✅ Fixed

1. **Import cleanup**: Xóa import LoginViewModel không cần thiết
2. **ViewModel integration**: Thêm ProfileViewModel parameter
3. **State observation**: Sử dụng `collectAsState()` để observe uiState
4. **LaunchedEffect**: Load user data khi screen mount
5. **Data binding**: Truyền email từ state vào ProfileTopBar

### ✅ Working Features

- Email hiển thị từ database
- Fallback "username" nếu chưa có data
- Verified badge
- State management với Flow
- Coroutine integration
- Room database query

---

## Testing Steps

1. **Login với user** → Data được lưu vào Room database
2. **Navigate to Profile tab** → ProfileScreen mounts
3. **LaunchedEffect triggers** → `handleGetUserDataLocal()` được gọi
4. **UserDao query** → Lấy UserEntity từ database
5. **State updates** → `uiState.profileDataLocal` được set
6. **UI recomposes** → Email hiển thị ở TopBar

---

## Next Steps - Enhancements

### 1. Hiển thị thêm thông tin user

```kotlin
ProfileHeader(
    email = uiState.profileDataLocal?.email ?: "unknown",
    name = uiState.profileDataLocal?.name ?: "User Name",
    avatar = uiState.profileDataLocal?.avatar
)
```

### 2. Thêm Loading State

```kotlin
if (uiState.isLoading) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
} else {
    // Show profile content
}
```

### 3. Thêm Error Handling

```kotlin
if (uiState.errorMsg.isNotEmpty()) {
    AlertCustom(
        title = "Error",
        message = uiState.errorMsg,
        onlyOk = true,
        handleConfirm = { viewModel.handleSetErrorMsg("") }
    )
}
```

### 4. Thêm Avatar từ URL/Local

```kotlin
AsyncImage(
    model = uiState.profileDataLocal?.avatar,
    contentDescription = "Profile Avatar",
    modifier = Modifier
        .fillMaxSize()
        .clip(CircleShape),
    placeholder = painterResource(R.drawable.placeholder_avatar),
    error = painterResource(R.drawable.placeholder_avatar)
)
```

### 5. Replace hardcoded data

```kotlin
// Current
Text(text = "Alex Rivera", ...)
Text(text = "Digital Creator 🎨 | NYC 📍", ...)

// Should be
Text(text = uiState.profileDataLocal?.name ?: "User", ...)
Text(text = uiState.profileDataLocal?.bio ?: "No bio yet", ...)
```

---

## Error Fixed ✅

**Before:**

```
Error: ProfileViewModel not found
Error: viewModel parameter not provided
Error: uiState not accessible
```

**After:**

```
✅ Compile successful
✅ No errors
✅ Email displays correctly
✅ State management working
✅ Database integration complete
```

---

## Summary

- ✅ **ProfileViewModel**: Đã có sẵn và hoạt động
- ✅ **MainActivity**: Khởi tạo và truyền viewModel
- ✅ **NavGraph**: Pass viewModel đến ProfileScreen
- ✅ **ProfileScreen**: Nhận viewModel và load data
- ✅ **ProfileTopBar**: Hiển thị email từ database
- ✅ **No compile errors**: Code sạch và hoạt động

**Status**: ✅ **FIXED & WORKING**  
**Date**: January 25, 2026

