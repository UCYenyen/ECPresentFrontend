package com.example.ecpresent.ui.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.ui.view.components.elements.MyNavigationBar
import com.example.ecpresent.ui.view.pages.GetStartedView

enum class AppView(val title: String, val icon: ImageVector? = null) {
    Landing("Landing"),

    SignUp("Sign Up"),
    SignIn("Sign In"),
    Profile("Profile", Icons.Filled.ManageAccounts),
    OverallFeedback("Overall Feedback"),

    Learning("Learning", Icons.Filled.CollectionsBookmark),
    LearningProgress("Learning Progress"),

    Present("Presentations", Icons.Filled.CameraAlt),
    PresentationHistory("Presentations"),
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
        BottomNavItem(AppView.Present, label = "Presentation"),
        BottomNavItem(AppView.Profile, label = "Profile"),
    )

    Scaffold(topBar = {
        MyTopAppBar(
            currentView = currentView,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() })},
        bottomBar = {
            MyBottomNavigationBar(
                navController = navController,
                currentDestination = currentDestination,
                items = bottomNavItems
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = AppView.Landing.name
        ) {
            composable(route = AppView.Landing.name) {
                GetStartedView(navController = navController)
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
            Text(text = currentView?.title ?: "ECPresent")
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
    items: List<BottomNavItem>
) {
    MyNavigationBar(
        navController = navController,
        currentDestination = currentDestination,
        items = items
    )
}

data class BottomNavItem(val view: AppView, val label: String)