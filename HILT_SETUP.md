# Hướng dẫn sử dụng Hilt Dagger trong dự án

## ✅ Đã cấu hình thành công

Hilt Dagger đã được cài đặt và cấu hình hoàn chỉnh trong dự án của bạn!

## 📦 Các file đã được tạo/cập nhật:

### 1. **build.gradle.kts (Project level)**

- Thêm plugin Hilt và KSP với phiên bản phù hợp với Kotlin 2.0.21

### 2. **app/build.gradle.kts**

- Đã có sẵn các dependencies cho Hilt:
    - `com.google.dagger:hilt-android:2.52`
    - `com.google.dagger:hilt-compiler:2.52` (KSP)
    - `androidx.hilt:hilt-navigation-compose:1.2.0`

### 3. **UnitConverterApplication.kt** ✨ MỚI

- Application class với annotation `@HiltAndroidApp`
- Đây là điểm bắt đầu cho Hilt

### 4. **MainActivity.kt**

- Thêm annotation `@AndroidEntryPoint` để enable Hilt injection

### 5. **AndroidManifest.xml**

- Thêm `android:name=".UnitConverterApplication"` vào thẻ `<application>`

### 6. **di/AppModule.kt** ✨ MỚI

- Ví dụ về Hilt Module đơn giản

## 🚀 Cách sử dụng Hilt

### 1. Tạo Module để provide dependencies:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
```

### 2. Inject dependencies vào ViewModel:

```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    // Your code here
}
```

### 3. Sử dụng trong Composable:

```kotlin
@Composable
fun MyScreen(
    viewModel: MyViewModel = hiltViewModel()
) {
    // Your composable code
}
```

### 4. Inject vào Activity/Fragment:

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var myDependency: MyDependency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use myDependency here
    }
}
```

## 📝 Các annotation quan trọng:

- `@HiltAndroidApp` - Đánh dấu Application class
- `@AndroidEntryPoint` - Đánh dấu Activity/Fragment/View có thể inject
- `@HiltViewModel` - Đánh dấu ViewModel để inject
- `@Module` - Đánh dấu module cung cấp dependencies
- `@InstallIn` - Xác định component lifecycle
- `@Provides` - Cung cấp dependency từ module
- `@Inject` - Inject dependency vào constructor hoặc field

## ✅ Kiểm tra build:

Chạy lệnh sau để verify:

```bash
./gradlew clean build
```

**Status: BUILD SUCCESSFUL** ✅

## 📚 Tài liệu tham khảo:

- [Hilt Official Documentation](https://dagger.dev/hilt/)
- [Android Hilt Guide](https://developer.android.com/training/dependency-injection/hilt-android)

