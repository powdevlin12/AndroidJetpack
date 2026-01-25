package com.dattran.unitconverter.social.ui.screens.home_qtv

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.dattran.unitconverter.R
import com.dattran.unitconverter.social.data.model.FeatureItem
import com.dattran.unitconverter.social.data.model.UserStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeQTVViewModel @Inject constructor() : ViewModel() {

    private val _userStats = MutableStateFlow(
        UserStats(
            name = "Thư Đạt",
            barcode = "1088",
            points = "55.600.450",
            gifts = 32
        )
    )
    val userStats: StateFlow<UserStats> = _userStats.asStateFlow()

    private val _features = MutableStateFlow(
        listOf(
            FeatureItem(
                id = 1,
                title = "Hot DEAL",
                icon = R.drawable.coupon,
                backgroundColor = Color(0xFFFFEBF3),
                hasNewBadge = false
            ),
            FeatureItem(
                id = 2,
                title = "Mua sắm",
                icon = R.drawable.shop,
                backgroundColor = Color(0xFFE8F5E9),
                hasNewBadge = true
            ),
            FeatureItem(
                id = 3,
                title = "Tài chính\ntiêu dùng",
                icon = R.drawable.finance,
                backgroundColor = Color(0xFFFFF9E6),
                hasNewBadge = true
            ),
            FeatureItem(
                id = 4,
                title = "Thợ Điện\nMáy Xanh",
                icon = R.drawable.dmx,
                backgroundColor = Color(0xFFE3F2FD),
                hasNewBadge = false
            ),
            FeatureItem(
                id = 5,
                title = "Dịch vụ\ntiện ích",
                icon = R.drawable.bill,
                backgroundColor = Color(0xFFE3F2FD),
                hasNewBadge = true
            ),
            FeatureItem(
                id = 6,
                title = "Phiếu quà\ntặng",
                icon = R.drawable.gift,
                backgroundColor = Color(0xFFFFEBF3),
                hasNewBadge = true
            ),
            FeatureItem(
                id = 7,
                title = "Đối tác\nliên kết",
                icon = R.drawable.apple,
                backgroundColor = Color(0xFFF5F5F5),
                hasNewBadge = false
            ),
            FeatureItem(
                id = 8,
                title = "Khác",
                icon = R.drawable.menu,
                backgroundColor = Color(0xFFE0F2F1),
                hasNewBadge = false
            )
        )
    )
    val features: StateFlow<List<FeatureItem>> = _features.asStateFlow()

    private val _banners = MutableStateFlow(
        listOf(
            "https://via.placeholder.com/400x150/FFD700/000000?text=Galaxy+A56",
            "https://via.placeholder.com/400x150/4169E1/FFFFFF?text=Awesome+Intelligence",
            "https://via.placeholder.com/400x150/FF6347/FFFFFF?text=Hot+Deal"
        )
    )
    val banners: StateFlow<List<String>> = _banners.asStateFlow()
}
