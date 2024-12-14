package com.abdelmageed.marveltask.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdelmageed.marveltask.ui.details.DetailsUi
import com.abdelmageed.marveltask.ui.details.SectionsListDetails
import com.abdelmageed.marveltask.ui.home.HomeUi
import com.abdelmageed.marveltask.ui.search.SearchUi


enum class Screens {
    HOME,
    CHARACTER_DETAILS,
    SECTIONS_DETAILS,
    SEARCH
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screens.HOME.name)
    data object CharacterDetails : NavigationItem(Screens.CHARACTER_DETAILS.name)
    data object SectionsDetails : NavigationItem(Screens.SECTIONS_DETAILS.name)
    data object Search : NavigationItem(Screens.SEARCH.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
    startDestination: String = Screens.HOME.name
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            HomeUi(navController, innerPadding)
        }
        composable(
            "${NavigationItem.CharacterDetails.route}/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val blogId = backStackEntry.arguments?.getInt("id")
            DetailsUi(blogId, navController, innerPadding)
        }
        composable(
            "${NavigationItem.SectionsDetails.route}/{resourceUri}",
            arguments = listOf(navArgument("resourceUri") { type = NavType.StringType })
        ) { backStackEntry ->
            val resourceUri = backStackEntry.arguments?.getString("resourceUri")
            SectionsListDetails(navController, innerPadding, resourceUri)
        }
        composable(NavigationItem.Search.route) {
            SearchUi(navController)
        }
    }
}