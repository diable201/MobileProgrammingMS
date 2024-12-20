package com.example.finalproject.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalproject.ui.theme.screens.CartScreen
import com.example.finalproject.ui.theme.screens.CategoryScreen
import com.example.finalproject.ui.theme.screens.CheckoutScreen
import com.example.finalproject.ui.theme.screens.LoginScreen
import com.example.finalproject.ui.theme.screens.ProductDetailScreen
import com.example.finalproject.ui.theme.screens.ProfileScreen
import com.example.finalproject.ui.theme.screens.RegisterScreen
import com.example.finalproject.ui.theme.screens.ShopScreen
import com.example.finalproject.ui.theme.screens.WebSocketScreen
import com.example.finalproject.viewmodel.CartViewModel
import com.example.finalproject.viewmodel.CheckoutViewModel
import com.example.finalproject.viewmodel.ProfileViewModel
import com.example.finalproject.viewmodel.ReviewViewModel
import com.example.finalproject.viewmodel.ShopViewModel
import com.example.finalproject.viewmodel.WebSocketViewModel

sealed class Screen(val route: String) {
    data object Register : Screen("register")
    data object Login : Screen("login")
    data object Shop : Screen("shop")
    data object Cart : Screen("cart")
    data object Profile : Screen("profile")
    data object Checkout : Screen("checkout")
    data object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }

    data object WebSocket : Screen("websocket_screen")
}

@Composable
fun  NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val shopViewModel: ShopViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val checkoutViewModel: CheckoutViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val reviewViewModel: ReviewViewModel = hiltViewModel()
    val webSocketViewModel: WebSocketViewModel = hiltViewModel()

    NavHost(
        navController = navController, startDestination = Screen.Login.route, modifier = modifier
    ) {
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Shop.route) {
            ShopScreen(
                navController = navController, viewModel = shopViewModel
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                shopViewModel = shopViewModel
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController, viewModel = profileViewModel
            )
        }
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                navController = navController,
                checkoutViewModel = checkoutViewModel,
                cartViewModel = cartViewModel,
                shopViewModel = shopViewModel
            )
        }
        composable(
            Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                navController = navController,
                productId = productId,
                shopViewModel = shopViewModel,
                cartViewModel = cartViewModel,
                reviewViewModel = reviewViewModel
            )
        }
        composable(Screen.WebSocket.route) {
            WebSocketScreen(
                navController = navController, webSocketViewModel = webSocketViewModel
            )
        }
        composable(
            "category/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            CategoryScreen(
                navController = navController, categoryId = categoryId, viewModel = shopViewModel
            )
        }
    }
}