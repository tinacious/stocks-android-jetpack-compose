package com.tinaciousdesign.interviews.stocks.ui.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tinaciousdesign.interviews.stocks.R

@Composable
fun TinaciousDesignLogoIcon(
    size: Dp = 24.dp
) {
    val context = LocalContext.current

    Image(
        painterResource(R.drawable.tinacious_design_logo),
        context.getString(R.string.tinacious_design_logo_content_description),
        modifier = Modifier
            .width(size)
            .height(size)
    )
}
