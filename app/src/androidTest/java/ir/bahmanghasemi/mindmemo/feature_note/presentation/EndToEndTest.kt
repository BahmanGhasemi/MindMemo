package ir.bahmanghasemi.mindmemo.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import ir.bahmanghasemi.mindmemo.MainActivity
import ir.bahmanghasemi.mindmemo.core.util.TestTags
import ir.bahmanghasemi.mindmemo.di.AppModule
import ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.AddNoteViewModel
import ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.composable.AddNoteScreen
import ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.NotesViewModel
import ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.composable.NotesScreen
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.AddNote
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.Notes
import ir.bahmanghasemi.mindmemo.ui.theme.MindMemoTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class EndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.activity.setContent {
            MindMemoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Notes) {
                    composable<Notes> {
                        val viewModel: NotesViewModel = hiltViewModel()
                        NotesScreen(
                            state = viewModel.state,
                            onEvent = viewModel::onEvent,
                            onNavigate = { id, color ->
                                navController.navigate(
                                    AddNote(id ?: -1, color)
                                )
                            }
                        )
                    }
                    composable<AddNote> {
                        val viewModel: AddNoteViewModel = hiltViewModel()
                        val args = it.toRoute<AddNote>()
                        AddNoteScreen(
                            state = viewModel.state,
                            oneTimeEvent = viewModel.oneTimeEvent,
                            onEvent = viewModel::onEvent,
                            onNavigateUp = { navController.navigateUp() },
                            id = args.id,
                            colorId = args.colorId
                        )
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_EditItAfter() {
        composeRule.onNodeWithContentDescription("Add").performClick()

        composeRule.onNodeWithContentDescription("TitleTextField").performTextInput("test_title")
        composeRule.onNodeWithContentDescription("ContentTextField")
            .performTextInput("test-content")
        composeRule.onNodeWithContentDescription("Save")

        composeRule.onNodeWithText("test_title").assertIsDisplayed()
        composeRule.onNodeWithText("test-content").assertIsDisplayed()
        composeRule.onNodeWithText("test_title").performClick()

        composeRule.onNodeWithContentDescription("TitleTextField").assertTextEquals("test_title")
        composeRule.onNodeWithContentDescription("ContentTextField")
            .assertTextEquals("test-content")
        composeRule.onNodeWithContentDescription("TitleTextField")
            .performTextReplacement("updated_title")
        composeRule.onNodeWithContentDescription("ContentTextField")
            .performTextReplacement("updated_contnet")
        composeRule.onNodeWithContentDescription("Save").performClick()

        composeRule.onNodeWithText("updated_title").assertIsDisplayed()
        composeRule.onNodeWithText("updated_contnet").assertIsDisplayed()
    }

    @Test
    fun deleteItem_RestoreIt() {
        for (i in 1..3) {
            composeRule.onNodeWithContentDescription("Add").performClick()

            composeRule.onNodeWithContentDescription("TitleTextField").performTextInput("title$i")
            composeRule.onNodeWithContentDescription("ContentTextField")
                .performTextInput("content$i")
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("title1").assertIsDisplayed()
        composeRule.onNodeWithText("title2").assertIsDisplayed()
        composeRule.onNodeWithText("title3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag("Title").performClick()
        composeRule.onNodeWithTag("Ascending").performClick()

        composeRule.onAllNodesWithContentDescription("Delete")[0].performClick()
        composeRule.onNodeWithText("title1").assertDoesNotExist()

        composeRule.onNodeWithText("Note item deleted", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Undo", useUnmergedTree = true).performClick()
        composeRule.onNodeWithText("title1").assertIsDisplayed()
    }

    @Test
    fun sortByTitle_ascending() {
        for (i in 1..3) {
            composeRule.onNodeWithContentDescription("Add").performClick()

            composeRule.onNodeWithContentDescription("TitleTextField").performTextInput("title $i")
            composeRule.onNodeWithContentDescription("ContentTextField")
                .performTextInput("content $i")
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("title 1").assertIsDisplayed()
        composeRule.onNodeWithText("title 2").assertIsDisplayed()
        composeRule.onNodeWithText("title 3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag("Ascending").performClick()
        composeRule.onNodeWithTag("Title").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("title 1")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("title 2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("title 3")
    }

    @Test
    fun sortByTitle_descending() {
        for (i in 1..3) {
            composeRule.onNodeWithContentDescription("Add").performClick()

            composeRule.onNodeWithContentDescription("TitleTextField").performTextInput("title $i")
            composeRule.onNodeWithContentDescription("ContentTextField")
                .performTextInput("content $i")
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("title 1").assertIsDisplayed()
        composeRule.onNodeWithText("title 2").assertIsDisplayed()
        composeRule.onNodeWithText("title 3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag("Title").performClick()
        composeRule.onNodeWithTag("Descending").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("title 3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("title 2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("title 1")
    }

    @Test
    fun sortByDate_ascending() {
        for (i in 1..3) {
            composeRule.onNodeWithContentDescription("Add").performClick()

            composeRule.onNodeWithContentDescription("TitleTextField").performTextInput("$i")
            composeRule.onNodeWithContentDescription("ContentTextField")
                .performTextInput("$i")
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag("Date").performClick()
        composeRule.onNodeWithTag("Ascending").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("1")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("3")
    }

    @Test
    fun sortByDate_descending() {
        for (i in 1..3) {
            composeRule.onNodeWithContentDescription("Add").performClick()

            composeRule.onNodeWithContentDescription("TitleTextField").performTextInput("$i")
            composeRule.onNodeWithContentDescription("ContentTextField")
                .performTextInput("$i")
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag("Date").performClick()
        composeRule.onNodeWithTag("Descending").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("1")
    }
}