package rs.raf.rmaprojekat1.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import rs.raf.rmaprojekat1.catDetails.detail.catDetails
import rs.raf.rmaprojekat1.cats.list.cats

@Composable
fun CatsNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "breeds",

    ) {

        cats(
            route = "breeds",
            onCatClick = {
                Log.d("TEST", "PORUKA DA JE USAO u jednu macku")
                navController.navigate(route = "breeds/$it")
            }
        )

        catDetails(
            route = "breeds/{catId}",
            arguments = listOf(
                navArgument(name = "catId") {
                    nullable = false
                    type = NavType.StringType
                }
            ),
            onClose = {
                navController.navigateUp()
            }
        )
    }
}