package com.tinaciousdesign.interviews.stocks.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openExternalBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    startActivity(intent)
}
