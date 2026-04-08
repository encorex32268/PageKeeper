package com.lihan.pagekeeper.core.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lihan.pagekeeper.R


val Lora = FontFamily(
    fonts = listOf(
        Font(R.font.lora , FontWeight.Normal),
        Font(R.font.lora_medium , FontWeight.Medium),
        Font(R.font.lora_bold , FontWeight.Bold),
        Font(R.font.lora_semibold , FontWeight.SemiBold),
    )
)

val Inter = FontFamily(
    fonts = listOf(
        Font(R.font.inter , FontWeight.Normal),
        Font(R.font.inter_medium , FontWeight.Medium),
        Font(R.font.inter_semibold , FontWeight.SemiBold),
        Font(R.font.inter_bold , FontWeight.Bold),
    )
)


val Typography.title_L_Bold
    get() = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        lineHeight = 30.sp,

    )

val Typography.title_M_Medium
    get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,

    )

val Typography.title_S_Medium
    get() = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 20.sp,

    )

val Typography.body_L_Regular
    get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,

    )

val Typography.body_M_Medium
    get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 18.sp,

    )

val Typography.body_M_Regular
    get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 18.sp,

    )

val Typography.body_S_Regular
    get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 16.sp,
    )

val Typography = Typography()


val Typography.navigation_Large
    get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp,
    )