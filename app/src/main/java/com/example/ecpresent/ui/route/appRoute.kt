package com.example.ecpresent.ui.route

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.ui.view.components.elements.MyNavigationBar
import com.example.ecpresent.ui.view.pages.GetStartedView
import com.example.ecpresent.ui.view.pages.auth.SignInView
import com.example.ecpresent.ui.view.pages.auth.SignUpView
import com.example.ecpresent.ui.view.pages.learning.LearningIndexView
import com.example.ecpresent.ui.view.pages.learning.LearningProgressView
import com.example.ecpresent.ui.view.pages.presentation.PresentationHistoryView
import com.example.ecpresent.ui.view.pages.presentation.PresentationIndexView
import com.example.ecpresent.ui.view.pages.presentation.PresentationUploadVideoView
import com.example.ecpresent.ui.view.pages.profile.ProfileIndexView

enum class AppView(
    val title: String,
    val icon: ImageVector? = null,
    val canNavigateBack: Boolean = false
) {
    Landing("Landing"),

    SignUp("Sign Up", canNavigateBack = false),
    SignIn("Sign In", canNavigateBack = false),
    Profile("Profile", Icons.Filled.ManageAccounts),
    OverallFeedback("Overall Feedback", canNavigateBack = true),

    Learning("Learning", Icons.Filled.CollectionsBookmark),
    LearningProgress("Learning Progress", canNavigateBack = true),

    Presentation("Presentations", Icons.Filled.CameraAlt),
    PresentationHistory("Presentations", canNavigateBack = true),
    TakePresentation("Presentation"),
    FollowUpQuestion("QNA"),
    PresentationFeedback("Presentation Feedback"),
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppRoute() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val currentView = AppView.entries.find { it.name == currentRoute }

    val bottomNavItems = listOf(
        BottomNavItem(AppView.Learning, label = "Learning"),
        BottomNavItem(AppView.Presentation, label = "Presentation"),
        BottomNavItem(AppView.Profile, label = "Profile"),
    )

    val tabOrder = listOf(
        AppView.Learning.name,
        AppView.Presentation.name,
        AppView.Profile.name
    )

    val showBars = currentRoute != AppView.Landing.name &&
            currentRoute != AppView.SignIn.name &&
            currentRoute != AppView.SignUp.name

    Scaffold(
        topBar = {
            if (showBars) {
                MyTopAppBar(
                    currentView = currentView,
                    canNavigateBack = (currentView?.canNavigateBack == true) && (navController.previousBackStackEntry != null),
                    navigateUp = { navController.navigateUp() }
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

            modifier = if(showBars) Modifier.padding(innerPadding) else Modifier.fillMaxWidth(),
            navController = navController,
            startDestination = AppView.Landing.name,

            enterTransition = {
                val fromIndex = tabOrder.indexOf(initialState.destination.route)
                val toIndex = tabOrder.indexOf(targetState.destination.route)

                if (fromIndex != -1 && toIndex != -1) {
                    if (toIndex > fromIndex) {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(400)
                        )
                    } else {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(400)
                        )
                    }
                } else {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    )
                }
            },
            exitTransition = {
                val fromIndex = tabOrder.indexOf(initialState.destination.route)
                val toIndex = tabOrder.indexOf(targetState.destination.route)

                if (fromIndex != -1 && toIndex != -1) {
                    if (toIndex > fromIndex) {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(400)
                        )
                    } else {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(400)
                        )
                    }
                } else {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    )
                }
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
            composable(route = AppView.Landing.name) {
                GetStartedView(navController = navController)
            }
            composable(route = AppView.SignIn.name) {
                SignInView(navController = navController)
            }
            composable(route = AppView.SignUp.name) {
                SignUpView(navController = navController)
            }
            composable(route = AppView.Learning.name) {
                LearningIndexView(navController = navController)
            }
            composable(route = AppView.LearningProgress.name){
                LearningProgressView(navController = navController)
            }
            composable(route = AppView.Presentation.name) {
                PresentationIndexView(navController = navController)
            }
            composable(route = AppView.PresentationHistory.name){
                PresentationHistoryView()
            }
            composable(route = AppView.TakePresentation.name) {
                PresentationUploadVideoView(navController = navController)
            }
            composable(route = AppView.Profile.name) {
                ProfileIndexView()
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
    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)) {
        MyNavigationBar(
            navController = navController,
            currentDestination = currentDestination,
            items = items
        )
    }
}

data class BottomNavItem(val view: AppView, val label: String)