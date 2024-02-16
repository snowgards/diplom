package ru.arinae_va.lensa.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import ru.arinae_va.lensa.domain.model.SpecialistModel
import ru.arinae_va.lensa.presentation.common.screen.ErrorScreen
import ru.arinae_va.lensa.presentation.feature.auth.compose.AuthScreen
import ru.arinae_va.lensa.presentation.feature.auth.compose.AuthViewModel
import ru.arinae_va.lensa.presentation.feature.auth.compose.OtpScreen
import ru.arinae_va.lensa.presentation.feature.feed.compose.FeedScreen
import ru.arinae_va.lensa.presentation.feature.feed.compose.FeedViewModel
import ru.arinae_va.lensa.presentation.feature.feed.compose.SpecialistDetailsScreen
import ru.arinae_va.lensa.presentation.feature.feed.compose.SpecialistDetailsViewModel
import ru.arinae_va.lensa.presentation.feature.onboarding.compose.LensaSplashScreen
import ru.arinae_va.lensa.presentation.feature.onboarding.compose.OnboardingScreen
import ru.arinae_va.lensa.presentation.feature.onboarding.compose.OnboardingViewModel
import ru.arinae_va.lensa.presentation.feature.profile.compose.ProfileScreen
import ru.arinae_va.lensa.presentation.feature.profile.compose.ProfileViewModel
import ru.arinae_va.lensa.presentation.feature.registration.compose.RegistrationRoleSelectorScreen
import ru.arinae_va.lensa.presentation.feature.registration.compose.RegistrationScreen
import ru.arinae_va.lensa.presentation.feature.registration.compose.RegistrationViewModel
import ru.arinae_va.lensa.presentation.feature.settings.compose.AboutAppScreen
import ru.arinae_va.lensa.presentation.feature.settings.compose.FeedbackScreen
import ru.arinae_va.lensa.presentation.feature.settings.compose.SettingsScreen
import ru.arinae_va.lensa.presentation.feature.settings.compose.SettingsViewModel
import java.net.URLDecoder
import java.net.URLEncoder

const val PHONE_ID_KEY = "phone"
const val ERROR_TEXT_KEY = "errorText"
const val IS_SPECIALIST_KEY = "isSpecialist"
const val PROFILE_UID_KEY = "profileUid"
const val IS_SELF_PROFILE_KEY = "isSelf"

fun <A> String?.fromJson(type: Class<A>): A {
    //return Gson().fromJson(this, type)
    return Gson().fromJson(URLDecoder.decode(this, "utf-8"), type)
}

fun <A> A.toJson(): String {
    //return Gson().toJson(this)
    return URLEncoder.encode(Gson().toJson(this), "utf-8")
}

@Composable
fun LensaNavGraph(
    navController: NavHostController,
) {
    // TODO вложенные графы для фичей, шаринг вью моделей
    return NavHost(
        navController = navController,
        startDestination = LensaScreens.SPLASH_SCREEN.name,
    ) {

        composable(route = LensaScreens.SPLASH_SCREEN.name) {
            val viewModel = hiltViewModel<OnboardingViewModel>()
            LensaSplashScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(route = LensaScreens.ONBOARDING_SCREEN.name) {
            val viewModel = hiltViewModel<OnboardingViewModel>()
            OnboardingScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(route = LensaScreens.AUTH_SCREEN.name) {
            val viewModel = hiltViewModel<AuthViewModel>()
            AuthScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(route = "${LensaScreens.OTP_SCREEN.name}/{$PHONE_ID_KEY}",
            arguments = listOf(
                navArgument(PHONE_ID_KEY) {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val viewModel = hiltViewModel<AuthViewModel>()
            val arguments = requireNotNull(backStackEntry.arguments)
            OtpScreen(
                navController = navController,
                viewModel = viewModel,
                phoneNumber = arguments.getString(PHONE_ID_KEY, "")
            )
        }
        composable(route = "${LensaScreens.COMMON_ERROR_SCREEN.name}/{$ERROR_TEXT_KEY}",
            arguments = listOf(
                navArgument(ERROR_TEXT_KEY) {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ErrorScreen(
                navController = navController,
                text = arguments.getString(ERROR_TEXT_KEY, "")
            )
        }
        composable(route = LensaScreens.REGISTRATION_ROLE_SELECTOR_SCREEN.name) {
            val viewModel = hiltViewModel<RegistrationViewModel>()
            RegistrationRoleSelectorScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(
            route = "${LensaScreens.REGISTRATION_SCREEN.name}/{$IS_SPECIALIST_KEY}",
            arguments = listOf(
                navArgument(IS_SPECIALIST_KEY) {
                    type = NavType.BoolType
                }
            ),
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val viewModel = hiltViewModel<RegistrationViewModel>()
            RegistrationScreen(
                viewModel = viewModel,
                isSpecialist = arguments.getBoolean(IS_SPECIALIST_KEY)
            )
        }
        composable(route = LensaScreens.FEED_SCREEN.name) {
            val viewModel = hiltViewModel<FeedViewModel>()
            FeedScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(route = LensaScreens.SETTINGS_SCREEN.name) {
            val viewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(route = LensaScreens.PROFILE_SCREEN.name) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(route = "${LensaScreens.SPECIALIST_DETAILS_SCREEN.name}/" +
                "{$PROFILE_UID_KEY}/" +
                "{$IS_SELF_PROFILE_KEY}",
            arguments = listOf(
                navArgument(PROFILE_UID_KEY) {
                    type = NavType.StringType
                },
                navArgument(IS_SELF_PROFILE_KEY) {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val viewModel = hiltViewModel<SpecialistDetailsViewModel>()
            arguments.getString(PROFILE_UID_KEY)?.let {
                SpecialistDetailsScreen(
                    navController = navController,
                    viewModel = viewModel,
                    isSelf = arguments.getBoolean(IS_SELF_PROFILE_KEY),
                    specialistUid =  it,
                )
            }
        }
        composable(route = LensaScreens.FEEDBACK_SCREEN.name) {
            val viewModel = hiltViewModel<SettingsViewModel>()
            FeedbackScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(route = LensaScreens.ABOUT_APP_SCREEN.name) {
            AboutAppScreen(
                navController = navController,
            )
        }
    }
}