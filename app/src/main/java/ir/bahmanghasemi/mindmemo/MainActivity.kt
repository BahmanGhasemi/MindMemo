package ir.bahmanghasemi.mindmemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.AddNoteViewModel
import ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.composable.AddNoteScreen
import ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.NotesViewModel
import ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.composable.NotesScreen
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.AddNote
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.Notes
import ir.bahmanghasemi.mindmemo.ui.theme.MindMemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMemoTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
private fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Notes) {
        composable<Notes> {
            val viewModel: NotesViewModel = hiltViewModel()
            NotesScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                onNavigate = {
                    navController.navigate(AddNote(id = it ?: -1))
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
                onNavigateUp = {
                    navController.navigateUp()
                },
                id = args.id
            )
        }
    }
}
