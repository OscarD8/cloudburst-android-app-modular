package com.example.cloudburst

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import com.example.cloudburst.ui.CloudburstAppBase
import com.example.ui.theme.CloudburstTheme
import dagger.hilt.android.AndroidEntryPoint
import android.app.Activity

const val TAG = "lifecycleCheck"

/*
 * By this point @HiltAndroidApp in CloudburstApplication.kt has been set up.
 * Therefore the base Application now has Hilt functionality plugged in.
 * At this point, can now build within that framework by creating activities.
 * By using @AndroidEntryPoint the OS links up this Activity creation with
 * the Hilt framework for dependency injection.
 */

/**
 * The main entry point [Activity] for the Cloudburst application.
 *
 * This activity is responsible for setting up the basic application window, enabling edge-to-edge display,
 * calculating the window size class, and hosting the main Jetpack Compose UI content defined
 * in [CloudburstAppBase].
 *
 * It is annotated with `@AndroidEntryPoint` to enable field injection by Hilt for this Activity
 * and allow Hilt to provide dependencies to lifecycle-aware components attached to it (like ViewModels).
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Initializes the activity, sets up the Compose UI, and enables edge-to-edge display.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in [onSaveInstanceState]. Otherwise it is null.
     */
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Log.d(TAG, "setContent called")
            CloudburstTheme {
                val layoutDirection = LocalLayoutDirection.current
                Surface(
                    modifier = Modifier.padding(
                        start = WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(layoutDirection),
                        end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(layoutDirection)
                    )
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    CloudburstAppBase(windowSize.widthSizeClass)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}
