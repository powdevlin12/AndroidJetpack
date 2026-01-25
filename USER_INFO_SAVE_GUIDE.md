# Hướng dẫn lưu thông tin User khi đăng nhập

## Tổng quan

Đã cài đặt tính năng lưu thông tin người dùng vào Room Database (local) khi đăng nhập thành công.

## Các thay đổi đã thực hiện

### 1. **LoginViewModel.kt**

- ✅ Thêm `UserDao` vào constructor
- ✅ Khởi tạo `UserRepository` với `userDao`
- ✅ Lưu thông tin user vào database khi login thành công

```kotlin
class LoginViewModel(
    val userPreferences: UserPreferences,
    private val userDao: UserDao
) : ViewModel() {
    // ...
    private val userInfoRepository = UserRepository(userDao = userDao)

    fun handleLogin(userForm: UserLoginBody, navController: NavController) {
        // ...
        onSuccess = { response ->
            // Lưu token
            userPreferences.saveLoginInfo(token = response.data.accessToken)

            // ⭐ Lưu user info vào Room Database
            val userEntity = UserEntity(
                id = 0,
                name = "",
                email = userForm.email,
                avatar = null
            )
            viewModelScope.launch {
                userInfoRepository.insertUserInfo(userEntity)
            }

            navController.navigate(Screen.Home.route)
        }
    }
}
```

### 2. **MainActivity.kt**

- ✅ Import `AppDatabase`
- ✅ Khởi tạo database instance
- ✅ Lấy `UserDao` từ database
- ✅ Truyền `userDao` vào `LoginViewModel`

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    // ...
    userPreferences = UserPreferences(applicationContext)

    // Get database instance and UserDao
    val database = AppDatabase.getDatabase(applicationContext)
    val userDao = database.userDao()

    loginViewModel = LoginViewModel(userPreferences, userDao)
    // ...
}
```

## Cấu trúc dữ liệu

### UserEntity (Room Database)

```kotlin
@Entity(tableName = "user_info")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String?
)
```

### UserRepository

```kotlin
class UserRepository(private val userDao: UserDao) {
    suspend fun insertUserInfo(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(userId: Int): Flow<UserEntity?> {
        return userDao.getUserById(userId)
    }
}
```

## Lưu ý quan trọng ⚠️

### Vấn đề hiện tại

API login hiện tại chỉ trả về **token**, không có thông tin user đầy đủ:

```kotlin
data class UserLoginResponse(
    val message: String,
    val data: Token  // Chỉ có accessToken và refreshToken
)
```

### Giải pháp tạm thời

Hiện tại đang lưu **email** từ form login vào database. Các trường khác (`id`, `name`, `avatar`) để
trống/mặc định.

### Giải pháp đề xuất 🎯

Để có đầy đủ thông tin user, bạn nên:

1. **Thêm API get profile** trong backend:

```kotlin
// MovieApiService.kt
@GET("users/me")
suspend fun getProfile(): UserProfileResponse
```

2. **Gọi API get profile sau khi login thành công**:

```kotlin
fun handleLogin(userForm: UserLoginBody, navController: NavController) {
    viewModelScope.launch {
        repository.login(userForm).fold(
            onSuccess = { loginResponse ->
                // Lưu token
                userPreferences.saveLoginInfo(token = loginResponse.data.accessToken)

                // ⭐ Gọi API get profile để lấy thông tin user đầy đủ
                repository.getProfile().fold(
                    onSuccess = { profileResponse ->
                        val userEntity = UserEntity(
                            id = profileResponse.data.id,
                            name = profileResponse.data.name,
                            email = profileResponse.data.email,
                            avatar = profileResponse.data.avatar
                        )
                        userInfoRepository.insertUserInfo(userEntity)
                    },
                    onFailure = { /* Handle error */ }
                )

                navController.navigate(Screen.Home.route)
            },
            onFailure = { /* Handle error */ }
        )
    }
}
```

## Cách sử dụng

### Lấy thông tin user đã lưu

```kotlin
// Trong ViewModel hoặc Repository
viewModelScope.launch {
    userRepository.getUserById(userId).collect { user ->
        if (user != null) {
            // Sử dụng thông tin user
            println("User: ${user.name}, Email: ${user.email}")
        }
    }
}
```

### Cập nhật thông tin user

```kotlin
val updatedUser = UserEntity(
    id = 123,
    name = "Tran Thu Dat",
    email = "dat@example.com",
    avatar = "https://example.com/avatar.jpg"
)

viewModelScope.launch {
    userRepository.insertUserInfo(updatedUser) // REPLACE strategy
}
```

## Testing

Để kiểm tra xem dữ liệu đã được lưu chưa:

1. **Sử dụng Database Inspector trong Android Studio**:
    - View > Tool Windows > App Inspection
    - Chọn tab "Database Inspector"
    - Chọn app đang chạy
    - Xem table `user_info`

2. **Log thông tin user sau khi login**:

```kotlin
viewModelScope.launch {
    userInfoRepository.getUserById(0).collect { user ->
        Log.d("LoginViewModel", "Saved user: $user")
    }
}
```

## Kết luận

✅ Đã hoàn thành việc lưu thông tin user vào Room Database khi đăng nhập thành công  
⚠️ Hiện tại chỉ lưu được email, cần API get profile để lấy đầy đủ thông tin  
🎯 Đề xuất thêm API get profile trong tương lai để hoàn thiện tính năng

