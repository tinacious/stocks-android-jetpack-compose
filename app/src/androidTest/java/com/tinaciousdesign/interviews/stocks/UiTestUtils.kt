package com.tinaciousdesign.interviews.stocks

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@OptIn(ExperimentalTestApi::class)
fun ComposeTestRule.waitForText(
    text: String,
    timeoutMillis: Long = 5000
) {
    waitUntilAtLeastOneExists(hasText(text), timeoutMillis = timeoutMillis)
}

fun ComposeTestRule.textIsDisplayed(
    text: String,
    expectedOccurrences: Int = 1
) {
    if (expectedOccurrences == 1) {
        onNodeWithText(text).assertIsDisplayed()
    } else {
        assertEquals(onAllNodesWithText(text).fetchSemanticsNodes().size, expectedOccurrences)
    }
}

fun ComposeTestRule.textIsDisplayedAtLeastOnce(
    text: String,
    minOccurrences: Int = 1
) {
    assertTrue(this.onAllNodesWithText(text).fetchSemanticsNodes().size >= minOccurrences)
}

@OptIn(ExperimentalTestApi::class)
fun ComposeTestRule.sleep(
    timeoutMillis: Long
) {
    @Suppress("SwallowedException")
    try {
        // Random string that will never be found
        waitUntilAtLeastOneExists(hasText("446fc9cdc8e63d9f11fb6bacd2f51ef5!"), timeoutMillis = timeoutMillis)
    } catch (t: Throwable) {
        // swallow this exception
    }
}
