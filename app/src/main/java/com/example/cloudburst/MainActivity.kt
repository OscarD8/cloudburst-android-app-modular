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
import com.example.cloudburst.ui.CloudburstApp
import com.example.ui.theme.CloudburstTheme
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "lifecycleCheck"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                    CloudburstApp(windowSize.widthSizeClass)
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
