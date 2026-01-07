package com.example.ecpresent.ui.route


import PresentationQNAView
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.ui.view.components.elements.MyNavigationBar
import com.example.ecpresent.ui.view.pages.GetStartedView
import com.example.ecpresent.ui.view.pages.SplashScreen
import com.example.ecpresent.ui.view.pages.auth.SignInView
import com.example.ecpresent.ui.view.pages.auth.SignUpFromGuestView
import com.example.ecpresent.ui.view.pages.auth.SignUpView
import com.example.ecpresent.ui.view.pages.learning.LearningIndexView
import com.example.ecpresent.ui.view.pages.learning.LearningProgressView
import com.example.ecpresent.ui.view.pages.learning.LearningDetailView
import com.example.ecpresent.ui.view.pages.learning.LearningProgressDetailView
import com.example.ecpresent.ui.view.pages.learning.LearningsView
import com.example.ecpresent.ui.view.pages.presentation.PresentationFeedbackView
import com.example.ecpresent.ui.view.pages.presentation.PresentationHistoryView
import com.example.ecpresent.ui.view.pages.presentation.PresentationIndexView
import com.example.ecpresent.ui.view.pages.presentation.PresentationUploadVideoView
import com.example.ecpresent.ui.view.pages.profile.ProfileIndexView
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import com.example.ecpresent.ui.viewmodel.LearningViewModel
import com.example.ecpresent.ui.viewmodel.PresentationViewModel

enum class AppView(
    val title: String,
    val icon: ImageVector? = null,
) {
    SplashScreen("Splash Screen"),
    Landing("Landing"),
    SignUp("Sign Up"),
    SignIn("Sign In"),
    GuestSignUp("Guest Sign Up"),
    Profile("Profile", Icons.Filled.ManageAccounts),
    OverallFeedback("Overall Feedback"),

    Learning("Learning", Icons.Filled.CollectionsBookmark),
    Learnings("Learnings"),
    LearningProgresses("Learning Progress"),

    Presentation("Presentations", Icons.Filled.CameraAlt),
    PresentationHistory("Presentations"),
    TakePresentation("Presenting"),
    FollowUpQuestion("QNA"),
    PresentationFeedback("Presentation Feedback"),
}

@Composable
fun AppRoute() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val learningViewModel: LearningViewModel = viewModel()
    val presentationViewModel: PresentationViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentRouteString = currentDestination?.route
    val currentView =
        AppView.entries.find { it.name == currentRouteString?.split("/")?.firstOrNull() }
            ?: AppView.entries.find { it.name == currentRouteString }

    val bottomNavItems = listOf(
        BottomNavItem(AppView.Learning, label = "Learning"),
        BottomNavItem(AppView.Presentation, label = "Presentation"),
        BottomNavItem(AppView.Profile, label = "Profile"),
    )

    val topLevelRoutes = listOf(
        AppView.Learning.name,
        AppView.Presentation.name,
        AppView.Profile.name,
        AppView.Landing.name,
        AppView.SplashScreen.name,
        AppView.SignIn.name,
        AppView.SignUp.name
    )

    val showBackButton =
        (currentRouteString !in topLevelRoutes) && (navController.previousBackStackEntry != null)

    val showBars = currentRouteString !in listOf(
        AppView.Landing.name,
        AppView.SignIn.name,
        AppView.SignUp.name,
        AppView.SplashScreen.name,
//        AppView.TakePresentation.name,   // Hide during upload
//        AppView.FollowUpQuestion.name,   // Hide during QnA
//        AppView.PresentationFeedback.name // Hide during Feedback
    )

    Scaffold(
        topBar = {
            if (showBars) {
                MyTopAppBar(
                    currentView = currentView,
                    canNavigateBack = showBackButton,
                    navigateUp = {
                        if (currentRouteString?.startsWith(AppView.PresentationFeedback.name) == true) {
                            navController.navigate(AppView.Presentation.name) {
                                popUpTo(AppView.Presentation.name) { inclusive = true }
                            }
                        } else {
                            navController.navigateUp()
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBars) {
                MyBottomNavigationBar(
                    navController = navController,
                    currentDestination = currentDestination,
                    items = bottomNavItems
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = if (showBars) Modifier.padding(innerPadding) else Modifier.fillMaxWidth(),
            navController = navController,
            startDestination = AppView.SplashScreen.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            }
        ) {
            composable(route = AppView.SplashScreen.name) {
                SplashScreen(navController = navController)
            }
            composable(route = AppView.Landing.name) {
                GetStartedView(navController = navController)
            }
            composable(route = AppView.SignIn.name) {
                SignInView(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
            composable(route = AppView.SignUp.name) {
                SignUpView(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
            composable(route = AppView.GuestSignUp.name) {
                SignUpFromGuestView(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
            composable(route = AppView.Learning.name) {
                LearningIndexView(
                    navController = navController,
                    learningViewModel = learningViewModel
                )
            }
            composable(route = AppView.Learnings.name) {
                LearningsView(navController = navController, learningViewModel = learningViewModel)
            }
            composable(route = "${AppView.Learning.name}/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                LearningDetailView(
                    id = id,
                    navController = navController,
                    learningViewModel = learningViewModel
                )
            }
            composable(route = AppView.LearningProgresses.name) {
                LearningProgressView(
                    navController = navController,
                    learningViewModel = learningViewModel
                )
            }
            composable(route = "${AppView.LearningProgresses.name}/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
                LearningProgressDetailView(
                    progressId = id,
                    navController = navController,
                    learningViewModel = learningViewModel
                )
            }
            composable(route = AppView.Presentation.name) {
                PresentationIndexView(navController = navController)
            }
            composable(route = AppView.PresentationHistory.name) {
                PresentationHistoryView(navController, presentationViewModel)
            }

            composable(route = AppView.TakePresentation.name) {
                PresentationUploadVideoView(
                    navController = navController,
                    presentationViewModel = presentationViewModel
                )
            }

            composable(route = "${AppView.FollowUpQuestion.name}/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                PresentationQNAView(
                    navController = navController,
                    presentationViewModel = presentationViewModel,
                    presentationId = id
                )
            }

            composable(route = "${AppView.PresentationFeedback.name}/{id}") {backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                PresentationFeedbackView(
                    navController = navController,
                    presentationViewModel = presentationViewModel,
                    presentationId = id
                )
            }

            composable(route = AppView.Profile.name) {
                ProfileIndexView(navController = navController, authViewModel = authViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentView: AppView?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = currentView?.title ?: "ECPresent",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun MyBottomNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    items: List<BottomNavItem>,
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)) {
        MyNavigationBar(
            navController = navController,
            currentDestination = currentDestination,
            items = items
        )
    }
}

data class BottomNavItem(val view: AppView, val label: String)