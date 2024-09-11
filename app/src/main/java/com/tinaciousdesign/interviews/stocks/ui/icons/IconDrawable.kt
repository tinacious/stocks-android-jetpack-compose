package com.tinaciousdesign.interviews.stocks.ui.icons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconDrawable(
    @DrawableRes drawableId: Int,
    @StringRes contentDescriptionRes: Int,
    tint: Color = Color.Unspecified,
    size: Dp = 24.dp
) {
    val context = LocalContext.current

    Image(
        painterResource(drawableId),
        context.getString(contentDescriptionRes),
        colorFilter = ColorFilter.tint(tint),
        modifier = Modifier
            .width(size)
            .height(size)
    )
}
