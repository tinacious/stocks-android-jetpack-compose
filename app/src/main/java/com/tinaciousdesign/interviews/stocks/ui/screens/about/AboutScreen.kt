package com.tinaciousdesign.interviews.stocks.ui.screens.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tinaciousdesign.interviews.stocks.R
import com.tinaciousdesign.interviews.stocks.ui.components.UnstyledButton
import com.tinaciousdesign.interviews.stocks.ui.icons.TinaciousDesignLogoIcon
import com.tinaciousdesign.interviews.stocks.utils.openExternalBrowser

@Composable
fun AboutScreen() {
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            UnstyledButton(onClick = {
                context.openExternalBrowser("https://tinaciousdesign.com")
            }) {
                TinaciousDesignLogoIcon(100.dp)
            }

            Text(
                text = context.getString(R.string.about_screen_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 20.dp)
            )

            Text(
                text = context.getString(R.string.about_screen_message),
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
            )
        }
    }
}
