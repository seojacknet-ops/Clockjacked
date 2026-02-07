package com.clockjacked.app.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clockjacked.app.ClockJackedApp
import com.clockjacked.app.ui.screens.AboutScreen
import com.clockjacked.app.ui.screens.AddClockScreen
import com.clockjacked.app.ui.screens.CrewModeScreen
import com.clockjacked.app.ui.screens.DashboardScreen
import com.clockjacked.app.viewmodel.ClockViewModel

private const val TRANSITION_DURATION = 250

@Composable
fun ClockJackedNavGraph(
    application: ClockJackedApp,
    navController: NavHostController = rememberNavController()
) {
    val viewModel: ClockViewModel = viewModel(
        factory = ClockViewModel.Factory(application.clockRepository, application.preferencesManager)
    )
    val isMusicMuted by viewModel.isMusicMuted.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "dashboard",
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(TRANSITION_DURATION)
            ) + fadeIn(animationSpec = tween(TRANSITION_DURATION))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(TRANSITION_DURATION)
            ) + fadeOut(animationSpec = tween(TRANSITION_DURATION))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(TRANSITION_DURATION)
            ) + fadeIn(animationSpec = tween(TRANSITION_DURATION))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(TRANSITION_DURATION)
            ) + fadeOut(animationSpec = tween(TRANSITION_DURATION))
        }
    ) {
        composable("dashboard") {
            DashboardScreen(
                onNavigateToAddClock = { navController.navigate("add_clock") },
                onNavigateToAbout = { navController.navigate("about") },
                onNavigateToCrewMode = { navController.navigate("crew_mode") },
                isMusicMuted = isMusicMuted,
                onToggleMute = { viewModel.toggleMusicMute() },
                viewModel = viewModel
            )
        }
        composable("add_clock") {
            AddClockScreen(
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable("about") {
            AboutScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("crew_mode") {
            CrewModeScreen(
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}
