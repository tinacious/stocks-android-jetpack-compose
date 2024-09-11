package com.tinaciousdesign.interviews.stocks.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tinaciousdesign.interviews.stocks.models.Stock

@Composable
fun StockSearchResultListItem(
    stock: Stock
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                horizontal = 12.dp,
                vertical = 12.dp,
            )
    ) {
        AsyncImage(
            model = "https://api.dicebear.com/9.x/glass/png?seed=${stock.ticker}",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
            Text(
                text = stock.ticker,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = stock.name,
            )
        }

        Spacer(
            modifier = Modifier.weight(1.0f)
        )

        Text(stock.formattedPrice)
    }
}
