package ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import ir.bahmanghasemi.mindmemo.MainActivity
import ir.bahmanghasemi.mindmemo.core.util.TestTags
import ir.bahmanghasemi.mindmemo.di.AppModule
import ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.NotesViewModel
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.Notes
import ir.bahmanghasemi.mindmemo.ui.theme.MindMemoTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            MindMemoTheme {
                NavHost(navController = navController, startDestination = Notes) {
                    composable<Notes> {

                        val viewModel: NotesViewModel = hiltViewModel()
                        NotesScreen(
                            state = viewModel.state,
                            onEvent = viewModel::onEvent,
                            onNavigate = { _, _ -> }
                        )
                    }
                }
            }
        }
    }

    @Test
    fun toggleOrderSection_ChangeVisibility() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).isDisplayed()
    }

}