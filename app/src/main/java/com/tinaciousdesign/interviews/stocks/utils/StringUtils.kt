package com.tinaciousdesign.interviews.stocks.utils

fun String.lastSegment(delimiter: String): String? =
    split(delimiter).lastOrNull()