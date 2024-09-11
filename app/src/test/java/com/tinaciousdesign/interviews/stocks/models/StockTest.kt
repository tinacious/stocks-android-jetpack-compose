package com.tinaciousdesign.interviews.stocks.models

import org.junit.Assert.assertEquals
import org.junit.Test

class StockTest {
    @Test
    fun `formats the price rounding it to 2 decimal places`() {
        val subject = Stock("ABC", "ABC Co.", 3.14159)

        val result = subject.formattedPrice

        assertEquals("3.14", result)
    }

    @Test
    fun `matches() returns only stocks whose name or ticker contain the provided query - case sensitive`() {
        val query = "A"
        val stocks = listOf(
            Stock("FFF", "FFF Co", 100.0),
            Stock("DDD", "DDD Co", 100.0),
            Stock("CCC", "CCC Co", 100.0),
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
            Stock("XYZ", "XYZ and ABC", 100.0),
        )

        val result = stocks.matches(query)
        val expected = listOf(
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
            Stock("XYZ", "XYZ and ABC", 100.0),
        )

        assertEquals(expected, result)
    }

    @Test
    fun `matches() returns only stocks whose name or ticker contain the provided query - case insensitive`() {
        val query = "a"
        val stocks = listOf(
            Stock("FFF", "FFF Co", 100.0),
            Stock("DDD", "DDD Co", 100.0),
            Stock("CCC", "CCC Co", 100.0),
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
            Stock("XYZ", "XYZ and ABC", 100.0),
        )

        val result = stocks.matches(query)
        val expected = listOf(
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
            Stock("XYZ", "XYZ and ABC", 100.0),
        )

        assertEquals(expected, result)
    }

    @Test
    fun `matches() returns an empty list if no matches`() {
        val query = "P"
        val stocks = listOf(
            Stock("FFF", "FFF Co", 100.0),
            Stock("DDD", "DDD Co", 100.0),
            Stock("CCC", "CCC Co", 100.0),
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
            Stock("XYZ", "XYZ and ABC", 100.0),
        )

        val result = stocks.matches(query)
        val expected = emptyList<Stock>()

        assertEquals(expected, result)
    }

    @Test
    fun `exact matches show first (AB)`() {
        val query = "AB"
        val stocks = listOf(
            Stock("XYZ", "XYZ and ABC", 100.0),
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
        )

        val result = stocks.sortedWith(Stock.compareQuery(query))

        assertEquals(3, result.size)
        assertEquals(
            Stock("AB", "Absolute", 100.0),
            result[0]
        )
    }

    @Test
    fun `exact matches show first (ABC)`() {
        val query = "ABC"
        val stocks = listOf(
            Stock("XYZ", "XYZ and ABC", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
        )

        val result = stocks.sortedWith(Stock.compareQuery(query))

        assertEquals(2, result.size)
        assertEquals(
            Stock("ABC", "ABC Industries", 100.0),
            result[0]
        )
    }
}
