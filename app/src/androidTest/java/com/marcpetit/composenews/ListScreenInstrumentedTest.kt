package com.marcpetit.composenews

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcpetit.composenews.ui.views.ListScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ListScreenInstrumentedTest {
    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun itemsAddedToScreen() {
        composeTestRule.setContent {
            ListScreen(
                navController = rememberNavController()
            )
        }
        composeTestRule.onNodeWithText("foo").assertExists()
        composeTestRule.onNodeWithText("bar").assertExists()
    }
}
