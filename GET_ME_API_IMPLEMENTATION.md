# Get-Me API Implementation Summary

## Tổng quan

Đã thêm chức năng gọi API `get-me` sau khi đăng nhập thành công để lấy thông tin đầy đủ của người
dùng và lưu vào Room Database.

## Các thay đổi đã thực hiện

### 1. **Thêm Data Models** (`User.kt`)

Đã thêm 2 data class mới:

```kotlin
data class UserInfo(
    val _id: String,
    val name: String,
    val email: String,
    val date_of_birth: String?,
    val created_at: String?,
    val updated_at: String?,
    val avatar: String?,
    val verify: Int?
)

data class GetMeResponse(
    val message: String,
    val data: UserInfo
)
```

### 2. **Cập nhật MovieApiService** (`MovieApiService.kt`)

- Thêm import `@Header` và `GetMeResponse`
- Thêm endpoint mới:

```kotlin
@GET("users/get-me")
suspend fun getMe(
    @Header("Authorization") authorization: String
): GetMeResponse
```

### 3. **Cập nhật MovieRepository** (`MovieRepository.kt`)

Thêm method để gọi API get-me:

```kotlin
suspend fun getMe(token: String): Result<GetMeResponse> {
    return try {
        val response = apiService.getMe(authorization = "Bearer $token")
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

### 4. **Cập nhật UserEntity** (`UserEntity.kt`)

Thay đổi type của `id` từ `Int` thành `String` để phù hợp với API response (_id từ MongoDB):

```kotlin
@PrimaryKey
@ColumnInfo(name = "id")
val id: String,  // Changed from Int to String
```

### 5. **Cập nhật AppDatabase** (`AppDatabase.kt`)

- Tăng version từ 1 lên 2 do thay đổi schema
- Xóa unused import TypeConverters

```kotlin
@Database(
    entities = [UserEntity::class],
    version = 2,  // Incremented from 1 to 2
    exportSchema = false
)
```

### 6. **Cập nhật LoginViewModel** (`LoginViewModel.kt`)

Cập nhật flow trong `handleLogin`:

**Luồng mới:**

1. Gọi API login
2. Lưu access token vào DataStore
3. **Gọi API get-me với token** (mới)
4. **Lưu thông tin user đầy đủ vào Room Database** (cải thiện)
5. Navigate đến Home screen

```kotlin
repository.login(userForm).fold(
    onSuccess = { response ->
        val token = response.data.accessToken

        // Lưu token vào DataStore
        userPreferences.saveLoginInfo(token = token)

        // Gọi API get-me để lấy thông tin user đầy đủ
        repository.getMe(token).fold(
            onSuccess = { userResponse ->
                val userInfo = userResponse.data

                // Lưu thông tin user vào Room Database
                val userEntity = UserEntity(
                    id = userInfo._id,
                    name = userInfo.name,
                    email = userInfo.email,
                    avatar = userInfo.avatar
                )

                viewModelScope.launch {
                    userInfoRepository.insertUserInfo(userEntity)
                }

                // Update UI state và navigate
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMsg = ""
                )

                navController.navigate(Screen.Home.route)
            },
            onFailure = { error ->
                // Xử lý lỗi khi get-me thất bại
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMsg = "Không thể lấy thông tin người dùng: ${error.message}"
                )
            }
        )
    },
    onFailure = { error ->
        // Xử lý lỗi khi login thất bại
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMsg = error.message ?: ""
        )
    }
)
```

## Cách hoạt động

### Request API

```bash
GET http://10.0.2.2:1236/users/get-me
Headers:
  Authorization: Bearer {access_token}
  accept: application/json
```

### Response API

```json
{
  "message": "Get me successfully",
  "data": {
    "_id": "695100b29d22bd6446cac10d",
    "name": "John Doe",
    "email": "john@example.com",
    "date_of_birth": "2000-01-01",
    "avatar": "https://...",
    "verify": 0,
    "created_at": "2024-01-01T00:00:00.000Z",
    "updated_at": "2024-01-01T00:00:00.000Z"
  }
}
```

### Data được lưu vào Room

```kotlin
UserEntity(
    id = "695100b29d22bd6446cac10d",  // MongoDB _id
    name = "John Doe",
    email = "john@example.com",
    avatar = "https://..."
)
```

## Lưu ý quan trọng

1. **Database Migration**: Do thay đổi type của `id` từ Int sang String, database sẽ tự động
   recreate với `.fallbackToDestructiveMigration()`

2. **Authorization Header**: Token được gửi với format `Bearer {token}` theo chuẩn OAuth2

3. **Error Handling**:
    - Nếu login thất bại → hiển thị lỗi login
    - Nếu get-me thất bại → hiển thị lỗi "Không thể lấy thông tin người dùng"

4. **Async Operations**: Cả login và get-me đều chạy trong coroutine để không block UI

## Build Status

✅ Build thành công không có lỗi:

```
BUILD SUCCESSFUL in 23s
39 actionable tasks: 15 executed, 24 up-to-date
```

## Testing

Để test chức năng:

1. Chạy app và đăng nhập với tài khoản hợp lệ
2. Sau khi đăng nhập thành công, kiểm tra:
    - Token được lưu trong DataStore
    - Thông tin user được lưu trong Room Database với đầy đủ: id, name, email, avatar
3. Có thể kiểm tra database bằng Database Inspector trong Android Studio

## Cải thiện so với trước

**Trước:**

- Chỉ lưu email từ form login
- id = 0 (temporary)
- name = "" (empty)
- avatar = null

**Sau:**

- Lấy đầy đủ thông tin từ API get-me
- id = MongoDB _id thực tế
- name = tên người dùng từ database
- email = email chính xác từ database
- avatar = URL avatar nếu có

