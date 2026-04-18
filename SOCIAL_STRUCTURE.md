# 📁 Cấu Trúc Source `social` — Mô Tả Chi Tiết

> **Package gốc:** `com.dattran.unitconverter.social`  
> **Kiến trúc:** MVVM + Repository Pattern + Clean Architecture (phân tầng rõ ràng)  
> **Stack chính:** Jetpack Compose · Retrofit · Room · DataStore · Hilt (một phần) · Kotlin
> Coroutines / Flow

---

## 📂 Cây thư mục tổng quan

```
social/
├── constant/
│   └── Color.kt
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt
│   │   ├── UserPreferences.kt
│   │   ├── dao/
│   │   │   └── UserDao.kt
│   │   └── entity/
│   │       └── UserEntity.kt
│   ├── model/
│   │   ├── FeatureItem.kt
│   │   ├── Movie.kt
│   │   ├── MovieResponse.kt
│   │   └── User.kt
│   ├── repository/
│   │   ├── MovieRepository.kt
│   │   └── UserRepository.kt
│   └── service/
│       ├── AuthApiService.kt
│       └── MovieApiService.kt
├── di/
│   └── AuthModule.kt
├── navigation/
│   └── NavGrap.kt
├── ui/
│   ├── components/
│   │   ├── AlertCustom.kt
│   │   ├── BottomNavigationBar.kt
│   │   ├── BottomSheetCustom.kt
│   │   ├── ButtonCustom.kt
│   │   ├── MovieItem.kt
│   │   ├── MovieList.kt
│   │   ├── TestNotification.kt
│   │   └── TextfieldCustom.kt
│   └── screens/
│       ├── MainScreen.kt
│       ├── cart/
│       │   └── CartScreen.kt
│       ├── category/
│       │   └── CategoryScreen.kt
│       ├── create_movie/
│       │   ├── CreateMovieScreen.kt
│       │   └── CreateMovieViewModel.kt
│       ├── edit_profile/
│       │   ├── EditProfile.kt
│       │   ├── EditProfileViewModel.kt
│       │   └── components/
│       │       └── LabelInput.kt
│       ├── home/
│       │   ├── HomeScreen.kt
│       │   └── HomeViewModel.kt
│       ├── home_qtv/
│       │   ├── HomeQTV.kt
│       │   ├── HomeQTVViewModel.kt
│       │   └── components/
│       │       ├── BannerCard.kt
│       │       ├── BarcodeCard.kt
│       │       ├── FeatureItemCard.kt
│       │       ├── FeatureList.kt
│       │       ├── PointAndGift.kt
│       │       ├── PromotionSection.kt
│       │       └── StatsCard.kt
│       ├── login/
│       │   ├── Login.kt
│       │   └── LoginViewModel.kt
│       ├── profile/
│       │   ├── ProfileScreen.kt
│       │   └── ProfileViewModel.kt
│       ├── register/
│       │   ├── Register.kt
│       │   └── RegisterViewModel.kt
│       ├── test_deeplink/
│       │   └── TestDeepLinkScreen.kt
│       └── update_movie/
│           ├── UpdateMovieScreen.kt
│           └── UpdateViewModel.kt
└── utils/
    ├── AuthManager.kt
    └── NotificationHelper.kt
```

---

## 🗂️ Mô tả chi tiết từng thư mục

---

### 1. `constant/`

| File       | Mô tả                                                                                                                                                                                                 |
|------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Color.kt` | Định nghĩa object `AppColor` — tập trung toàn bộ màu sắc dùng chung trong app (textBlack, textSecondary, bgGray, textBlue, btnBlue). Được import trực tiếp trong các Composable thay vì hardcode màu. |

---

### 2. `data/`

Tầng **Data Layer** — xử lý toàn bộ việc lấy/lưu dữ liệu từ remote API và local database.

#### 2.1 `data/local/`

| File                 | Mô tả                                                                                                                                                                      |
|----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `AppDatabase.kt`     | **Room Database** — singleton, khai báo các entity (`UserEntity`), version database (v3), cấp phát `UserDao`. Dùng `fallbackToDestructiveMigration()` khi schema thay đổi. |
| `UserPreferences.kt` | **DataStore Preferences** — lưu trữ `accessToken` và `isAuth` dưới dạng `Flow`. Cung cấp `saveLoginInfo()`, `clearLoginInfo()`, `getToken()` (synchronous qua `first()`).  |

#### 2.2 `data/local/dao/`

| File         | Mô tả                                                                                                                                                                                                                                    |
|--------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `UserDao.kt` | **Room DAO** — interface CRUD cho bảng `user_info`. Gồm: `insertUser` (upsert), `getUserById`, `getUserLocal` (trả `Flow<UserEntity?>`), `updateDataUserLocal`, `updateProfileFields` (chỉ update các trường profile, giữ nguyên token). |

#### 2.3 `data/local/entity/`

| File            | Mô tả                                                                                                                                                                                                                       |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `UserEntity.kt` | **Room Entity** — ánh xạ bảng `user_info`. Các cột: `id`, `name`, `email`, `avatar`, `bio`, `website`, `dateOfBirth`, `createdAt`, `updatedAt`, `verify`, `location`, `accessToken`, `refreshToken`. Email có unique index. |

#### 2.4 `data/model/`

| File               | Models                                                                                                                                                                           | Mô tả                                                                                                                                                             |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Movie.kt`         | `Movie`                                                                                                                                                                          | Data class cho một bộ phim (id, title, overview, posterPath, backdropPath, voteAverage, releaseDate, popularity). Có helper `getPosterUrl()`, `getBackdropUrl()`. |
| `MovieResponse.kt` | `MovieResponse`, `MovieByIdResponse`, `BodyUpdateMovie`, `UpdateMovieResponse`, `DeleteMovieResponse`                                                                            | Các response/request model liên quan đến Movie API.                                                                                                               |
| `User.kt`          | `UserRegisterBody`, `Token`, `UserRegisterResponse`, `UserLoginBody`, `UserLoginResponse`, `UserUpdateBody`, `UserLogoutBody`, `UserLogoutResponse`, `UserInfo`, `GetMeResponse` | Toàn bộ request/response model cho Authentication & User.                                                                                                         |
| `FeatureItem.kt`   | `FeatureItem`, `UserStats`                                                                                                                                                       | Model dùng cho màn hình HomeQTV: `FeatureItem` (icon, title, màu nền, badge), `UserStats` (tên, barcode, điểm, quà).                                              |

#### 2.5 `data/repository/`

| File                 | Phụ thuộc                   | Mô tả                                                                                                                                                                                                                 |
|----------------------|-----------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `MovieRepository.kt` | `MovieApiService`           | Repository cho Movie & Auth (register/login/getMe). Bọc mọi API call trong `try-catch`, trả `Result<T>`. Gồm: `getMovies`, `createMovie`, `getMovieById`, `deleteMovie`, `updateMovie`, `register`, `login`, `getMe`. |
| `UserRepository.kt`  | `UserDao`, `AuthApiService` | Repository cho User. Kết hợp **local DB** (Room) và **remote API** (Retrofit). Gồm: `insertUserInfo`, `getUserLocal` (Flow), `updateUser` (PATCH API), `updateProfileFieldsLocal` (Room), `logout`.                   |

#### 2.6 `data/service/`

| File                 | Base URL                | Endpoints                                                     | Mô tả                                                                                  |
|----------------------|-------------------------|---------------------------------------------------------------|----------------------------------------------------------------------------------------|
| `MovieApiService.kt` | `http://10.0.2.2:1236/` | GET/POST/PATCH/DELETE movies, POST register/login, GET get-me | Retrofit interface cho Movie & Auth API. Tạo instance qua `companion object create()`. |
| `AuthApiService.kt`  | `http://10.0.2.2:1236/` | POST logout, PATCH users/{user_id}                            | Retrofit interface riêng cho Auth actions sau login (logout, update profile).          |

---

### 3. `di/`

| File            | Mô tả                                                                                                                                                                                   |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `AuthModule.kt` | Module Hilt (hiện để trống — placeholder). Dự kiến dùng để provide `AuthApiService`, `MovieApiService`, các Repository vào dependency injection graph khi refactor sang Hilt hoàn toàn. |

---

### 4. `navigation/`

| File         | Mô tả                                                                                                                                                                                                                                                                                                |
|--------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `NavGrap.kt` | Định nghĩa **sealed class `Screen`** (Register, Login, Home, Category, Cart, Profile, EditProfile, Detail, Create, Update) với các route string tương ứng. Composable `NavGraph` nhận `NavHostController`, điều phối toàn bộ luồng màn hình. Các màn hình có bottom nav được bọc trong `MainScreen`. |

**Routes:**

| Route              | Screen                            | Có BottomNav |
|--------------------|-----------------------------------|--------------|
| `login`            | LoginScreen                       | ❌            |
| `register`         | Register                          | ❌            |
| `home`             | HomeQTV (trong MainScreen)        | ✅            |
| `category`         | CategoryScreen (trong MainScreen) | ✅            |
| `cart`             | CartScreen (trong MainScreen)     | ✅            |
| `profile`          | ProfileScreen (trong MainScreen)  | ✅            |
| `edit-profile`     | EditProfileScreen                 | ❌            |
| `detail/{movieId}` | UpdateMovieScreen                 | ❌            |
| `create`           | CreateMovieScreen                 | ❌            |
| `update/{movieId}` | UpdateMovieScreen                 | ❌            |

---

### 5. `ui/`

Tầng **Presentation Layer** — toàn bộ giao diện Jetpack Compose.

#### 5.1 `ui/components/` — Shared Composable tái sử dụng

| File                     | Mô tả                                                                                                                                                        |
|--------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `AlertCustom.kt`         | Dialog/Alert tùy chỉnh dùng chung.                                                                                                                           |
| `BottomNavigationBar.kt` | Bottom nav bar 4 tab: Trang chủ, Mua sắm, Thông báo (badge), Tài khoản. Nhận `selectedRoute` và callback `onItemSelected`. Dùng `NavigationItem` data class. |
| `BottomSheetCustom.kt`   | Bottom sheet modal tùy chỉnh dùng chung.                                                                                                                     |
| `ButtonCustom.kt`        | Button tùy chỉnh với style thống nhất.                                                                                                                       |
| `MovieItem.kt`           | Composable hiển thị 1 item phim (poster, tên, rating, ...).                                                                                                  |
| `MovieList.kt`           | Composable hiển thị danh sách phim (dùng `MovieItem`).                                                                                                       |
| `TestNotification.kt`    | Composable test gửi notification (dùng `NotificationHelper`).                                                                                                |
| `TextfieldCustom.kt`     | TextField tùy chỉnh với style thống nhất.                                                                                                                    |

#### 5.2 `ui/screens/` — Các màn hình

##### `MainScreen.kt`

> Container chính bọc nội dung + `BottomNavigationBar`. Dùng `Box` layout, content nhận padding
> bottom 80dp để tránh bị che bởi nav bar. Được gọi từ `NavGraph` cho các route có bottom nav.

---

##### `login/`

| File                | Mô tả                                                                                                                                                                                                          |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Login.kt`          | UI màn hình đăng nhập. Gồm form email/password, nút login, chuyển hướng sang Register.                                                                                                                         |
| `LoginViewModel.kt` | ViewModel quản lý `LoginState` (isLoading, errorMsg). Tạo thẳng `MovieRepository` và `UserRepository`. Sau login thành công: lưu token vào `UserPreferences`, lưu `UserEntity` vào Room, điều hướng sang Home. |

**Luồng Login:**

```
Login.kt → LoginViewModel.login()
    → MovieRepository.login() [Retrofit]
    → UserPreferences.saveLoginInfo(token) [DataStore]
    → MovieRepository.getMe() [Retrofit]
    → UserRepository.insertUserInfo(UserEntity) [Room]
    → NavController.navigate(Screen.Home)
```

---

##### `register/`

| File                   | Mô tả                                                              |
|------------------------|--------------------------------------------------------------------|
| `Register.kt`          | UI màn hình đăng ký.                                               |
| `RegisterViewModel.kt` | ViewModel quản lý state đăng ký, gọi `MovieRepository.register()`. |

---

##### `home_qtv/`

| File                  | Mô tả                                                                                                                        |
|-----------------------|------------------------------------------------------------------------------------------------------------------------------|
| `HomeQTV.kt`          | Màn hình trang chủ phong cách Thế Giới Di Động/QTV. Kết hợp tất cả component con.                                            |
| `HomeQTVViewModel.kt` | `@HiltViewModel` — Cung cấp `userStats: StateFlow<UserStats>` và `features: StateFlow<List<FeatureItem>>`. Data tĩnh (mock). |

**Components con của HomeQTV:**

| Component             | Mô tả                                                              |
|-----------------------|--------------------------------------------------------------------|
| `BannerCard.kt`       | Banner quảng cáo ngang.                                            |
| `BarcodeCard.kt`      | Thẻ hiển thị barcode thành viên. Dùng `UserStats.barcode`.         |
| `FeatureItemCard.kt`  | Card icon cho từng tính năng (có badge "Mới"). Dùng `FeatureItem`. |
| `FeatureList.kt`      | Grid/Row danh sách `FeatureItemCard`.                              |
| `PointAndGift.kt`     | Hiển thị điểm tích lũy và số quà. Dùng `UserStats`.                |
| `PromotionSection.kt` | Section khuyến mãi.                                                |
| `StatsCard.kt`        | Card thống kê tổng quan.                                           |

---

##### `home/`

| File               | Mô tả                                                                |
|--------------------|----------------------------------------------------------------------|
| `HomeScreen.kt`    | Màn hình home danh sách phim (cũ).                                   |
| `HomeViewModel.kt` | ViewModel gọi `MovieRepository.getMovies()`, quản lý danh sách phim. |

---

##### `profile/`

| File                  | Mô tả                                                                                                                                                          |
|-----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `ProfileScreen.kt`    | Hiển thị thông tin user từ Room DB. Có nút Logout và nút điều hướng sang EditProfile.                                                                          |
| `ProfileViewModel.kt` | Collect `UserRepository.getUserLocal()` Flow → tự cập nhật `ProfileState.profileDataLocal` khi DB thay đổi. Có `handleLogout()` gọi `UserRepository.logout()`. |

---

##### `edit_profile/`

| File                       | Mô tả                                                                                                                                                                                                               |
|----------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `EditProfile.kt`           | Form chỉnh sửa thông tin: name, bio, website, location, email.                                                                                                                                                      |
| `EditProfileViewModel.kt`  | Load user từ Room (`getUserLocal().first()`), quản lý `EditProfileState`. `onFieldChange()` update từng field. `handleSave()` gọi `UserRepository.updateUser()` (API PATCH) và `updateProfileFieldsLocal()` (Room). |
| `components/LabelInput.kt` | Composable input có label dùng trong form EditProfile.                                                                                                                                                              |

---

##### `create_movie/`

| File                      | Mô tả                                |
|---------------------------|--------------------------------------|
| `CreateMovieScreen.kt`    | Form tạo phim mới (title, overview). |
| `CreateMovieViewModel.kt` | Gọi `MovieRepository.createMovie()`. |

---

##### `update_movie/`

| File                   | Mô tả                                                                        |
|------------------------|------------------------------------------------------------------------------|
| `UpdateMovieScreen.kt` | Form cập nhật phim theo `movieId`.                                           |
| `UpdateViewModel.kt`   | Gọi `MovieRepository.getMovieById()` để load, sau đó `updateMovie()` để lưu. |

---

##### `category/`, `cart/`

| File                | Mô tả                                                       |
|---------------------|-------------------------------------------------------------|
| `CategoryScreen.kt` | Màn hình danh mục sản phẩm (placeholder / đang phát triển). |
| `CartScreen.kt`     | Màn hình giỏ hàng (placeholder / đang phát triển).          |

---

##### `test_deeplink/`

| File                    | Mô tả                                                        |
|-------------------------|--------------------------------------------------------------|
| `TestDeepLinkScreen.kt` | Màn hình test Deep Link, dùng cho mục đích phát triển/debug. |

---

### 6. `utils/`

| File                    | Mô tả                                                                                                                                                                                          |
|-------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `AuthManager.kt`        | Hiện để trống — placeholder cho logic quản lý xác thực (có thể dùng để refresh token, kiểm tra trạng thái auth, ...).                                                                          |
| `NotificationHelper.kt` | Helper quản lý Notification: tạo 3 channel (`default`, `important`, `movie`), gửi notification đơn giản và notification với deep link. Dùng `NotificationCompat`, `NotificationManagerCompat`. |

---

## 🔗 Sơ đồ mối liên hệ giữa các tầng

```
┌──────────────────────────────────────────────────────────────┐
│                        UI LAYER                              │
│  Screens (Composable)  ←→  ViewModels (StateFlow/MutableState│
│  login / register / home_qtv / profile / edit_profile / ...  │
│  └── ui/components/ (BottomNavigationBar, ButtonCustom, ...)  │
└───────────────────┬──────────────────────────────────────────┘
                    │ observes State / calls functions
┌───────────────────▼──────────────────────────────────────────┐
│                    REPOSITORY LAYER                          │
│  MovieRepository          UserRepository                     │
│  (Movie CRUD + Auth)      (User local + remote)              │
└──────┬────────────────────────────┬─────────────────────────┘
       │ Retrofit                   │ Room DAO + Retrofit
┌──────▼──────────┐        ┌────────▼────────────────────────┐
│  REMOTE SOURCE  │        │         LOCAL SOURCE            │
│  MovieApiService│        │  UserDao → AppDatabase          │
│  AuthApiService │        │  UserPreferences (DataStore)    │
└─────────────────┘        └─────────────────────────────────┘
```

---

## 🔗 Sơ đồ phụ thuộc của từng ViewModel

| ViewModel              | Repository dùng                     | Service dùng                        | Local Storage                                   |
|------------------------|-------------------------------------|-------------------------------------|-------------------------------------------------|
| `LoginViewModel`       | `MovieRepository`, `UserRepository` | `MovieApiService`, `AuthApiService` | `UserPreferences` (DataStore), `UserDao` (Room) |
| `RegisterViewModel`    | `MovieRepository`                   | `MovieApiService`                   | —                                               |
| `ProfileViewModel`     | `UserRepository`                    | `AuthApiService`                    | `UserDao` (Room)                                |
| `EditProfileViewModel` | `UserRepository`                    | `AuthApiService`                    | `UserDao` (Room)                                |
| `HomeViewModel`        | `MovieRepository`                   | `MovieApiService`                   | —                                               |
| `HomeQTVViewModel`     | — (mock data)                       | —                                   | —                                               |
| `CreateMovieViewModel` | `MovieRepository`                   | `MovieApiService`                   | —                                               |
| `UpdateViewModel`      | `MovieRepository`                   | `MovieApiService`                   | —                                               |

---

## 🗺️ Luồng điều hướng (Navigation Flow)

```
App Start
    │
    ▼
LoginScreen ──────────────────► RegisterScreen
    │ (login thành công)
    ▼
HomeQTV (route: "home")  ◄──── BottomNavigationBar ────► CategoryScreen
                                                    ────► CartScreen  (badge 🔔)
                                                    ────► ProfileScreen
                                                              │
                                                              ▼
                                                        EditProfileScreen
                                                              │
                                                              ◄ (back)

HomeScreen ──► CreateMovieScreen
           ──► UpdateMovieScreen (detail/{movieId} hoặc update/{movieId})
```

---

## 📌 Ghi chú kiến trúc

- **MVVM:** View (Composable) → ViewModel (StateFlow) → Repository → Service/DAO
- **Repository Pattern:** Trừu tượng hóa nguồn dữ liệu (remote/local). ViewModel không biết data đến
  từ API hay DB.
- **Single Source of Truth:** User info được lưu vào Room sau khi login, mọi màn hình đọc từ Room
  thay vì gọi API lại.
- **Flow-based Reactivity:** `UserDao.getUserLocal()` trả `Flow<UserEntity?>` — Profile và
  EditProfile tự cập nhật khi DB thay đổi, không cần pull thủ công.
- **Hilt (một phần):** `HomeQTVViewModel` dùng `@HiltViewModel`, nhưng các ViewModel còn lại tạo
  Repository thủ công trong constructor (chưa inject). `AuthModule.kt` là placeholder.
- **DataStore:** Lưu `accessToken` và `isAuth` để kiểm tra trạng thái đăng nhập khi khởi động app.

