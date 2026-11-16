package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.repository.DetectiveRepository
import com.example.data.repository.LocalGuestRepository
import com.example.ui.auth.AuthScreen
import com.example.ui.auth.signOut
import com.example.ui.screens.ArchiveScreen
import com.example.ui.screens.CharacterStudioScreen
import com.example.ui.screens.FairyMapScreen
import com.example.ui.screens.HiddenObjectSceneScreen
import com.example.ui.screens.QuestsScreen
import com.example.ui.theme.FairyDetectiveTheme
import com.example.ui.viewmodel.DetectiveViewModel
import com.example.ui.viewmodel.GameUiState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

const val ROUTE_MAP = "map"
const val ROUTE_QUESTS = "quests"
const val ROUTE_ARCHIVE = "archive"
const val ROUTE_PROFILE = "profile"

private data class NavTab(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

private val mainTabs = listOf(
    NavTab(ROUTE_MAP, "Карта", Icons.Filled.Explore, Icons.Outlined.Explore),
    NavTab(ROUTE_QUESTS, "Квесты", Icons.Filled.Assignment, Icons.Outlined.Assignment),
    NavTab(ROUTE_ARCHIVE, "Улики", Icons.Filled.WorkOutline, Icons.Outlined.WorkOutline),
    NavTab(ROUTE_PROFILE, "Сыщик", Icons.Filled.Face, Icons.Outlined.Face)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FairyDetectiveTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val auth = Firebase.auth
    val guestRepo = remember(context) { LocalGuestRepository(context) }
    var currentUser by remember { mutableStateOf(auth.currentUser) }
    var isGuestMode by remember { mutableStateOf(guestRepo.isGuestActive()) }

    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            currentUser = firebaseAuth.currentUser
        }
        auth.addAuthStateListener(listener)
        onDispose { auth.removeAuthStateListener(listener) }
    }

    val user = currentUser
    when {
        user != null -> {
            key("cloud_${user.uid}") {
                AuthenticatedGameHost(
                    currentUserId = user.uid,
                    isGuestMode = false,
                    onExitGuestMode = {}
                )
            }
        }
        isGuestMode -> {
            key("guest_local") {
                AuthenticatedGameHost(
                    currentUserId = "guest_local",
                    isGuestMode = true,
                    onExitGuestMode = {
                        guestRepo.setGuestActive(false)
                        isGuestMode = false
                    }
                )
            }
        }
        else -> {
            AuthScreen(
                onAuthSuccess = { currentUser = auth.currentUser },
                onPlayAsGuest = {
                    guestRepo.setGuestActive(true)
                    isGuestMode = true
                }
            )
        }
    }
}

@Composable
fun AuthenticatedGameHost(
    currentUserId: String,
    isGuestMode: Boolean,
    onExitGuestMode: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = remember(context) { CredentialManager.create(context) }

    val viewModel: DetectiveViewModel = viewModel(
        key = "${if (isGuestMode) "guest" else "cloud"}_$currentUserId",
        factory = viewModelFactory {
            initializer {
                val app = checkNotNull(this[APPLICATION_KEY])
                val dataSource = if (isGuestMode) {
                    LocalGuestRepository(app)
                } else {
                    val databaseId = app.getString(R.string.firestore_database_id)
                    val db = FirebaseFirestore.getInstance(databaseId)
                    DetectiveRepository(db)
                }
                DetectiveViewModel(dataSource, currentUserId)
            }
        }
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activeScene by viewModel.activeScene.collectAsStateWithLifecycle()
    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(toastMessage) {
        toastMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearToast()
        }
    }

    when (val state = uiState) {
        is GameUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Открываем архив школы сыска...", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        is GameUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { signOut(credentialManager, coroutineScope) }) {
                        Text("Сменить аккаунт")
                    }
                }
            }
        }

        is GameUiState.Ready -> {
            val profile = state.profile
            if (profile == null || !profile.onboardingCompleted) {
                CharacterStudioScreen(
                    isOnboarding = true,
                    initialProfile = profile,
                    onSave = { name, gender, skin, hairStyle, hairColor, hat, coat, acc ->
                        viewModel.completeOnboarding(
                            name = name,
                            gender = gender,
                            skinToneIndex = skin,
                            hairStyleIndex = hairStyle,
                            hairColorIndex = hairColor,
                            hatId = hat,
                            coatId = coat,
                            accessoryId = acc
                        )
                    }
                )
            } else {
                val scene = activeScene
                if (scene != null) {
                    HiddenObjectSceneScreen(
                        scene = scene,
                        hintsBalance = profile.hintsBalance,
                        onItemTapped = { viewModel.onItemTapped(it) },
                        onEmptyTapped = { viewModel.onEmptySceneTapped() },
                        onUseHint = { viewModel.useHint() },
                        onAdvanceIntro = { viewModel.advanceIntroDialogue() },
                        onSkipIntro = { viewModel.skipIntroDialogue() },
                        onAdvanceOutro = { viewModel.advanceOutroDialogue() },
                        onReplay = { viewModel.startLocation(scene.location.id, scene.difficulty) },
                        onExit = { viewModel.exitActiveScene() }
                    )
                } else {
                MainHubScaffold(
                        state = state,
                        isGuestMode = isGuestMode,
                        snackbarHostState = snackbarHostState,
                        viewModel = viewModel,
                        onSignOut = {
                            if (isGuestMode) {
                                onExitGuestMode()
                            } else {
                                signOut(credentialManager, coroutineScope)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MainHubScaffold(
    state: GameUiState.Ready,
    isGuestMode: Boolean,
    snackbarHostState: SnackbarHostState,
    viewModel: DetectiveViewModel,
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ROUTE_MAP
    val profile = state.profile ?: return

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useRail = maxWidth >= 680.dp

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                if (!useRail) {
                    NavigationBar {
                        mainTabs.forEach { tab ->
                            val selected = currentRoute == tab.route
                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    navController.navigate(tab.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(ROUTE_MAP) { saveState = true }
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.label
                                    )
                                },
                                label = { Text(tab.label) },
                                modifier = Modifier.testTag("nav_${tab.route}")
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (useRail) {
                    NavigationRail {
                        mainTabs.forEach { tab ->
                            val selected = currentRoute == tab.route
                            NavigationRailItem(
                                selected = selected,
                                onClick = {
                                    navController.navigate(tab.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(ROUTE_MAP) { saveState = true }
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.label
                                    )
                                },
                                label = { Text(tab.label) },
                                modifier = Modifier.testTag("rail_${tab.route}")
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = ROUTE_MAP,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(ROUTE_MAP) {
                        FairyMapScreen(
                            profile = profile,
                            locationsProgress = state.locations,
                            onSelectDifficulty = { viewModel.setDifficulty(it) },
                            onStartLocation = { loc, diff -> viewModel.startLocation(loc.id, diff) }
                        )
                    }
                    composable(ROUTE_QUESTS) {
                        QuestsScreen(
                            questsProgress = state.quests,
                            hintsBalance = profile.hintsBalance,
                            onClaimReward = { viewModel.claimQuestReward(it) }
                        )
                    }
                    composable(ROUTE_ARCHIVE) {
                        ArchiveScreen(
                            clues = state.clues,
                            achievements = state.achievements
                        )
                    }
                    composable(ROUTE_PROFILE) {
                        CharacterStudioScreen(
                            isOnboarding = false,
                            isGuestMode = isGuestMode,
                            initialProfile = profile,
                            onSave = { name, gender, skin, hairStyle, hairColor, hat, coat, acc ->
                                viewModel.updateCustomization(
                                    name = name,
                                    gender = gender,
                                    skinToneIndex = skin,
                                    hairStyleIndex = hairStyle,
                                    hairColorIndex = hairColor,
                                    hatId = hat,
                                    coatId = coat,
                                    accessoryId = acc
                                )
                            },
                            onSignOut = onSignOut
                        )
                    }
                }
            }
        }
    }
}
